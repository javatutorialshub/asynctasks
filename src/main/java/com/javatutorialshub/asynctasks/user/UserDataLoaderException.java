package com.javatutorialshub.asynctasks.user;

public class UserDataLoaderException extends Throwable {
    public UserDataLoaderException() {
    }

    public UserDataLoaderException(String message) {
        super(message);
    }

    public UserDataLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDataLoaderException(Throwable cause) {
        super(cause);
    }

    public UserDataLoaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
