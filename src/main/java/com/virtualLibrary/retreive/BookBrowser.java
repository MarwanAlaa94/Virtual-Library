package com.virtualLibrary.retreive;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
import com.google.api.services.books.model.Volume;
import com.virtualLibrary.Authentication.ClientCredentials;
import com.virtualLibrary.Authentication.User;
import com.virtualLibrary.model.Book;
import com.virtualLibrary.model.BrowsingModel;
import com.virtualLibrary.utils.Utils;

@Controller
public class BookBrowser {
	private static final String APPLICATION_NAME = "VirtualLibrary";
	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat
			.getCurrencyInstance();
	private static final NumberFormat PERCENT_FORMATTER = NumberFormat
			.getPercentInstance();
	
	@Autowired
	private BrowsingModel browsingModel;
	
	private ClientCredentials clientCredentials;
	private JsonFactory jsonFactory;
	private Books books;
	private User user;
	private BookInfo bookInfo;
	public BookBrowser() {
		ClientCredentials clientCredentials = new ClientCredentials();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		clientCredentials.errorIfNotSpecified();
		
		try {
			books = new Books.Builder(
					GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
					.setApplicationName(com.virtualLibrary.retreive.BookBrowser.APPLICATION_NAME)
					.setGoogleClientRequestInitializer(
						new BooksRequestInitializer(
							ClientCredentials.API_KEY
						)
					).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		bookInfo = new BookInfo(books); 
	}
	
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String browseHome(ModelMap model) {
		List<String> categories = Utils.getSupportedCategories();
		HashMap<String, ArrayList<Book>> map = new HashMap<String, ArrayList<Book>> ();
		for(String category : categories){
			map.put(category.replaceAll("\"", ""), browsingModel.browseBooks(books, category.replaceAll("\"", ""), bookInfo));
		}
		model.addAttribute("categoryList", map);
		return "home";
	}

	@RequestMapping(value = "/bookGrid", method = RequestMethod.POST)
	public String  getBookGrid(ModelMap model,  @RequestParam String category) {
		HashMap<String, ArrayList<Book>> map = new HashMap<String, ArrayList<Book>> ();	
		map.put(category, browsingModel.search(books, "category", category, 40, bookInfo));
		model.addAttribute("categoryList", map);
		return "bookGrid";
	}
	

    @RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(ModelMap model, @RequestParam String key, @RequestParam String value){
    	HashMap<String, ArrayList<Book>> map = new HashMap<String, ArrayList<Book>> ();
    	map.put(value, browsingModel.search(books, key, value, 40, bookInfo));
    	model.addAttribute("categoryList", map);
		model.keySet().forEach(temp->System.out.println("model =      " + temp));
		return "bookGrid";
	}
    
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
   	public String dummy(ModelMap model){
    	System.out.println(user.getName());
		HashMap<String, List<Book>> map = new HashMap<String, List<Book>> ();
		List<Book> favBooks = Utils.getFavouriteBooks(user.getUserId(), browsingModel, books, bookInfo);
		List<Book> readBooks = Utils.getReadBooks(user.getUserId(), browsingModel, books, bookInfo);
		List<Book> toBeReadBooks = Utils.getToBeReadBooks(user.getUserId(), browsingModel, books, bookInfo);		
		
		for(Book book : favBooks){
			System.out.println("favourites: " + book.getTitle());	
		}

		for(Book book : readBooks){
			System.out.println("read: " + book.getTitle());	
		}

		for(Book book : toBeReadBooks){
			System.out.println("toBeRead: " + book.getTitle());	
		}
		
		map.put("Favourites", favBooks);
		map.put("ReadBooks", readBooks);
		map.put("TobeReadBooks",toBeReadBooks);

		model.addAttribute("UserInfo", map);
		model.addAttribute("user", user);
		
		System.out.println("hamda is here");
   		return "userInfo";
   	}
      
    @RequestMapping(value = "/bookInfo", method = RequestMethod.POST)
   	public String getBookInfo(ModelMap model,@RequestParam String ISBN){
    	System.out.println(ISBN);
    	Book book = browsingModel.search(books, "Title", ISBN, 1, bookInfo).get(0);
        model.addAttribute("book", book);
        return "bookInfo";
   	}
      
    @RequestMapping(value = "/addFav", method = RequestMethod.POST)
   	public void addFav(ModelMap model, @RequestParam String ISBN){
    	System.out.println("I am hereeeeeeeeeeeeeeeeeeeeeeeeeeee");
    	Book book = browsingModel.search(books, "Title", ISBN, 1, bookInfo).get(0);
    	Utils.addFavBook(user.getUserId(), book, ISBN);
    	
    	System.out.println("I am executed again :)");
   	}
      
    @RequestMapping(value = "/addToRead", method = RequestMethod.POST)
   	public void dummy4(ModelMap model, @RequestParam String ISBN){
    	Book book = browsingModel.search(books, "Title", ISBN, 1, bookInfo).get(0);
    	Utils.addToBeReadBook(user.getUserId(), book, ISBN);
      	System.out.println("added to read");
   	}
      
    @RequestMapping(value = "/done", method = RequestMethod.POST)
   	public void dummy5(ModelMap model, @RequestParam String ISBN){
    	Book book = browsingModel.search(books, "Title", ISBN, 1, bookInfo).get(0);
    	Utils.addReadBook(user.getUserId(), book, ISBN);
      	System.out.println("done");
   	}
    
    @RequestMapping(value = "/rate", method = RequestMethod.POST)
   	public void rateBook(ModelMap model, @RequestParam String rate, @RequestParam String ISBN){
      	bookInfo.rateBook(ISBN, Integer.parseInt(rate));
      	
   	}
    
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
	public String getUserBooks(ModelMap model, @RequestParam String token) {
    	user = getUserInfo(token);
    	if(!Utils.userDataFileExists(user.getUserId())) {
			Utils.initiateUserDataFile(user.getUserId());
		}else {
			System.out.println("data file exists!");
		}

		System.out.println("token: " + token);
		
		System.out.println(user.getName());
		HashMap<String, List<Book>> map = new HashMap<String, List<Book>> ();
		List<Book> favBooks = Utils.getFavouriteBooks(user.getUserId(), browsingModel, books, bookInfo);
		List<Book> readBooks = Utils.getReadBooks(user.getUserId(), browsingModel, books, bookInfo);
		List<Book> toBeReadBooks = Utils.getToBeReadBooks(user.getUserId(), browsingModel, books, bookInfo);		
		
		for(Book book : favBooks){
			System.out.println("favourites: " + book.getTitle());	
		}

		for(Book book : readBooks){
			System.out.println("read: " + book.getTitle());	
		}

		for(Book book : toBeReadBooks){
			System.out.println("toBeRead: " + book.getTitle());	
		}
		
		map.put("Favourites", favBooks);
		map.put("ReadBooks", readBooks);
		map.put("TobeReadBooks",toBeReadBooks);

		model.addAttribute("UserInfo", map);
		model.addAttribute("user", user);
		
		System.out.println("hamda is here");
		
		return "userInfo";
	}

      
    @RequestMapping(value = "/addReview", method = RequestMethod.POST)
      public void addReview(ModelMap model,@RequestParam String review, @RequestParam String ISBN){
          bookInfo.reviewBook(ISBN, "hamada", review);    
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
		return null;
	}
}