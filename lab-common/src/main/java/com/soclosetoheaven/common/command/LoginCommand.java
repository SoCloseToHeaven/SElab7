package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.InvalidAuthCredentialsException;
import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;
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

import static java.lang.System.console;

public class LoginCommand extends AbstractCommand{

    private final UserManager um;

    private final BasicIO io;

    public LoginCommand(UserManager um, BasicIO io) {
        super("login");
        this.um = um;
        this.io = io;
    }
    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException {
        return um.login(requestBody);
    }



    @Override
    public Request toRequest(String[] args) {
        Request request = new Request(getName(), new RequestBody(args));
        io.writeln(TerminalColors.setColor("Enter login:", TerminalColors.GREEN));
        String login = System.console().readLine();
        io.writeln(TerminalColors.setColor("Enter password:", TerminalColors.GREEN));
        char[] password =  System.console().readPassword();
        // Заменить на System.console().readPassword(), сейчас не так, потому что из-за эмуляции консоли в IDE
        request.setAuthCredentials(new AuthCredentials(login, PasswordHasher.hashMD2(password)));
        return request;
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "login",
                " - logins registered user"
        );
    }
}
