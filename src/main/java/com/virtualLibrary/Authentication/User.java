package com.virtualLibrary.Authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

public class User {
	private String name;
	private String familyName;
	private String givenName;
	private String email;
	private String token;
	private String locale;
	private String pictureUrl;
	private String userId;
	private boolean emailVerified;
	
	public User(Payload payload) {
		userId = payload.getSubject();
		System.out.println("User ID: " + userId);
		email = payload.getEmail();
		emailVerified = Boolean.valueOf(payload.getEmailVerified());
		name = (String) payload.get("name");
		pictureUrl = (String) payload.get("picture");
		locale = (String) payload.get("locale");
		familyName = (String) payload.get("family_name");
		givenName = (String) payload.get("given_name");
	}
	
	public String getUserId() {
		return userId;
	}
}
