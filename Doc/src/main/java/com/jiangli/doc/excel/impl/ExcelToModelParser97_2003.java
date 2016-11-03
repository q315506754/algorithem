package com.jiangli.doc.excel.impl;

import com.jiangli.doc.excel.Exception.ExcelParsingException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

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
public class ExcelToModelParser97_2003<T> extends ExcelToModelParserForAnyVersion<T> {
    private HSSFSheet page = null;
    private HSSFWorkbook workbook = null;

    public ExcelToModelParser97_2003(String sheetName, Class<T> modelCls) {
        super(sheetName, modelCls);
    }

    @Override
    protected Iterator<Row> getRowIterator(File excelFile) throws ExcelParsingException {
        HSSFWorkbook workbook = getWorkBookFromFile(excelFile);
        if (workbook == null) {
            throw new ExcelParsingException("不能为文件" + excelFile.getName() + "创建工作对象");
        }

        HSSFSheet page1 = getSheetFromWorkBook(workbook);
        if (page1 == null) {
            throw new ExcelParsingException("不能读取工作薄的" + sheetName + "");
        }
        lastRowNum = page1.getLastRowNum() + 1;
        return page1.rowIterator();
    }

    /**
     * @param workbook
     *
     * @return HSSFSheet
     *
     * @author JiangLi
     * CreateTime 2014-12-3 上午11:03:13
     */
    private HSSFSheet getSheetFromWorkBook(HSSFWorkbook workbook) {
        try {
            page = workbook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    /**
     * @param excelFile
     *
     * @return HSSFWorkbook
     *
     * @author JiangLi
     * CreateTime 2014-12-3 上午11:02:23
     */
    private HSSFWorkbook getWorkBookFromFile(File excelFile) {
        try {
            workbook = new HSSFWorkbook(new FileInputStream(excelFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
}
