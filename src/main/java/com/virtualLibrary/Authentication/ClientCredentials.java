package com.virtualLibrary.Authentication;


public class ClientCredentials {
  public static final String API_KEY =
		"AIzaSyDVkqfCQohc1Ts9oe0hrRMTtH5ljsrIsX0";

  public void errorIfNotSpecified() {
	System.out.println("Key is: " + API_KEY);
    if (API_KEY.startsWith("Enter ")) {
      System.err.println(API_KEY);
      System.exit(1);
    }
  }
}