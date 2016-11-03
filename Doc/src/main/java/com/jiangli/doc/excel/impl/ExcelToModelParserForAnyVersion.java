package com.jiangli.doc.excel.impl;

import com.jiangli.common.utils.DateUtil;
import com.jiangli.doc.excel.Exception.ExcelParsingException;
import com.jiangli.doc.excel.anno.FromExcel;
import com.jiangli.doc.excel.core.ExcelMetaInfo;
import com.jiangli.doc.excel.enums.ExcelValType;
import com.jiangli.doc.excel.inf.ExcelToModelParser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/5 0005 15:13
 */
public abstract class ExcelToModelParserForAnyVersion<T> implements ExcelToModelParser<T> {


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final String sheetName;//config 读取excel第几页的账号配置
    protected final Class<T> modelCls;
    protected XSSFSheet page = null;
    protected XSSFWorkbook workbook = null;
    protected int lastRowNum = -1;//
    private List<String> titleIdxMap = new LinkedList<String>();
    private Map<String, ExcelMetaInfo> metaExcelMap = new HashMap<String, ExcelMetaInfo>();//excelName -> order.field

    public ExcelToModelParserForAnyVersion(String sheetName, Class<T> modelCls) {
        this.sheetName = sheetName;
        this.modelCls = modelCls;
    }

    protected abstract Iterator<Row> getRowIterator(File excelFile) throws ExcelParsingException;

    @Override
    public List<T> parse(File excelFile) throws ExcelParsingException {
        List<T> ret = new LinkedList<T>();

        if (excelFile == null || !excelFile.exists()) {
            throw new ExcelParsingException("不能对空文件或不存在的文件解析");
        }

        Iterator<Row> rowIterator = getRowIterator(excelFile);
        logger.debug("lastRowNum:" + lastRowNum);

        //meta
        parseMetaInfo();

        final Row head = rowIterator.next();
        parseHeadMap(head);

        int count = 1;

        while (rowIterator.hasNext()) {
            count++;

            logger.debug("current row:" + count + " / " + lastRowNum + "-----------------------------------------------");

            final Row curRow = rowIterator.next();
            final T propModel = parseRowToPropModel(curRow);

            ret.add(propModel);

//            System.out.println(propModel);
        }
        return ret;
    }

    protected void parseHeadMap(Row head) {
        final Iterator<Cell> cellIterator = head.cellIterator();
        while (cellIterator.hasNext()) {
            final Cell next = cellIterator.next();
            titleIdxMap.add(getStringValue(next));
        }

        logger.debug("parseHeadMap over..." + titleIdxMap);
    }

    protected void parseMetaInfo() {
        final Field[] declaredFields = modelCls.getDeclaredFields();
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

    protected T parseRowToPropModel(Row row) throws ExcelParsingException {
        try {
            T ret = modelCls.newInstance();
            parseRowToObject(row, ret);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void parseRowToObject(Row row, Object ret) throws ExcelParsingException {
        int n = 0;
        for (String title : titleIdxMap) {
            final Cell cell = row.getCell(n++);
            final ExcelMetaInfo excelMetaInfo = metaExcelMap.get(title);
            if (excelMetaInfo == null) {
                final String errorMsg = title + "的元配置无法找到";
//                throw new ExcelParsingException(errorMsg);
                logger.warn(errorMsg);
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
