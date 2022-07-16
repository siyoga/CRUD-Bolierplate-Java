package com.sdyak.crudboilerplatejava.modules.user;

import com.sdyak.crudboilerplatejava.misc.dto.UserDTO;
import com.sdyak.crudboilerplatejava.misc.exceptions.BadRequestException;
import com.sdyak.crudboilerplatejava.modules.db.model.User;
import com.sdyak.crudboilerplatejava.modules.db.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

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


    @SneakyThrows
    public String hashPlainPassword(String plainPassword, byte[] salt) {
        KeySpec spec = new PBEKeySpec(plainPassword.toCharArray(), salt, 65536, 32);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).toString();
    }

    @SneakyThrows
    private User createEntity(UserDTO user) {
        byte[] salt = new byte[12];
        SecureRandom.getInstanceStrong().nextBytes(salt);
        String hash = this.hashPlainPassword(user.getPassword(), salt);

        return new User(user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), salt, hash);
    }
}
