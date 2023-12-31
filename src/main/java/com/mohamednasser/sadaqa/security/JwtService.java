package com.mohamednasser.sadaqa.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final Key key;

    public JwtService(Environment env) {
        String secretKey = env.getProperty("SECRET_KEY");
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.key).build().parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        return claimResolver.apply(extractClaims(token));
    }

    public Map<String, Object> extractClamesAsMap(String token, String... names) {
        Map<String, Object> map = new HashMap<>();
        Claims claims = this.extractClaims(token);
        Arrays.stream(names).forEach((name) -> map.put(name, claims.get(name)));
        return map;
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateJwtToken(String username, Map<String, Object> extraClaims) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
