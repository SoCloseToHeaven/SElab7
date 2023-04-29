package com.soclosetoheaven.common.exceptions;

public class InvalidAccessException extends InvalidRequestException{

    public InvalidAccessException() {
        super("No access to do this operation!");
    }
}
