package com.kushal.ecommerce.exception;

import com.kushal.ecommerce.dto.ApiResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleResourceNotFound(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND)
                .body(new ApiResponseDTO<>("Not Found!", e.getMessage()));
    }

    // 409 - Already exists
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleAlreadyExists(AlreadyExistsException e){
        return ResponseEntity.status(CONFLICT)
                .body(new ApiResponseDTO<>("Already Exists!", e.getMessage()));
    }

    // 400 - Illegal argument (like category not found case)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.badRequest()
                .body(new ApiResponseDTO<>("Invalid Data!", e.getMessage()));
    }

    // 400 - Validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleValidation(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .findFirst().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation Error!");

        return ResponseEntity.badRequest().body(new ApiResponseDTO<>("Validation Failed!", errorMessage));
    }

    // 500 - Fallback handler (very important)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<String>> handleGeneralException(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDTO<>("Something Went Wrong!", e.getMessage()));
    }
}
