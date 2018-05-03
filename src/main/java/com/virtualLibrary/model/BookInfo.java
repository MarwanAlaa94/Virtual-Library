	package com.virtualLibrary.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;

public class BookInfo {
	private Books books;
	
	public BookInfo(Books books) {
		this.books = books;
	}
	public double getRating(String bookId) {
		return 0;

	}
	
	public void rateBook(String bookId, int rating) {

	}
	
	public void reviewBook(String bookId, String userName, String review){

	}
	
	public int getReviewCount(String bookId) {
		return 0;
	
	}
	

	public ArrayList<List<String>> getReviews(String bookId) {
		return null;

	}
	

	public Volume getBook(String bookId) {
		try {
			List<Volume> vols = books.volumes().list("Title:" + bookId).execute().getItems();
			if (vols != null && !vols.isEmpty()) return vols.get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
