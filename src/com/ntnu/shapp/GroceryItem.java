package com.ntnu.shapp;

public class GroceryItem {
	
	private String formatType;
	private String articleNumber;
	private String description;
	
	public GroceryItem(String formatType, String articleNumber, String descrption){
		this.formatType = formatType;
		this.articleNumber = articleNumber;
		this.description = descrption;
	}
	
	public String getFormatType(){
		return this.formatType;
	}
	
	public String getArticleNumber(){
		return this.articleNumber;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}

}
