package com.virtualLibrary.utils;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class Queries {
	
	public static String selectBook = "select * from book where ISBN = ?;";
	public static String insertBook = "INSERT INTO BOOK (title, ISBN, rate, rateNo) VALUES (?, ?, ?, ?);";
	public static String getReviews = "select review,reviewer from review where ISBN = ?;";
	public static String updateRate = "UPDATE BOOK SET rate = ?, rateNo = ? WHERE ISBN = ?;";
	public static String addReview = "INSERT INTO REVIEW (REVIEW, REVIEWER, ISBN) VALUES (?, ?, ?)";

}
