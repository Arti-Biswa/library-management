package com.java.librarymanagement.users.service;


import com.java.librarymanagement.auth.helper.UserInfoDetails;
import com.java.librarymanagement.users.mapper.UserMapper;
import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.model.UserDTO;
import com.java.librarymanagement.users.repository.UserRepository;
import com.java.librarymanagement.utils.exception.GlobalExceptionWrapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.java.librarymanagement.utils.constants.UserConstants.*;


@Service
public class UserService implements IUserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        List<User> user = this.userRepository.findAll();
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO save(@NonNull User user) {
        //Check if same user already exists during signup
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("LIBRARIAN");
        User savedUser = this.userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO findById(long id) {
        User user = this.userRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
        return UserMapper.toDTO(user);
    }

    public UserDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())));
    }

    public Optional<UserDTO> findByEmail(@NonNull String emailId) {
        Optional<User> user = this.userRepository.findByEmail(emailId);
        return UserMapper.toDTO(user);
    }

    @Override
    public String update(long id, @NonNull User entity) {
        UserDTO authenticatedUser = fetchSelfInfo();
        User userEntity = UserMapper.toEntity(authenticatedUser);

        //Allow update by admin to the instructor info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))) {
            userEntity = UserMapper.toEntity(findById(id));
        }

        userEntity.setCourse(entity.getCourse());
        userEntity.setName(entity.getName());
        userEntity.setPhoneNumber(entity.getPhoneNumber());

        this.userRepository.save(userEntity);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        UserDTO authenticatedUser = fetchSelfInfo();
        User userEntity = UserMapper.toEntity(authenticatedUser);

        //Allow to delete by admin to the instructor info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase("ADMIN"))) {
            userEntity = UserMapper.toEntity(findById(id));
        }

        this.userRepository.deleteById(userEntity.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, USER);
    }

}
