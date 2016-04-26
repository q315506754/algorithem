package com.jiangli.common.exception;

import net.sf.json.JSONObject;

/**
 * Service层公用的Exception.
 *
 * @author zhaoxiang
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(JSONObject errorObj) {
        super(errorObj.toString());
    }
}
