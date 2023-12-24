package com.mohamednasser.sadaqa.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    public static JwtService singleton;

    private static final String SECRET_KEY = "8fg7dhg8hsd8f8w98q09k9jggg938401ld0j39hj9f32028hf439f8h2mnznznowugh204854t";

    private static final int EXPIRATION_DURATION = 10000 * 60 * 24;

    public JwtService() {
        JwtService.singleton = this;
    }

    public boolean validateKey(String key) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(JwtService.getSigningKey())
                    .build()
                    .parse(key);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Map<String, Object> extractClamesAsMap(String token, String... names) {
        Map<String, Object> map = new HashMap<>();
        Claims claims = this.extractClaims(token);
        Arrays.stream(names).forEach((name) -> map.put(name, claims.get(name)));
        return map;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        return claimResolver.apply(extractClaims(token));
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateJwtToken(String username) {
        return generateJwtToken(username, null);
    }

    public String generateJwtToken(String username, Map<String, Object> extraClaims) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
