package com.github.kovaku.user.service;

import java.util.List;

import com.github.kovaku.user.presentation.domain.User;
import com.github.kovaku.user.presentation.domain.UserRequest;
import com.github.kovaku.user.service.exception.UserNotFoundException;

public interface UserService {
    User getUserById(Integer id) throws UserNotFoundException;
    User getUserByName(String name) throws UserNotFoundException;
    List<User> geAllUsers();
    User createUser(UserRequest userRequest);
    User updateUser(Integer id, UserRequest userRequest) throws UserNotFoundException;;
    void deleteUser(Integer id) throws UserNotFoundException;
}
