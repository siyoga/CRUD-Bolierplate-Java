package com.sdyak.crudboilerplatejava.services;

import com.sdyak.crudboilerplatejava.dto.TokenPairDTO;
import com.sdyak.crudboilerplatejava.exceptions.ConflictException;
import com.sdyak.crudboilerplatejava.model.RefreshToken;
import com.sdyak.crudboilerplatejava.model.User;
import com.sdyak.crudboilerplatejava.repository.RefreshTokenRepository;
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

        String accessToken = jwtBuilder.signWith(SignatureAlgorithm.HS256, jwtKey).compact();
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), user);
        refreshTokenRepository.save(refreshToken);

        return new TokenPairDTO(accessToken, refreshToken.getRefreshToken());
    }
}
