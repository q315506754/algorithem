package com.jiangli.doc.excel.inf;


import com.jiangli.doc.excel.Exception.ExcelParsingException;

import java.io.File;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/15 0015 9:38
 */
public interface ExcelToModelParser<T> {
    public List<T> parse(File file) throws ExcelParsingException;
}
