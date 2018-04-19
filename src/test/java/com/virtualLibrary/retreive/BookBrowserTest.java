package com.virtualLibrary.retreive;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import com.google.api.services.books.Books;
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
		browser.browseBooks(modelMap);
		verify(model, atLeast(1)).browseBooks(any(Books.class), anyString());
	}
	@Test
	public void testGetBookGridCallsSearch() {
		String drama = "Drama";
		browser.getBookGrid(modelMap, drama);
		verify(model).search(any(Books.class), anyString(), anyString(), Mockito.anyLong());
	}
}
