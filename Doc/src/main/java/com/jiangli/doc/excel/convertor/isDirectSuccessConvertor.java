package com.jiangli.doc.excel.convertor;


import com.jiangli.doc.excel.core.Order;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/17 0017 14:21
 */
public class isDirectSuccessConvertor implements FieldToColumnConvertor {

    @Override
    public Object convert(Object orgVal, Order excelOrder) {
        if ("是".equals(String.valueOf(orgVal))) {
            return "Y";
        }
        if ("否".equals(String.valueOf(orgVal))) {
            return "N";
        }
        return "?";
    }
}
