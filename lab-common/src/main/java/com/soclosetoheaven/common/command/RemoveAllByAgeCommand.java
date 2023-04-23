package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.FileCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.net.factorн.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RemoveAllByAgeCommand extends AbstractCommand{
    private final FileCollectionManager cm;
    public RemoveAllByAgeCommand(FileCollectionManager cm) {
        super("remove_all_by_age");
        this.cm = cm;
    }

    @Override
    public Response execute(RequestBody requestBody) {
        Long age = Long.parseLong(requestBody.getArgs()[0]);
        cm.setCollection(cm
                .getCollection()
                .stream()
                .filter(elem -> !elem.getAge().equals(age))
                .collect(Collectors.toCollection(ArrayList::new))
        );
        return ResponseFactory.createResponse("%s: %s"
                .formatted("Removed elements with age", age)
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
