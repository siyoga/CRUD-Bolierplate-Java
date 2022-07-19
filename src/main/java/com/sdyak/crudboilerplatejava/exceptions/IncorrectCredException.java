package com.sdyak.crudboilerplatejava.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IncorrectCredException extends Exception {
    public IncorrectCredException() {
        super("Incorrect credentials");
    }
}
