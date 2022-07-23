package crudboilerplatejava.repository;

import crudboilerplatejava.model.RefreshToken;
import crudboilerplatejava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findTokenPairByUser(User user);
}
