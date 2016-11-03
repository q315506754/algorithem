/**
 *
 */
package com.jiangli.doc.excel.Exception;

/**
 * @author JiangLi
 *
 *         CreateTime 2014-12-4 上午11:03:05
 */
public class NoSuchConvertorException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -4916495503488179888L;

    /**
     *
     */
    public NoSuchConvertorException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public NoSuchConvertorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public NoSuchConvertorException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public NoSuchConvertorException(Throwable cause) {
        super(cause);
    }


}
