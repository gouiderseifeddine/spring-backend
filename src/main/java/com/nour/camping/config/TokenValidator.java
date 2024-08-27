/*package com.nour.camping.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class TokenValidator {
    private static final String CLIENT_ID = "YOUR_GOOGLE_CLIENT_ID";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Payload verifyToken(String idTokenString) throws Exception {
        GoogleIdToken idToken = GoogleIdToken.parse(JSON_FACTORY, idTokenString);
        if (idToken != null && idToken.verify(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, CLIENT_ID)) {
            return idToken.getPayload();
        } else {
            throw new Exception("Invalid ID token");
        }
    }
}*/
