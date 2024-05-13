package com.individual.authservice.service;

import com.individual.authservice.security.Department;
import com.individual.authservice.security.Roles;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final FirebaseAuth firebaseAuth;

    public void setUserClaims(String uid, List<Roles> requestedRoles, List<Department> departments) throws FirebaseAuthException {
        List<String> roles = requestedRoles.stream().map(Roles::toString).toList();
        List<String> subroles = departments.stream().map(Department::toString).toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.toArray(new String[0]));
        claims.put("subroles", subroles.toArray(new String[0]));

        firebaseAuth.setCustomUserClaims(uid, claims);
    }

    public void setUserClaims(String uid, List<Roles> requestedRoles) throws FirebaseAuthException {
        List<String> roles = requestedRoles.stream().map(Roles::toString).toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles.toArray(new String[0]));

        firebaseAuth.setCustomUserClaims(uid, claims);
    }

    public void deleteUser(String uid) throws FirebaseAuthException {
        firebaseAuth.deleteUser(uid);
    }

    public String signUpWithEmailAndPassword(String email, String password) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password);

        UserRecord userRecord = firebaseAuth.createUser(request);
        setUserClaims(userRecord.getUid(), Collections.singletonList(Roles.USER));
        return userRecord.getUid();
    }

    public String signInWithEmailAndPassword(String email, String password) throws FirebaseAuthException {
        try {
            UserRecord userRecord = firebaseAuth.getUserByEmail(email);
            String uid = userRecord.getUid();
            firebaseAuth.getUser(uid);

            return userRecord.getUid();
        } catch (FirebaseAuthException e) {
            throw  e;
        }
    }
}