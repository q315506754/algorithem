package com.jiangli.doc.excel.impl;

import com.jiangli.doc.excel.Exception.ExcelParsingException;
import com.jiangli.doc.excel.core.Color;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/15 0015 9:43
 */
public class ExcelParser97_2003 extends ExcelParserAnyVersion {
    private HSSFSheet page = null;
    private HSSFWorkbook workbook = null;


    @Override
    protected Color getColors(Cell cell) {
        Color ret = new Color();
        final CellStyle cellStyle = cell.getCellStyle();
        ret.setFore(cellStyle.getFontIndex());
        ret.setBack(cellStyle.getFontIndex());
        return ret;
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

}
