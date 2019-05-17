package ru.tinkoff.fintech.notes.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private String key;

    private HttpStatus httpStatus;

    private Object[] args;

    public ApiException(HttpStatus httpStatus, String key, Object... args) {
        this.key = key;
        this.httpStatus = httpStatus;
        this.args = args;
    }

    public ApiException(HttpStatus httpStatus, String key) {
        this.key = key;
        this.httpStatus = httpStatus;
    }
}
