package com.soclosetoheaven.common.model;


import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;
import com.soclosetoheaven.common.util.AbstractValidator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class DragonCave implements Serializable {
    /**
     * default validator
     */
    @Serial
    private static final long serialVersionUID = -55588780166L;

    public static final Validator VALIDATOR = new Validator();

    private long depth;

    private int numberOfTreasures;

    /**
     * creates new object of class and validates it via {@link #VALIDATOR#validate()}
     * @param startDepth value to validate
     * @param startNumberOfTreasures value have to be greater than {@link #VALIDATOR#MIN_NUMBER_OF_TREASURES}
     */

    public DragonCave(long startDepth, int startNumberOfTreasures) {
        this.depth = startDepth;
        this.numberOfTreasures = startNumberOfTreasures;
        VALIDATOR.validate(this);
    }

    /**
     * update fields with new values, validates them before via {@link #VALIDATOR#validate()}
     * @param depth value to update
     * @param numberOfTreasures value to update
     */
    public void setCave(long depth, int numberOfTreasures) {
        VALIDATOR.validateNumberOfTreasures(numberOfTreasures);
        this.depth = depth;
        this.numberOfTreasures = numberOfTreasures;
    }

    public long getDepth() {
        return depth;
    }

    public int getNumberOfTreasures() {
        return numberOfTreasures;
    }

    @Override
    public String toString() {
        return "[%s, %s]".formatted(depth, numberOfTreasures);
    }

    public static class Validator implements AbstractValidator<DragonCave> {

        private static final int MIN_NUMBER_OF_TREASURES = 0;
        @Serial
        private static final long serialVersionUID = -5519361223588786L;
        @Override
        public void validate(DragonCave cave)  {
            validateNumberOfTreasures(cave.numberOfTreasures);
        }

        /**
         *
         * @param numberOfTreasures value to validate
         */
        public void validateNumberOfTreasures(int numberOfTreasures)  {
            if (numberOfTreasures <= MIN_NUMBER_OF_TREASURES)
                throw new InvalidFieldValueException("Number of treasures can't be lower than zero");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DragonCave that = (DragonCave) o;
        return depth == that.depth && numberOfTreasures == that.numberOfTreasures;
    }

    @Override
    public int hashCode() {
        return Objects.hash(depth, numberOfTreasures);
    }
}
