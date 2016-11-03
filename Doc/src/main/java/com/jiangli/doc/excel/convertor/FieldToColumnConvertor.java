package com.jiangli.doc.excel.convertor;


import com.jiangli.doc.excel.core.Order;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/17 0017 14:10
 */
public interface FieldToColumnConvertor {
    Object convert(Object orgVal, Order excelOrder);
}
