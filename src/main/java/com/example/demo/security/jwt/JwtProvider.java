package com.example.demo.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.security.Key;

@Component
public class JwtProvider {
    private final Key secretKey;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    public JwtProvider(
            @Value("${secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTokenValidity = 20 * 60 * 1000;
        this.refreshTokenValidity = 24 * 60 * 60 * 1000;
    }

    public String createAccessToken(Long user_id, String provider) {
        Map<String, String> claims = new HashMap<>();
        claims.put("user_id", user_id.toString());
        claims.put("provider", provider);

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        String accessToken = Jwts.builder()
                .setSubject(user_id.toString())
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(now.toInstant().plusMillis(accessTokenValidity)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return accessToken;
    }

    public String createRefreshToken(Long user_id, String provider) {
        Map<String, String> claims = new HashMap<>();
        claims.put("user_id", user_id.toString());
        claims.put("provider", provider);

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        String refreshToken = Jwts.builder()
                .setSubject(user_id.toString())
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(now.toInstant().plusMillis(refreshTokenValidity)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return refreshToken;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return true;
        } catch (IllegalArgumentException | JwtException e) {
            return false;
        }
    }

    public boolean checkTokenExpiration(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JwtAuthFilter - checkTokenExpiration - access token is expired");
            return false;
        }
        return true;
    }

    public Map<String, String> getClaimsFromToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
    
            Map<String, String> map = new HashMap<>();
            map.put("user_id", claims.get("user_id", String.class));
            map.put("provider", claims.get("provider", String.class));
    
            return map;
        } catch (ExpiredJwtException e) {
            Claims claims = e.getClaims();
    
            Map<String, String> map = new HashMap<>();
            System.out.println("User Id: " + claims.get("user_id", String.class));
            System.out.println("Provider: " + claims.get("provider", String.class));
            map.put("user_id", claims.get("user_id", String.class));
            map.put("provider", claims.get("provider", String.class));
    
            return map;
        }
    }
}
