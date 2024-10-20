package com.management.book.exceptionHandler;

import com.management.book.exception.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExcHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> RemainingExceptionHandler(Exception e) {

        ExceptionMessage message = new ExceptionMessage();
        message.setMessage(e.getMessage());
        message.setStatus(HttpStatus.BAD_REQUEST.value());
        message.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<> (message, HttpStatus.BAD_REQUEST);
    }
}
