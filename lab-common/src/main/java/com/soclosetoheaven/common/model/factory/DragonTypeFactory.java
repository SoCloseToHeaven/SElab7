package com.soclosetoheaven.common.model.factory;

import com.soclosetoheaven.common.model.DragonType;
import com.soclosetoheaven.common.io.BasicIO;


public class DragonTypeFactory {

    private DragonTypeFactory() {
    }

    public static DragonType createDragonType(BasicIO io) {
        return inputDragonType(io);
    }

    private static DragonType inputDragonType(BasicIO io) {
        String inputLine = io.stdRead(
                "%s(%s): ".formatted("Type dragon's type", DragonType.stringValues()));
        DragonType type = DragonType.parseDragonType(inputLine);
        if (type == null) {
            io.writeln("%s - %s".formatted(inputLine, "can't be converted to DragonType"));
            return inputDragonType(io);
        }
        return type;
    }
}
