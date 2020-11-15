package com.github.kovaku.user.presentation.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.kovaku.user.presentation.domain.User;
import com.github.kovaku.user.presentation.domain.UserRequest;
import com.github.kovaku.user.service.UserService;
import com.github.kovaku.user.service.exception.UserNotFoundException;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/user/{userId}")
    public User getUserById(@PathVariable(name = "userId") Integer id) throws UserNotFoundException {
        return enrichUserWithSelfLinks(userService.getUserById(id));
    }

    @GetMapping("/v1/users")
    public List<User> getAllUsers() {
        return enrichUserWithSelfLinks(userService.geAllUsers());
    }

    @PostMapping("/v1/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Validated @RequestBody UserRequest user) {
        return enrichUserWithSelfLinks(userService.createUser(user));
    }

    @PutMapping("/v1/user/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@PathVariable(name = "userId") Integer id, @Validated @RequestBody UserRequest user) throws UserNotFoundException {
        return enrichUserWithSelfLinks(userService.updateUser(id, user));
    }

    @DeleteMapping("/v1/user/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable(name = "userId") Integer id) throws UserNotFoundException {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private User enrichUserWithSelfLinks(User user) {
        return user.add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
    }

    private List<User> enrichUserWithSelfLinks(List<User> users) {
        users.forEach(this::enrichUserWithSelfLinks);
        return users;
    }

    //TODO: New Api
    //TODO: Readme
    //TODO: IT
    //TODO: APIdoc

}
