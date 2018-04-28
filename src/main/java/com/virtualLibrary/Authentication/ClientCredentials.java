package com.virtualLibrary.Authentication;

import org.springframework.stereotype.Service;

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
}