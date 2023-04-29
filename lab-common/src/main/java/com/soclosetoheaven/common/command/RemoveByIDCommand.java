package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidAccessException;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;


public class RemoveByIDCommand extends AbstractCommand{

    private final DragonCollectionManager cm;

    private final UserManager um;
    public RemoveByIDCommand(DragonCollectionManager cm, UserManager um) {
        super("remove_by_id");
        this.cm = cm;
        this.um = um;
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidRequestException{
        String[] args = requestBody.getArgs();
        if (args.length < 1 || !args[0].chars().allMatch(Character::isDigit))
            throw new InvalidRequestException();
        User user = um.getUserByAuthCredentials(requestBody.getAuthCredentials());
        int id = Integer.parseInt(args[0]);
        if (!user.isAdmin() || cm.getByID(id).getCreatorId() != user.getID())
            throw new InvalidAccessException();
        if (!cm.removeByID(id))
            throw new InvalidRequestException("Unsuccessfully!");
        return ResponseFactory.createResponse("Successfully deleted!");
    }

    @Override
    public Request toRequest(String[] args) {
        if (args.length > 0 && args[0].chars().allMatch(Character::isDigit))
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
