package com.ntnu.shapp;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

public class Goal{
	private Calendar date;
	private int healthy;
	private int environmentallyFriendly;
	private int price;
	private ArrayList <String> groceries;
	
	public Goal(int healthy, int price, int environmentallyFriendly){
		this.date = Calendar.getInstance();
		this.healthy = healthy;
		this.price = price;
		this.environmentallyFriendly = environmentallyFriendly;	
	}
	
	public Calendar getDate() {
		return date;
	}

	public int getHealthy() {
		return healthy;
	}

	public int getEnvironmentallyFriendly() {
		return environmentallyFriendly;
	}

	public int getPrice() {
		return price;
	}

	public ArrayList<String> getGroceries() {
		return groceries;
	}


	
}
