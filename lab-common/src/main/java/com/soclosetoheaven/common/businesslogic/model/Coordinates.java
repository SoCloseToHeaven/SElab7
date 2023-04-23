package com.soclosetoheaven.common.businesslogic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
@AllArgsConstructor
@Getter
public class Coordinates {

    @NotNull
    private Integer x;

    @Max(value = 36, message = "Y value should be less than 36")
    private double y;
}
