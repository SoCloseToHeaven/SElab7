package com.soclosetoheaven.common.net.messaging;


import com.soclosetoheaven.common.model.Dragon;

import java.io.Serial;

public class RequestBodyWithDragon extends RequestBody {
    @Serial
    private final static long serialVersionUID = 9595955901L;
    private final Dragon dragon;
    public RequestBodyWithDragon(String[] args, Dragon dragon) {
        super(args);
        this.dragon = dragon;
    }

    public Dragon getDragon() {
        return dragon;
    }
}
