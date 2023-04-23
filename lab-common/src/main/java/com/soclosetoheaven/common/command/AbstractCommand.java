package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.net.factorн.RequestFactory;

abstract public class AbstractCommand {

    private final String name;

    public AbstractCommand(String name) {
        this.name = name;
    }
    abstract public Response execute(RequestBody requestBody);

    public Request toRequest(String[] args) {
        return RequestFactory.createRequest(getName(), args);
    }

    public String getName() {
        return name;
    }

    abstract String getUsage();

    @Override
    public String toString() {
        return getName();
    }
}
