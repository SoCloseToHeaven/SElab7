package com.soclosetoheaven.common.businesslogic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class DragonCave {

    private long depth;
    @Positive(message = "Number of treasures should be greater than zero!")
    private int numberOfTreasures;
}
