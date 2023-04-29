package com.soclosetoheaven.server.dao;

import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.auth.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDAO extends SQLUserDAO {

    public JDBCUserDAO(Connection connection) {
        super(connection);
    }


    @Override
    public int create(User user) throws SQLException {
        PreparedStatement statement = connection
                .prepareStatement(Query.CREATE.query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getName());
        statement.setString(2, user.getAuthCredentials().getLogin());
        char[] password = user.getAuthCredentials().getPassword();
        statement.setString(3, new String(password));
        statement.setBoolean(4, user.isAdmin());
        statement.executeUpdate();
        ResultSet set = statement.getGeneratedKeys();
        set.next();
        int id = set.getInt("id");
        return id;
    }

    @Override
    public List<User> readAll() throws SQLException {
        List<User> list = new ArrayList<>();
        ResultSet set = connection.prepareStatement(Query.READ_ALL.query).executeQuery();
        while (set.next()) {
            int id = set.getInt("id");
            String name = set.getString("name");
            String login = set.getString("login");
            char[] password = set.getString("password").toCharArray();
            boolean isAdmin = set.getBoolean("is_admin");
            User user = new User(id, name, new AuthCredentials(login, password), isAdmin);
            list.add(user);
        }
        return list;
    }

    @Override
    public void update(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(Query.UPDATE.query);
        statement.setString(1, user.getName());
        statement.setString(2, user.getAuthCredentials().getLogin());
        statement.setString(3, new String(user.getAuthCredentials().getPassword()));
        statement.setBoolean(4, user.isAdmin());
        statement.setInt(5, user.getID());
    }

    @Override
    public void delete(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(Query.DELETE.query);
        statement.setInt(1, user.getID());
        statement.executeUpdate();
    }


    private enum Query {
        CREATE("INSERT INTO users(name, login, password, is_admin) VALUES (?,?,?,?)"),
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
