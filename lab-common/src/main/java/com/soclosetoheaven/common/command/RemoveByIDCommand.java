package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidAccessException;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;


public class RemoveByIDCommand extends AbstractCommand{

    private final DragonCollectionManager collectionManager;

    private final UserManager userManager;
    public RemoveByIDCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        super("remove_by_id");
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    public RemoveByIDCommand() {
        this(null, null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        String[] args = requestBody.getArgs();
        if (args.length < MIN_ARGS_SIZE || !args[FIRST_ARG].chars().allMatch(Character::isDigit))
            throw new InvalidRequestException();
        User user = userManager.getUserByAuthCredentials(requestBody.getAuthCredentials());
        int id = Integer.parseInt(args[FIRST_ARG]);
        Dragon dragon = collectionManager.getByID(id);
        if (dragon == null)
            throw new InvalidRequestException("No such element!");
        if (!user.isAdmin() && dragon.getCreatorId() != user.getID())
            throw new InvalidAccessException();
        if (!collectionManager.removeByID(id))
            throw new InvalidRequestException("Unsuccessfully!");
        return ResponseFactory.createResponse("Successfully deleted!");
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        if (args.length >= MIN_ARGS_SIZE && args[FIRST_ARG].chars().allMatch(Character::isDigit))
            return super.toRequest(args);
        throw new InvalidCommandArgumentException();
    }

    public String getUsage() {
        return "%s%s".formatted(
                "remove_all_by_age {age}",
                " - removes all collection elements with the same age"
        );
    }
}
