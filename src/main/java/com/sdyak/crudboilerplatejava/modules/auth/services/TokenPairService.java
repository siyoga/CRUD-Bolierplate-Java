package com.sdyak.crudboilerplatejava.modules.auth.services;

import com.sdyak.crudboilerplatejava.misc.dto.TokenPairDTO;
import com.sdyak.crudboilerplatejava.misc.exceptions.ConflictException;
import com.sdyak.crudboilerplatejava.modules.db.model.RefreshToken;
import com.sdyak.crudboilerplatejava.modules.db.model.User;
import com.sdyak.crudboilerplatejava.modules.db.repository.RefreshTokenRepository;
import com.sdyak.crudboilerplatejava.modules.user.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenPairService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private Environment environment;

    @SneakyThrows
    public TokenPairDTO generateTokenPair(User user) {
        RefreshToken existTokenPair = refreshTokenRepository.findTokenPairByUser(user);
        if (existTokenPair != null) {
            throw new ConflictException("Refresh token already exist");
        }

        Map<String, Object> tokenData = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 10);

        tokenData.put("userId", user.getId());
        tokenData.put("expiration_data", calendar.getTime());

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(calendar.getTime());
        jwtBuilder.addClaims(tokenData);
        String jwtKey = environment.getProperty("jwt.secretkey");

        String accessToken = jwtBuilder.signWith(SignatureAlgorithm.HS512, jwtKey).compact();
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), user);
        refreshTokenRepository.save(refreshToken);

        return new TokenPairDTO(accessToken, refreshToken.getRefreshToken());
    }


}
