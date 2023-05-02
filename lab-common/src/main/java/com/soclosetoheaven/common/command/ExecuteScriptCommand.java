package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.ExecutingScriptException;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashSet;

public class ExecuteScriptCommand extends AbstractCommand{
    private static final HashSet<File> OPENED_FILES = new HashSet<>();
    private final BasicIO io;

    public ExecuteScriptCommand(BasicIO io) {
        super("execute_script");
        this.io = io;
    }


    @Override
    public Response execute(RequestBody requestBody) {
        throw new UnsupportedOperationException("Not a server-side command!");
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        if (args.length < MIN_ARGS_SIZE)
            throw new InvalidCommandArgumentException();
        Path file = Path.of(args[FIRST_ARG]);
        if (OPENED_FILES.contains(file.toFile()))
            throw new ExecutingScriptException("Recursion alert, script execution canceled");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(URLDecoder.decode(args[0], StandardCharsets.UTF_8))) {
                @Override
                public void close() throws IOException {
                    super.close();
                    ExecuteScriptCommand.OPENED_FILES.remove(file.toFile());
                }
            };
            OPENED_FILES.add(file.toFile());
            io.add(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExecutingScriptException(e.getMessage());
        }
        return null;
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "execute_script {filepath}",
                " - runs script from your file."
        );
    }
}
