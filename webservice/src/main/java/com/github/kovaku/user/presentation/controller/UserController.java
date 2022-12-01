package com.github.kovaku.user.presentation.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.kovaku.user.presentation.domain.User;
import com.github.kovaku.user.presentation.domain.UserRequest;
import com.github.kovaku.user.service.UserService;
import com.github.kovaku.user.service.exception.UserNotFoundException;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Retrieves user by id", produces = MediaTypes.HAL_JSON_VALUE)
    @GetMapping("/v1/user/{userId}")
    public User getUserById(@PathVariable(name = "userId") Integer id) throws UserNotFoundException {
        return enrichUserWithSelfLinks(userService.getUserById(id));
    }

    @ApiOperation(value = "Retrieves user by name", produces = MediaTypes.HAL_JSON_VALUE)
    @GetMapping("/v1/user")
    public User getUserById(@RequestParam(name = "name") String name) throws UserNotFoundException {
        return enrichUserWithSelfLinks(userService.getUserByName(name));
    }

    @GetMapping("/v1/users")
    @ApiOperation(value = "Retrieves all users", produces = MediaTypes.HAL_JSON_VALUE)
    public List<User> getAllUsers() {
        return enrichUserWithSelfLinks(userService.geAllUsers());
    }

    @PostMapping("/v1/user")
    @ApiOperation(value = "Creates new user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Validated @RequestBody UserRequest user) {
        return enrichUserWithSelfLinks(userService.createUser(user));
    }

    @PutMapping("/v1/user/{userId}")
    @ApiOperation(value = "Updates user by id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@PathVariable(name = "userId") Integer id, @Validated @RequestBody UserRequest user) throws UserNotFoundException {
        return enrichUserWithSelfLinks(userService.updateUser(id, user));
    }

    @DeleteMapping("/v1/user/{userId}")
    @ApiOperation(value = "Deletes user by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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

    //TODO: IT
}
