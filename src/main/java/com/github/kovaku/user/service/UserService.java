package com.github.kovaku.user.service;

import java.util.List;

import com.github.kovaku.user.presentation.domain.User;
import com.github.kovaku.user.presentation.domain.UserRequest;
import com.github.kovaku.user.service.exception.UserNotFoundException;

public interface UserService {
    User getUserById(Integer id) throws UserNotFoundException;
    List<User> geAllUsers();
    User createUser(UserRequest user);
    User updateUser(Integer id, UserRequest user) throws UserNotFoundException;;
    void deleteUser(Integer id) throws UserNotFoundException;
}
