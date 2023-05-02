package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.util.TerminalColors;

public class ExitCommand extends AbstractCommand{

    private final BasicIO io;
    public ExitCommand(BasicIO io) {
        super("exit");
        this.io = io;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        throw new UnsupportedOperationException("Not a server-side command!");
    }

    @Override
    public Request toRequest(String[] args) {
        io.writeln(TerminalColors.setColor(
                "Client application stopped, thanks for using it!",
                TerminalColors.CYAN)
        );
        System.exit(0);
        return null;
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "exit",
                " - stops this program"
        );
    }
}
