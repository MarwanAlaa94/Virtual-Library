package com.virtualLibrary.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.books.model.Volume;
import com.virtualLibrary.utils.Utils;

public class Book {
	private String title;
	private String author;
	private String description;
	private String imageLink;
	private List<String> reviews = new ArrayList<String>();
	
	public Book(Volume volume) {
		Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
		this.title = volumeInfo.getTitle();
		List<String> authors = volumeInfo.getAuthors();
		this.googleMoreInfo = volumeInfo.getInfoLink();
		//System.out.println(this.googleMoreInfo);
		if (authors != null && !authors.isEmpty())
			this.author = authors.get(0);
		String imageLink = null;
        try {
        	imageLink = volumeInfo.getImageLinks().get("thumbnail").toString();
        	this.imageLink = imageLink;
        } catch (Exception e) {
        }finally {
        	if(imageLink == null){
        		this.imageLink = Utils.standardImageLink;
        	}
        }
	}
	
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
	public void setReviews(ArrayList<String> result) {
		this.reviews = result;
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
