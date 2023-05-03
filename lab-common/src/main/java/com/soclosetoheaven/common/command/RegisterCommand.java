package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.InvalidAuthCredentialsException;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.util.PasswordHasher;
import com.soclosetoheaven.common.util.TerminalColors;

import java.util.regex.Pattern;

public class RegisterCommand extends AbstractCommand{

    private final UserManager userManager;
    private final BasicIO io;

    public RegisterCommand(UserManager userManager, BasicIO io) {
        super("register");
        this.userManager = userManager;
        this.io = io;
    }

    public RegisterCommand(UserManager userManager) {
        this(userManager, null);
    }

    public RegisterCommand(BasicIO io) {
        this(null, io);
    }

    @Override
    public Response execute(RequestBody requestBody) throws ManagingException {
        return userManager.register(requestBody);
    }


    @Override
    public Request toRequest(String[] args) throws InvalidCommandArgumentException, InvalidAuthCredentialsException{
        if (args.length < MIN_ARGS_SIZE || !Pattern.matches(User.NAME_PATTERN, args[FIRST_ARG]))
            throw new InvalidCommandArgumentException("Username must match pattern: %s".formatted(User.NAME_PATTERN));
        io.writeln(TerminalColors.setColor("Enter login:", TerminalColors.GREEN));
        String login = System.console().readLine();
        if (login == null)
            throw new InvalidCommandArgumentException();
        if (!login.matches(AuthCredentials.LOGIN_PATTERN))
            throw new InvalidAuthCredentialsException(
                    "Invalid login: it must be from match pattern: %s"
                            .formatted(AuthCredentials.LOGIN_PATTERN)
            );
        io.writeln(TerminalColors.setColor("Enter password:", TerminalColors.GREEN));
        char[] password = System.console().readPassword();
        if (password == null)
            throw new InvalidCommandArgumentException();
        if (password.length < AuthCredentials.MIN_PASSWORD_SIZE || password.length > AuthCredentials.MAX_PASSWORD_SIZE)
            throw new InvalidAuthCredentialsException(
                    "Invalid password: it must be from %s to %s size!"
                            .formatted(AuthCredentials.MIN_PASSWORD_SIZE, AuthCredentials.MAX_PASSWORD_SIZE)
            );
        AuthCredentials authCredentials = new AuthCredentials(login, PasswordHasher.hashMD2(password));
        Request request = new Request(getName(), new RequestBody(args));
        request.setAuthCredentials(authCredentials);
        return request;
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "register {username}",
                " - registers new user"
        );
    }
}
