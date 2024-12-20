/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.it.taskzen.jwt;

import com.it.taskzen.entities.AdminEntity;
import com.it.taskzen.entities.ClientMasterEntity;
import com.it.taskzen.entities.FreelancerMasterEntity;
import com.it.taskzen.entities.UserEntity;
import com.it.taskzen.repositories.AdminRepository;
import com.it.taskzen.repositories.ClientMstRepository;
import com.it.taskzen.repositories.FreelancerMstRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aditya Pathak R
 */
@Service
public class JWTService {

    @Autowired
    private ClientMstRepository clientMstRepository;

    @Autowired
    private FreelancerMstRepository freelancerMstRepository;

    @Autowired
    private AdminRepository adminRepository;

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

//    when you have to use JWT for admin so add Long admin_id
    public String generateToken(String email, String role, Long user_id, Long client_id, Long freelancer_id, Long admin_id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", String.valueOf(user_id));
        // Ensure only the client_id (Long) is added, not the entire entity
        claims.put("client_id", client_id != null ? String.valueOf(client_id) : null);
        claims.put("freelancer_id", freelancer_id != null ? String.valueOf(freelancer_id) : null);
        claims.put("admin_id", admin_id != null ? String.valueOf(admin_id) : null);
        claims.put("role", role);

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

    private Claims parseToken(String token) {
        return extractAllClaims(token);
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
        Claims claims = parseToken(token);
        return Long.parseLong(claims.get("user_id", String.class));
    }

    public Long extractClientId(String token) {
        Claims claims = parseToken(token);
        String clientIdStr = claims.get("client_id", String.class);
        if (clientIdStr == null) {
            throw new RuntimeException("Client profile not created yet");
        }
        return Long.parseLong(clientIdStr);  // Parse as Long, not as an object
    }

    public Long extractFreelancerId(String token) {
        Claims claims = extractAllClaims(token);
        String freelancerIdStr = claims.get("freelancer_id", String.class);
        return freelancerIdStr != null ? Long.parseLong(freelancerIdStr) : null;
    }

    public Long extractAdminId(String token) {
        Claims claims = extractAllClaims(token);
        String adminIdStr = claims.get("admin_id", String.class);
        return adminIdStr != null ? Long.parseLong(adminIdStr) : null;
    }

}
