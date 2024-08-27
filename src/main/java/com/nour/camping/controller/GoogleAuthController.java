/*package com.nour.camping.controller;
import com.nour.camping.dto.GoogleSignInRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.userinfo.OidcUserInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class GoogleAuthController {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @PostMapping("/google-signin")
    public ResponseEntity<?> googleSignIn(@RequestBody GoogleSignInRequest request) {
        // Validate the ID token and retrieve user info
        // Google People API can be used to retrieve user details using the token
        String idToken = request.getIdToken();
        // Validate and parse ID token using Google API
        OidcUserInfo userInfo = ...; // Retrieve user info

        // Handle user info and create or update user in your system

        return ResponseEntity.ok("User signed in successfully");
    }
}*/
