package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.net.factorн.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public class SortCommand extends AbstractCommand{
    private final FileCollectionManager cm;
    public SortCommand(FileCollectionManager cm) {
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
