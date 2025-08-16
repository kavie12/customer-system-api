package com.kavie12.customer_system_api.config;

import com.kavie12.customer_system_api.dto.response.StandardResponseDto;
import com.kavie12.customer_system_api.exception.NoSuchUserExistsException;
import com.kavie12.customer_system_api.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(
                new StandardResponseDto(HttpStatus.BAD_REQUEST.value(), "Invalid input found.", errors),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardResponseDto> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ResponseEntity<>(
                new StandardResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NoSuchUserExistsException.class)
    public ResponseEntity<StandardResponseDto> handleNoSuchUserExistsException(NoSuchUserExistsException e) {
        return new ResponseEntity<>(
                new StandardResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardResponseDto> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(
                new StandardResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardResponseDto> handleBadCredentialsException(BadCredentialsException e) {
        return new ResponseEntity<>(
                new StandardResponseDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null),
                HttpStatus.UNAUTHORIZED
        );
    }

}
