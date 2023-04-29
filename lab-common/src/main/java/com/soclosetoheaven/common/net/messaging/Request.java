package com.soclosetoheaven.common.net.messaging;

import com.soclosetoheaven.common.net.auth.AuthCredentials;

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

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setAuthCredentials(AuthCredentials auth) {
        this.requestBody.setAuthCredentials(auth);
    }

    public AuthCredentials getAuthCredentials() {
        return this.requestBody.getAuthCredentials();
    }
}
