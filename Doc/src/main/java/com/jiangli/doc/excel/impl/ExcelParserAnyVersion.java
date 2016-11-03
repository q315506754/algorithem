package com.jiangli.doc.excel.impl;

import com.jiangli.common.utils.DateUtil;
import com.jiangli.common.utils.SpringUtil;
import com.jiangli.doc.excel.Exception.ExcelParsingException;
import com.jiangli.doc.excel.core.Color;
import com.jiangli.doc.excel.core.Order;
import com.jiangli.doc.excel.enums.CMDType;
import com.jiangli.doc.excel.inf.ExcelToDBParser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/15 0015 9:44
 */
public abstract class ExcelParserAnyVersion implements ExcelToDBParser {
    public static final Map<String, JSONObject> processMemory = new HashMap<String, JSONObject>();
    //    protected  final static short fontColorIndex  = 18;//
    protected final static String FONTCOLORRGB = "FFCCE8CF";//default
    //    protected static int readAtPage = 1;//config 读取excel第几页的账号配置
    protected static String sheetName = "Worksheet";//config 读取excel第几页的账号配置
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final int updateMemoryInterval = 50;//每隔多少次更新一次内存记录
    protected int lastRowNum = -1;//
    protected CMDType cmdType = CMDType.NONE;//
    protected Long checkinDateStart = null;//
    protected Long checkinDateEnd = null;//
    protected boolean redOnlyMode = false;//
    protected boolean modifyDateMode = false;//
    protected boolean notWriteMode = false;//
    protected String processId = String.valueOf(System.currentTimeMillis());//

    @Override
    public void setCMDType(CMDType type) {
        this.cmdType = type;
    }

    @Override
    public String queryParseId() {
        processId = String.valueOf(System.currentTimeMillis());
        return processId;
    }

    @Override
    public void enableNotWriteMode() {
        notWriteMode = true;
    }

    @Override
    public void enableModifyDateMode(String s1, String s2) {
        if (s1 != null && s2 != null) {
            try {
                final long start = DateUtil.getDateYYMMDD(s1);
                final long end = DateUtil.getDateYYMMDD(s2);
                checkinDateStart = start;
                checkinDateEnd = end + TimeUnit.DAYS.toMillis(1);
                modifyDateMode = true;
                logger.debug("modifyDateMode enabled:" + s1 + "~" + s2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void enableRedOnlyMode() {
        redOnlyMode = true;
    }

    protected abstract Iterator<Row> getRowIterator(File excelFile) throws ExcelParsingException;

    protected abstract Color getColors(Cell cell);


    private Order convert(Row curRow) {
        Order order = new Order();
        List<Color> cellColors = new LinkedList<Color>();
        order.setCellColors(cellColors);

        final Cell orderIdCell = curRow.getCell(0);
        cellColors.add(getColors(orderIdCell));

        final Cell inTotalCell = curRow.getCell(16);
        cellColors.add(getColors(inTotalCell));

        final Cell outTotalCell = curRow.getCell(17);
        cellColors.add(getColors(outTotalCell));

        final Cell roomNightCell = curRow.getCell(20);
        cellColors.add(getColors(roomNightCell));

        final Cell entNameCell = curRow.getCell(11);
        cellColors.add(getColors(entNameCell));
        final Cell cusNameCell = curRow.getCell(14);
        cellColors.add(getColors(cusNameCell));
        final Cell liveNameCell = curRow.getCell(25);
        cellColors.add(getColors(liveNameCell));
        final Cell remarkCell = curRow.getCell(13);
        cellColors.add(getColors(remarkCell));

        final String orderId = getStringValue(orderIdCell);
        order.setOrderId(orderId);

        final Double inTotal = getNumericValue(inTotalCell, 2);
        order.setInTotal(inTotal);

        final Double outTotal = getNumericValue(outTotalCell, 2);
        order.setOutTotal(outTotal);

        final Double roomNight = getNumericValue(roomNightCell);
        order.setRoomNight(roomNight);

        order.setEntName(getStringValue(entNameCell));
        order.setCusName(getStringValue(cusNameCell));
        order.setLiveName(getStringValue(liveNameCell));
        order.setRemark(getStringValue(remarkCell));

        return order;
    }

    private Order findOrderModel(String orderId, Connection conn) {
        Order order = new Order();


        try {
            final Statement statement = conn.createStatement();//tatement接口需要通过connection接口进行实例化操作
            final ResultSet resultSet = statement.executeQuery("select * from fact_staging_order where orderId='" + orderId + "'");//执行sql语句，结果集放在result中
            if (resultSet.next()) {
                order.setOrderId(resultSet.getString("orderId"));
                order.setInOnePrice(resultSet.getFloat("inOnePrice"));
                order.setOutOnePrice(resultSet.getFloat("outOnePrice"));
                order.setInTotal(resultSet.getFloat("inTotal"));
                order.setOutTotal(resultSet.getFloat("outTotal"));
                order.setRoomNight(resultSet.getFloat("roomNight"));
                order.setDays(resultSet.getFloat("days"));
                order.setQuantity(resultSet.getFloat("quantity"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return order;
    }

    public boolean hasSpecialColor(List<Color> excelFile) throws ExcelParsingException {
        for (Color color : excelFile) {
            if (!(color.getRgb().equals(FONTCOLORRGB) || (color.getRgb().equals("FF000000")))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject parse(File excelFile) throws ExcelParsingException {
        JSONObject ret = new JSONObject();
        JSONArray arr = new JSONArray();

        if (excelFile == null || !excelFile.exists()) {
            throw new ExcelParsingException("不能对空文件或不存在的文件解析");
        }

        Iterator<Row> rowIterator = getRowIterator(excelFile);
        logger.debug("lastRowNum:" + lastRowNum);


        final Row head = rowIterator.next();

        int count = 1;

        final DataSource dataSource = SpringUtil.getApplicationContext().getBean("dataSource", DataSource.class);
        logger.debug("dataSource:" + dataSource);
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("connection:" + connection);

        while (rowIterator.hasNext()) {
            count++;

            final Row curRow = rowIterator.next();
            final Order update = convert(curRow);
            final List<Color> cellColors = update.getCellColors();
//            update.setCellColors(null);
            final boolean b = hasSpecialColor(cellColors);
            if (!b) {
                continue;
            }

            logger.debug("current row:" + count + " / " + lastRowNum + "-----------------------------------------------");
            logger.debug("update order:" + update);
            final String orderId = update.getOrderId();
            final Order orderDB = findOrderModel(orderId, connection);
            logger.debug("orderDB:" + orderDB);

            final double quantityCaculated = new BigDecimal(update.getRoomNight() / orderDB.getDays()).setScale(1, BigDecimal.ROUND_UP).doubleValue();
            final double inOnePriceCaculated = new BigDecimal(update.getInTotal() / update.getRoomNight()).setScale(2, BigDecimal.ROUND_UP).doubleValue();
            final double outOnePriceCaculated = new BigDecimal(update.getOutTotal() / update.getRoomNight()).setScale(2, BigDecimal.ROUND_UP).doubleValue();

            logger.debug("caculate quantity:" + orderDB.getQuantity() + "---->" + quantityCaculated);
            logger.debug("caculate inOnePrice:" + orderDB.getInOnePrice() + "---->" + inOnePriceCaculated);
            logger.debug("caculate outOnePrice:" + orderDB.getOutOnePrice() + "---->" + outOnePriceCaculated);

            final Order updateFinal = new Order();
            updateFinal.setOrderId(orderId);
            updateFinal.setInTotal(update.getInTotal());
            updateFinal.setOutTotal(update.getOutTotal());
            updateFinal.setRoomNight(update.getRoomNight());
            updateFinal.setRemark(update.getRemark());
            updateFinal.setEntName(update.getEntName());
            updateFinal.setCusName(update.getCusName());
            updateFinal.setLiveName(update.getLiveName());
            updateFinal.setQuantity(quantityCaculated);
            updateFinal.setInOnePrice(inOnePriceCaculated);
            updateFinal.setOutOnePrice(outOnePriceCaculated);
            logger.debug("updateFinal:" + updateFinal);


            //update
            updateOrder(updateFinal, connection);

//            if (count == 20) {
//                break;
//            }


            JSONObject one = new JSONObject();
            one.put("orderId", orderId);
            one.put("row", count);
            one.put("db", orderDB);
            one.put("update", update);
            one.put("updateFinal", updateFinal);
            arr.add(one);
        }

        ret.put("data", arr);
        ret.put("updated", arr.size());
        ret.put("rows", lastRowNum + 1);
        return ret;
    }

    private void updateOrder(Order order, Connection connection) {
        String updateSql = "update fact_staging_order set inTotal=?,outTotal=?,roomNight=?,quantity=?,inOnePrice=?,outOnePrice=?,entName=?,remark=?,cusName=?,liveName=?  where orderid=?";

        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setDouble(1, order.getInTotal());
            preparedStatement.setDouble(2, order.getOutTotal());
            preparedStatement.setDouble(3, order.getRoomNight());
            preparedStatement.setDouble(4, order.getQuantity());
            preparedStatement.setDouble(5, order.getInOnePrice());
            preparedStatement.setDouble(6, order.getOutOnePrice());
            preparedStatement.setString(7, order.getEntName());
            preparedStatement.setString(8, order.getRemark());
            preparedStatement.setString(9, order.getCusName());
            preparedStatement.setString(10, order.getLiveName());

            preparedStatement.setString(11, order.getOrderId());
//            logger.debug("update sql:" + preparedStatement.toString());

            int i = preparedStatement.executeUpdate();
//                logger.debug("update count:" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getStringValue(Cell cell) {
        try {
            return cell.getStringCellValue();
        } catch (Exception e) {
        }

        try {
            return new BigDecimal(cell.getNumericCellValue()).setScale(0, BigDecimal.ROUND_UP).toString();
        } catch (Exception e) {
        }


        return null;
    }

    protected Double getNumericValue(Cell cell) {
        try {
            return new BigDecimal(cell.getStringCellValue()).setScale(0, BigDecimal.ROUND_UP).doubleValue();
        } catch (Exception e) {
        }

        try {
            return new BigDecimal(cell.getNumericCellValue()).setScale(0, BigDecimal.ROUND_UP).doubleValue();
        } catch (Exception e) {
        }
        return null;
    }

    protected Double getNumericValue(Cell cell, int scale) {
        try {
            return new BigDecimal(cell.getStringCellValue()).setScale(scale, BigDecimal.ROUND_UP).doubleValue();
        } catch (Exception e) {
        }

        try {
            return new BigDecimal(cell.getNumericCellValue()).setScale(scale, BigDecimal.ROUND_UP).doubleValue();
        } catch (Exception e) {
        }
        return null;
    }

}
