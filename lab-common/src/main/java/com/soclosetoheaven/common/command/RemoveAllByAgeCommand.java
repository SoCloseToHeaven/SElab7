package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;


public class RemoveAllByAgeCommand extends AbstractCommand{
    private final DragonCollectionManager collectionManager;

    private final UserManager userManager;
    public RemoveAllByAgeCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        super("remove_all_by_age");
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    public RemoveAllByAgeCommand() {
        this(null,null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        String[] args = requestBody.getArgs();
        if (args.length < MIN_ARGS_SIZE || !args[FIRST_ARG].chars().allMatch(Character::isDigit)) {
            throw new InvalidRequestException();
        }

        long age = Long.parseLong(args[FIRST_ARG]);

        User user = userManager.getUserByAuthCredentials(requestBody.getAuthCredentials());

        collectionManager
                .getCollection().stream()
                .filter(elem -> elem.getCreatorId() == user.getID())
                .filter(elem -> elem.getAge() == age)
                .forEach(elem -> collectionManager.removeByID(elem.getID()));
        return ResponseFactory.createResponse("Removed elements with age: %s".formatted(age));
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
