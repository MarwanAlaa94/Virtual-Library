package com.virtualLibrary.login;

import java.util.Collections;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.virtualLibrary.Authentication.ClientCredentials;
import com.virtualLibrary.Authentication.User;
@Controller
@SessionAttributes("name")
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showGoogleLoginPage() {
		return "login";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String handleUserLogin(ModelMap model, @RequestParam String token){
		System.out.println(token);
		User user = getUserInfo(token);
		return "home";
	}
	
	
	public User getUserInfo(String token) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
			  .Builder(new ApacheHttpTransport(), JacksonFactory.getDefaultInstance())
			  .setAudience(Collections.singletonList(ClientCredentials.CLIENT_ID))
			  .build();
			// (Receive idTokenString by HTTPS POST)
		GoogleIdToken idToken;
		try {
			idToken = verifier.verify(token);
		  	if (idToken != null) {
				return new User(idToken.getPayload());
		  	} else {
		  		System.out.println("Invalid ID token.");
		  	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
