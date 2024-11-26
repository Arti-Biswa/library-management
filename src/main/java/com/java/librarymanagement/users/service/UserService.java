package com.java.librarymanagement.users.service;


import com.java.librarymanagement.constants.UserConstants;
import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{
    @Autowired
    private UserRepository usersRepository;


    @Override
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public User save(@NonNull User user) {
        return usersRepository.save(user);
    }

    @Override
    public User findById(long id) throws Exception {
        //TODO: Handle Not Found Exception with its own http codes.
        return usersRepository.findById(id)
                .orElseThrow(()->new Exception(UserConstants.NOT_FOUND));
    }

    @Override
    public String update(@NonNull User user) {
        //TODO: Validate for update
        usersRepository.save(user);
        return UserConstants.UPDATE_SUCCESSFUL;
    }

    @Override
    public String deleteById(long id) {
        //TODO: Validate for deletion
        usersRepository.deleteById(id);
        return UserConstants.DELETE_SUCCESSFUL;
    }

}
