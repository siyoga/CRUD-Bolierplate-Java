package crudboilerplatejava.exceptions;

import lombok.Getter;

@Getter
public class ExceptionMessages {
    public final String invalidCredentials = "Sorry, credentials are invalid";
    public final String userNotFound = "User not found";
    public final String usernameAlreadyTaken = "This username already taken";
    public final String emailAlreadyTaken = "This email already taken";
}
