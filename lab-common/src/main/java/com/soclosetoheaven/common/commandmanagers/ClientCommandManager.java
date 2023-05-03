package com.soclosetoheaven.common.commandmanagers;

import com.soclosetoheaven.common.command.*;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.exceptions.UnknownCommandException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.util.LRUCache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ClientCommandManager implements CommandManager<Request, String> {

    private static final int MAX_HISTORY_SIZE = 13;

    private final LRUCache<AbstractCommand> history;
    private final HashMap<String, AbstractCommand> commands;

    private static final int ARGS_START_POSITION = 1;

    public ClientCommandManager() {
        commands = new HashMap<>();
        history = new LRUCache<>(MAX_HISTORY_SIZE);
    }

    @Override
    public Request manage(String inputLine) throws ManagingException {
        String[] args = inputLine.trim().split("\\s+");
        String commandName = args[AbstractCommand.FIRST_ARG].toLowerCase();
        args = Arrays.copyOfRange(args, ARGS_START_POSITION, args.length);
        AbstractCommand command;
        if ((command = commands.get(commandName)) == null)
            throw new UnknownCommandException(commandName);
        history.add(command);
        return command.toRequest(args);
    }

    @Override
    public Map<String, ? extends AbstractCommand> getCommands() {
        return commands;
    }

    @Override
    public void addCommand(AbstractCommand command) {
        commands.put(command.getName(), command);
    }



    public static ClientCommandManager defaultManager(BasicIO io) {
        ClientCommandManager commandManager = new ClientCommandManager();
        Arrays.asList(
                new LoginCommand(io),
                new RegisterCommand(io),
                new InfoCommand(),
                new AddCommand(io),
                new SortCommand(),
                new RemoveAllByAgeCommand(),
                new ShowCommand(),
                new ShowCommand(),
                new CountLessThanAgeCommand(),
                new ClearCommand(),
                new RemoveByIDCommand(),
                new RemoveAtCommand(),
                new HelpCommand(),
                new GroupCountingByCreationDateCommand(),
                new UpdateCommand(io),
                new ExitCommand(io),
                new ExecuteScriptCommand(io),
                new HistoryCommand(commandManager, io),
                new LogoutCommand()
                ).forEach(commandManager::addCommand);
        return commandManager;
    }

    public LRUCache<AbstractCommand> getHistory() {
        return this.history;
    }
}
