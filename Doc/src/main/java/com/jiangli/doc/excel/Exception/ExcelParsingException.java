/**
 *
 */
package com.jiangli.doc.excel.Exception;

/**
 * @author JiangLi
 *
 *         CreateTime 2014-12-3 上午10:00:00
 */
public class ExcelParsingException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -2023707452438060060L;

    /**
     *
     */
    public ExcelParsingException() {
        super();

    }

    /**
     * @param message
     * @param cause
     */
    public ExcelParsingException(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * @param message
     */
    public ExcelParsingException(String message) {
        super(message);

    }

    /**
     * @param cause
     */
    public ExcelParsingException(Throwable cause) {
        super(cause);

    }


}
