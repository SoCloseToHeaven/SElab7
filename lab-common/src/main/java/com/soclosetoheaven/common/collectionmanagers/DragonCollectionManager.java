package com.soclosetoheaven.common.collectionmanagers;

import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.util.Savable;

public interface DragonCollectionManager extends CollectionManager<Dragon> {

    boolean removeByID(int id);

    boolean update(Dragon dragon, int id);

    Dragon getByID(int id);

    boolean removeAllByAge(long age);
}
