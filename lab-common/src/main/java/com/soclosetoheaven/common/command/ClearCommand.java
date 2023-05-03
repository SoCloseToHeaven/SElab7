package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidAccessException;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.auth.User;
import com.soclosetoheaven.common.net.auth.UserManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

import java.util.List;

public class ClearCommand extends AbstractCommand{

    private final DragonCollectionManager collectionManager;
    private final UserManager userManager;
    public ClearCommand(DragonCollectionManager collectionManager, UserManager userManager) {
        super("clear");
        this.collectionManager = collectionManager;
        this.userManager = userManager;
    }

    public ClearCommand() {
        this(null, null);
    }

    @Override
    public Response execute(RequestBody requestBody) throws InvalidAccessException{
        List<Dragon> dragons = collectionManager.getCollection();


        User user = userManager.getUserByAuthCredentials(requestBody.getAuthCredentials());

        dragons
                .stream()
                .filter(elem -> elem.getCreatorId() == user.getID())
                .forEach(elem -> collectionManager.removeByID(elem.getID()));
        return ResponseFactory
                .createResponse("All possible elements removed");
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        return super.toRequest(null);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "clear",
                " - removes all elements out of collection"
        );
    }
}
