package com.ecommerce.YourChoice.Exception;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class myGlobalException extends RuntimeException {
    public myGlobalException(String message) {
        super(message);
    }
}
