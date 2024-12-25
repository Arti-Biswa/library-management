package com.java.librarymanagement.users.service;


import com.company.coursemanager.utils.IGenericCrudService;
import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.model.UserDTO;

public interface UserService extends IGenericCrudService<User, UserDTO> {

    /**
     * Fetches the authenticated instructor info.
     *
     * @return The instructor dto
     */
    User fetchSelfInfo();

    /**
     * Updates the user entity.
     *
     * @param user The user entity to be updated.
     * @return The confirmation message on whether the user is updated or not.
     */
    String updateEntity(User user);
}

