package com.soclosetoheaven.common.net.factorн;

import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.net.messaging.ResponseWithException;

public class ResponseFactory {

    private ResponseFactory() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static Response createResponse(String description) {
        return new Response(description);
    }

    public static Response createResponseWithException(Exception e) {
        return new ResponseWithException(e);
    }


}
