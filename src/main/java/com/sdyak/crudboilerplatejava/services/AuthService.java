package com.sdyak.crudboilerplatejava.services;

import com.sdyak.crudboilerplatejava.dto.TokenPairDTO;
import com.sdyak.crudboilerplatejava.dto.UserDTO;
import com.sdyak.crudboilerplatejava.exceptions.IncorrectCredException;
import com.sdyak.crudboilerplatejava.model.User;
import com.sdyak.crudboilerplatejava.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenPairService tokenPairService;

    @Autowired
    private UserRepository userRepository;

    public void register(UserDTO userInfo) {
        userService.create(userInfo);
    }

    @SneakyThrows
    public TokenPairDTO login(String username, String password) {
        User existUser = userRepository.findUserByUsername(username);

        if (existUser == null) {
            throw new IncorrectCredException();
        }

        if (userService.checkPassword(password, existUser.getSalt(), existUser.getHashedPassword())) {
            throw new IncorrectCredException();
        }

        return tokenPairService.generateTokenPair(existUser);
    }
}
