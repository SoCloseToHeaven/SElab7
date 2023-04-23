package com.soclosetoheaven.common.businesslogic.service;

import com.soclosetoheaven.common.businesslogic.dao.DragonDAO;
import com.soclosetoheaven.common.businesslogic.model.Dragon;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
public class DragonService {

    @NotNull
    private DragonDAO dao;


    void add(Dragon dragon) {
        dao.add(dragon);
    }

    public List<Dragon> getAll() {
        return dao.getAll();
    }

    public void update(Dragon dragon) {
        dao.update(dragon);
    }

    void removeByID(long id) {
        dao.removeByID(id);
    }

    void removeAllByAge(long age) {
        dao.removeAllByAge(age);
    }

    void removeAt(long position) {
        dao.removeAt(position);
    }

}

