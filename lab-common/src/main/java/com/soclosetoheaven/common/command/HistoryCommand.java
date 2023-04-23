package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.commandmanagers.ClientCommandManager;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.util.TerminalColors;

public class HistoryCommand extends AbstractCommand{

    private final ClientCommandManager cm;
    private final BasicIO io;

    public HistoryCommand(ClientCommandManager cm, BasicIO io) {
        super("history");
        this.cm = cm;
        this.io = io;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        throw new UnsupportedOperationException("Not a server-side command!");
    }

    @Override
    public Request toRequest(String[] args) {
        io.writeln(TerminalColors.setColor(cm.getHistory().toString(), TerminalColors.CYAN));
        // sends nothing to server
        return null;
    }

    @Override
    String getUsage() {
        return "%s%s".formatted(
                "history",
                " - displays 13 recently used commands"
        );
    }
}
