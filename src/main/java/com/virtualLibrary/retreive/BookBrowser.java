package com.virtualLibrary.retreive;

import java.security.GeneralSecurityException;
import java.text.NumberFormat;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import com.virtualLibrary.Authentication.ClientCredentials;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Scanner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookBrowser {
	private static final String APPLICATION_NAME = "VirtualLibrary";
	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
	private static final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance();
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String queryGoogleBooks(ModelMap model){
		
	  String query = "title:Harry Potter and the Half-Blood Prince";	
	  ClientCredentials clientCredentials = new ClientCredentials();
	  JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
	  
	  clientCredentials.errorIfNotSpecified();
	  Books books = null;
	  try {
		books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
	     .setApplicationName(APPLICATION_NAME)
	     .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
         .build();
	  } catch (GeneralSecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
      }
	  
	  System.out.println("Query: [" + query + "]");
	  List volumesList = null;
	try {
		volumesList = books.volumes().list(query);
	} catch (IOException e) {
		e.printStackTrace();
	}

	  Volumes volumes = null;
	try {
		volumes = volumesList.execute();
	} catch (IOException e) {
		e.printStackTrace();
	}
	  if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
	    System.out.println("No matches found.");
	    return "home";
	  }

	  // Output results.
	  for (Volume volume : volumes.getItems()) {
	    Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
	    System.out.println("==========");
	    // Title.
	    System.out.println("Title: " + volumeInfo.getTitle());
	    // Author(s).
	    java.util.List<String> authors = volumeInfo.getAuthors();
	    if (authors != null && !authors.isEmpty()) {
	      System.out.print("Author(s): ");
	      for (int i = 0; i < authors.size(); ++i) {
	        System.out.print(authors.get(i));
	        if (i < authors.size() - 1) {
	          System.out.print(", ");
	        }
	        }
	        System.out.println();
	      }
	      // Description (if any).
	      if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
	        System.out.println("Description: " + volumeInfo.getDescription());
	      }
	      // Ratings (if any).
	      if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
	        int fullRating = (int) Math.round(volumeInfo.getAverageRating().doubleValue());
	        System.out.print("User Rating: ");
	        for (int i = 0; i < fullRating; ++i) {
	          System.out.print("*");
	        }
	        System.out.println(" (" + volumeInfo.getRatingsCount() + " rating(s))");
	      }

	      // Access status.
	      String accessViewStatus = volume.getAccessInfo().getAccessViewStatus();
	      String message = "Additional information about this book is available from Google eBooks at:";
	      if ("FULL_PUBLIC_DOMAIN".equals(accessViewStatus)) {
	        message = "This public domain book is available for free from Google eBooks at:";
	      } else if ("SAMPLE".equals(accessViewStatus)) {
	        message = "A preview of this book is available from Google eBooks at:";
	      }
	      System.out.println(message);
	      // Link to Google eBooks.
	      System.out.println(volumeInfo.getInfoLink());
	    }
	    System.out.println("==========");
	    try {
			System.out.println(
			    volumes.getTotalItems() + " total results at http://books.google.com/ebooks?q="
			    + URLEncoder.encode(query, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return "home";
	  }

}