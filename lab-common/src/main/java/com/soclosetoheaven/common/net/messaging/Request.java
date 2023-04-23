package com.soclosetoheaven.common.net.messaging;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    @Serial

    private final static long serialVersionUID = 95959559L;

    private final String commandName;

    private final RequestBody requestBody;

    public Request(String commandName, RequestBody requestBody) {
        this.commandName = commandName;
        this.requestBody = requestBody;
    }

    public RequestBody getBody() {
        return requestBody;
    }

    public String getCommandName() {
        return commandName;
    }
}
