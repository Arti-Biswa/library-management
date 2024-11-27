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
        return usersRepository.findById(id)
                .orElseThrow(()->new Exception(UserConstants.NOT_FOUND));
    }

    @Override
    public String update(long id, @NonNull User user) {
        try {
            User selectedUser = findById(id);
            selectedUser.setName(user.getName());
            selectedUser.setCourse(user.getCourse());
            usersRepository.save(selectedUser);
            return UserConstants.UPDATE_SUCCESSFUL;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteById(long id) {
        //TODO: Validate for deletion
        usersRepository.deleteById(id);
        return UserConstants.DELETE_SUCCESSFUL;
    }


    public List<User> getAllUsers() {
        return findAll(); // Fetches all users from the database
    }

    public User getUserById(long id) throws Exception {
        return findById(id); // Fetches a user by their ID
    }

}
