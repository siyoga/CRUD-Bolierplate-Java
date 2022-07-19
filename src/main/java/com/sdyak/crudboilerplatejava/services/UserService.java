package com.sdyak.crudboilerplatejava.services;

import com.sdyak.crudboilerplatejava.dto.UserDTO;
import com.sdyak.crudboilerplatejava.exceptions.BadRequestException;
import com.sdyak.crudboilerplatejava.exceptions.Pbkdf2NotFoundException;
import com.sdyak.crudboilerplatejava.model.User;
import com.sdyak.crudboilerplatejava.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

@Service
public class UserService {
    private final Environment environment;
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository, Environment environment) {
        this.userRepository = userRepository;
        this.environment = environment;
    }

    @SneakyThrows
    public void create(UserDTO user) {
        User existUsername = userRepository.findUserByUsername(user.getUsername());

        if (existUsername != null) {
            throw new BadRequestException("Username already taken");
        }

        User existEmail = userRepository.findUserByEmail(user.getEmail());

        if (existEmail != null) {
            throw new BadRequestException("This email has already been used");
        }

        User newUser = createEntity(user);
        userRepository.save(newUser);
    }

    public boolean checkPassword(String rawPassword, String salt, String hashedPassword) {
        String pbkdf2Key = environment.getProperty("pbkdf2.secretkey");

        if(pbkdf2Key == null) {
            throw new Pbkdf2NotFoundException();
        }

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
        String pbkdf2Key = environment.getProperty("pbkdf2.secretkey");

        if(pbkdf2Key == null) {
            throw new Pbkdf2NotFoundException();
        }

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
