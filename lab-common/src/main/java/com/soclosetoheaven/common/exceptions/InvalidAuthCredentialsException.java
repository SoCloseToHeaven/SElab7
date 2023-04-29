package com.soclosetoheaven.common.exceptions;

public class InvalidAuthCredentialsException extends InvalidRequestException{

    public InvalidAuthCredentialsException() {
        super("Invalid auth credentials!");
    }
}
