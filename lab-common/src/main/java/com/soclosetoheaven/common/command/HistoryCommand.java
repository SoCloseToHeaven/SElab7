package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.commandmanagers.CommandManager;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.util.TerminalColors;

public class HistoryCommand extends AbstractCommand{

    private final CommandManager<?,?> commandManager;
    private final BasicIO io;

    public HistoryCommand(CommandManager<?,?> commandManager, BasicIO io) {
        super("history");
        this.commandManager = commandManager;
        this.io = io;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        throw new UnsupportedOperationException("Not a server-side command!");
    }

    @Override
    public Request toRequest(String[] args) {
        io.writeln(TerminalColors.setColor(commandManager.getHistory().toString(), TerminalColors.CYAN));
        // sends nothing to server
        return null;
    }

    @Override
    String getUsage() {
        return "%s - %s %s %s".formatted(
                "history",
                "displays",
                commandManager.getHistory().getMaxSize(),
                "recently used commands"
        );
    }
}
