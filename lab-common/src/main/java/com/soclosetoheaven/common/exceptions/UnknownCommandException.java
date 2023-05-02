package com.soclosetoheaven.common.exceptions;

/**
 * Used when command manager can't find command
 */
public class UnknownCommandException extends ManagingException {

    /**
     * @param name command's name
     */

    public UnknownCommandException(String name) {
        super(
                "%s: %s - %s".formatted(
                        "There is no command with name",
                        name,
                        "Type {help} to get list of commands"
                )
        );
    }
}
