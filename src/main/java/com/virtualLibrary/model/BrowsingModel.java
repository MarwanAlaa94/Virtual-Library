package com.virtualLibrary.model;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volumes;
import com.virtualLibrary.retreive.BookInfo;

@Service
public class BrowsingModel {

	public ArrayList<Book> browseBooks(Books books, String category, BookInfo bookInfo) {
		return search(books, "category", category, 7, bookInfo);
	}
	
	public ArrayList<Book> search(Books books,
			String searchKey, String searchVal, long limit ,BookInfo bookInfo) {
		
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
			return result;
		}
		volumes.getItems().forEach(item -> result.add(new Book(item, bookInfo)));
		System.out.println(searchVal);
		return result;
	}	
}
