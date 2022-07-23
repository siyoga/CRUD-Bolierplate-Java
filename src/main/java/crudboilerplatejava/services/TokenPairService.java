package crudboilerplatejava.services;

import crudboilerplatejava.dto.TokenPairDTO;
import crudboilerplatejava.model.RefreshToken;
import crudboilerplatejava.model.User;
import crudboilerplatejava.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TokenPairService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @SneakyThrows
    public TokenPairDTO generateTokenPair(User user) {
        Claims claims = Jwts.claims().setAudience(user.getUsername());
        claims.put("auth", user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).filter(obj -> true).collect(Collectors.toList()));

        long validityIn = 3600000L * 24 * 30; // 1 month
        Date now = new Date();
        Date validity = new Date(now.getDate() + validityIn);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), user);
        refreshTokenRepository.save(refreshToken);

        return new TokenPairDTO(accessToken, refreshToken.getRefreshToken());
    }
}
