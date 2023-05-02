package com.soclosetoheaven.server.net.auth;

import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.server.dao.SQLUserDAO;

import java.sql.SQLException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchronizedSQLUserManager extends SQLUserManager{

    private final ReadWriteLock lock;

    public SynchronizedSQLUserManager(SQLUserDAO dao) throws SQLException {
        super(dao);
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public Response login(RequestBody requestBody) throws ManagingException {
        lock.readLock().lock();
        try {
            return super.login(requestBody);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Response register(RequestBody requestBody) throws ManagingException {
        lock.writeLock().lock();
        try {
            return super.register(requestBody);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public User getUserByID(Long id) {
        lock.readLock().lock();
        try {
            return super.getUserByID(id);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public User getUserByAuthCredentials(AuthCredentials authCredentials) {
        lock.readLock().lock();
        try {
            return super.getUserByAuthCredentials(authCredentials);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean checkIfAuthorized(AuthCredentials authCredentials){
        lock.readLock().lock();
        try {
            return super.checkIfAuthorized(authCredentials);
        } finally {
            lock.readLock().unlock();
        }
    }

}
