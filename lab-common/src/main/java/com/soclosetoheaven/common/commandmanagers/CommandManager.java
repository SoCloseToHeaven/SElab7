package com.soclosetoheaven.common.commandmanagers;

import com.soclosetoheaven.common.command.AbstractCommand;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;

import java.util.Map;

public interface CommandManager<ReturningType, ManagingType> {

    ReturningType manage(ManagingType t) throws Exception;

    Map<String, ? extends AbstractCommand> getCommands();

    void addCommand(AbstractCommand command);


}
