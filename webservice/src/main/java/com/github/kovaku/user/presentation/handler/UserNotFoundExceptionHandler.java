package com.github.kovaku.user.presentation.handler;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.kovaku.user.service.exception.UserNotFoundException;

@ControllerAdvice
public class UserNotFoundExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Void> handleDeliveryException(UserNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .cacheControl(CacheControl.noStore())
            .build();
    }
}
