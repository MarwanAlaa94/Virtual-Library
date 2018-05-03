package com.virtualLibrary.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.api.services.books.Books;
import com.virtualLibrary.model.Book;
import com.virtualLibrary.model.BrowsingModel;
import com.virtualLibrary.retreive.BookInfo;



public class Utils {
	
	public static String standardImageLink = "/resources/img/book1.png";
	public static String standardUserLink = "/resources/img/user.png";
    public static List getSupportedCategories() {
    	Properties prop = new Properties();
    	InputStream input = null;
    	
    	List<String> categories = new ArrayList<String>();
    	try {

    		input = Utils.class.getResourceAsStream("homeCategories.properties");
    		prop.load(input);
    		
    		int probNo = Integer.parseInt(prop.getProperty("categoriesNo"));
    		
    		for(int i = 1; i <= probNo; i++){
    			categories.add(prop.getProperty("category" + i));
    		}

    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
		return categories;
    	
    }
    
	public static void initiateUserDataFile(String userId) {
		try {
			Properties properties = new Properties();
			properties.setProperty("favouriteNo", "0");
			properties.setProperty("readNo", "0");
			properties.setProperty("toBeReadNo", "0");
			
			System.out.println("before declaring the file");
			
			File file = new File("src/main/resources/users/" +
					userId + ".properties");
			
			
			
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "user " + userId);
			fileOut.close();
			System.out.println("after closing the stream");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addFavBook(String userId, Book favBook, String isbn) {
		addBook(userId, favBook, "favouriteNo", "favourite", isbn);
	}
	public static void addReadBook(String userId, Book readBook, String isbn) {
		addBook(userId, readBook, "readNo", "read", isbn);
	}
	public static void addToBeReadBook(String userId, Book toBeReadBook,
			String isbn) {
		addBook(userId, toBeReadBook, "toBeReadNo", "toBeRead", isbn);
	}

	private static void addBook(String userId, Book Book, String headProperty,
			String childProperty, String isbn) {
		
		Properties prop = new Properties();
		InputStream input = null;

		List<String> favBooksISBN = new ArrayList<String>();
		try {

			File file = new File("src/main/resources/users/" + userId + ".properties");
			if (!file.exists()) {
				System.out.println("here");
				try {
					System.out.println(file.createNewFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			prop.load(new FileInputStream(file));

			int probNo = Integer.parseInt(prop.getProperty(headProperty));
			
			if(!bookAlreadyExists(prop, probNo, isbn, childProperty)) {
				prop.setProperty(headProperty, String.valueOf(probNo + 1));
				prop.setProperty(childProperty + String.valueOf( probNo + 1), isbn);

				FileOutputStream fileOut = new FileOutputStream(file);
				prop.store(fileOut, "user " + userId);
				fileOut.close();				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static boolean bookAlreadyExists(Properties prop, int probNo, String isbn,
			String childProperty) {

		for(int i = 1; i <= probNo; i++) {
			if(prop.getProperty(childProperty + i).equals(isbn)){
				return true;
			}
		}
		
		return false;
	}

	public static List<Book> getFavouriteBooks(String userId,
			BrowsingModel browsingModel, Books books, BookInfo bookInfo) {
		Properties prop = new Properties();
		InputStream input = null;

		List<String> favBooksISBN = new ArrayList<String>();
		try {

			File file = new File("src/main/resources/users/" + userId
					+ ".properties");
			if (!file.exists()) {
				System.out.println("here");
				try {
					System.out.println(file.createNewFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			prop.load(new FileInputStream(file));

			int probNo = Integer.parseInt(prop.getProperty("favouriteNo"));
			for (int i = 1; i <= probNo; i++) {
				favBooksISBN.add(prop.getProperty("favourite" + i));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		List<Book> favBooks = new ArrayList<Book>();
		String key = "Title";

		for (String value : favBooksISBN) {
			favBooks.addAll(browsingModel.search(books, key, value.replace("\"", ""), 1, bookInfo));
		}

		return favBooks;
	}
	public static List<Book> getReadBooks(String userId,
			BrowsingModel browsingModel, Books books, BookInfo bookInfo) {
		Properties prop = new Properties();
		InputStream input = null;

		List<String> readBooksISBN = new ArrayList<String>();
		try {

			File file = new File("src/main/resources/users/" + userId
					+ ".properties");
			if (!file.exists()) {
				System.out.println("here");
				try {
					System.out.println(file.createNewFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			prop.load(new FileInputStream(file));

			int probNo = Integer.parseInt(prop.getProperty("readNo"));
			for (int i = 1; i <= probNo; i++) {
				readBooksISBN.add(prop.getProperty("read" + i));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		List<Book> readBooks = new ArrayList<Book>();
		String key = "Title";

		for (String value : readBooksISBN) {
			readBooks.addAll(browsingModel.search(books, key, value.replace("\"", ""), 1, bookInfo));
		}

		return readBooks;
	}
	public static List<Book> getToBeReadBooks(String userId,
			BrowsingModel browsingModel, Books books, BookInfo bookInfo) {
		Properties prop = new Properties();
		InputStream input = null;

		List<String> toBeReadBooksISBN = new ArrayList<String>();
		try {

			File file = new File("src/main/resources/users/" + userId
					+ ".properties");
			if (!file.exists()) {
				System.out.println("here");
				try {
					System.out.println(file.createNewFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			prop.load(new FileInputStream(file));

			int probNo = Integer.parseInt(prop.getProperty("toBeReadNo"));
			for (int i = 1; i <= probNo; i++) {
				toBeReadBooksISBN.add(prop.getProperty("toBeRead" + i));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		List<Book> toBeReadBooks = new ArrayList<Book>();
		String key = "ISBN";

		for (String value : toBeReadBooksISBN) {
			toBeReadBooks.addAll(browsingModel.search(books, key, value.replace("\"", ""), 1, bookInfo));
		}

		return toBeReadBooks;
	}
	
	public static boolean userDataFileExists(String userId) {
		File f = new File("src/main/resources/users/" + userId + ".properties");
		if(f.exists() && !f.isDirectory()) { 
		    return true;
		}
		
		return false;
	}

}
