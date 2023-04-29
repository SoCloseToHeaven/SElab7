package com.soclosetoheaven.common.net.auth;

import com.soclosetoheaven.common.collectionmanagers.CollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidAuthCredentialsException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public interface UserManager {
    Response login(RequestBody requestBody) throws InvalidAuthCredentialsException;
    Response register(RequestBody requestBody) throws InvalidRequestException;

    User getUserByID(Long id); // null if user with such id is not present

    User getUserByAuthCredentials(AuthCredentials authCredentials); // null if user with such login is not present


    boolean checkIfAuthorized(AuthCredentials authCredentials);
}
