package com.soclosetoheaven.common.collectionmanagers;

import java.io.FileNotFoundException;

/**
 * interface for collection that can be saved
 * @param <T> class type of collection
 */

@Deprecated
public interface SaveableCollectionManager<T> extends CollectionManager<T>{

    /**
     *
     * @return true if saved successfully, false if else
     * @throws FileNotFoundException if file where collection is supposed to be saved is not found
     */
    boolean save() throws FileNotFoundException;
}
