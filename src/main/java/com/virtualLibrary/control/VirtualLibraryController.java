package com.virtualLibrary.control;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.virtualLibrary.Authentication.ClientCredentials;
import com.virtualLibrary.Authentication.User;
import com.virtualLibrary.model.Book;
import com.virtualLibrary.retreive.BookHandler;
import com.virtualLibrary.utils.Utils;

@Controller
public class VirtualLibraryController {
	private static final String APPLICATION_NAME = "VirtualLibrary";
	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat
			.getCurrencyInstance();
	private static final NumberFormat PERCENT_FORMATTER = NumberFormat
			.getPercentInstance();
	
	@Autowired
	private BookHandler bookHandler;
	private ClientCredentials clientCredentials;
	private Books books;
	private User user;
	public VirtualLibraryController() {
		ClientCredentials clientCredentials = new ClientCredentials();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		clientCredentials.errorIfNotSpecified();
		
		try {
			books = new Books.Builder(
					GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
					.setApplicationName(com.virtualLibrary.control.VirtualLibraryController.APPLICATION_NAME)
					.setGoogleClientRequestInitializer(
						new BooksRequestInitializer(
							ClientCredentials.API_KEY
						)
					).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String browseHome(ModelMap model) {
		List<String> categories = Utils.getSupportedCategories();
		HashMap<String, ArrayList<Book>> map = new HashMap<String, ArrayList<Book>> ();
		for(String category : categories){
			map.put(category.replaceAll("\"", ""), bookHandler.browseBooks(books, category.replaceAll("\"", "")));
		}
		model.addAttribute("categoryList", map);
		return "home";
	}

	@RequestMapping(value = "/bookGrid", method = RequestMethod.POST)
	public String  getBookGrid(ModelMap model,  @RequestParam String category) {
		HashMap<String, ArrayList<Book>> map = new HashMap<String, ArrayList<Book>> ();	
		map.put(category, bookHandler.search(books, "category", category, 40));
		model.addAttribute("categoryList", map);
		return "bookGrid";
	}
	

    @RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(ModelMap model, @RequestParam String key, @RequestParam String value){
    	HashMap<String, ArrayList<Book>> map = new HashMap<String, ArrayList<Book>> ();
    	map.put(value, bookHandler.search(books, key, value, 40));
    	model.addAttribute("categoryList", map);
		model.keySet().forEach(temp->System.out.println("model =      " + temp));
		return "bookGrid";
	}
    
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
   	public String getUserInfo(ModelMap model, @RequestParam String token){
    	user = getUserInfo(token);
    	model.addAttribute("user", user);
   		return "userInfo";
   	}
    
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
   	public String getUserInfo(ModelMap model){
    	model.addAttribute("user", user);
    	Map<String, List<Book>> map = new HashMap<>();
    	map.put("Favourites", createBooksList(user.getFavorites()));
		map.put("ReadBooks", createBooksList(user.getRead()));
		map.put("TobeReadBooks", createBooksList(user.getToRead()));
		model.addAttribute("UserInfo", map);
		model.addAttribute("user", user);
   		return "userInfo";
   	}
      
    @RequestMapping(value = "/bookInfo", method = RequestMethod.POST)
   	public String getBookInfo(ModelMap model,@RequestParam String ISBN){
    	Book book = bookHandler.getBook(books, ISBN);
    	model.addAttribute("book", book);
    	return "bookInfo";
   	}
      
    @RequestMapping(value = "/addFav", method = RequestMethod.POST)
   	public void addFav(ModelMap model, @RequestParam String ISBN){
    	user.addToFavorites(ISBN);
   	}
      
    @RequestMapping(value = "/addToRead", method = RequestMethod.POST)
   	public void dummy4(ModelMap model, @RequestParam String ISBN){
    	user.addToBeRead(ISBN);
   	}
      
    @RequestMapping(value = "/done", method = RequestMethod.POST)
   	public void dummy5(ModelMap model, @RequestParam String ISBN){
    	user.addRead(ISBN);
   	}
    
    @RequestMapping(value = "/rate", method = RequestMethod.POST)
   	public String rateBook(ModelMap model, @RequestParam String rate, @RequestParam String ISBN){
        Book book = bookHandler.getBook(books, ISBN);
        book.updateRate(Double.parseDouble(rate));
    	model.addAttribute("book", book);
    	return "bookInfo";
        
   	}
    
    @RequestMapping(value = "/addReview", method = RequestMethod.POST)
    public String addReview(ModelMap model,@RequestParam String review, @RequestParam String ISBN){
       Book book = bookHandler.getBook(books, ISBN);
       book.addReview(review, "John Doe");
   	   model.addAttribute("book", book);
   	   return "bookInfo";
    }
    

    public User getUserInfo(String token) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
			  .Builder(new ApacheHttpTransport(), JacksonFactory.getDefaultInstance())
			  .setAudience(Collections.singletonList(ClientCredentials.CLIENT_ID))
			  .build();
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
		System.out.println("problem in getting user :)");
		return null;
	}
    
    private List<Book> createBooksList(List<String> isbns) {
    	List<Book> ret = new ArrayList<Book>();
    	isbns.forEach(isbn -> ret.add(bookHandler.getBook(books, isbn)));
    	return ret;
    }
}