package com.soclosetoheaven.common.net.messaging;

import java.io.Serial;
import java.io.Serializable;

public class RequestBody implements Serializable {
    @Serial
    private final static long serialVersionUID = 959595590L;

    private final String[] args;
    public RequestBody(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }
}
