package com.soclosetoheaven.common.model.factory;

import com.soclosetoheaven.common.model.Coordinates;
import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.util.TerminalColors;

public class CoordinatesFactory {

    private CoordinatesFactory() {
    }

    public static Coordinates createCoordinates(BasicIO io) {
        return new Coordinates(inputX(io), inputY(io));
    }


    private static Integer inputX(BasicIO io) {
        try {
            Integer x = Integer.parseInt(io.stdReadLineWithNull("Type coordinate X: "));
            Coordinates.VALIDATOR.validateX(x);
            return x;
        } catch (NumberFormatException | InvalidFieldValueException e) {
            io.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputX(io);
        }
    }

    private static double inputY(BasicIO io) {
        try {
            double y = Double.parseDouble(io.stdRead("Type coordinate Y: "));
            Coordinates.VALIDATOR.validateY(y);
            return y;
        } catch (NumberFormatException | InvalidFieldValueException e) {
            io.writeln(TerminalColors.setColor(e.getMessage(), TerminalColors.RED));
            return inputY(io);
        }
    }
}
