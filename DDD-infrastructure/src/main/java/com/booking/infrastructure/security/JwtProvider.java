package com.booking.infrastructure.security;

import com.booking.infrastructure.model.entity.JwtPayload;
import com.booking.infrastructure.model.enums.JwtType;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {

    private final String SECRET_KEY = "rvxpll3di4hp7w9wcrbqx5blnbhsh2t6";

    public String generateToken(JwtPayload payload) {
        try {
            JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet
                    .Builder();
            Map<String, Object> claims = payload.toPayloadMap();
            claims.forEach(claimsSetBuilder::claim);
            JWTClaimsSet claimsSet = claimsSetBuilder.build();
            JWSSigner signer = new MACSigner(SECRET_KEY);
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public String generateAccessToken(long id, String sub,String email, String[] roles) {
        JwtPayload payload = new JwtPayload()
                .setEmail(email)
                .setId(id)
                .setSub(sub)
                .setRoles(roles)
                .setType(JwtType.ACCESS_TOKEN)
                .setIat(new Date())
                .setExp(new Date(1000*60*60));
        return generateToken(payload);
    }


    public String generateRefreshToken(long id, String sub,String email, String[] roles) {
        JwtPayload payload = new JwtPayload()
                .setEmail(email)
                .setId(id)
                .setSub(sub)
                .setRoles(roles)
                .setType(JwtType.REFRESH_TOKEN)
                .setIat(new Date())
                .setExp(new Date(System.currentTimeMillis()+1000L*60*60*24*360));
        return generateToken(payload);
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


    public String getUsername(String token) {
        try {
            SignedJWT signer = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET_KEY);
            if (signer.verify(verifier)) {
                JWTClaimsSet claims = signer.getJWTClaimsSet();
                return claims.getSubject();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    public JwtPayload getPayload(String token) throws JOSEException, ParseException, AuthenticationException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Create the MAC verifier
        JWSVerifier verifier = new MACVerifier(SECRET_KEY);

        // Verify the signature
        if (!signedJWT.verify(verifier)) {
            throw new AuthenticationException("invalid token");
        }

        // Extract the claims from the token
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        Map<String, Object> claimsMap = claims.getClaims();
        return new JwtPayload()
                .setId((Long) claimsMap.get("id"))
                .setSub((String) claimsMap.get("sub"))
                .setType(JwtType.valueOf( (String) claimsMap.get("type")))
                .setRoles( ((ArrayList<String>) claimsMap.get("roles")).toArray(new String[0]))
                .setEmail((String) claimsMap.get("email"))
                .setIat(claims.getIssueTime())
                .setExp(claims.getExpirationTime());
    }


    public static void main(String[] args) throws ParseException, JOSEException, AuthenticationException {
//        JwtPayload jwtPayload = new JwtPayload().setId(1).setEmail("hau@gmail.com").setSub("user1").setRoles(new String[]{"ROLE_USER", "ROLE_ADMIN"}).setType(JwtType.ACCESS_TOKEN);
//        String token = new JwtProvider().generateToken(jwtPayload);
//        System.out.println(token);
//        System.out.println("check token:" + new JwtProvider().verifyToken(token));
//        System.out.println("check payload" + new JwtProvider().getPayload(token));
//        System.out.println(new JwtProvider().generateAccessToken(1, "user1", "user1@gmail.com", new String[]{"admin", "user1"}));
//        System.out.println(new JwtProvider().generateRefreshToken(1, "user1", "user1@gmail.com", new String[]{"admin", "user1"}));

        System.out.println(new JwtProvider().getPayload("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsInJvbGVzIjpbImFkbWluIiwidXNlcjEiXSwiaWQiOjEsImV4cCI6MzYwMCwidHlwZSI6IkFDQ0VTU19UT0tFTiIsImlhdCI6MTczOTE2MTEzNCwiZW1haWwiOiJ1c2VyMUBnbWFpbC5jb20ifQ.Qi_Ii7mTqFk_7wbIvSbdWUYZ_cFh5oOKAHIi36AfRAg"));
    }
}