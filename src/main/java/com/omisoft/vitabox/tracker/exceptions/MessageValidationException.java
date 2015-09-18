package com.omisoft.vitabox.tracker.exceptions;

/**
 * Created by dido on 9/15/15.
 */
public class MessageValidationException extends Exception {
    private String message;
    public MessageValidationException(String s) {
        message = s;
    }
}
