package com.soclosetoheaven.server.dao;

import com.soclosetoheaven.common.model.Dragon;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

abstract public class SQLDragonDAO implements DAO<Dragon>{

    protected Connection connection;

    public SQLDragonDAO(Connection connection) {
        this.connection = connection;
    }

    abstract public int create(Dragon dragon) throws SQLException;

    abstract public List<Dragon> readAll() throws SQLException;

    abstract public void update(Dragon dragon) throws SQLException;

    abstract public void delete(Dragon dragon) throws SQLException;
}
