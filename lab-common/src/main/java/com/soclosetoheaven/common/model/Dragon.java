package com.soclosetoheaven.common.model;



import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;

import com.soclosetoheaven.common.util.AbstractValidator;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Dragon implements Serializable, Comparable<Dragon> {
    /**
     * Default validator for dragon
     */
    @Serial
    private static final long serialVersionUID = -361223588786L;
    public static final Validator Validator = new Validator();


    private int id;

    private String name;

    private final Coordinates coordinates;

    /**
     * The creation date of dragon, can't be null, generates automatically
     */
    private final Date creationDate;


    private Long age;



    private String description;

    private Integer wingspan;

    private DragonType type;


    private final DragonCave cave;

    private int creatorId;


    public Dragon(String name, Coordinates coordinates, Long age, String description, Integer wingspan, DragonType type, DragonCave cave) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.age = age;
        this.description = description;
        this.wingspan = wingspan;
        this.type = type;
        this.cave = cave;
        Validator.validate(this);
    }

    public Dragon(int id,
                  String name,
                  Coordinates coordinates,
                  Date date,
                  Long age,
                  String description,
                  int wingspan,
                  DragonType type,
                  DragonCave cave,
                  int creatorId) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = date;
        this.age = age;
        this.description = description;
        this.wingspan = wingspan;
        this.type = type;
        this.cave = cave;
        this.creatorId = creatorId;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {return this.name;}

    public Dragon setName(String name) {
        Validator.validateName(name);
        this.name = name;
        return this;
    }
    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    public Dragon setCoordinates(Integer x, double y) {
        coordinates.setCoordinates(x,y);
        return this;
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Long getAge() {return this.age;}

    public Dragon setAge(Long age) {
        Validator.validateAge(age);
        this.age = age;
        return this;
    }

    public String getDescription() {return this.description;}

    public Dragon setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getWingspan() {return this.wingspan;}

    public Dragon setWingspan(Integer wingspan) {
        Validator.validateWingspan(wingspan);
        this.wingspan = wingspan;
        return this;
    }

    public DragonType getType() {return this.type;}

    public Dragon setType(DragonType type) {
        this.type = type;
        return this;
    }

    public void update(String name,
                       Integer x,
                       double y,
                       Long age,
                       String desc,
                       Integer wingspan,
                       DragonType type,
                       long depth,
                       int numberOfTreasures) {
        this.
                setName(name).
                setCoordinates(x,y).
                setAge(age).
                setDescription(desc).
                setWingspan(wingspan).
                setType(type).
                setCave(depth, numberOfTreasures);
    }

    @Override
    public int compareTo(Dragon o) {
        if (this.getID() < o.getID())
            return -1;
        else if (this.getID() == o.getID())
            return 0;
        return 1;
    }

    public DragonCave getCave() {return this.cave;}

    public Dragon setCave(long depth, int numberOfTreasures) {
        this.cave.setCave(depth, numberOfTreasures);
        return this;
    }
    @Override
    public String toString() {

        return "Name: %s; ID: %s; CreationDate: %s; Age: %s; Description: %s; Wingspan: %s; Type: %s; Cave: %s; Coordinates: %s".
                formatted(
                        String.valueOf(getName()),
                        String.valueOf(getID()),
                        String.valueOf(getCreationDate()),
                        String.valueOf(getAge()),
                        String.valueOf(getDescription()),
                        String.valueOf(getWingspan()),
                        String.valueOf(getType()),
                        String.valueOf(getCave()),
                        String.valueOf(getCoordinates()));
    }

    public Dragon setID(int id) {
        this.id = id;
        return this;
    }


    public int getCreatorId() {
        return creatorId;
    }

    public static class Validator implements AbstractValidator<Dragon> {

        /**
         * collection to cache usedIDs
         */

        private static final int WINGSPAN_MIN_VALUE = 0;

        private static final long AGE_MIN_VALUE = 0;
        @Serial
        private static final long serialVersionUID = -5519361186L;
        @Override
        public void validate(Dragon dragon) {
            validateName(dragon.name);
            validateCoordinates(dragon.coordinates);
            validateCreationDate(dragon.creationDate);
            validateAge(dragon.age);
            validateDescription(dragon.description);
            validateWingspan(dragon.wingspan);
            validateDragonCave(dragon.cave);
            validateDragonType(dragon.type);
        }

        /**
         * validates object's name
         * @param name value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateName(String name) {
            AbstractValidator.checkIfNull(name, "Name can't be null!");
            if (name.isEmpty())
                throw new InvalidFieldValueException("Field name can't be empty!");
        }

        /**
         * validates object's coordinates
         * @param coordinates value to validate
         * @throws InvalidFieldValueException if value is invalid
         */

        public void validateCoordinates(Coordinates coordinates) {
            AbstractValidator.checkIfNull(coordinates, "Coordinates can't be null!");
        }

        /**
         * validates object's creation date
         * @param creationDate value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateCreationDate(Date creationDate) {
            AbstractValidator.checkIfNull(creationDate, "Creation date can't be null!");
        }

        /**
         * validates object's age
         * @param age value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateAge(Long age) {
            if (age == null)
                return;
            if (age <= AGE_MIN_VALUE)
                throw new InvalidFieldValueException("Age can't be lower than zero!");
        }

        /**
         * validates object's description
         * @param description value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateDescription(String description) {
            AbstractValidator.checkIfNull(description, "Description can't be null!");
        }

        /**
         * validates object's wingspan
         * @param wingspan value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateWingspan(Integer wingspan) {
            AbstractValidator.checkIfNull(wingspan, "Wingspan can't be null!");
            if (wingspan <= WINGSPAN_MIN_VALUE)
                throw new InvalidFieldValueException("Wingspan value can't be lower than zero!");
        }

        /**
         * validates object's type
         * @param dragonType value to validate
         * @throws InvalidFieldValueException if value is invalid
         */
        public void validateDragonType(DragonType dragonType) {
            AbstractValidator.checkIfNull(dragonType, "Dragon type can't be null!");
        }

        /**
         * validates object's cave
         * @param dragonCave value to validate
         * @throws InvalidFieldValueException if value is invalid
         */

        public void validateDragonCave(DragonCave dragonCave) {
            AbstractValidator.checkIfNull(dragonCave, "Dragon cave can't be null!");
        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragon = (Dragon) o;
        return Objects.equals(id, dragon.id) && Objects.equals(name, dragon.name) && Objects.equals(coordinates, dragon.coordinates) && Objects.equals(creationDate, dragon.creationDate) && Objects.equals(age, dragon.age) && Objects.equals(description, dragon.description) && Objects.equals(wingspan, dragon.wingspan) && type == dragon.type && Objects.equals(cave, dragon.cave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, age, description, wingspan, type, cave);
    }
}