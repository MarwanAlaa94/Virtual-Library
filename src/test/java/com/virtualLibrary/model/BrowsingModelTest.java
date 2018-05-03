//package com.virtualLibrary.model;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import javax.validation.constraints.AssertTrue;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Matchers;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.runners.MockitoJUnitRunner;
//import org.mockito.stubbing.Answer;
//import static org.mockito.Mockito.verify;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.times;
//import com.google.api.services.books.Books;
//import com.google.api.services.books.Books.Volumes.List;
//import com.google.api.services.books.model.Volume;
//import com.virtualLibrary.retreive.BookBrowser;
//
//@RunWith(MockitoJUnitRunner.class)
//public class BrowsingModelTest {
//	Random random = new Random();
//	@Mock
//	BrowsingModel model;
//	@Mock
//	Books books;
//	@InjectMocks
//	BookBrowser browser;
//	@Before
//	public void initializeMockito() {
//		MockitoAnnotations.initMocks(this);
//	}
//	@Test
//	public void testBrowseBooks() {
//		int count = random.nextInt(10);
//		when(model.search(Mockito.any(Books.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong()))
//		.thenReturn(createList(count));
//		ArrayList<Book> ret = model.search(books, "key", "value", count);
//		assertTrue(count == ret.size());
//		
//	}
//
//	@Test
//	public void testBrowseParameters() {
//		int count = random.nextInt(5);
//		ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
//		
//		when(model.search(Mockito.any(Books.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyLong()))
//		.thenReturn(createList(count));
//		ArrayList<Book> ret = model.search(books, "key", "value", count);
//		verify(model, times(1)).search(Mockito.any(Books.class), stringCaptor.capture(), stringCaptor.capture(), Mockito.anyLong());
//		
//		java.util.List<String> capturedStrings = stringCaptor.getAllValues();
//		assertEquals("key", capturedStrings.get(0));
//		assertEquals("value", capturedStrings.get(1));
//	}
//	
//	@Test
//	public void testCountValue() {
//		int count = random.nextInt(10);
//		Long testingCount = 0L + count;
//		ArgumentCaptor<Long> intCaptor = ArgumentCaptor.forClass(Long.class);
//		
//		when(model.search(Mockito.any(Books.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
//		.thenReturn(createList(count));
//		ArrayList<Book> ret = model.search(books, "key", "value", count);
//		verify(model, times(1)).search(Mockito.any(Books.class), Mockito.anyString(), Mockito.anyString(), intCaptor.capture());
//		
//		java.util.List<Long> capturedInt = intCaptor.getAllValues();
//		assertEquals(testingCount, capturedInt.get(0));
//	}
//	
//	private ArrayList<Book> createList(int booksCount) {
//		ArrayList<Book> res = new ArrayList<>();
//		for (int i = 0; i < booksCount; i++) {
//			res.add(new Book(i + ""));
//		}
//		return res;
//	}
//
//}
