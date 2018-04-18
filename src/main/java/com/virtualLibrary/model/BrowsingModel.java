package com.virtualLibrary.model;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.ui.ModelMap;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volumes;

public class BrowsingModel {

	public void browseBooks(Books books, ModelMap model, String category) {
		search(books, model,"category", category, 5);
	}
	
	public void search(Books books, ModelMap model,
			String searchKey, String searchVal, long limit) {
		
		ArrayList<Book> result = new ArrayList<Book>();
		String query = searchKey + ":" + searchVal;
		System.out.println(query);
		List volumesList = null;
		try {
			volumesList = books.volumes().list(query).setOrderBy("relevance").setMaxResults(limit);
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
			return;
		}
		volumes.getItems().forEach(item -> result.add(new Book(item)));
		System.out.println(searchVal);
		model.addAttribute(searchVal, result);
		return;
	}	
}
