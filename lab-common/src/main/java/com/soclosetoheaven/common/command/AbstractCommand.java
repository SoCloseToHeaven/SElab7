package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.net.factory.RequestFactory;

public abstract class AbstractCommand {

    private final String name;

    public static final int FIRST_ARG = 0;

    public static final int MIN_ARGS_SIZE = 1;


    public AbstractCommand(String name) {
        this.name = name;
    }
    public abstract Response execute(RequestBody requestBody) throws ManagingException;

    public Request toRequest(String[] args) throws ManagingException {
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
