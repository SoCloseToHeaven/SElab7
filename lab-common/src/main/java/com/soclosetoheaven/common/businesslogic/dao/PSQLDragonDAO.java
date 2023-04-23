package com.soclosetoheaven.common.businesslogic.dao;

import com.soclosetoheaven.common.businesslogic.model.Dragon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
@AllArgsConstructor
@Getter
public class PSQLDragonDAO implements DragonDAO{


    private Connection connection;

    @Override
    @SneakyThrows // временный костыль
    public void add(@NotNull Dragon dragon) {
        PreparedStatement statement = connection.prepareStatement(Query.ADD.toString());
        statement.setString(1, dragon.getName());
        statement.setInt(2, dragon.getCoordinates().getX());
        statement.setDouble(3, dragon.getCoordinates().getY());
        statement.setDate(4, new Date(dragon.getDate().getTime()));
        statement.setLong(5, dragon.getAge());
        statement.setString(6, dragon.getDescription());
        statement.setInt(7, dragon.getWingspan());
        statement.setString(8, dragon.getType().toString());
        statement.setLong(9, dragon.getCave().getDepth());
        statement.setInt(10, dragon.getCave().getNumberOfTreasures());
        statement.setInt(10, 0); // пока ещё не сделал регистрацию
        statement.execute();
    }

    @Override
    @SneakyThrows // временный костыль
    public void removeByID(@Min(0) long id) {
        PreparedStatement statement = connection.prepareStatement(Query.REMOVE_BY_ID.toString());
        statement.setLong(1, id);
        statement.execute();
    }

    @Override
    public List<Dragon> getAll() {
        return null;
    }

    @Override
    @SneakyThrows // временный костыль
    public void removeAllByAge(@Min(0) long age) {
        PreparedStatement statement = connection.prepareStatement(Query.REMOVE_ALL_BY_AGE.toString());
        statement.setLong(1, age);
        statement.execute();
    }

    @Override
    @SneakyThrows // временный костыль
    public void removeAt(@Min(0) long position) {
        //PreparedStatement statement = connection.prepareStatement();
    }

    @Override
    public void update(@NotNull Dragon dragon) {

    }

    @AllArgsConstructor
    @ToString
    private enum Query {

        REMOVE_BY_ID("DELETE FROM DRAGONS WHERE id IN (?)"),
        UPDATE(""),
        REMOVE_AT(""), // row_number
        ADD("INSERT INTO DRAGONS VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?)"),

        REMOVE_ALL_BY_AGE("DELETE FROM DRAGONS WHERE age IN (?)"),

        GET_ALL("SELECT * FROM DRAGONS");

        private String query;
    }
}
