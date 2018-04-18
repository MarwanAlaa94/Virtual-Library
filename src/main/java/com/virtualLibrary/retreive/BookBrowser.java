package com.virtualLibrary.retreive;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.virtualLibrary.Authentication.ClientCredentials;
import com.virtualLibrary.model.BrowsingModel;
import com.virtualLibrary.utils.Utils;

@Controller
public class BookBrowser {
	private static final String APPLICATION_NAME = "VirtualLibrary";
	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat
			.getCurrencyInstance();
	private static final NumberFormat PERCENT_FORMATTER = NumberFormat
			.getPercentInstance();
	private BrowsingModel browsingModel = new BrowsingModel();
	private ClientCredentials clientCredentials;
	private  JsonFactory jsonFactory;
	private  Books books;
	
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
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String browseBooks(ModelMap model) {
		List<String> categories = Utils.getSupportedCategories();
		for(String category : categories){
		    System.out.println("hamada" + category);
			browsingModel.browseBooks(books, model,category.replaceAll("\"", ""));
		}
		return "home";
	}
	
	//@RequestMapping(value = "/search", method = RequestMethod.POST)
	//public String search(ModelMap model){
	//	return browsingModel.search(books, model);
		
	//}
}