package com.virtualLibrary.model;

import java.util.ArrayList;
import java.util.Map;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volumes;

public class BrowsingModel {

	public void browseBooks(Books books, Map<String,ArrayList<Book>> map, String category) {
		search(books, map, "category", category, 10);
	}
	
	public void search(Books books, Map<String, ArrayList<Book>> map,
			String searchKey, String searchVal, long limit) {
		String query = searchKey + ":" + searchVal;
		ArrayList<Book> result = search(books, query, limit);
		if (result != null && result.size() > 0)
			map.put(searchVal, result);
		return;
	}
	
	public ArrayList<Book> search(Books books, String query, long limit) {
		ArrayList<Book> result = new ArrayList<Book>();
		System.out.println(query);
		Volumes volumes = null;
		try {
			volumes = books.volumes()
							.list(query)
							.setPrintType("books")
							.setOrderBy("relevance")
							.setMaxResults(limit)
							.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
			System.out.println("No matches found.");
			return null;
		}
		volumes.getItems().forEach(item -> result.add(new Book(item)));
		return result;
	}
}
