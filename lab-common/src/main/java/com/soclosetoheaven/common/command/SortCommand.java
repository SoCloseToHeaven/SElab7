package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.model.Dragon;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

import java.util.Collections;
import java.util.List;

public class SortCommand extends AbstractCommand{
    private final DragonCollectionManager collectionManager;
    public SortCommand(DragonCollectionManager collectionManager) {
        super("sort");
        this.collectionManager = collectionManager;
    }

    public SortCommand() {
        this(null);
    }

    @Override
    public Response execute(RequestBody requestBody) {
        List<Dragon> dragons = collectionManager.getCollection();
        Collections.sort(dragons);
        StringBuilder stringBuilder = new StringBuilder("Collection was sort in default order\n");
        dragons.forEach(elem -> stringBuilder.append(elem.toString()).append("\n"));
        return ResponseFactory.createResponse(stringBuilder.toString().trim());
    }

    @Override
    public Request toRequest(String[] args) throws ManagingException {
        return super.toRequest(null);
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "sort",
                " - sorts the collection in ascending order by ID"
        );
    }
}
