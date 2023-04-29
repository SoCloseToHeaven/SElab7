package com.soclosetoheaven.common.net.factory;

import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.net.messaging.ResponseWithDragon;
import com.soclosetoheaven.common.net.messaging.ResponseWithException;

public class ResponseFactory {

    private ResponseFactory() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public synchronized static Response createResponse(String description) {
        return new Response(description);
    }

    public synchronized static Response createResponseWithException(Exception e) {
        return new ResponseWithException(e);
    }

    public synchronized static Response createResponseWithDragon(String description, Dragon dragon) {
        return new ResponseWithDragon(description, dragon);
    }


}
