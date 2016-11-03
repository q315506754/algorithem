/**
 *
 */
package com.jiangli.doc.excel.Exception;

/**
 * @author JiangLi
 *
 *         CreateTime 2014-12-3 上午10:00:00
 */
public class ExcelFieldValueMustException extends ExcelFieldException {

    /**
     * @param message
     * @param rowIdx
     * @param colIdx
     */
    public ExcelFieldValueMustException(String message, int rowIdx, int colIdx) {
        super(message, rowIdx, colIdx);
    }


}
