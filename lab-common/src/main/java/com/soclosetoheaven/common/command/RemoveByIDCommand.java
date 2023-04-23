package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.net.factorн.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;


public class RemoveByIDCommand extends AbstractCommand{

    private final FileCollectionManager cm;
    public RemoveByIDCommand(FileCollectionManager cm) {
        super("remove_by_id");
        this.cm = cm;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        int id = Integer.parseInt(requestBody.getArgs()[0]);
        if (cm.getCollection().removeIf(elem -> elem.getId() == id)) {
            return ResponseFactory.createResponse(
                    "Successfully removed element with ID: %s".formatted(id)
            );
        }
        return ResponseFactory.createResponseWithException(
                new InvalidRequestException("No elements found with ID: %s".formatted(id))
        );
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
