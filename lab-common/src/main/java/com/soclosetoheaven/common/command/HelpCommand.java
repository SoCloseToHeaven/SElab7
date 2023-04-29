package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.commandmanagers.CommandManager;
import com.soclosetoheaven.common.net.factory.RequestFactory;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class HelpCommand extends AbstractCommand{

    private final CommandManager<?,?> cm;

    public HelpCommand(CommandManager<?,?> cm) {
        super("help");
        this.cm = cm;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        StringBuilder responseText = new StringBuilder();
        cm.getCommands().forEach((k,v) -> responseText.append("%s%n".formatted(v.getUsage())));
        return ResponseFactory.createResponse(responseText.toString().trim());
    }

    @Override
    public Request toRequest(String[] args) {
        return RequestFactory.createRequest(getName(), args);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "help",
                " - provides information for all usable commands"
        );
    }
}
