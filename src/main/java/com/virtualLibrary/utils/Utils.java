package com.virtualLibrary.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class Utils {
	
	public static String standardImageLink = "/resources/img/book1.png";
    public static List getSupportedCategories() {
    	Properties prop = new Properties();
    	InputStream input = null;
    	
    	List<String> categories = new ArrayList<String>();
    	try {

    		input = Utils.class.getClassLoader().getResourceAsStream("homeCategories.properties");
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
}
