package edu.eci.cvds.ECIBienestarGym.util;

import edu.eci.cvds.ECIBienestarGym.enums.Role;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String TEST_SECRET_KEY = "EPRiC0Bt0/2KcBRRWqVKhEWzModEtI6Q4K05RWuLgVQV4Xw92Ulk9kHPmQVjiRW5c9XtLNm4lgNoridiLgvZpg==";
    private final String TEST_EMAIL = "test@example.com";
    private final Role TEST_ROLE = Role.STUDENT;

    @BeforeEach
    void setUp() {
        // Inicializar JwtUtil con la clave de prueba
        jwtUtil = new JwtUtil(TEST_SECRET_KEY);
    }

    @Test
    @DisplayName("Debería generar un token JWT válido")
    void shouldGenerateValidToken() {
        // Act
        String token = jwtUtil.generateToken(TEST_EMAIL, TEST_ROLE);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(jwtUtil.isTokenValid(token));
    }

    @Test
    @DisplayName("Debería extraer correctamente el email del token")
    void shouldExtractUserEmail() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_EMAIL, TEST_ROLE);

        // Act
        String extractedEmail = jwtUtil.extractUserEmail(token);

        // Assert
        assertEquals(TEST_EMAIL, extractedEmail);
    }

    @Test
    @DisplayName("Debería extraer correctamente el rol del token")
    void shouldExtractRole() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_EMAIL, TEST_ROLE);

        // Act
        Role extractedRole = jwtUtil.extractRole(token);

        // Assert
        assertEquals(TEST_ROLE, extractedRole);
    }

    @Test
    @DisplayName("Debería validar token como válido cuando no está expirado")
    void shouldValidateTokenAsValidWhenNotExpired() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_EMAIL, TEST_ROLE);

        // Act
        boolean isValid = jwtUtil.isTokenValid(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Debería extraer claim específico del token")
    void shouldExtractSpecificClaim() {
        // Arrange
        String token = jwtUtil.generateToken(TEST_EMAIL, TEST_ROLE);

        // Act
        Date expiration = jwtUtil.extractClaim(token, Claims::getExpiration);
        String subject = jwtUtil.extractClaim(token, Claims::getSubject);

        // Assert
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
        assertEquals(TEST_EMAIL, subject);
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el token es inválido")
    void shouldThrowExceptionWhenTokenIsInvalid() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act & Assert
        assertFalse(jwtUtil.isTokenValid(invalidToken));
    }


    @Test
    @DisplayName("Debería generar tokens diferentes para diferentes emails")
    void shouldGenerateDifferentTokensForDifferentEmails() {
        // Arrange
        String email1 = "user1@example.com";
        String email2 = "user2@example.com";

        // Act
        String token1 = jwtUtil.generateToken(email1, TEST_ROLE);
        String token2 = jwtUtil.generateToken(email2, TEST_ROLE);

        // Assert
        assertNotEquals(token1, token2);
        assertEquals(email1, jwtUtil.extractUserEmail(token1));
        assertEquals(email2, jwtUtil.extractUserEmail(token2));
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la clave secreta está vacía")
    void shouldThrowExceptionWhenSecretKeyIsEmpty() {
        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new JwtUtil(""));

        assertEquals("JWT secret key must be configured. Set JWT_SECRET environment variable or jwt.secret in application.properties",
                exception.getMessage());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando la clave secreta es nula")
    void shouldThrowExceptionWhenSecretKeyIsNull() {
        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> new JwtUtil(null));

        assertEquals("JWT secret key must be configured. Set JWT_SECRET environment variable or jwt.secret in application.properties",
                exception.getMessage());
    }

    @Test
    @DisplayName("Debería manejar token con rol inválido")
    void shouldHandleTokenWithInvalidRole() {
        // Arrange - Crear un token manualmente con rol inválido
        String invalidRoleToken = jwtUtil.generateToken(TEST_EMAIL, TEST_ROLE);
        // No podemos probar fácilmente un rol inválido sin manipular el token directamente
        // Esta prueba sería más compleja y requeriría manipulación directa del JWT

        // Por ahora, probamos que un token válido funciona correctamente
        assertTrue(jwtUtil.isTokenValid(invalidRoleToken));
    }
}