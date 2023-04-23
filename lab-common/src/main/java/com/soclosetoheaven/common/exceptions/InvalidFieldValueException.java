package com.soclosetoheaven.common.exceptions;

/**
 * used when field value in class is incorrect
 */
public class InvalidFieldValueException extends RuntimeException {

    /**
     * @param message exception message
     */
    public InvalidFieldValueException(String message) {
        super(message);
    }
}
