package com.soclosetoheaven.common.net.messaging;

import com.soclosetoheaven.common.model.Dragon;

public class ResponseWithDragon extends Response{

    private final Dragon dragon;

    public ResponseWithDragon(String message, Dragon dragon) {
        super(message);
        this.dragon = dragon;
    }

    public Dragon getDragon() {
        return dragon;
    }

    @Override
    public String toString() {
        return "%s : %s".formatted(getDescription(), dragon.toString());
    }
}
