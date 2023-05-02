package com.soclosetoheaven.common.model.factory;

import com.soclosetoheaven.common.model.DragonCave;
import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.util.TerminalColors;

public class DragonCaveFactory {

    private DragonCaveFactory() {
    }

    public static DragonCave createDragonCave(BasicIO io) {
        return new DragonCave(inputDepth(io), inputNumberOfTreasures(io));
    }

    private static long inputDepth(BasicIO io) {
        try {
            return Long.parseLong(io.stdRead("Type cave's depth: "));
        } catch (NumberFormatException e) {
            io.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputDepth(io);
        }
    }

    private static int inputNumberOfTreasures(BasicIO io) {
        try {
            int numberOfTreasures = Integer.parseInt(io.stdRead("Type number of treasures: "));//CaveNumberOfTreasures
            DragonCave.VALIDATOR.validateNumberOfTreasures(numberOfTreasures);
            return numberOfTreasures;
        } catch (NumberFormatException | InvalidFieldValueException e) {
            io.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputNumberOfTreasures(io);
        }
    }
}
