/**
 *
 */
package com.jiangli.doc.excel.Exception;

/**
 * @author JiangLi
 *
 *         CreateTime 2014-12-3 上午10:00:00
 */
public class UnkownExcelVersionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -2023707452438060060L;

    /**
     *
     */
    public UnkownExcelVersionException() {
        super();

    }

    /**
     * @param message
     * @param cause
     */
    public UnkownExcelVersionException(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * @param message
     */
    public UnkownExcelVersionException(String message) {
        super(message);

    }

    /**
     * @param cause
     */
    public UnkownExcelVersionException(Throwable cause) {
        super(cause);

    }


}
