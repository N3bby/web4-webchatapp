package com.razacx.domain.db.exception;

public class DBEntityDoesNotExistException extends DBException {

    public DBEntityDoesNotExistException() {
    }

    public DBEntityDoesNotExistException(String message) {
        super(message);
    }

    public DBEntityDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBEntityDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public DBEntityDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
