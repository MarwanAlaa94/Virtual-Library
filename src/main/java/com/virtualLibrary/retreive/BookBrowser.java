package com.virtualLibrary.retreive;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volume.VolumeInfo;
import com.google.api.services.books.model.Volumes;
import com.virtualLibrary.Authentication.ClientCredentials;
import com.virtualLibrary.model.Book;

import java.util.*;

@Controller
public class BookBrowser {
	private static final String APPLICATION_NAME = "VirtualLibrary";
	private static final NumberFormat CURRENCY_FORMATTER = NumberFormat
			.getCurrencyInstance();
	private static final NumberFormat PERCENT_FORMATTER = NumberFormat
			.getPercentInstance();

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String browseBooks(ModelMap model) {
		ArrayList<Book> result = new ArrayList<Book>();
		String query = "title:Harry Potter and the Half-Blood Prince";
		ClientCredentials clientCredentials = new ClientCredentials();
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

		clientCredentials.errorIfNotSpecified();
		Books books = null;
		try {
			books = new Books.Builder(
					GoogleNetHttpTransport.newTrustedTransport(), jsonFactory,
					null)
					.setApplicationName(APPLICATION_NAME)
					.setGoogleClientRequestInitializer(
							new BooksRequestInitializer(
									ClientCredentials.API_KEY)).build();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			Book book = new Book();
			Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();

			book.setTitle(volumeInfo.getTitle());

			java.util.List<String> authors = volumeInfo.getAuthors();
			if (authors != null && !authors.isEmpty())
				book.setAuthor(authors.get(0));
			
            String imageLink = volumeInfo.getImageLinks().get("thumbnail").toString();
            book.setImageLink(imageLink);
            
			result.add(book);
		}
		model.addAttribute("result", result);
		return "home";
	}

}