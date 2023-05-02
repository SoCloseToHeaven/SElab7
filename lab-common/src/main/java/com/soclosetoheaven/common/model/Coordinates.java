package com.soclosetoheaven.common.model;


import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;
import com.soclosetoheaven.common.util.AbstractValidator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Coordinates  implements Serializable {
    @Serial
    private static final long serialVersionUID = -551936122399L;
    /**
     *  default validator for coordinates
     */
    public static final Validator VALIDATOR = new Validator();

    private Integer x;

    private double y;

    /**
     * creates object and validate its fields via {@link #VALIDATOR#validate()}
     * @param startX can't be null
     * @param startY can't be greater than {@link #VALIDATOR#Y_MAX_VALUE}
     */

    public Coordinates(Integer startX, double startY) {
        this.x = startX;
        this.y = startY;
        VALIDATOR.validate(this);
    }


    public Integer getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setCoordinates(Integer x, double y) {
        VALIDATOR.validateX(x);
        VALIDATOR.validateY(y);
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "[%s, %f]".formatted(String.valueOf(x),y);
    }


    public static class Validator implements AbstractValidator<Coordinates> {
        @Serial
        private static final long serialVersionUID = -55221488786L;

        private static final double Y_MAX_VALUE = 36.0;
        @Override
        public void validate(Coordinates cords) {
            validateX(cords.x);
            validateY(cords.y);
        }

        /**
         *
         * @param x value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateX(Integer x) {
            AbstractValidator.checkIfNull(x, "Field X can't be null");
        }

        /**
         *
         * @param y value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateY(double y) {
            if (y >= Y_MAX_VALUE)
                throw new InvalidFieldValueException("Field Y can't be greater than 36");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.y, y) == 0 && x.equals(that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
