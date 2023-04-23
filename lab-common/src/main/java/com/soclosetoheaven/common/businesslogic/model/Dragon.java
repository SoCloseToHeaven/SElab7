package com.soclosetoheaven.common.businesslogic.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Dragon {


    @Positive
    private Long id; // Добавить уникальность поля позже (скорее всего будет реализовано в DAO)

    @NotNull
    @Size(min = 1, max = 30)
    private String name;

    @NotNull
    private Coordinates coordinates;

    @NotNull
    @Positive
    private Long age;

    @NotNull
    @Size(max = 50)
    private String description;

    @NotNull
    private Date date;
    @NotNull
    @Positive
    private Integer wingspan;

    @NotNull
    private DragonType type;

    @NotNull
    private DragonCave cave;

    public Dragon(String name, Coordinates coordinates, Long age, String description, Integer wingspan, DragonType type, DragonCave cave) {
        this(null, name, coordinates, age, description, new Date(), wingspan, type, cave);
    }   // dragon constructor
}
