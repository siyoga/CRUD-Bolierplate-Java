package com.sdyak.crudboilerplatejava.modules.db.repository;

import com.sdyak.crudboilerplatejava.modules.db.model.RefreshToken;
import com.sdyak.crudboilerplatejava.modules.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findTokenPairByUser(User user);
}
