package com.virtualLibrary.model;

import java.util.ArrayList;
import java.util.List;

public class Book {
	private String title;
	private String author;
	private String description;
	private String imageLink;
	private List<String> reviews = new ArrayList<String>();
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageLink() {
		return imageLink;
	}
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public List<String> getReviews() {
		return reviews;
	}
	public void setReviews(List<String> reviews) {
		this.reviews = reviews;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getGoogleMoreInfo() {
		return googleMoreInfo;
	}
	public void setGoogleMoreInfo(String googleMoreInfo) {
		this.googleMoreInfo = googleMoreInfo;
	}
	private double rating;
	private String googleMoreInfo;
	
	
	

}
