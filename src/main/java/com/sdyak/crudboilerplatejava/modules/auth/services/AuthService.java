package com.sdyak.crudboilerplatejava.modules.auth.services;

import com.sdyak.crudboilerplatejava.misc.dto.UserDTO;
import com.sdyak.crudboilerplatejava.modules.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    public void register(UserDTO userInfo) {
        userService.create(userInfo);
    }

    public
}
