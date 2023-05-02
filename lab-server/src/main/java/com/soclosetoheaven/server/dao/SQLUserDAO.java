package com.soclosetoheaven.server.dao;

import com.soclosetoheaven.common.net.auth.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

abstract public class SQLUserDAO implements DAO<User> {
    public static final int ERROR_CODE = -1;

    protected final Connection connection;

    public SQLUserDAO(Connection connection) {
        this.connection = connection;
    }

    abstract public int create(User user);

    abstract public List<User> readAll() throws SQLException;

    abstract public int update(User user);

    abstract public int delete(User user);
}
