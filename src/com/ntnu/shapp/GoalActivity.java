package com.ntnu.shapp;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class GoalActivity extends MainActivity implements OnSeekBarChangeListener {
	private ArrayList<Integer> goalValues;
	private SeekBar priceBar, healthyBar, environmentBar;
	private TextView priceTextView, healthyTextView, environmentTextView, priceValueTextView, healthyValueTextView, environmentValueTextView;
	private Button startShopping;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goal);
		priceTextView = (TextView)findViewById(R.id.goal_textview_price);
		healthyTextView = (TextView)findViewById(R.id.goal_textview_healthy);
		environmentTextView = (TextView)findViewById(R.id.goal_textview_environmentally_friendly);
		
		priceValueTextView = (TextView)findViewById(R.id.goal_textview_price_value);
		healthyValueTextView = (TextView)findViewById(R.id.goal_textview_healthy_value);
		environmentValueTextView = (TextView)findViewById(R.id.goal_textview_environmentallyfriendly_value);
		
		healthyBar = (SeekBar) findViewById(R.id.goal_seekbar_healthy);
		healthyBar.setOnSeekBarChangeListener(this);
		environmentBar = (SeekBar) findViewById(R.id.goal_seekbar_environmentally_friendly);
		environmentBar.setOnSeekBarChangeListener(this);
		priceBar = (SeekBar) findViewById(R.id.goal_seekbar_price);
		priceBar.setOnSeekBarChangeListener(this);
		
		startShopping = (Button) findViewById(R.id.goal_button_start_shopping);
		startShopping.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goalValues = new ArrayList<Integer>();
				goalValues.add(priceBar.getProgress());
				goalValues.add(healthyBar.getProgress());
				goalValues.add(environmentBar.getProgress());
				
				Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
				intent.putIntegerArrayListExtra("goalValues", goalValues);
				startActivity(intent);
				
			}
		});
	}
	@Override
	public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
		if(sb.equals(healthyBar)){
			healthyValueTextView.setText(""+progress);
		}
		else if(sb.equals(environmentBar)){
			environmentValueTextView.setText(""+progress);
		}
		else if(sb.equals(priceBar)){
			priceValueTextView.setText(""+progress);
		}
		
	}
	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

}
