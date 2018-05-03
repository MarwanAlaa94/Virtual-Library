	package com.virtualLibrary.retreive;

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
		double currRating = 0f;
		int rateCount = 0;
		File file = new File("src/main/resources/books/" + bookId + ".properties");
		try {
			Properties prop = getProp(file);
			currRating = Float.parseFloat(prop.getProperty("BookRating", "0f"));
			rateCount = Integer.parseInt(prop.getProperty("RatingCount", "0"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Volume vol = getBook(bookId);
		System.out.println(vol);
		
		//Double apiRating = 3.5;
		//if(apiRating == null) apiRating = (double) 0;
	//	int apiCount = 5;
		//return (currRating * rateCount + apiRating * apiCount) /
			//	(rateCount + apiCount);
		return currRating;
	}
	
	public void rateBook(String bookId, int rating) {
		if (bookId == null) return;
		File file = new File("src/main/resources/books/" + 
						bookId + ".properties");
		try {
			Properties prop = getProp(file);
			double currRating = Float.parseFloat(prop
							.getProperty("BookRating", "0f"));
			int rateCount = Integer.parseInt(prop
							.getProperty("RatingCount", "0"));
			WritePropertiesFile("BookRating", String
					.valueOf((currRating * rateCount + rating) /
							(++rateCount)), file, prop);
			WritePropertiesFile("RatingCount", String
					.valueOf(rateCount), file, prop);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void reviewBook(String bookId, String userName, String review){
		if (bookId == null || userName == null || review == null) return;
		File file = new File("src/main/resources/books/" +
						bookId + ".properties");
		int reviews = getReviewCount(bookId);
		Properties prop;
		try {
			prop = getProp(file);
			WritePropertiesFile("ReviewCount", String
						.valueOf(++reviews), file, prop);
			WritePropertiesFile("ReviewName" + reviews, userName, file, prop);
			WritePropertiesFile("ReviewDes" + reviews, review, file, prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getReviewCount(String bookId) {
		File file = new File("src/main/resources/books/" +
						bookId + ".properties");
		Properties prop;
		try {
			prop = getProp(file);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return Integer.parseInt(prop.getProperty("ReviewCount", "0"));
	}
	

	public ArrayList<List<String>> getReviews(String bookId) {
		ArrayList<List<String>> ret = new ArrayList<>();
		ret.add(new ArrayList<String>());
		ret.add(new ArrayList<String>());
		int reviews = getReviewCount(bookId);
		File file = new File("src/main/resources/books/" + 
				bookId + ".properties");
		try {
			Properties prop = getProp(file);
			for (int i = 1; i <= reviews; i++) {
				ret.get(0).add(prop.getProperty("ReviewName" + i));
				ret.get(1).add(prop.getProperty("ReviewDes" + i));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private Properties getProp(File file) throws IOException {
		Properties prop = new Properties();
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(file.getAbsolutePath());
		FileInputStream is = new FileInputStream(file);
		prop.load(is);
		is.close();
		return prop;
	}
	public Volume getBook(String bookId) {
		try {
			List<Volume> vols = books.volumes().list("Title:" + bookId).execute().getItems();
			System.out.println("hamada"+bookId);
			if (vols != null && !vols.isEmpty()) return vols.get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void WritePropertiesFile(String key, String data, File file, Properties configProperty) {
		configProperty.setProperty(key, data);
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			configProperty.store(fileOut, "");
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
//	public static void main(String [] arfs) {
//		try {
//			books = new Books.Builder(
//					GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
//					.setGoogleClientRequestInitializer(
//						new BooksRequestInitializer(
//							ClientCredentials.API_KEY
//						)
//					).build();
//			BookInfo ss = new BookInfo(books);
//			ss.getReviews("1781100489").forEach(q -> q.forEach(z->System.out.println(z)));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
