package com.soclosetoheaven.common.command;

import com.soclosetoheaven.common.collectionmanagers.DragonCollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidCommandArgumentException;
import com.soclosetoheaven.common.exceptions.InvalidFieldValueException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.net.factory.ResponseFactory;
import com.soclosetoheaven.common.net.messaging.Request;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;


public class CountLessThanAgeCommand extends AbstractCommand {

    private final DragonCollectionManager cm;
    public CountLessThanAgeCommand(DragonCollectionManager cm) {
        super("count_less_than_age");
        this.cm = cm;
    }


    @Override
    public Response execute(RequestBody requestBody)  throws InvalidRequestException {
        String[] args = requestBody.getArgs();
        if (args.length < 1 || !args[0].chars().allMatch(Character::isDigit))
            throw new InvalidRequestException();
        Long age = Long.parseLong(requestBody.getArgs()[0]);
        Long count = cm.getCollection().stream().filter(elem -> elem.getAge() < age).count();
        return ResponseFactory.createResponse("%s - %s: %s"
                .formatted(count, "elements less than age", age)
        );
    }

    @Override
    public Request toRequest(String[] args) {
        if (args.length > 0 && args[0].chars().allMatch(Character::isDigit))
            return super.toRequest(args);
        throw new InvalidCommandArgumentException();
    }

    @Override
    public String getUsage() {
        return "%s%s".formatted(
                "count_less_than_age {age}",
                " - counts elements of collection which age is lower than {age}(can't be null)"
        );
    }
}
