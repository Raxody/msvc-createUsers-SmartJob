package com.smartjob.technicaltest.service;


import com.smartjob.technicaltest.common.util.Constants;
import com.smartjob.technicaltest.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenUtilServiceTest {

    private JwtTokenUtilService jwtTokenUtilService;

    @BeforeEach
    void setUp() {
        jwtTokenUtilService = new JwtTokenUtilService();
    }

    @Test
    void testGenerateSecretKey() throws Exception {
        // Act
        SecretKey secretKey = jwtTokenUtilService.generateSecretKey(256);

        // Assert
        assertEquals("AES", secretKey.getAlgorithm());
        assertEquals(256, secretKey.getEncoded().length * 8); // Check if key length is 256 bits
    }

    @Test
    public void testGetSecretKey() {
        JwtTokenUtilService jwtTokenUtilService = new JwtTokenUtilService();

        try {
            String secretKeyHex = jwtTokenUtilService.getSecretKey();
            assertNotNull(secretKeyHex);
            // Asegúrate de que la longitud de la clave sea correcta
            assertEquals(64, secretKeyHex.length());
            // Puedes realizar más aserciones sobre la validez del formato si lo deseas
        } catch (NoSuchAlgorithmException e) {
            // Manejo de la excepción si el algoritmo de generación de clave no está disponible
            fail("No se pudo generar la clave: " + e.getMessage());
        }
    }

    @Test
    void testBytesToHex() {
        // Arrange
        byte[] testBytes = {(byte) 0xDE, (byte) 0xAD, (byte) 0xBE, (byte) 0xEF};

        // Act
        String hexString = jwtTokenUtilService.bytesToHex(testBytes);

        // Assert
        assertEquals("DEADBEEF", hexString);
    }

}