package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.util.PasswordHasher;
import com.soclosetoheaven.common.util.TerminalColors;

import java.util.regex.Pattern;

public class RegisterCommand extends AbstractCommand{

    private final UserManager um;
    private final BasicIO io;
    public RegisterCommand(UserManager um, BasicIO io) {
        super("register");
        this.um = um;
        this.io = io;
    }
    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException {
        return um.register(requestBody);
    }


    @Override
    public Request toRequest(String[] args) {
        if (args.length < 1 || !Pattern.matches("\\S+", args[0]))
            throw new InvalidCommandArgumentException();
        io.writeln(TerminalColors.setColor("Enter login:", TerminalColors.GREEN));
        String login = System.console().readLine();
        io.writeln(TerminalColors.setColor("Enter password:", TerminalColors.GREEN));
        char[] password =  System.console().readPassword();
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
