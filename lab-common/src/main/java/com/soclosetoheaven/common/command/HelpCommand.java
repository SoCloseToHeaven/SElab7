package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.commandmanagers.ClientCommandManager;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.util.TerminalColors;

public class HelpCommand extends AbstractCommand{

    private final ClientCommandManager cm;

    private final BasicIO io;
    public HelpCommand(ClientCommandManager cm, BasicIO io) {
        super("help");
        this.cm = cm;
        this.io = io;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Request toRequest(String[] args) {
        StringBuilder responseText = new StringBuilder();
        cm.getCommands().forEach((k,v) -> responseText.append("%s%n".formatted(v.getUsage())));
        io.writeln(TerminalColors.setColor(responseText.toString().trim(), TerminalColors.CYAN));
        return null;
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "help",
                " - provides information for all usable commands"
        );
    }
}
