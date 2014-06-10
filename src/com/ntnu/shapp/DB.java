package com.ntnu.shapp;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DB {

	private DBHelper dbHelper;
	private SQLiteDatabase database;
	
	public DB(Context c){
		dbHelper = new DBHelper(c);
		database = dbHelper.getWritableDatabase();
	}
	
	public void addInsight(String ean, String insight){
		Log.i("Database Transaction", "Adding entry: " + ean + " with insight: " + insight);
		ContentValues values = new ContentValues();
		values.put("insight", insight);
		values.put("ean", ean);
		database.insert("Products", null, values);
	}
	//Henter siste insight for dette produktet, hvis det eksisterer
	public String getInsight(String ean){
		String query = "SELECT * FROM Products WHERE ean=" + ean + ";";
		Cursor c = database.rawQuery(query, null);
		String insight;
		if(c.getCount()>0){
			c.moveToLast();
			insight = c.getString(2);
			c.close();			
		}else
			insight = "ingen insights funnet";
		return insight;
	}
	
	public void deleteInsights(){
		database.execSQL("delete from Products");
		Log.i("Database Transaction", "Deleted all records from Products table");
	}
	/*
	public void addUser(String userName){
		Log.i("Database Transaction", "Adding " + userName + " to the database");
		ContentValues values = new ContentValues();
		values.put("name", userName);
		database.insert("Users", null, values);
	}
	
	
	public ArrayList<String> getUsers(){
		Cursor c = database.query("Users", null, null, null, null, null, null);
		ArrayList<String> users = new ArrayList<String>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			users.add(c.getString(1));
			c.moveToNext();
		}
		c.close();
		
		return users;
		
	}*/
}
