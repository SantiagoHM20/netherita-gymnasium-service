package edu.eci.cvds.ECIBienestarGym.util;

import edu.eci.cvds.ECIBienestarGym.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String secretKey = "EPRiC0Bt0/2KcBRRWqVKhEWzModEtI6Q4K05RWuLgVQV4Xw92Ulk9kHPmQVjiRW5c9XtLNm4lgNoridiLgvZpg==";

    public String extractUserEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Role extractRole(String token) {
        List<String> roles = extractClaims(token, claims -> claims.get("roles", List.class));
        String role = roles.get(0);
        return Role.valueOf(role);
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
