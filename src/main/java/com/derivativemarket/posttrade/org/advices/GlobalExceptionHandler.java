package com.derivativemarket.posttrade.org.advices;

import com.derivativemarket.posttrade.org.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    public ResponseEntity<ApiResponse<?>> handledResourceNotFound(ResourceNotFoundException exception){
        ApiError apiError=ApiError.builder().
                status(HttpStatus.NOT_FOUND).
                message(exception.getMessage()).
                build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handledInternalServerError(Exception exception){
        ApiError apiError=ApiError.builder().
                status(HttpStatus.INTERNAL_SERVER_ERROR).
                message(exception.getMessage()).
                build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handledInputValidationErrors(MethodArgumentNotValidException exception){
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
        return buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse(apiError),apiError.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException ex){
        ApiError apiError=ApiError.builder().
                status(HttpStatus.UNAUTHORIZED).
                message(ex.getLocalizedMessage()).
                build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(JwtException ex){
        ApiError apiError=ApiError.builder().
                status(HttpStatus.UNAUTHORIZED).
                message(ex.getLocalizedMessage()).
                build();
        return buildErrorResponseEntity(apiError);
    }
}
