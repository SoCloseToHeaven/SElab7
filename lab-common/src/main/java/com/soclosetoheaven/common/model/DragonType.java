package com.soclosetoheaven.common.model;


import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;

import java.io.Serial;
import java.io.Serializable;

public enum DragonType implements Serializable {
    WATER,

    UNDERGROUND,

    AIR,

    FIRE;

    @Serial
    private static final long serialVersionUID = -551936780166L;


    private static final String POSITIVE_NUMBER_PATTERN = "[1-9]\\d*";

    private static final int NUMBER_OFFSET = 1;


    public static DragonType parseDragonType(String line) {
        for (DragonType type : DragonType.values()) {
            if (type.toString().equals(line.toUpperCase()) ||
                    (line.matches(POSITIVE_NUMBER_PATTERN) && Integer.parseInt(line) == (type.ordinal() + NUMBER_OFFSET)))
                return type;
        }
        return null;
    }

    /**
     * @return all DragonType.values() in one line
     */

    public static String stringValues() {
        StringBuilder line = new StringBuilder();
        for (DragonType type : DragonType.values()) {
            line.append("%s ".formatted(type.toString()));
        }
        return line.toString().trim();
    }
}
