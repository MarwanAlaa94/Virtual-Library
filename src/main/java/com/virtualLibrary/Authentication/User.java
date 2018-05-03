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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}
}
