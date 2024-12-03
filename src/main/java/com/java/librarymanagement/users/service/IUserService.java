package com.java.librarymanagement.users.service;

import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.model.UserDTO;
import com.java.librarymanagement.utils.IGenericCrudService;

public interface IUserService extends IGenericCrudService<User, UserDTO> {

    /**
     * Fetches the authenticated  user info.
     *
     * @return The user dto
     */
    UserDTO fetchSelfInfo();

}

