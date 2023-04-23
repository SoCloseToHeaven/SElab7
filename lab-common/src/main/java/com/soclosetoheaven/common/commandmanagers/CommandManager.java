package com.soclosetoheaven.common.commandmanagers;

import com.soclosetoheaven.common.command.AbstractCommand;

import java.util.Map;

public interface CommandManager<ReturningType, ManagingType> {

    ReturningType manage(ManagingType t);

    Map<String, ? extends AbstractCommand> getCommands();

    void addCommand(AbstractCommand command);

}
