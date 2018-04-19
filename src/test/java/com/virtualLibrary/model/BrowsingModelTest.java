package com.virtualLibrary.model;

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
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volume;

@RunWith(MockitoJUnitRunner.class)
public class BrowsingModelTest {
	BrowsingModel model = new BrowsingModel();
	Random random = new Random();
	@Test
	public void testBrowseBooks() {
		int count = random.nextInt(10);
//		BrowsingModel model = Mockito.mock(BrowsingModel.class);
//		Map<String, ArrayList<Book>> map = new HashMap<>();
//		doAnswer((Answer) invocation -> {
//			Map<String, ArrayList<Book>> arg = (Map<String, ArrayList<Book>>) invocation.getArgument(1);
//			map.put("hamada", new ArrayList<>());
//		})
//		Map<String, ArrayList<Book>> map = new HashMap<>();
//		when(model.search(Matchers.any(), "", count))
//			.thenReturn(createList(count));
//		model.search(books, map, "", "", count);
//		Assert.assertFalse(map.size() > count);
//		verify(model, Mockito.calls(1)).search(books, Mockito.anyString(), count);
	}

	private ArrayList<Book> createList(int booksCount) {
		ArrayList<Book> res = new ArrayList<>();
		for (int i = 0; i < booksCount; i++) {
//			res.add(new Book(vol));
		}
		return res;
	}

}
