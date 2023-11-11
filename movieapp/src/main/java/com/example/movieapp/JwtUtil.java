package com.example.movieapp;

import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import me.paulschwarz.springdotenv.DotenvConfig;
import me.paulschwarz.springdotenv.DotenvPropertyLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.dsig.SignatureMethod;
import java.security.Key;
import java.util.Base64;

@Component
public class JwtUtil {
    private String sec ="1eWbFvFIf1bX7v57oGz6yIm7DObO0b5po53prtsvcvbr";

    byte[] secretKeyBytes = Base64.getDecoder().decode(sec);

    // Create a SecretKey object from the byte array
    SecretKey secret = Keys.hmacShaKeyFor(secretKeyBytes);
    public String generateToken(String username){
        return Jwts.builder().signWith(secret).subject(username).compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try{
             String username= Jwts.parser().verifyWith( secret).build().parseSignedClaims(token).getPayload().getSubject();
            return userDetails.getUsername().equals(username);
        }catch (Exception e){
            return  false;
        }


    }


}
