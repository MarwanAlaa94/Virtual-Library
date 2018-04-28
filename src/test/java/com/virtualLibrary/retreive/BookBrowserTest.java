package com.virtualLibrary.retreive;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import com.google.api.services.books.Books;
import com.virtualLibrary.model.Book;
import com.virtualLibrary.model.BrowsingModel;

public class BookBrowserTest {
	ModelMap modelMap = new ModelMap();
	@Mock
	BrowsingModel model;
	@InjectMocks
	BookBrowser browser;
	@Before
	public void initializeMockito() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void testBrowseBooksCallsInnerSearch() {
		browser.browseBooks(modelMap, "test");
		verify(model, atLeast(1)).browseBooks(any(Books.class), anyString());
	}
	@Test
	public void testGetBookGridCallsSearch() {
		String drama = "Drama";
		browser.getBookGrid(modelMap, drama);
		verify(model).search(any(Books.class), anyString(), anyString(), Mockito.anyLong());
	}
	
	@Test
	public void testGetBookGridParameters() {
		String drama = "Drama";
		ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
		
		browser.getBookGrid(modelMap, drama);
		
		verify(model).search(any(Books.class), stringCaptor.capture(), stringCaptor.capture(), Mockito.anyLong());
		java.util.List<String> capturedStrings = stringCaptor.getAllValues();
		
		assertEquals("category", capturedStrings.get(0));
		assertEquals(drama, capturedStrings.get(1));
	}
}
