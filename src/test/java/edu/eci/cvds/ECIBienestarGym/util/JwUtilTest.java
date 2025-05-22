package edu.eci.cvds.ECIBienestarGym.util;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class JwUtilTest {

    private JwtUtil jwtUtil;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();
    }

    @Test
    void testGenerateTokenAndExtractUsername() {
        when(userDetails.getUsername()).thenReturn("testuser");
        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);
        assertEquals("testuser", jwtUtil.extractUsername(token));
    }

    @Test
    void testTokenExpiration() {
        when(userDetails.getUsername()).thenReturn("testuser");
        String token = jwtUtil.generateToken(userDetails);
        assertFalse(jwtUtil.extractExpiration(token).before(new Date()));
    }

    @Test
    void testValidateToken() {
        when(userDetails.getUsername()).thenReturn("testuser");
        String token = jwtUtil.generateToken(userDetails);
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void testInvalidToken() {
        when(userDetails.getUsername()).thenReturn("testuser");
        String token = jwtUtil.generateToken(userDetails);
        UserDetails differentUser = new User("otheruser", "password", userDetails.getAuthorities());
        assertFalse(jwtUtil.validateToken(token, differentUser));
    }


    @Test
    void testValidateExternalTokenValid() {
        String externalSecretKey = "EPRiC0Bt0/2KcBRRWqVKhEWzModEtI6Q4K05RWuLgVQV4Xw92Ulk9kHPmQVjiRW5c9XtLNm4lgNoridiLgvZpg==";

        String token = Jwts.builder()
                .setSubject("externalUser")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(SignatureAlgorithm.HS256, externalSecretKey)
                .compact();

        Claims claims = jwtUtil.validateExternalToken(token);
        assertEquals("externalUser", claims.getSubject());
    }

    @Test
    void testValidateExternalTokenInvalid() {
        String invalidToken = "abc.def.ghi";
        Exception exception = assertThrows(RuntimeException.class, () -> jwtUtil.validateExternalToken(invalidToken));
        assertTrue(exception.getMessage().contains("Token externo inválido"));
    }
}
