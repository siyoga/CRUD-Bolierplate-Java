package crudboilerplatejava.controller;

import crudboilerplatejava.dto.LoginDTO;
import crudboilerplatejava.dto.TokenPairDTO;
import crudboilerplatejava.dto.UserDTO;
import crudboilerplatejava.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserDTO newUser) {
        authService.register(newUser);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public TokenPairDTO login(@RequestBody LoginDTO  cred) {
        return authService.login(cred.getUsername(), cred.getPassword());
    }
}
