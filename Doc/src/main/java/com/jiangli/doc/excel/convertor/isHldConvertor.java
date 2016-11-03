package com.jiangli.doc.excel.convertor;


import com.jiangli.doc.excel.core.Order;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/17 0017 14:18
 */
public class isHldConvertor implements FieldToColumnConvertor {

    @Override
    public Object convert(Object orgVal, Order excelOrder) {
        if ("是".equals(String.valueOf(orgVal))) {
            return 1;
        }
        if ("否".equals(String.valueOf(orgVal))) {
            return 0;
        }
        return -1;
    }

}
