package com.soclosetoheaven.server.dao;

import com.soclosetoheaven.common.model.Dragon;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

abstract public class SQLDragonDAO implements DAO<Dragon>{
    public static final int ERROR_CODE = -1;
    protected Connection connection;

    public SQLDragonDAO(Connection connection) {
        this.connection = connection;
    }

    abstract public int create(Dragon dragon);

    abstract public List<Dragon> readAll() throws SQLException;

    abstract public int update(Dragon dragon);

    abstract public int delete(Dragon dragon);


    abstract public int delete(List<Dragon> list);
}
