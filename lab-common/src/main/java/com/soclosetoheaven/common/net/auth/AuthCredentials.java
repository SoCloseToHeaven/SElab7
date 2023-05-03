package com.soclosetoheaven.common.net.auth;



import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class AuthCredentials implements Serializable {
    @Serial
    private static final long serialVersionUID = -361223588543L;

    public static final String LOGIN_PATTERN = "[a-zA-Z0-9]{4,30}";


    public static final int MIN_PASSWORD_SIZE = 4;

    public static final int MAX_PASSWORD_SIZE = 16;

    private final String login;
    private final char[] password;

    public AuthCredentials(String login, char[] password) {
        this.login = login;
        this.password = password;
    }


    public String getLogin() {
        return login;
    }

    public char[] getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthCredentials that = (AuthCredentials) o;
        return login.equals(that.login) && Arrays.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(login);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }
}
