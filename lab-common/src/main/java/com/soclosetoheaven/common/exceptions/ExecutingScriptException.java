package com.soclosetoheaven.common.exceptions;

/**
 * exception that is used when something is wrong with executing script
 */
public class ExecutingScriptException extends ManagingException{

    /**
     * @param message exception message
     */

    public ExecutingScriptException(String message) {
        super(message);
    }
}
