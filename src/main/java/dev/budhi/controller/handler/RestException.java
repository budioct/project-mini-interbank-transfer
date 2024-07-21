package dev.budhi.controller.handler;

import dev.budhi.utilitis.Constants;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class RestException {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestResponse.restError<String>> constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RestResponse.restError.<String>builder()
                        .errors(exception.getMessage())
                        .status_code(Constants.BAD_REQUEST)
                        .message(Constants.VALIDATION_MESSAGE)
                        .build());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<RestResponse.restError<String>> responseStatusException(ResponseStatusException exception) {
        return ResponseEntity.status(exception.getStatusCode())
                .body(RestResponse.restError.<String>builder()
                        .errors(exception.getReason())
                        .status_code(exception.getStatusCode().value())
                        .message(Constants.BAD_REQUEST_MESSAGE)
                        .build());
    }

}
