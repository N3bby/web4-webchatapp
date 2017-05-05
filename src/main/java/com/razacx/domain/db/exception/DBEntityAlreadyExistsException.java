package com.razacx.domain.db.exception;

public class DBEntityAlreadyExistsException extends DBException {

    public DBEntityAlreadyExistsException() {
    }

    public DBEntityAlreadyExistsException(String message) {
        super(message);
    }

    public DBEntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBEntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public DBEntityAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
