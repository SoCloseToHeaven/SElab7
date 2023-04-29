package com.soclosetoheaven.server.collectionmanager;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.server.dao.SQLDragonDAO;
import com.soclosetoheaven.common.model.Dragon;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchronizedSQLCollectionManager implements DragonCollectionManager {

    private final List<Dragon> collection; // cache


    private final ReadWriteLock lock;

    private final SQLDragonDAO dao; // data access object

    public SynchronizedSQLCollectionManager(SQLDragonDAO dao) throws SQLException {
        this.lock = new ReentrantReadWriteLock();
        this.dao = dao;
        this.collection = dao.readAll();
    }

    @Override
    public List<Dragon> getCollection() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(collection); // чтобы никто не сделал каких-то приколов с коллекцией вне менеджера
        } finally {
            lock.readLock().unlock();
        }
    }
    @Deprecated
    @Override
    public void setCollection(List<Dragon> collection) {
        lock.writeLock().lock();
        this.collection.clear();
        this.collection.addAll(collection);
        lock.writeLock().unlock();
    }

    @Override
    public boolean add(Dragon dragon) {
        lock.writeLock().lock();
        try {
            int id = dao.create(dragon);
            dragon.setID(id);
            return collection.add(dragon);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
          lock.writeLock().unlock();
        }
    }

    @Override
    public Dragon remove(int index) {
        lock.writeLock().lock();
        if (collection.size() < index)
            return null;
        try {
            Dragon dragon = collection.get(index);
            dao.delete(dragon);
            return collection.remove(index);
        } catch (SQLException e) {
            return null;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Dragon get(int index) {
        Dragon dragon = null;
        lock.readLock().lock();
        if (index < collection.size())
            dragon = collection.get(index);
        lock.readLock().unlock();
        return dragon;
    }

    @Override
    public void clear() {
        lock.writeLock().lock();
        this.collection.clear(); // надо чёт тут придумать
        lock.writeLock().unlock();
    }

    @Override
    public void sort() {
        lock.writeLock().lock();
        Collections.sort(collection);
        lock.writeLock().unlock();
    }

    @Override
    public boolean removeByID(int id) {
        lock.writeLock().lock();
        Dragon dragon = collection
                .stream()
                .filter(elem -> elem.getID() == id)
                .findFirst()
                .orElse(null);
        if (dragon == null)
            return false;
        try {
            dao.delete(dragon);
            collection.remove(dragon);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean update(Dragon dragon, int id) {
        lock.writeLock().lock();
        Dragon element = collection
                .stream()
                .filter(elem -> elem.getID() == id)
                .findFirst()
                .orElse(null);
        if (element == null)
            return false;
        try {
            dragon.setID(id);
            dao.update(dragon);
            collection.remove(element);
            collection.add(dragon);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }


    @Override
    public Dragon getByID(int id) {
        lock.readLock().lock();
        Dragon dragon =  collection.stream()
                .filter(p -> p.getID() == id)
                .findFirst()
                .orElse(null);
        lock.readLock().unlock();
        return dragon;
    }


    @Override
    public boolean removeAllByAge(long age) {
        lock.writeLock().lock();
        try {
            List<Dragon> dragons = collection
                    .stream()
                    .filter(elem -> elem.getAge() == age)
                    .toList();
            for (var dragon: dragons) {
                dao.delete(dragon); // переделать под транзакции
                collection.remove(dragon);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public String toString() {
        lock.readLock().lock();
        String info = "%s - %s, %s - %s".formatted(
                "Collection type",
                "List",
                "Current size",
                collection.size()
        );
        lock.readLock().unlock();
        return info;
    }
}
