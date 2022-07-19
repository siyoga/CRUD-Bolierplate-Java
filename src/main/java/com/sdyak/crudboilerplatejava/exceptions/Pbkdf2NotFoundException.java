package com.sdyak.crudboilerplatejava.exceptions;

public class Pbkdf2NotFoundException extends RuntimeException {
    public Pbkdf2NotFoundException() {
        super("Pbkdf2 key not found.");
    }
}
