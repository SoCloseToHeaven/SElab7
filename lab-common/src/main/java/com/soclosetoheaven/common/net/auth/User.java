package com.soclosetoheaven.common.net.auth;

import java.util.*;

public class User {

    public static final String NAME_PATTERN = "[a-zA-Z0-9]{4,30}";

    private int id;

    private final String name;

    private final AuthCredentials authCredentials;

    private final boolean isAdmin;

    public User(int id, String name, AuthCredentials auth, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.authCredentials = auth;
        this.isAdmin = isAdmin;
    }


    public User(String name, AuthCredentials auth) {
        this.name = name;
        this.authCredentials = auth;
        this.isAdmin = false;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setID(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public AuthCredentials getAuthCredentials() {
        return authCredentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAdmin == user.isAdmin && Objects.equals(authCredentials, user.authCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authCredentials, isAdmin);
    }
}
