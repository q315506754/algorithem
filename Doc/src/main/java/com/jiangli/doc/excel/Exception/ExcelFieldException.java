/**
 *
 */
package com.jiangli.doc.excel.Exception;

/**
 * @author JiangLi
 *
 *         CreateTime 2014-12-4 上午11:03:05
 */
public class ExcelFieldException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -4916495503488179888L;
    protected int rowIdx;
    protected int colIdx;

    /**
     *
     */
    public ExcelFieldException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public ExcelFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ExcelFieldException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ExcelFieldException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     */
    public ExcelFieldException(String message, int rowIdx, int colIdx) {
        super(message);
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
    }

}
