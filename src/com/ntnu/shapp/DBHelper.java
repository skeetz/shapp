package com.ntnu.shapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ShappDB";
	private static final  int DATABASE_VERSION = 1;
	//Database creation statement
	//private static final String DATABASE_CREATE_USERS = "create table Users ( _id integer primary key, name text not null);";
	private static final String DATABASE_CREATE_PRODUCTS = "create table Products (_id integer primary key, ean text, insight text);";
	
	public DBHelper(Context c) {
		super(c, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL(DATABASE_CREATE_USERS);
		db.execSQL(DATABASE_CREATE_PRODUCTS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        //database.execSQL("DROP TABLE IF EXISTS Users");
        database.execSQL("DROP TABLE IF EXISTS Products");
        onCreate(database);
	}

}
