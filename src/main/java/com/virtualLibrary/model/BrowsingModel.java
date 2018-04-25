package com.virtualLibrary.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volumes;

public class BrowsingModel {

	public void browseBooks(Books books, Map<String,ArrayList<Book>> map, String category) {
		search(books, map, "category", category, 10);
	}
	
	public void search(Books books, Map<String, ArrayList<Book>> map,
			String searchKey, String searchVal, long limit) {
		
		ArrayList<Book> result = new ArrayList<Book>();
		String query = searchKey + ":" + searchVal;
		System.out.println(query);
		List volumesList = null;
		try {
			volumesList = books.volumes().list(query).setPrintType("books").
					setOrderBy("relevance").setMaxResults(limit);
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
		map.put(searchVal, result);
		return;
	}	
}
