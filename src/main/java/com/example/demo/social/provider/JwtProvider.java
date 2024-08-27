package com.example.demo.social.provider;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtProvider {
//    Dotenv dotenv = Dotenv.load();
    @Value("${secret-key}")
    private String secretKey;
//    private String secretKey = dotenv.get("SECRET_KEY");

    public String createAccessToken(Long user_id){
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Map<String, Long> claims = new HashMap<>();
        claims.put("user_id", user_id);

        String accessToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();

        return accessToken;
    }

    //발급시간, 만료시간만 들어있어야됨
    public String createRefreshToken(Long user_id){
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS)); // 1 day expiry
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Map<String, Long> claims = new HashMap<>();
        claims.put("user_id", user_id);

        String refreshToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();

        return refreshToken;
    }

    //토큰 검증
    public Long validate(String jwt){
        Long subject = null;
        Date expiration = null;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            expiration = claims.getExpiration();

            if (expiration.before(new Date())) {
                // JWT가 만료된 경우
                System.out.println("JWT is expired");
                return null;
            }

            subject = claims.get("user_id", Long.class);
            System.out.println("subject: " + subject);
            return subject; //유저 식별 아이디 userIdentifyId

        } catch (Exception exception){
            exception.printStackTrace();
            return null;

        }
    }

    public String getUserIdByJWT(String jwt){
        String userId = null;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            userId = claims.get("user_id").toString();
            return userId;

        } catch (Exception exception){
            exception.printStackTrace();
            return null;

        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    jwt는 자동으로 만료시간 검증함, refreshtoken은 알아서 하루동안만 유지 가능
//    public boolean validateRefreshToken(String refreshToken, String userIdentifyId) {
//        try {
//            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//            Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(refreshToken);
//
//            String storedRefreshToken = refreshTokenStore.get(userIdentifyId);
//            return refreshToken.equals(storedRefreshToken);
//
//        } catch (Exception e) {
//            return false;
//        }
//    }
}
