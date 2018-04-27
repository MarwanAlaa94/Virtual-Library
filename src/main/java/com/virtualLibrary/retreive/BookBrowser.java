package com.virtualLibrary.retreive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Review;
import com.virtualLibrary.Authentication.ClientCredentials;
import com.virtualLibrary.Authentication.User;
import com.virtualLibrary.model.Book;
import com.virtualLibrary.model.BrowsingModel;
import com.virtualLibrary.utils.Utils;

@Controller
public class BookBrowser {
	private static final String APPLICATION_NAME = "VirtualLibrary";
	private BrowsingModel browsingModel = new BrowsingModel();
	private  Books books;
	private User user;
	public BookBrowser() {
		ClientCredentials clientCredentials = new ClientCredentials();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		clientCredentials.errorIfNotSpecified();
		try {
			books = new Books.Builder(
					GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(), null)
						.setApplicationName(com.virtualLibrary.retreive.BookBrowser.APPLICATION_NAME)
						.setGoogleClientRequestInitializer(
							new BooksRequestInitializer(ClientCredentials.API_KEY)
						).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
  	  
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String browseBooks(ModelMap model, @RequestParam String token) {
		System.out.println(token);
		user = getUserInfo(token);
		System.out.println("hamda is here");
		List<String> categories = Utils.getSupportedCategories();
		HashMap<String, ArrayList<Book>> map = new HashMap<String, ArrayList<Book>> ();
		for(String category : categories){
			browsingModel.browseBooks(books, map, category.replaceAll("\"", ""));
		}
		model.addAttribute("categoryList", map);
		return "home";
	}

	@RequestMapping(value = "/bookGrid", method = RequestMethod.POST)
	public String  getBookGrid(ModelMap model,  @RequestParam String category) {
		HashMap<String, ArrayList<Book>> map = new HashMap<String, ArrayList<Book>> ();	
		browsingModel.search(books, map, "category", category, 40);
		model.addAttribute("categoryList", map);
		return "bookGrid";
	}

    @RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(ModelMap model, @RequestParam String key, @RequestParam String value){
    	HashMap<String, ArrayList<Book>> map = new HashMap<String, ArrayList<Book>> ();
    	browsingModel.search(books, map, key, value, 40);
    	model.addAttribute("categoryList", map);
		model.keySet().forEach(temp->System.out.println("model =      " + temp));
		return "bookGrid";
	}
    
    public User getUserInfo(String token) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
			  .Builder(new ApacheHttpTransport(), JacksonFactory.getDefaultInstance())
			  .setAudience(Collections.singletonList(ClientCredentials.CLIENT_ID))
			  .build();
//		GoogleIdToken idToken;
		try {
//			idToken = verifier.verify(token);
//		  	if (idToken != null) {
		  	  final Collection<String> scopes = Arrays.asList("https://www.googleapis.com/auth/books");
		  	  GoogleAuthorizationCodeFlow  flow = new GoogleAuthorizationCodeFlow.Builder(
						new NetHttpTransport(),
						JacksonFactory.getDefaultInstance(),
						"29518267527-r4dd50mjsjb5qoa7be1agcrie53rg13i.apps.googleusercontent.com",
				        "AdUkZwuGiYXjeqEL0ra34YB4",
				        scopes)
				        .build();
		  	//LocalServerReceiver lsr = new LocalServerReceiver();
		  	//Credential cr = new AuthorizationCodeFlow(flow, lsr).authorize("user");

		  	//return cr.getAccessToken();
			  GoogleTokenResponse tokenResponse =
			           new GoogleAuthorizationCodeTokenRequest(
			                new ApacheHttpTransport(),
			                JacksonFactory.getDefaultInstance(),
			                "https://www.googleapis.com/oauth2/v4/token",
			                "29518267527-r4dd50mjsjb5qoa7be1agcrie53rg13i.apps.googleusercontent.com",
			                "AdUkZwuGiYXjeqEL0ra34YB4",
			                token,
			                "http://localhost:8080")  // Specify the same redirect URI that you use with your web
			                               // app. If you don't have a web version of your app, you can
			                               // specify an empty string.
			                .execute();
//			  String accessToken = tokenResponse.getAccessToken();
//			  System.out.println(accessToken);
			  Credential credential = flow.createAndStoreCredential(tokenResponse, "114828915672982309273");
			  books = new Books.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(), credential)
					.setApplicationName(com.virtualLibrary.retreive.BookBrowser.APPLICATION_NAME)
					.setGoogleClientRequestInitializer(
						new BooksRequestInitializer(ClientCredentials.API_KEY)
					).build();
			  books.volumes().mybooks().list().setOauthToken(token).execute().getItems().forEach(hamada->System.out.println(hamada));
//		  	  return new User(idToken.getPayload());
//		  	} else {
//		  		System.out.println("Invalid ID token.");
//		  	}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
    
}