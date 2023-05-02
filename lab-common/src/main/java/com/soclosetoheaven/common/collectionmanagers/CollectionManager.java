package com.soclosetoheaven.common.collectionmanagers;


import java.util.List;

/**
 * @param <T> classtype in collection
 */


public interface CollectionManager<T> {
    /**
     * returns ArrayList with elements of collection
     * @return collection
     */

    List<T> getCollection();


    /**
     * adds new element of collection
     * @param t new element
     */
    boolean add(T t);

    T get(int index);


    T remove(int index);

    /**
     * clears collection
     */
    boolean clear();

    /**
     * sorts collection
     */
    void sort();

}
