/**
 *
 */
package com.jiangli.doc.excel.Exception;

/**
 * @author JiangLi
 *
 *         CreateTime 2014-12-3 上午10:00:00
 */
public class UnkownExcelSuffixException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -2023707452438060060L;

    /**
     *
     */
    public UnkownExcelSuffixException() {
        super();

    }

    /**
     * @param message
     * @param cause
     */
    public UnkownExcelSuffixException(String message, Throwable cause) {
        super(message, cause);

    }

    /**
     * @param message
     */
    public UnkownExcelSuffixException(String message) {
        super(message);

    }

    /**
     * @param cause
     */
    public UnkownExcelSuffixException(Throwable cause) {
        super(cause);

    }


}
