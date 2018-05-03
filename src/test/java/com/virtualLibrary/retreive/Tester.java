package com.virtualLibrary.retreive;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.virtualLibrary.Authentication.ClientCredentials;
import com.virtualLibrary.model.Book;
import com.virtualLibrary.model.BrowsingModel;
import com.virtualLibrary.utils.Utils;


public class Tester {
	private Books books;
	private BookInfo bookInfo;
	public Tester() throws GeneralSecurityException, IOException {
		books = new Books.Builder(
				GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
				.setGoogleClientRequestInitializer(
					new BooksRequestInitializer(
						ClientCredentials.API_KEY
					)
				).build();
		bookInfo = new BookInfo(books);
	}
	
	@Before
	public void autoDelete() {
		new File("src/main/resources/books/NotISBN.properties").delete();
	}
	
	@Test
	public void testGetRatingNonExistingBook() {
		Double result = bookInfo.getRating("NotISBN");
		System.out.println(result);
		assertTrue(Math.abs(0 - result) < 1e-6);
	}
	
	@Test
	public void testRateBook() {
		bookInfo.rateBook(null, 5);
	}

	@Test
	public void testReviewBookNonExisting() {
		int count = bookInfo.getReviewCount("NotISBN");
		assertTrue(count == 0);
	}
	
	@Test
	public void testReviewBookExisting() {
		bookInfo.reviewBook("NotISBN", "user", "review");
		int count = bookInfo.getReviewCount("NotISBN");
		assertTrue(count == 1);
		bookInfo.reviewBook("NotISBN", "user2", "review2");
		count = bookInfo.getReviewCount("NotISBN");
		assertTrue(count == 2);
	}
	
	@Test
	public void testNullReview() {
		bookInfo.reviewBook("NotISBN", null, null);
	}

	@Test
	public void testGetReviews() {
		bookInfo.reviewBook("NotISBN", "user", "review");
		bookInfo.reviewBook("NotISBN", "user1", "review2");
		List<List<String>> lists = bookInfo.getReviews("NotISBN");
		List<String> users = lists.get(0);
		List<String> reviews = lists.get(1);
		assertTrue(users.get(0).equals("user"));
		assertTrue(users.get(1).equals("user1"));
		assertTrue(reviews.get(0).equals("review"));
		assertTrue(reviews.get(1).equals("review2"));
	}
	

}
