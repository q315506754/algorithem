package com.jiangli.doc.excel.impl;

import com.jiangli.common.utils.DateUtil;
import com.jiangli.common.utils.SpringUtil;
import com.jiangli.common.utils.TimeUtil;
import com.jiangli.doc.excel.Exception.ExcelParsingException;
import com.jiangli.doc.excel.Exception.HandleErrorException;
import com.jiangli.doc.excel.Exception.HandleInterruptException;
import com.jiangli.doc.excel.anno.FromExcel;
import com.jiangli.doc.excel.anno.ToSql;
import com.jiangli.doc.excel.convertor.ConvertorManager;
import com.jiangli.doc.excel.core.ExcelMetaInfo;
import com.jiangli.doc.excel.core.Order;
import com.jiangli.doc.excel.enums.CMDType;
import com.jiangli.doc.excel.enums.ExcelValType;
import com.jiangli.doc.excel.enums.SqlType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import javax.sql.DataSource;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/17 0017 11:28
 */
public abstract class ORMExcelParserAnyVersion extends ExcelParserAnyVersion {
    private List<String> titleIdxMap = new LinkedList<String>();
    private Map<String, ExcelMetaInfo> metaExcelMap = new HashMap<String, ExcelMetaInfo>();//excelName -> order.field
    private List<Field> metaSqlList = new LinkedList<Field>();//
    private Set<String> insertedOrderIds = new HashSet<String>();

    protected void parseHeadMap(Row head) {
        final Iterator<Cell> cellIterator = head.cellIterator();
        while (cellIterator.hasNext()) {
            final Cell next = cellIterator.next();
            titleIdxMap.add(getStringValue(next));
        }

        logger.debug("parseHeadMap over..." + titleIdxMap);
    }

    protected Order parseRowToOrder(Row row) throws ExcelParsingException {
        Order ret = new Order();
        parseRowToObject(row, ret);
        return ret;
    }

    protected void infoNoSuchExcelMetaInfo(String errorMsg) {
        logger.warn(errorMsg);
    }

    protected void parseRowToObject(Row row, Object ret) throws ExcelParsingException {
        int n = 0;
        for (String title : titleIdxMap) {
            final Cell cell = row.getCell(n++);
            final ExcelMetaInfo excelMetaInfo = metaExcelMap.get(title);
            if (excelMetaInfo == null) {
                final String errorMsg = title + "的元配置无法找到";
//                throw new ExcelParsingException(errorMsg);
                infoNoSuchExcelMetaInfo(errorMsg);
                continue;
            }
            final Field field = excelMetaInfo.field;
            final FromExcel fromExcel = excelMetaInfo.fromExcel;

            Object val = parseCellValue(cell, fromExcel);

            try {
                field.setAccessible(true);
                field.set(ret, val);
            } catch (Exception e) {
//                e.printStackTrace();
            }

        }

    }

    private Object parseCellValue(Cell cell, FromExcel fromExcel) throws ExcelParsingException {
        Object val = null;
        if (fromExcel.valType() == ExcelValType.STRING) {
            val = getStringValue(cell);
        }
        if (fromExcel.valType() == ExcelValType.INT) {
            val = getNumericValue(cell, 0);
        }
        if (fromExcel.valType() == ExcelValType.MONEY) {
            val = getNumericValue(cell, 2);
        }
        if (fromExcel.valType() == ExcelValType.DATEYMD) {
            try {
                final Date dateCellValue = cell.getDateCellValue();
                val = DateUtil.getDate_YYYYMMDD(dateCellValue.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExcelParsingException(ExcelValType.DATEYMD + " " + fromExcel.columnName() + "不能解析该Cell数据!");
            }

        }
        if (fromExcel.valType() == ExcelValType.DATEHMS) {
            try {
                final Date dateCellValue = cell.getDateCellValue();
                val = DateUtil.getDate_HMS(dateCellValue.getTime());
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExcelParsingException(ExcelValType.DATEYMD + " " + fromExcel.columnName() + "不能解析该Cell数据!");
            }

        }
        return val;
    }

    @Override
    public JSONObject parse(File excelFile) throws ExcelParsingException {
        JSONObject ret = new JSONObject();
        JSONArray arr = new JSONArray();
        insertedOrderIds.clear();

        if (excelFile == null || !excelFile.exists()) {
            throw new ExcelParsingException("不能对空文件或不存在的文件解析");
        }

        Iterator<Row> rowIterator = getRowIterator(excelFile);
        logger.debug("lastRowNum:" + lastRowNum);

        //meta
        parseMetaInfo(Order.class);

        final Row head = rowIterator.next();
        parseHeadMap(head);


        final DataSource dataSource = SpringUtil.getApplicationContext().getBean("dataSource", DataSource.class);
        logger.debug("dataSource:" + dataSource);
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("connection:" + connection);

        int count = 1;
        long startTs = System.currentTimeMillis();

        while (rowIterator.hasNext()) {
            count++;

            logger.debug("current row:" + count + " / " + lastRowNum + "-----------------------------------------------");

            if (canRecord(count)) {
                long curCost = System.currentTimeMillis() - startTs;
                if (curCost < 1) {
                    curCost = 1;
                }
                double speed = curCost / count;
                String speedInverseStr = new BigDecimal(count * 1000.0 / curCost).setScale(4, BigDecimal.ROUND_UP).toString();
                double totalCost = lastRowNum * speed;
                double restCost = totalCost - curCost;
                JSONObject rec = new JSONObject();
                rec.put("finish", false);
                rec.put("cur", count);
                rec.put("total", lastRowNum);
                rec.put("speed", TimeUtil.getSpeedCNString(speed) + "秒/条");
                rec.put("speedInverseStr", speedInverseStr + "条/秒");
                rec.put("totalCost", TimeUtil.getCNString(totalCost));
                rec.put("curCost", TimeUtil.getCNString(curCost));
                rec.put("restCost", TimeUtil.getCNString(restCost));
                logger.debug("[rec]" + rec.toString());
                ExcelParserAnyVersion.processMemory.put(processId, rec);
            }


            try {
                final Row curRow = rowIterator.next();
                final Order excelOrder = parseRowToOrder(curRow);


                //special
                excelOrder.setOrderTime(excelOrder.getOrderDate() + " " + excelOrder.getOrderTime());
                excelOrder.setCreateTime(excelOrder.getOrderDate());

                //query
                queryPropId(excelOrder, connection);

                //caculate
                caculateDays(excelOrder);

                //修正roomNight、inTOtal、outTotal
                //必须在caculateDays 之后
                modifyDateProcess(excelOrder);


                //caculate
                caculateQuantity(excelOrder);
                caculateOnePrice(excelOrder);

                logger.debug("excelOrder:" + excelOrder);


                handleReuqest(cmdType, excelOrder, ret, connection);
            } catch (HandleErrorException e) {
                e.printStackTrace();
                break;
            } catch (HandleInterruptException e) {
                JSONObject error = new JSONObject();
                error.put("msg", e.getMessage());
                error.put("row", count);
                error.put("e", e.getClass().getCanonicalName());
                arr.add(error);
                continue;
            } catch (ExcelParsingException e) {
                JSONObject error = new JSONObject();
                error.put("msg", e.getMessage());
                error.put("row", count);
                error.put("e", e.getClass().getCanonicalName());
                arr.add(error);
                continue;
            }

        }


        JSONObject rec = new JSONObject();
        rec.put("finish", true);
        rec.put("cur", lastRowNum);
        rec.put("total", lastRowNum);
        rec.put("totalCost", TimeUtil.getCNString(System.currentTimeMillis() - startTs));
        rec.put("errors", arr);
        ExcelParserAnyVersion.processMemory.put(processId, rec);

        logger.debug("[rec]" + rec.toString());

        ret.put("errors", arr);
        return ret;
    }

    protected boolean canRecord(int count) {
        return count == lastRowNum || count % updateMemoryInterval == 0;
    }

    private void modifyDateProcess(Order excelOrder) {
        if (modifyDateMode) {
            logger.debug("modifyDateMode enabled ...start modifyDateProcess");
            try {
                final String checkinDate = excelOrder.getCheckinDate();
                final String checkoutDate = excelOrder.getCheckoutDate();
                final long lStart = DateUtil.getDateYYMMDD(checkinDate);
                final long lEnd = DateUtil.getDateYYMMDD(checkoutDate);
                long biStart = lStart;
                long biEnd = lEnd;
                boolean crossMonth = false;
                if (lStart < checkinDateStart) {
                    biStart = checkinDateStart;
                    crossMonth = true;
                }
                if (lEnd > (checkinDateEnd)) {
                    biEnd = checkinDateEnd;
                    crossMonth = true;
                }
                if (crossMonth) {
                    logger.debug("crossMonth...." + excelOrder.getOrderId());
                    int BIdays = (int) ((biEnd - biStart) / TimeUnit.DAYS.toMillis(1));
                    logger.debug("BIdays:" + BIdays);

                    if (BIdays == 0) {
                        throw new HandleInterruptException(excelOrder.getOrderId() + " BIdays 0 exceptiion!!!");
                    }

                    //说明订单和修正区间没有交集
                    if (BIdays < 0) {
                        logger.error("wrong startDate,endDate" + checkinDate + "," + checkoutDate + " for modifyDate");
                        return;
                    }

                    final double realDays = excelOrder.getDays();
                    logger.debug("real days:" + realDays);

                    double rate = realDays / BIdays;

                    excelOrder.setRoomNight(excelOrder.getRoomNight() * rate);
                    excelOrder.setInTotal(excelOrder.getInTotal() * rate);
                    excelOrder.setOutTotal(excelOrder.getOutTotal() * rate);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void caculateOnePrice(Order excelOrder) {
        try {
            final double inTotal = excelOrder.getInTotal();
            final double outTotal = excelOrder.getOutTotal();
            final double roomNight = excelOrder.getRoomNight();

            double inOnePrice = 0d;
            if (roomNight != 0) {
                inOnePrice = inTotal / roomNight;
            }

            double outOnePrice = 0d;
            if (roomNight != 0) {
                outOnePrice = outTotal / roomNight;
            }

            excelOrder.setInOnePrice(inOnePrice);
            excelOrder.setOutOnePrice(outOnePrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void caculateQuantity(Order excelOrder) {
        try {
            double days = excelOrder.getDays();
            final double roomNight = excelOrder.getRoomNight();
            double quantity = 0d;
            if (days != 0) {
                quantity = roomNight / days;
            }
            excelOrder.setQuantity(quantity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void caculateDays(Order excelOrder) {
        try {
            final String checkinDate = excelOrder.getCheckinDate();
            final String checkoutDate = excelOrder.getCheckoutDate();
            final Date d1 = DateUtil.getDate_yyyy_MM_dd(checkinDate);
            final Date d2 = DateUtil.getDate_yyyy_MM_dd(checkoutDate);

            double days = (double) ((d2.getTime() - d1.getTime()) / TimeUnit.DAYS.toMillis(1));
            excelOrder.setDays(days);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void queryPropId(Order excelOrder, Connection connection) {
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("select id from staging_hotel where name = ?");
            preparedStatement.setString(1, excelOrder.getHotel());
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final String propId = resultSet.getString("id");
                excelOrder.setPropId(propId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void handleReuqest(CMDType cmdType, Order excelOrder, JSONObject ret, Connection connection) throws HandleErrorException, HandleInterruptException {
        if (cmdType == CMDType.NONE) {
            logger.debug("CMDType.NONE:no command provided!");
        }

        if (cmdType == CMDType.INSERT) {
            logger.debug("CMDType.INSERT:activated!");


//            excelOrder
            StringBuilder sql = new StringBuilder();
            sql.append("insert into fact_staging_order(");

            List<ValueAndType> values = new LinkedList<ValueAndType>();

            final Field[] declaredFields = excelOrder.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(ToSql.class)) {
                    final ToSql annotation = declaredField.getAnnotation(ToSql.class);
                    sql.append(annotation.columnName());
                    sql.append(",");

                    Object val = null;
                    try {
                        declaredField.setAccessible(true);
                        val = declaredField.get(excelOrder);

                        //convert
                        if (annotation.dataConvert()) {
                            final Object convert = ConvertorManager.convert(annotation.columnName(), val, excelOrder);
                            logger.debug("field " + declaredField.getName() + " value " + "from " + val + " -> " + convert);
                            val = convert;
                        }
                    } catch (Exception e) {
                        throw new HandleErrorException(e);
                    }

                    values.add(new ValueAndType(val, annotation.valType(), declaredField.getName()));
                }
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(") values (");

            for (ValueAndType value : values) {
                sql.append("?,");
            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");


            logger.debug("sql:");
            logger.debug(sql.toString());

            int n = 1;

            try {
                final PreparedStatement pstmt = connection.prepareStatement(sql.toString());
                for (ValueAndType value : values) {
                    setPstmt(pstmt, n++, value);
                }

                if (notWriteMode) {
                    logger.warn("[WARN]notWriteMode enabled...");
                } else {
                    final String orderId = excelOrder.getOrderId();
                    if (insertedOrderIds.contains(orderId)) {
                        logger.warn("[WARN]insert duplicate order...for:" + orderId);
                    } else {
                        logger.warn("start delete...");
                        //delete before insert
                        final PreparedStatement deletePstmt = connection.prepareStatement("delete from fact_staging_order where orderid = ?");
                        deletePstmt.setString(1, orderId);
                        deletePstmt.execute();
                    }

                    logger.warn("start insert...");
                    //then insert
                    final boolean execute = pstmt.execute();

                    insertedOrderIds.add(orderId);
                }


            } catch (Exception e) {
                final ValueAndType valueAndType = values.get(n - 2);
                logger.error("【ERROR】cur fieldName:" + valueAndType.fieldName + " val:" + valueAndType.value + " sqlType:" + valueAndType.type);
                throw new HandleErrorException(e);
            }

        }

        if (cmdType == CMDType.UPDATE) {
            logger.debug("CMDType.UPDATE:activated!");
        }

    }

    private void setPstmt(PreparedStatement pstmt, int i, ValueAndType value) throws SQLException {
        if (value.type == SqlType.VARCHAR) {
            pstmt.setString(i, value.value == null ? "" : value.value.toString());
        }
        if (value.type == SqlType.INT) {
            pstmt.setInt(i, (Integer) (value.value));
        }
        if (value.type == SqlType.FLOAT) {
            pstmt.setDouble(i, (Double) (value.value));
        }
        if (value.type == SqlType.DATETIME) {
            pstmt.setString(i, String.valueOf(value.value));
        }
    }

    protected void parseMetaInfo(Class orderClass) {
        final Field[] declaredFields = orderClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(FromExcel.class)) {
                final FromExcel annotation = declaredField.getAnnotation(FromExcel.class);
                ExcelMetaInfo one = new ExcelMetaInfo();
                one.field = declaredField;
                one.fromExcel = annotation;

                metaExcelMap.put(annotation.columnName(), one);
            }
//            if (declaredField.isAnnotationPresent()) {
//
//            }
        }

        logger.debug("parseMetaInfo over...");
        logger.debug("metaExcelMap:" + metaExcelMap.keySet());
    }

    class ValueAndType {
        Object value;
        SqlType type;
        String fieldName;

        public ValueAndType(Object value, SqlType type, String name) {
            this.value = value;
            this.type = type;
            this.fieldName = name;
        }
    }
}
