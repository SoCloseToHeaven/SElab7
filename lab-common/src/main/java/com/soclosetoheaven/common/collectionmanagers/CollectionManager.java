package com.soclosetoheaven.common.collectionmanagers;


import java.util.ArrayList;

/**
 * @param <T> classtype in collection
 */

@Deprecated
public interface CollectionManager<T> {
    /**
     * returns ArrayList with elements of collection
     * @return collection
     */

    ArrayList<T> getCollection();

    /**
     * adds new element of collection
     * @param t new element
     */
    void add(T t);

    /**
     * removes element by its index in collection
     * @param index index of element
     * @return true if successfully removed, false if else
     */
    boolean remove(int index);

    /**
     * clears collection
     */
    void clear();

    /**
     * opens collection
     */
    void open();

    /**
     * sorts collection
     */
    void sort();

    /**
     * removes element by its ID
     * @param id element's ID
     * @return true if successfully removed, false if else
     */

    void removeByID(long id);
}
