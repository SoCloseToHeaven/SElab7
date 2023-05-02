package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidAccessException;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.RequestFactory;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.*;


public class UpdateCommand extends AbstractCommand{

    private final DragonCollectionManager collectionManager;

    private final BasicIO io;

    private final UserManager userManager;

    public UpdateCommand(DragonCollectionManager collectionManager, BasicIO io, UserManager userManager) {
        super("update");
        this.collectionManager = collectionManager;
        this.io = io;
        this.userManager = userManager;
    }

    public UpdateCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        this(collectionManager, null, userManager);
    }

    public UpdateCommand(BasicIO io) {
        this(null, io, null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        String[] args = requestBody.getArgs();
        if (
                !(requestBody instanceof RequestBodyWithDragon) ||
                args.length < MIN_ARGS_SIZE ||
                !args[FIRST_ARG].chars().allMatch(Character::isDigit)
        )
           throw new InvalidRequestException("Unable to update element");

        int id = Integer.parseInt(args[FIRST_ARG]);

        Dragon dragon = collectionManager.getByID(id);
        if (dragon == null)
            throw new InvalidRequestException("No dragon with such ID");
        User user = userManager.getUserByAuthCredentials(requestBody.getAuthCredentials());
        if (!user.isAdmin() && dragon.getCreatorId() != user.getID())
            throw new InvalidAccessException();
        if (!collectionManager.update(((RequestBodyWithDragon) requestBody).getDragon(), id))
            throw new InvalidRequestException("Unsuccessfully!");
        return ResponseFactory.createResponse("Successfully updated!");
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        if (args.length >= MIN_ARGS_SIZE && args[FIRST_ARG].chars().allMatch(Character::isDigit))
            return RequestFactory.createRequestWithDragon(getName(), args, io);
        throw new InvalidCommandArgumentException();
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "update {ID} {element}",
                " - updates data(in the same way as in {add} command) with the same ID"
        );
    }
}
