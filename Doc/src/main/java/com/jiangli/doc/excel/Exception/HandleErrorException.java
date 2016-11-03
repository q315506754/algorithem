/**
 *
 */
package com.jiangli.doc.excel.Exception;

/**
 * @author JiangLi
 *
 *         CreateTime 2014-12-4 上午11:03:05
 */
public class HandleErrorException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = -4916495503488179888L;

    /**
     *
     */
    public HandleErrorException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public HandleErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public HandleErrorException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public HandleErrorException(Throwable cause) {
        super(cause);
    }


}
