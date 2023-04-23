package com.soclosetoheaven.common.net.messaging;

import java.io.Serial;
import java.io.Serializable;

public class Response implements Serializable {
    @Serial
    private final static long serialVersionUID = 9511113;

    private final String description;
    public Response(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
