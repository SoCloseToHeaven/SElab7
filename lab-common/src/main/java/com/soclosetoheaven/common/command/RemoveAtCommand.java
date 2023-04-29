package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.exceptions.InvalidAccessException;
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

    private final DragonCollectionManager cm;

    private final UserManager um;
    public RemoveAtCommand(DragonCollectionManager cm, UserManager um) {
        super("remove_at");
        this.cm = cm;
        this.um = um;
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        String[] args = requestBody.getArgs();
        if (args.length < 1 || !args[0].chars().allMatch(Character::isDigit))
            throw new InvalidRequestException();

        User user = um.getUserByAuthCredentials(requestBody.getAuthCredentials());
        int index = Integer.parseInt(args[0]);
        Dragon dragon = cm.get(index);
        if (dragon == null)
            throw new InvalidRequestException("No such element!");
        if (!user.isAdmin() || dragon.getCreatorId() != user.getID())
            throw new InvalidAccessException();
        if (cm.remove(index) == null)
            throw new InvalidRequestException("Unsuccessfully!");
        return ResponseFactory.createResponseWithDragon("Deleted", dragon);
    }

    @Override
    public Request toRequest(String[] args) {
        if (args.length > 0 && args[0].chars().allMatch(Character::isDigit))
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
