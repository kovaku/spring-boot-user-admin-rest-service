package com.github.kovaku.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.kovaku.user.persistence.UserRepository;
import com.github.kovaku.user.presentation.domain.User;
import com.github.kovaku.user.presentation.domain.UserRequest;
import com.github.kovaku.user.service.exception.UserNotFoundException;

@Service
public class RepositoryBasedUserService implements UserService {

    private final UserRepository userRepository;

    public RepositoryBasedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Integer id) throws UserNotFoundException {
        return userRepository
            .findById(id)
            .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByName(String name) throws UserNotFoundException {
        return Optional.ofNullable(userRepository.findUserByName(name))
            .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> geAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserRequest userRequest) {
        return userRepository.save(convertUserRequestToUser(userRequest));
    }

    @Override
    public User updateUser(Integer id, UserRequest userRequest) throws UserNotFoundException {
        User user = getUserById(id);
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    private User convertUserRequestToUser(UserRequest userRequest) {
        return User.builder()
            .withName(userRequest.getName())
            .withEmail(userRequest.getEmail())
            .build();
    }
}
