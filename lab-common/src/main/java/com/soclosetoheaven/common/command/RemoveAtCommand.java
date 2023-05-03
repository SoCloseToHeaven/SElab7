package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.InvalidAccessException;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class RemoveAtCommand extends AbstractCommand {

    private final DragonCollectionManager collectionManager;

    private final UserManager userManager;
    public RemoveAtCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        super("remove_at");
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    public RemoveAtCommand() {
        this(null, null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        String[] args = requestBody.getArgs();

        if (args.length < MIN_ARGS_SIZE || !args[FIRST_ARG].chars().allMatch(Character::isDigit))
            throw new InvalidRequestException();

        User user = userManager.getUserByAuthCredentials(requestBody.getAuthCredentials());
        int index = Integer.parseInt(args[FIRST_ARG]);
        Dragon dragon = collectionManager.get(index);
        if (dragon == null)
            throw new InvalidRequestException("No such element!");
        if (!user.isAdmin() && dragon.getCreatorId() != user.getID())
            throw new InvalidAccessException();
        if (collectionManager.remove(index) == null)
            throw new InvalidRequestException("Unsuccessfully!");
        return ResponseFactory.createResponseWithDragon("Deleted", dragon);
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        if (args.length >= MIN_ARGS_SIZE && args[FIRST_ARG].chars().allMatch(Character::isDigit))
            return super.toRequest(args);
        throw new InvalidCommandArgumentException();
    }

    @Override
    String getUsage() {
        return "%s%s".formatted(
                "remove_at {index}",
                " - removes element with the same index from collection"
        );
    }
}
