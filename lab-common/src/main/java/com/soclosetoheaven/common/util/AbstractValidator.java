package com.soclosetoheaven.common.util;


import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;

import java.io.Serializable;

@FunctionalInterface
public interface AbstractValidator<T> extends Serializable {

    /**
     * abstract method to validate any fields of class
     * @param somethingToValidate Object of class that needs to be validated
     * @throws InvalidFieldValueException if value of any field is not correct
     */
    void validate(T somethingToValidate);

    /**
     * validates field if it is not null
     * @param field something to validate
     * @param fieldName name of field used in exception message
     * @throws InvalidFieldValueException if object is null
     */
    static void checkIfNull(Object field, String fieldName){
        if (field == null)
            throw new InvalidFieldValueException(fieldName);
    }

}
