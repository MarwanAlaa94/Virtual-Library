package com.virtualLibrary.Authentication;

import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class ClientCredentials {
	public static final String API_KEY =
		"AIzaSyDhuxOYrtwpNw-63es6mHdAAdRh-HrDLKQ";
	public static final String CLIENT_ID =
		"29518267527-r4dd50mjsjb5qoa7be1agcrie53rg13i.apps.googleusercontent.com";
	public void errorIfNotSpecified() {
		System.out.println("Key is: " + API_KEY);
	    if (API_KEY.startsWith("Enter ")) {
	    	System.err.println(API_KEY);
	    	System.exit(1);
	    }
	}
	
	public String getUserInfo(String token) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
			  .Builder(new ApacheHttpTransport(), JacksonFactory.getDefaultInstance())
			  .setAudience(Collections.singletonList(CLIENT_ID))
			  .build();
			// (Receive idTokenString by HTTPS POST)
		GoogleIdToken idToken;
		try {
			idToken = verifier.verify(token);
		  	if (idToken != null) {
				Payload payload = idToken.getPayload();
				String userId = payload.getSubject();
				System.out.println("User ID: " + userId);
				
				String email = payload.getEmail();
				boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
				String name = (String) payload.get("name");
				String pictureUrl = (String) payload.get("picture");
				String locale = (String) payload.get("locale");
				String familyName = (String) payload.get("family_name");
				String givenName = (String) payload.get("given_name");

			  // Use or store profile information
			  // ...
				return userId;
		  	} else {
		  		System.out.println("Invalid ID token.");
		  	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}