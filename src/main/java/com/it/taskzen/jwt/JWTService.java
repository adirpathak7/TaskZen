/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.jwt;

import com.it.taskzen.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class JWTService {

    private String secretKey = "";

    public JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(JWTService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String generateToken(String email, String role, Long user_id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("user_id", String.valueOf(user_id));
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 4 * 60 * 60 * 1000)) // Change to 4 hours
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String getRoleFromToken(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserEntity userEntity) {
        final String userName = getUsernameFromToken(token);
        return (userName.equals(userEntity.getEmail()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long extractUserId(String token) {
        token = token.trim();
        return Long.parseLong(extractClaim(token, claims -> claims.get("user_id", String.class)));
    }

    public Long extractClientId(String token) {
        token = token.trim();
        return Long.parseLong(extractClaim(token, claims -> claims.get("client_id", String.class)));
    }
    
    public Long extractFreelancerId(String token) {
        token = token.trim();
        return Long.parseLong(extractClaim(token, claims -> claims.get("freelancer_id", String.class)));
    }
}
