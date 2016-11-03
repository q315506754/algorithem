package com.jiangli.doc.excel.inf;

import com.jiangli.doc.excel.Exception.ExcelParsingException;
import net.sf.json.JSONObject;

import java.io.File;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/15 0015 9:38
 */
public interface ExcelParser {
    public JSONObject parse(File file) throws ExcelParsingException;
}
