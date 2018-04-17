package com.virtualLibrary.model;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.ui.ModelMap;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volumes;

public class BrowsingModel {

	public String browseBooks(Books books, ModelMap model) {
		return search(books, model);
	}
	
	public String search(Books books, ModelMap model) {
		ArrayList<Book> result = new ArrayList<Book>();
		String query = model.getOrDefault("searchType", "title") + ": " + model.getOrDefault("query", "a");
		List volumesList = null;
		try {
			volumesList = books.volumes().list(query).setOrderBy("relevance");
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
		volumes.getItems().forEach(item -> result.add(new Book(item)));
		model.addAttribute("result", result);
		return "home";
	}	
}
