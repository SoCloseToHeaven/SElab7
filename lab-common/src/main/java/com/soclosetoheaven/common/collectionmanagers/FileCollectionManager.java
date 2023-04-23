package com.soclosetoheaven.common.collectionmanagers;



import com.soclosetoheaven.common.businesslogic.model.Dragon;
import com.soclosetoheaven.common.util.JSONFileManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * This class is used to organise working with collection, that could be saved in file or loaded from it
 */

@Deprecated
public class FileCollectionManager implements SaveableCollectionManager<Dragon>{



    private ArrayList<Dragon> collection = new ArrayList<>();


    private final JSONFileManager fileManager;

    /**
     * initialization date
     */
    private final Date initDate;

    public FileCollectionManager(String filePath) throws FileNotFoundException{
        fileManager = new JSONFileManager(filePath);
        initDate = new Date();
    }



    public Dragon getByID(long id) {
        return collection.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void add(Dragon dragon) {
        collection.add(dragon);
    }

    @Override
    public void clear() {
        this.collection = new ArrayList<>();
        //Dragon.VALIDATOR.clearUsedID();
    }

    @Override
    public void sort() { // sorting by ID
        //this.collection.sort((dragon, dragon1) -> dragon.getId() - dragon1.getId());
    }

    @Override
    public boolean remove(int index) {
        if (index >= 0 && index < collection.size()) {
            //Dragon.VALIDATOR.removeUsedID(collection.get(index).getID());
            collection.remove(index);
            return true;
        }
        return false;
    }

    @Override
    public void removeByID(long id) {
        boolean flag = collection.removeIf(element -> {
            if (element.getId() == id) {
                //Dragon.VALIDATOR.removeUsedID(id);
                return true;
            }
            return false;
        });
        if (!flag)
            throw new NoSuchElementException("No such element with ID: %s".formatted(id));
    }

    @Override
    public boolean save() {

        return fileManager.saveToFile(collection);
    }

    @Override
    public ArrayList<Dragon> getCollection() {
        return this.collection;
    }

    @Override
    public void open(){
        this.collection = fileManager.readFromFile();
    }

    @Override
    public String toString() {
        return "%s; %s - %d; %s - %s".formatted(
                "Collection type - ArrayList", "current size", collection.size(), "Initial data", initDate.toString());
    }

    public void setCollection(ArrayList<Dragon> collection) {
        this.collection = collection;
    }
}

