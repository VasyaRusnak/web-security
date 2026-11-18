package com.example.demo.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")      // seconds
    private long expirationSeconds;

    public String generateToken(Map<String, Object> extraClaims, UserDetails user) {
        long now = System.currentTimeMillis();

        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put("authorities", authorities);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())                 // "login"
                .setIssuedAt(new Date(now))                     // iat
                .setExpiration(new Date(now + expirationSeconds * 1000)) // exp
                .signWith(signKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /** Validates signature + expiry; throws JwtException (e.g. ExpiredJwtException) if invalid. */
    public Claims parse(String token) {
        return Jwts.parserBuilder().setSigningKey(signKey()).build()
                .parseClaimsJws(token).getBody();
    }

    private Key signKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
