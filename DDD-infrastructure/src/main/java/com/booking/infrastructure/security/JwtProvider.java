package com.booking.infrastructure.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtTokenProvider {
    private final String SECRET_KEY = "rvxpll3di4hp7w9wcrbqx5blnbhsh2t6";
    public String generateToken(String subject) {
        try {
            Date now = new Date();
            Date expiration = new Date(now.getTime() + 1000 * 60 * 60 * 24);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .issueTime(now)
                    .expirationTime(expiration)
                    .subject(subject).build();
            JWSSigner signer = new MACSigner(SECRET_KEY);
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public boolean verifyToken(String token) {
        try {
            // Parse the token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Create the MAC verifier
            JWSVerifier verifier = new MACVerifier(SECRET_KEY);

            // Verify the signature
            if (!signedJWT.verify(verifier)) {
                return false; // Invalid signature
            }

            // Extract the claims from the token
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            // Check the expiration time
            Date expirationTime = claims.getExpirationTime();
            if (expirationTime != null && expirationTime.before(new Date())) {
                return false; // Token is expired
            }

            // Optionally, check the subject (if needed)
            String subject = claims.getSubject();
            if (subject == null) {
                return false; // No subject in the claims
            }

            return true; // Token is valid
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Invalid token or any other error
        }
    }
}
