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
		
	}
}
