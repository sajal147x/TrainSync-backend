package com.trainSync.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * @author sajalgupta
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // 15 min
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userId, String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("uid", userId)  // UUID STRING
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /**
     * Extract username from token
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("uid", String.class);
    }

    /**
     * Validate token (expiration + signature)
     */
    public boolean isTokenValid(String token, String username) {
        String extracted = extractUsername(token);
        return (extracted.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
