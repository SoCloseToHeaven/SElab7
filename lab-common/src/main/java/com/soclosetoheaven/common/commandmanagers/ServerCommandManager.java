package com.soclosetoheaven.common.commandmanagers;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.command.*;

import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.net.auth.UserManager;
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
    public Response manage(Request request) throws InvalidRequestException {
        AbstractCommand command =  commands.get(request.getCommandName());
        if (command == null)
            throw new InvalidRequestException("Unable to execute command!");
        return command.execute(request.getRequestBody());
    }

    @Override
    public void addCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
    }

    @Override
    public Map<String, AbstractCommand> getCommands() {
        return commands;
    }

    public synchronized static ServerCommandManager defaultManager(DragonCollectionManager cm, UserManager um) {
        ServerCommandManager scm = new ServerCommandManager(); // add commands later
        Arrays.asList(
                new HelpCommand(scm),
                new InfoCommand(cm),
                new AddCommand(cm, null, um),
                new SortCommand(cm),
                new RemoveAllByAgeCommand(cm, um),
                new ShowCommand(cm),
                new SortCommand(cm),
                new CountLessThanAgeCommand(cm),
                new ClearCommand(cm, um),
                new RemoveByIDCommand(cm, um),
                new RemoveAtCommand(cm, um),
                new GroupCountingByCreationDateCommand(cm),
                new UpdateCommand(cm, null, um),
                new LoginCommand(um, null),
                new RegisterCommand(um, null)
        ).forEach(scm::addCommand);
        return scm;
    }

    public synchronized static ServerCommandManager authManager(UserManager um) {
        ServerCommandManager scm = new ServerCommandManager();
        scm.addCommand(new LoginCommand(um, null));
        scm.addCommand(new RegisterCommand(um, null));
        scm.addCommand(new HelpCommand(scm));
        return scm;
    }
}
