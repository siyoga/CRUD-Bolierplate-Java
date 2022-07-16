package com.sdyak.crudboilerplatejava.modules.auth;

import com.sdyak.crudboilerplatejava.misc.exceptions.BadRequestException;
import com.sdyak.crudboilerplatejava.misc.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthController {


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserDTO newUser) throws BadRequestException {

    }
}
