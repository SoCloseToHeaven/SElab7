package com.soclosetoheaven.common.businesslogic.dao;

import com.soclosetoheaven.common.businesslogic.model.Dragon;

import javax.validation.constraints.Min;
import java.util.List;

public interface DragonDAO { // CRUD

    void add(Dragon dragon); // Create

    List<Dragon> getAll(); // Read

    void update(Dragon dragon); // Update

    void removeByID(@Min(0) long id);

    void removeAllByAge(@Min(0) long age);

    void removeAt(@Min(0) long position);
}
