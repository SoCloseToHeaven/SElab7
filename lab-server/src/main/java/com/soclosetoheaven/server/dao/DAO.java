package com.soclosetoheaven.server.dao;

import com.soclosetoheaven.common.util.Savable;

import java.util.List;

public interface DAO<T> {
    int create(T t) throws Exception;
    List<T> readAll() throws Exception;

    void update(T t) throws Exception;

    void delete(T t) throws Exception;

}
