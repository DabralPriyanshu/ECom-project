package com.example.Fake_Commerce_App.exceptions;


import com.example.Fake_Commerce_App.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleAllGeneralException(Exception e) {
        return ResponseEntity.status(500).body(ApiResponse.error("Internal server error!!!", "Something went wrong!!!"));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage(),
                "Resource not found!!!"));
    }

}
