package com.soclosetoheaven.common.net.messaging;

import com.soclosetoheaven.common.net.auth.AuthCredentials;

import java.io.Serial;
import java.io.Serializable;

public class RequestBody implements Serializable {
    @Serial
    private final static long serialVersionUID = 959595590L;

    private final String[] args;

    private AuthCredentials authCredentials;

    public RequestBody(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public void setAuthCredentials(AuthCredentials authCredentials) {
        this.authCredentials = authCredentials;
    }

    public AuthCredentials getAuthCredentials() {
        return this.authCredentials;
    }
}
