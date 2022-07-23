package crudboilerplatejava.services;

import crudboilerplatejava.dto.TokenPairDTO;
import crudboilerplatejava.dto.UserDTO;
import crudboilerplatejava.exceptions.ExceptionMessages;
import crudboilerplatejava.exceptions.FlexibleException;
import crudboilerplatejava.model.User;
import crudboilerplatejava.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenPairService tokenPairService;

    @Autowired
    private UserRepository userRepository;

    ExceptionMessages exceptionMessages;

    public void register(UserDTO userInfo) {
        userService.create(userInfo);
    }

    @SneakyThrows
    public TokenPairDTO login(String username, String password) {
        User existUser = userRepository.findUserByUsername(username);

        if (existUser == null) {
            throw new FlexibleException(exceptionMessages.userNotFound, HttpStatus.NOT_FOUND);
        }

        if (userService.checkPassword(password, existUser.getSalt(), existUser.getHashedPassword())) {
            throw new FlexibleException(exceptionMessages.invalidCredentials, HttpStatus.BAD_REQUEST);
        }

        return tokenPairService.generateTokenPair(existUser);

    }
}
