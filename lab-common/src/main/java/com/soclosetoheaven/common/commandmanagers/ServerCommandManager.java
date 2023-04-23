package com.soclosetoheaven.common.commandmanagers;

import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.command.*;

import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServerCommandManager implements CommandManager<Response, Request>{

    private final Map<String, AbstractCommand> commands;

    public ServerCommandManager() {
        commands = new HashMap<>();
    }

    @Override
    public Response manage(Request request){
        return commands.get(request.getCommandName()).execute(request.getBody());
    }

    @Override
    public void addCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
    }

    @Override
    public Map<String, AbstractCommand> getCommands() {
        return commands;
    }

    public static ServerCommandManager defaultManager(FileCollectionManager cm) {
        ServerCommandManager scm = new ServerCommandManager(); // add commands later
        Arrays.asList(
                new InfoCommand(cm),
                new AddCommand(cm, null),
                new SortCommand(cm),
                new RemoveAllByAgeCommand(cm),
                new ShowCommand(cm),
                new SortCommand(cm),
                new CountLessThanAgeCommand(cm),
                new ClearCommand(cm),
                new RemoveByIDCommand(cm),
                new RemoveAtCommand(cm),
                new GroupCountingByCreationDateCommand(cm),
                new UpdateCommand(cm, null)
        ).forEach(scm::addCommand);
        return scm;
    }
}
