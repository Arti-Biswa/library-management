package com.java.librarymanagement.users.service;


import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.model.UserDTO;
import com.java.librarymanagement.utils.IGenericCrudService;

public interface UserService extends IGenericCrudService<User, UserDTO> {

    /**
     * Fetches the authenticated instructor info.
     *
     * @return The instructor dto
     */
    User fetchSelfInfo();

    String updateEntity(User user);
}
