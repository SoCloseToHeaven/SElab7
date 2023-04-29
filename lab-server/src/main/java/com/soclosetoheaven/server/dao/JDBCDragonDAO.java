package com.soclosetoheaven.server.dao;

import com.soclosetoheaven.common.model.Coordinates;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.model.DragonCave;
import com.soclosetoheaven.common.model.DragonType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCDragonDAO extends SQLDragonDAO {


    public JDBCDragonDAO(Connection connection) {
        super(connection);
    }

    @Override
    public int create(Dragon dragon) throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Query.CREATE.query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, dragon.getName());
        statement.setInt(2, dragon.getCoordinates().getX());
        statement.setDouble(3, dragon.getCoordinates().getY());
        statement.setDate(4, new Date(dragon.getCreationDate().getTime()));
        statement.setLong(5, dragon.getAge());
        statement.setString(6, dragon.getDescription());
        statement.setInt(7, dragon.getWingspan());
        statement.setString(8, dragon.getType().toString());
        statement.setLong(9, dragon.getCave().getDepth());
        statement.setInt(10, dragon.getCave().getNumberOfTreasures());
        statement.setInt(11, dragon.getCreatorId());
        statement.executeUpdate();
        ResultSet set = statement.getGeneratedKeys();
        set.next();
        return set.getInt("id");
    }

    @Override
    public List<Dragon> readAll() throws SQLException{
        ResultSet set = connection.prepareStatement(Query.READ_ALL.query).executeQuery();
        List<Dragon> dragons = new ArrayList<>();
        while(set.next()) {
            int id = set.getInt("id");
            String name = set.getString("name");
            Coordinates cords = new Coordinates(set.getInt("coordinate_x"), set.getDouble("coordinate_y"));
            java.util.Date date = new java.util.Date(set.getDate("creation_date").getTime());
            Long age = set.getLong("age");
            String description = set.getString("description");
            int wingspan = set.getInt("wingspan");
            DragonType type = DragonType.parseDragonType(set.getString("type"));
            DragonCave cave = new DragonCave(set.getLong("cave_depth"), set.getInt("cave_number_of_treasures"));
            int creatorId = set.getInt("creator_id");
            Dragon dragon = new Dragon(id, name, cords, date, age, description, wingspan, type, cave, creatorId);
            dragons.add(dragon);
        }
        return dragons;
    }

    @Override
    public void update(Dragon dragon) throws SQLException{
            PreparedStatement statement = connection.prepareStatement(Query.UPDATE.query);
            statement.setString(1, dragon.getName());
            statement.setInt(2, dragon.getCoordinates().getX());
            statement.setDouble(3, dragon.getCoordinates().getY());
            statement.setDate(4, new Date(dragon.getCreationDate().getTime()));
            statement.setLong(5, dragon.getAge());
            statement.setString(6, dragon.getDescription());
            statement.setInt(7, dragon.getWingspan());
            statement.setString(8, dragon.getType().toString());
            statement.setLong(9, dragon.getCave().getDepth());
            statement.setInt(10, dragon.getCave().getNumberOfTreasures());
            statement.setInt(11, dragon.getCreatorId());
            statement.setLong(12, dragon.getID());
            statement.execute();
    }
    @Override
    public void delete(Dragon dragon) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(Query.DELETE.query);
        statement.setLong(1, dragon.getID());
        statement.execute();
    }


    private enum Query {

        CREATE("INSERT INTO DRAGONS" +
                "(name, coordinate_x, coordinate_y, creation_date, age, description, wingspan, type, cave_depth," +
                "cave_number_of_treasures, creator_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?)"), // потом добавить ещё один вопросительный знак для id создателя
        READ_ALL("SELECT * FROM DRAGONS"),

        UPDATE("UPDATE DRAGONS SET %s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s WHERE %s" // добавить потом ещё вопросительный знак
                .formatted("name = ?",
                        "coordinate_x = ?",
                        "coordinate_y = ?",
                        "creation_date = ?",
                        "age = ?",
                        "description = ?",
                        "wingspan = ?",
                        "type = ?",
                        "cave_depth = ?",
                        "cave_number_of_treasures = ?",
                        "creator_id = ?",
                        "id = ?"
                        )
        ),
        DELETE("DELETE FROM DRAGONS WHERE id in (?)");

        private final String query;

        Query(String query) {
            this.query = query;
        }
    }
}
