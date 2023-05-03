package com.soclosetoheaven.common.commandmanagers;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.command.*;

import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.exceptions.UnknownCommandException;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.Response;
import com.soclosetoheaven.common.util.LRUCache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServerCommandManager implements CommandManager<Response, Request>{

    private static final int MAX_HISTORY_SIZE = 13;

    private final LRUCache<AbstractCommand> history;

    private final Map<String, AbstractCommand> commands;

    public ServerCommandManager() {
        commands = new HashMap<>();
        history = new LRUCache<>(MAX_HISTORY_SIZE);
    }

    @Override
    public Response manage(Request request) throws ManagingException {
        AbstractCommand command =  commands.get(request.getCommandName());
        if (command == null)
            throw new UnknownCommandException("Unable to execute command!");
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

    public synchronized static ServerCommandManager defaultManager(DragonCollectionManager collectionManager, UserManager userManager) {
        ServerCommandManager commandManager = new ServerCommandManager();
        Arrays.asList(
                new HelpCommand(commandManager),
                new InfoCommand(collectionManager),
                new AddCommand(collectionManager, userManager),
                new SortCommand(collectionManager),
                new RemoveAllByAgeCommand(collectionManager, userManager),
                new ShowCommand(collectionManager),
                new SortCommand(collectionManager),
                new CountLessThanAgeCommand(collectionManager),
                new ClearCommand(collectionManager, userManager),
                new RemoveByIDCommand(collectionManager, userManager),
                new RemoveAtCommand(collectionManager, userManager),
                new GroupCountingByCreationDateCommand(collectionManager),
                new UpdateCommand(collectionManager,  userManager),
                new LoginCommand(userManager),
                new RegisterCommand(userManager),
                new LogoutCommand()
        ).forEach(commandManager::addCommand);
        return commandManager;
    }

    public synchronized static ServerCommandManager authManager(UserManager um) {
        ServerCommandManager scm = new ServerCommandManager();
        scm.addCommand(new LoginCommand(um));
        scm.addCommand(new RegisterCommand(um));
        scm.addCommand(new HelpCommand(scm));
        scm.addCommand(new LogoutCommand());
        return scm;
    }

    public static int getMaxHistorySize() {
        return MAX_HISTORY_SIZE;
    }

    @Override
    public LRUCache<AbstractCommand> getHistory() {
        return history;
    }
}
