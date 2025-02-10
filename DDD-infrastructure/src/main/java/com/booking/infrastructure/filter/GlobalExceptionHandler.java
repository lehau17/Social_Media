package com.booking.infrastructure.filter;

import com.booking.infrastructure.model.entity.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse()
                .setException(e)
                .setMessage(e.getMessage())
                .setSuccess(false).setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> runtimeException(Exception e) {
        log.error(e.getMessage());
        log.info("Error runtime exception");
        ErrorResponse errorResponse = new ErrorResponse()
                .setException(e)
                .setMessage(e.getMessage())
                .setSuccess(false).setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }



    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestException(BadRequestException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse()
                .setException(e)
                .setMessage(e.getMessage())
                .setSuccess(false).setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authenticationException(AuthenticationException e) {
        log.error(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse()
                .setException(e)
                .setMessage(e.getMessage())
                .setSuccess(false).setCode(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
