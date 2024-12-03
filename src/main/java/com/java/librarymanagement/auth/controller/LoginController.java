package com.java.librarymanagement.auth.controller;

import com.java.librarymanagement.auth.model.AuthRequest;
import com.java.librarymanagement.auth.service.LoginService;
import com.java.librarymanagement.utils.RestHelper;
import com.java.librarymanagement.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private LoginService loginService;


}
