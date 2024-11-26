package com.java.librarymanagement.users.controller;

import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User saveUser(@Validated @RequestBody User users)
    {
        return userService.save(users);
    }
}
