package com.smartjob.technicaltest.service;

import com.smartjob.technicaltest.common.util.Constants;
import com.smartjob.technicaltest.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class JwtTokenUtilService {

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.expiration}")
    private Long JWT_EXPIRATION;
    private static final Logger logger = Logger.getLogger(JwtTokenUtilService.class.getName());
    private Boolean mustGenerateANewKey = Boolean.FALSE;
    public String generateToken(User user) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + JWT_EXPIRATION);

        Map<String, Object> claims = Map.of(Constants.ID_UUID, user.getPassword(), Constants.ID_EMAIL, user.getEmail());

        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

        MacAlgorithm signatureAlgorithm = Jwts.SIG.HS256;

        return Jwts
                .builder()
                .header()
                .type(Constants.JWT)
                .and()
                .subject(user.getEmail())
                .claims(claims)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String getSecretKey() throws NoSuchAlgorithmException {
        SecretKey secretKey = generateSecretKey(Constants.KEY_SIZE);

        byte[] keyBytes = secretKey.getEncoded();
        return bytesToHex(keyBytes);
    }

    protected SecretKey generateSecretKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(Constants.ALGORITHM_AES);
        keyGenerator.init(keySize);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    protected String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    @Scheduled(cron = Constants.JWT_CRON_RENEW_KEY)
    public void notificatorMustGenerateANewSecretKey() {
        // Acá se podría enviar un correo que notifique.
        logger.info("Debe generar un nuevo token por politicas de seguridad ejecutando el siguiente endpoint: /technicalTest/generateNewSecretKey");
        mustGenerateANewKey = Boolean.TRUE;
    }

    public Boolean getMustGenerateANewKey() {
        return mustGenerateANewKey;
    }

    public void setMustGenerateANewKey(Boolean mustGenerateANewKey) {
        this.mustGenerateANewKey = mustGenerateANewKey;
    }
}