package com.soclosetoheaven.common.model.factory;

import com.soclosetoheaven.common.model.DragonType;
import com.soclosetoheaven.common.io.BasicIO;

import com.soclosetoheaven.common.util.TerminalColors;

public class DragonTypeFactory {

    private DragonTypeFactory() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static DragonType createDragonType(BasicIO io) {
        return inputDragonType(io);
    }

    private static DragonType inputDragonType(BasicIO io) {
        try {
            return DragonType.parseDragonType(io.stdRead(
                    "%s(%s): ".formatted("Type dragon's type", DragonType.stringValues())));
        } catch (UnsupportedOperationException e) {
            io.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputDragonType(io);
        }
    }
}
