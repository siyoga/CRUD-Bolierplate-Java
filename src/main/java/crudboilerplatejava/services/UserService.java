package crudboilerplatejava.services;

import crudboilerplatejava.dto.UserDTO;
import crudboilerplatejava.exceptions.ExceptionMessages;
import crudboilerplatejava.exceptions.FlexibleException;
import crudboilerplatejava.model.User;
import crudboilerplatejava.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${pbkdf2.secret}")
    private String pbkdf2Key;

    ExceptionMessages exceptionMessages;

    @SneakyThrows
    public void create(UserDTO user) {
        User existUsername = userRepository.findUserByUsername(user.getUsername());

        if (existUsername != null) {
            throw new FlexibleException(exceptionMessages.usernameAlreadyTaken, HttpStatus.BAD_REQUEST);
        }

        User existEmail = userRepository.findUserByEmail(user.getEmail());

        if (existEmail != null) {
            throw new FlexibleException(exceptionMessages.emailAlreadyTaken, HttpStatus.BAD_REQUEST);
        }

        User newUser = createEntity(user);
        userRepository.save(newUser);
    }

    public boolean checkPassword(String rawPassword, String salt, String hashedPassword) {
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(pbkdf2Key, 20000, 256);
        pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);
        return pbkdf2PasswordEncoder.matches(rawPassword + salt, hashedPassword);
    }


    private User createEntity(UserDTO user) {
        String salt = this.generateSalt();
        String hash = this.hashPassword(user.getPassword());

        return new User(user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), salt, hash);
    }

    private String hashPassword(String rawPassword) {
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(pbkdf2Key, 20000, 256);
        pbkdf2PasswordEncoder.setEncodeHashAsBase64(true);
        return pbkdf2PasswordEncoder.encode(rawPassword);
    }

    private String generateSalt() {
        byte[] rawSalt = new byte[12];
        Random random = new SecureRandom();
        random.nextBytes(rawSalt);
        return Base64.getEncoder().encodeToString(rawSalt);
    }


}
