package com.sdyak.crudboilerplatejava.repository;

import com.sdyak.crudboilerplatejava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
