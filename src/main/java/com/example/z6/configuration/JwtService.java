package com.example.z6.configuration;

import com.example.z6.Entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String ENCODER_KEY = "35c9bc9c572fddb150ccb712fd3760c9202087be255b9973924550c3fccdf14f";

    public String extractEmail(String jwToken) {
        return extractClaim(jwToken, Claims::getSubject);
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(ENCODER_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }
    public String generateToken(UserDetails userDetails){
        return generateTokens(new HashMap<>(), userDetails);
    }
    public boolean isTokenValid(UserDetails userDetails, String token){
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && ! isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiredAt(token).before(new Date());
    }

    private Date extractExpiredAt(String token) {
        return extractClaim(token , Claims::getExpiration);
    }

    public String generateTokens(Map<String, Object> claims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *60* 24))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }
}
