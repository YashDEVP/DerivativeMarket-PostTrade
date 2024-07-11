package com.derivativemarket.posttrade.org.advices;

import com.derivativemarket.posttrade.org.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/*if we don't add this annotation then we will not get custom exception on
postman it will Display your custom exception on UI */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handledResourceNotFound(ResourceNotFoundException exception){
        ApiError apiError=ApiError.builder().
                status(HttpStatus.NOT_FOUND).
                message(exception.getMessage()).
                build();
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handledInternalServerError(Exception exception){
        ApiError apiError=ApiError.builder().
                status(HttpStatus.INTERNAL_SERVER_ERROR).
                message(exception.getMessage()).
                build();
        return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handledInputValidationErrors(MethodArgumentNotValidException exception){
       /*combine all the error */
        List<String> errors=exception.
                getBindingResult()
                .getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError=ApiError.builder().
                status(HttpStatus.BAD_REQUEST).
                message("input validation failed")
                .subErrors(errors).
                build();
        return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
    }
}
