package ru.tinkoff.fintech.notes.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tinkoff.fintech.notes.dto.Error;
import ru.tinkoff.fintech.notes.exception.ApiException;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity handleApiException(ApiException e) {
        Error error = new Error(e.getKey());
        if (e.getKey() != null) {
            String message = messageSource.getMessage(e.getKey(), e.getArgs(), Locale.US);
            error.setInfo(message);
        }
        return ResponseEntity.status(e.getHttpStatus()).body(error);
    }
}
