package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.ExecutingScriptException;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    public Request toRequest(String[] args) {
        if (args.length < 1)
            throw new InvalidCommandArgumentException();
        File file = new File(args[0]);
        if (OPENED_FILES.contains(file))
            throw new ExecutingScriptException("Recursion alert, script execution canceled");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file)) {
                @Override
                public void close() throws IOException {
                    super.close();
                    ExecuteScriptCommand.OPENED_FILES.remove(file);
                }
            };
            OPENED_FILES.add(file);
            io.add(reader);
        } catch (IOException e) {
            throw new ExecutingScriptException(e.getMessage());
        }
        //this command doesn't send anything to server
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
