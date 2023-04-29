package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.net.factory.RequestFactory;

abstract public class AbstractCommand {

    private final String name;


    public AbstractCommand(String name) {
        this.name = name;
    }
    abstract public Response execute(RequestBody requestBody) throws InvalidRequestException;

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
