package com.razacx.domain.db.exception;

public class DBNotImplementedSpecificationException extends DBException {

    public DBNotImplementedSpecificationException() {
    }

    public DBNotImplementedSpecificationException(String message) {
        super(message);
    }

    public DBNotImplementedSpecificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBNotImplementedSpecificationException(Throwable cause) {
        super(cause);
    }

    public DBNotImplementedSpecificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
