/**
 *
 */
package com.jiangli.doc.excel.Exception;

/**
 * @author JiangLi
 *
 *         CreateTime 2014-12-3 上午10:00:00
 */
public class NotValidExcelFieldValueException extends ExcelFieldException {

    /**
     *
     */
    public NotValidExcelFieldValueException() {
        super();

    }

    /**
     * @param message
     * @param rowIdx
     * @param colIdx
     */
    public NotValidExcelFieldValueException(String message, int rowIdx, int colIdx) {
        super(message, rowIdx, colIdx);

    }

    /**
     * @param message
     * @param cause
     */
    public NotValidExcelFieldValueException(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * @param message
     */
    public NotValidExcelFieldValueException(String message) {
        super(message);

    }

    /**
     * @param cause
     */
    public NotValidExcelFieldValueException(Throwable cause) {
        super(cause);

    }

}
