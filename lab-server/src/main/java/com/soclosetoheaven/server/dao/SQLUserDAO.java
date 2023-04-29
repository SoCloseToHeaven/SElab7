package com.soclosetoheaven.server.dao;

import com.soclosetoheaven.common.net.auth.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

abstract public class SQLUserDAO implements DAO<User> {

    protected final Connection connection;

    public SQLUserDAO(Connection connection) {
        this.connection = connection;
    }

    abstract public int create(User user) throws SQLException;

    abstract public List<User> readAll() throws SQLException;

    abstract public void update(User user) throws SQLException;

    abstract public void delete(User user) throws SQLException;
}
