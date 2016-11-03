package com.jiangli.doc.excel.impl;

import com.jiangli.doc.excel.Exception.ExcelParsingException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/5 0005 15:19
 */
public class ExcelToModelParser2007<T> extends ExcelToModelParserForAnyVersion<T> {
    public ExcelToModelParser2007(String sheetName, Class<T> modelCls) {
        super(sheetName, modelCls);
    }

    protected Iterator<Row> getRowIterator(File excelFile) throws ExcelParsingException {
        XSSFWorkbook workbook = getWorkBookFromFile(excelFile);
        if (workbook == null) {
            throw new ExcelParsingException("不能为文件" + excelFile.getName() + "创建工作对象");
        }

        XSSFSheet page1 = getSheetFromWorkBook(workbook);
        if (page1 == null) {
            throw new ExcelParsingException("不能读取工作薄的" + sheetName + "");
        }

        lastRowNum = page1.getLastRowNum() + 1;
        return page1.rowIterator();
    }

    private XSSFSheet getSheetFromWorkBook(XSSFWorkbook workbook) {

        try {
            page = workbook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    private XSSFWorkbook getWorkBookFromFile(File excelFile) {
        try {
            workbook = new XSSFWorkbook(new FileInputStream(excelFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
}
