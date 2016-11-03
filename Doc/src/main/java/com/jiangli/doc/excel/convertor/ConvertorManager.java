package com.jiangli.doc.excel.convertor;


import com.jiangli.doc.excel.Exception.NoSuchConvertorException;
import com.jiangli.doc.excel.core.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/17 0017 14:10
 */
public class ConvertorManager {
    static final Map<String, FieldToColumnConvertor> convertorList = new HashMap<String, FieldToColumnConvertor>();

    static {
        convertorList.put("autoSign", new AutoSignConvertor());
        convertorList.put("isHld", new isHldConvertor());
        convertorList.put("isDirectSuccess", new isDirectSuccessConvertor());
    }

    public static Object convert(String columnName, Object orgVal, Order excelOrder) throws NoSuchConvertorException {
        if (convertorList.get(columnName) == null) {
            throw new NoSuchConvertorException(columnName);
        }
        return convertorList.get(columnName).convert(orgVal, excelOrder);
    }
}
