package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class SortCommand extends AbstractCommand{
    private final DragonCollectionManager cm;
    public SortCommand(DragonCollectionManager cm) {
        super("sort");
        this.cm = cm;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        cm.sort();
        return ResponseFactory.createResponse("Collection was sorted in default order");
    }

    @Override
    public Request toRequest(String[] args) {
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
