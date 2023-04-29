package com.soclosetoheaven.common.net.auth;

import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;
import com.soclosetoheaven.common.util.AbstractValidator;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class AuthCredentials implements Serializable {
    @Serial
    private static final long serialVersionUID = -361223588543L;

    private final Validator validator = new Validator();

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

    private class Validator implements AbstractValidator<AuthCredentials> {


        @Override
        public void validate(AuthCredentials somethingToValidate) throws InvalidFieldValueException {

        }

/*        public void validateLogin(String login) {
            if (!Pattern.matches("^[a-zA-Z0-9_-]{3,16}$", login))
                throw new InvalidFieldValueException("Login: \"3-16 chars, alphanum, _, -\"");
        }

        public void validatePassword(String password) {
            if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", password))
                throw new InvalidFieldValueException("Password: \"8+ chars, 1 lower, 1 upper, 1 digit\"");
        }*/
    }
}
