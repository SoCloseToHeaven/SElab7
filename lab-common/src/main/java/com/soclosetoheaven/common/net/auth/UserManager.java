package com.soclosetoheaven.common.net.auth;

import com.soclosetoheaven.common.collectionmanagers.CollectionManager;
import com.soclosetoheaven.common.exceptions.InvalidAuthCredentialsException;
import com.soclosetoheaven.common.exceptions.InvalidRequestException;
import com.soclosetoheaven.common.exceptions.ManagingException;
import com.soclosetoheaven.common.net.messaging.RequestBody;
import com.soclosetoheaven.common.net.messaging.Response;

public interface UserManager {

    int FIRST_ARG = 0;
    Response login(RequestBody requestBody) throws ManagingException;
    Response register(RequestBody requestBody) throws ManagingException;

    /**
     *
     * @param id user's id
     * @return null if user with such id is not present
     */
    User getUserByID(Long id);

    /**
     *
     * @param authCredentials
     * @return null if user with such login is not present
     */

    User getUserByAuthCredentials(AuthCredentials authCredentials);


    boolean checkIfAuthorized(AuthCredentials authCredentials);
}
