package com.soclosetoheaven.server.dao;


import java.util.List;

public interface DAO<T> {

    /**
     *
     * @param t object
     * @return error code if some error occurred
     */
    int create(T t);

    /**
     *
     * @return list of objects
     * @throws Exception if
     */
    List<T> readAll() throws Exception;

    /**
     *
     * @param t object
     * @return error code if some error occurred
     */
    int update(T t);

    /**
     *
     * @param t object
     * @return error code if some error occurred
     */
    int delete(T t);



}
