package com.ntnu.shapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class GoalActivity extends MainActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goal);
	}
	
	public void startShopping(View v){
		SeekBar priceBar = (SeekBar) findViewById(R.id.goal_seekbar_price);
		int price = priceBar.getProgress();
		
		SeekBar healthyBar = (SeekBar) findViewById(R.id.goal_seekbar_healthy);
		int healthy = healthyBar.getProgress();
		
		SeekBar environmentBar = (SeekBar) findViewById(R.id.goal_seekbar_environmentally_friendly);
		int environmentally = environmentBar.getProgress();
		
		Intent intent = new Intent(this, ShoppingActivity.class);
		intent.putExtra("price", price);
		intent.putExtra("healthy", healthy);
		intent.putExtra("environmentally", environmentally);
		startActivity(intent);
		
	}

}
