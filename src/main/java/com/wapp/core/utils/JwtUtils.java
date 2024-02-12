package com.wapp.core.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
public class JwtUtils {

    private final static SecretKey jwtSecret = Keys.hmacShaKeyFor(System.getenv("JWT_SECRET_KEY").getBytes());

    public static String generateToken(String userId, String username, String email, String name) {
        return Jwts.builder()
                       .setSubject(username)
                       .claim("userId", userId)
                       .claim("username", username)
                       .claim("email", email)
                       .claim("name", name)
                       .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 6)) // 6 hours
                       .signWith(jwtSecret, HS512)
                       .compact();
    }

    public static boolean validateToken(String token) {
        try {
            token = token.substring(7);
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
