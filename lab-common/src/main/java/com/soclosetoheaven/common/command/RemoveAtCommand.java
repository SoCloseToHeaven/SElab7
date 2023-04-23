package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.net.factorн.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class RemoveAtCommand extends AbstractCommand {

    private final FileCollectionManager cm;
    public RemoveAtCommand(FileCollectionManager cm) {
        super("remove_at");
        this.cm = cm;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        int index = Integer.parseInt(requestBody.getArgs()[0]);
        try {
            cm.getCollection().remove(index);
            return new Response("Successfully removed element with index: %s".formatted(index));
        } catch (IndexOutOfBoundsException e) {
            return ResponseFactory.createResponseWithException(
                    new InvalidRequestException(e.getMessage())
            );
        }
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
