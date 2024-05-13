package com.individual.authservice.admin;

import com.individual.authservice.Http.SignInRequest;
import com.individual.authservice.Http.SignUpRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.individual.authservice.RabbitMQ.publisher.RabbitMQJsonProducer;
import com.individual.authservice.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/firebase")
public class AuthController {
    private final UserManagementService userManagementService;
    @Autowired
    private FirebaseAuth firebaseAuth;
    @Autowired
    private RabbitMQJsonProducer jsonProducer;

    public AuthController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        System.out.println("test");
        try {

            // You might want to implement your custom logic for sign in here
            // This example assumes you've already authenticated the user with Firebase
            return ResponseEntity.ok().body(FirebaseAuth.getInstance().getUserByEmail(signInRequest.getEmail()));
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            String uid = userManagementService.signUpWithEmailAndPassword(signUpRequest.getEmail(), signUpRequest.getPassword());
            jsonProducer.registerAccountDetailsMessage(uid);

            return ResponseEntity.ok("User signed up successfully. UID: " + uid);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sign up failed: " + e.getMessage());
        }
    }

}
