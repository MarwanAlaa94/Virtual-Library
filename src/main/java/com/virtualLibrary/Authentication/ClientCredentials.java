package com.virtualLibrary.Authentication;


public class ClientCredentials {
  public static final String API_KEY =
		"AIzaSyDhuxOYrtwpNw-63es6mHdAAdRh-HrDLKQ";

  public void errorIfNotSpecified() {
	System.out.println("Key is: " + API_KEY);
    if (API_KEY.startsWith("Enter ")) {
      System.err.println(API_KEY);
      System.exit(1);
    }
  }
}