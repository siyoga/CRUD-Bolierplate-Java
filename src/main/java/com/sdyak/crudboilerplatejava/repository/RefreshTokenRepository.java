package com.sdyak.crudboilerplatejava.repository;

import com.sdyak.crudboilerplatejava.model.RefreshToken;
import com.sdyak.crudboilerplatejava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findTokenPairByUser(User user);
}
