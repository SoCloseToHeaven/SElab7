package com.soclosetoheaven.common.exceptions;

import java.io.Serial;
//server-side exception
public class InvalidRequestException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -3667872;

    public InvalidRequestException(String message) {
        super(message);
    }
}
