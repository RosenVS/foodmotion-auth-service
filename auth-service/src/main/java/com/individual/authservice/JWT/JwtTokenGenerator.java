package com.individual.authservice.JWT;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final FirebaseAuth firebaseAuth;

    public String generateToken(String uid, List<String> customClaims, String email) throws FirebaseAuthException {
        long now = System.currentTimeMillis();
        long expirationTime = now + 3600 * 1000; // Token expires in 1 hour

        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("custom_claims", customClaims);
        additionalClaims.put("iss", "https://securetoken.google.com/root-fort-412616");
        additionalClaims.put("aud", "root-fort-412616");
        additionalClaims.put("auth_time", now / 1000);
        additionalClaims.put("user_id", uid);
        additionalClaims.put("sub", uid);
        additionalClaims.put("iat", now / 1000);
        additionalClaims.put("exp", expirationTime / 1000);
        additionalClaims.put("email", email);
        additionalClaims.put("email_verified", false);

        Map<String, Object> firebaseIdentity = new HashMap<>();
        firebaseIdentity.put("email", List.of(email));

        Map<String, Object> firebaseData = new HashMap<>();
        firebaseData.put("identities", firebaseIdentity);
        firebaseData.put("sign_in_provider", "password");

        additionalClaims.put("firebase", firebaseData);

        String customToken = firebaseAuth.createCustomToken(uid, additionalClaims);

        return customToken;
    }
}