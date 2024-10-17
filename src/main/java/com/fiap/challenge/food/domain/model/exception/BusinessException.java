package com.fiap.challenge.food.domain.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final String message;

    private final String code;

    public BusinessException(HttpStatus httpStatus, String message, String code) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }
}
