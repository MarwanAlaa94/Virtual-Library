package com.virtualLibrary.test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.api.services.books.Books;
import com.google.api.services.books.model.Volume;
import com.virtualLibrary.model.Book;
import com.virtualLibrary.model.BrowsingModel;

@RunWith(MockitoJUnitRunner.class)
public class BrowsingModelTest {	Random random = new Random();
	@Mock
	Books books;
	@Mock
	Volume vol;
	@Mock
	BrowsingModel model = new BrowsingModel();
	
	@Test
	public void testBrowseBooks() {
		int count = random.nextInt(10);
		Map<String, ArrayList<Book>> map = new HashMap<>();
		when(model.search(Matchers.any(), "", count))
			.thenReturn(createList(count));
		model.search(books, map, "", "", count);
		Assert.assertFalse(map.size() > count);
		verify(model, Mockito.calls(1)).search(books, Mockito.anyString(), count);
	}

	private ArrayList<Book> createList(int booksCount) {
		ArrayList<Book> res = new ArrayList<>();
		for (int i = 0; i < booksCount; i++) {
			res.add(new Book(vol));
		}
		return res;
	}

}
