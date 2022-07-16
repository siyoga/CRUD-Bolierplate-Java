package com.sdyak.crudboilerplatejava.misc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ConflictException extends Exception {
    public ConflictException(String message) {
        super(message);
    }
}
