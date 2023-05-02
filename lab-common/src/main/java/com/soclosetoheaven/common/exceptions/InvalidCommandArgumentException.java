package com.soclosetoheaven.common.exceptions;

import java.io.Serial;
//client-side exception
public class InvalidCommandArgumentException extends ManagingException{

    @Serial
    private static final long serialVersionUID = -3657872;

    /**
     * default constructor
     */

    public InvalidCommandArgumentException(){
        super("Invalid command arguments");
    }

    /**
     * @param message exception message
     */

    public InvalidCommandArgumentException(String message) {
        super("%s - %s".formatted(message, "Invalid command arguments"));
    }

}
