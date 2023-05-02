package com.soclosetoheaven.server.dao;

import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.server.ServerApp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class JDBCUserDAO extends SQLUserDAO {

    public static final int NAME_INDEX = 1;

    public static final int LOGIN_INDEX = 2;

    public static final int PASSWORD_INDEX = 3;

    public static final int IS_ADMIN_INDEX = 4;

    public JDBCUserDAO(Connection connection) {
        super(connection);
    }


    @Override
    public int create(User user) {
        try (PreparedStatement statement = connection.prepareStatement(Query.CREATE.query)) {
            statement.setString(NAME_INDEX, user.getName());
            statement.setString(LOGIN_INDEX, user.getAuthCredentials().getLogin());
            char[] password = user.getAuthCredentials().getPassword();
            statement.setString(PASSWORD_INDEX, new String(password));
            statement.setBoolean(IS_ADMIN_INDEX, user.isAdmin());
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
    public List<User> readAll() throws SQLException {
        List<User> list = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(Query.READ_ALL.query);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            int id = set.getInt("id");
            String name = set.getString("name");
            String login = set.getString("login");
            char[] password = set.getString("password").toCharArray();
            boolean isAdmin = set.getBoolean("is_admin");
            User user = new User(id, name, new AuthCredentials(login, password), isAdmin);
            list.add(user);
        }
        statement.close();
        set.close();
        return list;
    }

    @Override
    public int update(User user) {
        final int idIndex = 5;
        try (PreparedStatement statement = connection.prepareStatement(Query.UPDATE.query)) {
            statement.setString(NAME_INDEX, user.getName());
            statement.setString(LOGIN_INDEX, user.getAuthCredentials().getLogin());
            statement.setString(PASSWORD_INDEX, new String(user.getAuthCredentials().getPassword()));
            statement.setBoolean(IS_ADMIN_INDEX, user.isAdmin());
            statement.setInt(idIndex, user.getID());
            return statement.executeUpdate();
        } catch (SQLException e) {
            ServerApp.log(Level.SEVERE, e.getMessage());
            return ERROR_CODE;
        }
    }

    @Override
    public int delete(User user) {
        final int idIndex = 1;
        try (PreparedStatement statement = connection.prepareStatement(Query.DELETE.query)) {
            statement.setInt(idIndex, user.getID());
            return statement.executeUpdate();
        } catch (SQLException e) {
            ServerApp.log(Level.SEVERE, e.getMessage());
            return ERROR_CODE;
        }
    }


    private enum Query {
        CREATE("INSERT INTO users(name, login, password, is_admin) VALUES (?,?,?,?) RETURNING id"),
        READ_ALL("SELECT * FROM USERS"),
        UPDATE("UPDATE USERS SET %s, %s, %s, %s WHERE id = ?"
                .formatted("name = ?", "login = ?", "password = ?", "is_admin = ?")
        ),
        DELETE("DELETE FROM USERS WHERE id = ?");

        final String query;
        Query(String query) {
            this.query = query;
        }
    }
}
