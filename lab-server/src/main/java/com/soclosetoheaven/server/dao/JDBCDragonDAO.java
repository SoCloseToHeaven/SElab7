package com.soclosetoheaven.server.dao;

import com.soclosetoheaven.common.model.Coordinates;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.model.DragonCave;
import com.soclosetoheaven.common.model.DragonType;
import com.soclosetoheaven.server.ServerApp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class JDBCDragonDAO extends SQLDragonDAO {


    public static final int NAME_INDEX = 1;
    public static final int COORDINATE_X_INDEX = 2;
    public static final int COORDINATE_Y_INDEX = 3;
    public static final int DATE_INDEX = 4;
    public static final int AGE_INDEX = 5;
    public static final int DESCRIPTION_INDEX = 6;
    public static final int WINGSPAN_INDEX = 7;
    public static final int TYPE_INDEX = 8;
    public static final int DEPTH_INDEX = 9;
    public static final int TREASURES_INDEX = 10;
    public static final int CREATOR_ID_INDEX = 11;

    public JDBCDragonDAO(Connection connection) {
        super(connection);
    }

    @Override
    public int create(Dragon dragon){
        try (PreparedStatement statement = connection.prepareStatement(Query.CREATE.query)) {
            statement.setString(NAME_INDEX, dragon.getName());
            statement.setInt(COORDINATE_X_INDEX, dragon.getCoordinates().getX());
            statement.setDouble(COORDINATE_Y_INDEX, dragon.getCoordinates().getY());
            statement.setDate(DATE_INDEX, new Date(dragon.getCreationDate().getTime()));
            statement.setLong(AGE_INDEX, dragon.getAge());
            statement.setString(DESCRIPTION_INDEX, dragon.getDescription());
            statement.setInt(WINGSPAN_INDEX, dragon.getWingspan());
            statement.setString(TYPE_INDEX, dragon.getType().toString());
            statement.setLong(DEPTH_INDEX, dragon.getCave().getDepth());
            statement.setInt(TREASURES_INDEX, dragon.getCave().getNumberOfTreasures());
            statement.setInt(CREATOR_ID_INDEX, dragon.getCreatorId());
            ResultSet set = statement.executeQuery();
            set.next();
            int id = set.getInt("id");
            set.close();
            return id;
        } catch (SQLException e) {
            ServerApp.log(Level.SEVERE, e.getMessage());
            return ERROR_CODE;
        }
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
    public int update(Dragon dragon) {
            final int idIndex = 11;
            try (PreparedStatement statement = connection.prepareStatement(Query.UPDATE.query)) {
                statement.setString(NAME_INDEX, dragon.getName());
                statement.setInt(COORDINATE_X_INDEX, dragon.getCoordinates().getX());
                statement.setDouble(COORDINATE_Y_INDEX, dragon.getCoordinates().getY());
                statement.setDate(DATE_INDEX, new Date(dragon.getCreationDate().getTime()));
                statement.setLong(AGE_INDEX, dragon.getAge());
                statement.setString(DESCRIPTION_INDEX, dragon.getDescription());
                statement.setInt(WINGSPAN_INDEX, dragon.getWingspan());
                statement.setString(TYPE_INDEX, dragon.getType().toString());
                statement.setLong(DEPTH_INDEX, dragon.getCave().getDepth());
                statement.setInt(TREASURES_INDEX, dragon.getCave().getNumberOfTreasures());
                statement.setLong(idIndex, dragon.getID());
                return statement.executeUpdate();
            } catch (SQLException e) {
                ServerApp.log(Level.SEVERE, e.getMessage());
                return ERROR_CODE;
            }
    }
    @Override
    public int delete(Dragon dragon) {
        final int idIndex = 1;
        try (PreparedStatement statement = connection.prepareStatement(Query.DELETE.query)) {
            statement.setLong(idIndex, dragon.getID());
            return statement.executeUpdate();
        } catch (SQLException e) {
            ServerApp.log(Level.SEVERE, e.getMessage());
            return ERROR_CODE;
        }
    }

    @Override
    public int delete(List<Dragon> list) {
        final int idArrayIndex = 1;
        Integer[] ids = list
                .stream()
                .map(Dragon::getID)
                .toArray(Integer[]::new);
        try (PreparedStatement statement = connection.prepareStatement(Query.DELETE_MULTIPLE.query)) {
            Array array = connection.createArrayOf("integer", ids);
            statement.setArray(idArrayIndex, array);
            return statement.executeUpdate();
        } catch (SQLException e) {
            ServerApp.log(Level.SEVERE, e.getMessage());
            return ERROR_CODE;
        }
    }

    private enum Query {

        CREATE("INSERT INTO DRAGONS" +
                "(name, coordinate_x, coordinate_y, creation_date, age, description, wingspan, type, cave_depth," +
                "cave_number_of_treasures, creator_id) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?) RETURNING id"),
        READ_ALL("SELECT * FROM DRAGONS"),

        UPDATE("UPDATE DRAGONS SET %s,%s,%s,%s,%s,%s,%s,%s,%s,%s WHERE %s"
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
                        "id in (?)"
                        )
        ),
        DELETE("DELETE FROM DRAGONS WHERE id in (?)"),
        DELETE_MULTIPLE("DELETE FROM DRAGONS WHERE id = ANY (?)");

        final String query;

        Query(String query) {
            this.query = query;
        }
    }
}
