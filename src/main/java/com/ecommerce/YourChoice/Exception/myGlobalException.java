package com.ecommerce.YourChoice.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class myGlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> myInvalidArgExcp(MethodArgumentNotValidException input) {
        Map<String , String> response = new HashMap<>();
        input.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError)err).getField();
            String fieldMessage = err.getDefaultMessage();
            response.put(fieldName , fieldMessage);
        });
        return new ResponseEntity<Map<String , String>>(response , HttpStatus.BAD_REQUEST);
    }
}
