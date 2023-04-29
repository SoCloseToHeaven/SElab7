package com.soclosetoheaven.common.net.factory;

import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Request;

public class RequestFactory {

    private RequestFactory() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static Request createRequest(String commandName, String[] args){
        return new Request(commandName, RequestBodyFactory.createRequestBody(args));
    }

    public static Request createRequestWithDragon(String commandName, String[] args, BasicIO io) {
        return new Request(commandName, RequestBodyFactory.createRequestBodyWithDragon(args, io));
    }


}
