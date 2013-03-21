package com.example.fadderukeappen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class DayActivity extends Activity {

	private GestureDetector mGesture;

	private DBEventDataSource dbEventDataSource;

	Date date;
	LinearLayout listLayout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("DEBUG", "OnCreate");
		setContentView(R.layout.activity_day);
		listLayout = (LinearLayout) findViewById(R.id.activity_day_linear_layout);
		listLayout.setOrientation(LinearLayout.VERTICAL);	

		Bundle extras = getIntent().getExtras();
		dbEventDataSource = new DBEventDataSource(this);
		dbEventDataSource.open();

		if (extras != null) {
			String date = extras.getString("com.example.fadderukeappen.daylist");
			this.date = new Date(date);
			Log.d("DEBUG", "Intent date is " + date);
			displayEventsOnDate(this.date);
		} else {

			date = new Date("18.08.2013");
			displayEventsOnDate(date);
		}

		mGesture = new GestureDetector(this, mOnGesture);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d("DEBUG", "OnNewIntent");
		listLayout.removeAllViews();
		listLayout.setOrientation(LinearLayout.VERTICAL);	

		Bundle extras = intent.getExtras();
		if (extras != null) {
			String date = extras.getString("com.example.fadderukeappen.daylist");
			this.date = new Date(date);
			displayEventsOnDate(this.date);

		} else {

			date = new Date("18.08.2013");
			displayEventsOnDate(date);
		}
		Log.d("DEBUG", "Intent date isss " + date);
		mGesture = new GestureDetector(this, mOnGesture);
	}

	protected void displayEventsOnDate(Date date) {
		this.date = date;
		insertEventViews(getAllEventViews(date));
	}

	protected void insertEventViews(ArrayList<EventLayout> eventViews) {		

		for(EventLayout el : eventViews) {
			FrameLayout borderParent = new FrameLayout(this);
			borderParent.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT));
			borderParent.setPadding(0, 1, 0, 1);
			borderParent.setBackgroundColor(Color.GRAY);
			borderParent.addView(el);
			listLayout.addView(borderParent);
		}

	}

	protected ArrayList<EventLayout> getAllEventViews(Date date) {
		List<Event> events = dbEventDataSource.getAllEventsOnDate(date);
		Collections.sort(events);
		Log.d("DEBUG", "SIZE: " + events.size());
		ArrayList<EventLayout> eventLayouts = new ArrayList<EventLayout>();
		for(int i = 1; i < events.size(); i++) {
			eventLayouts.add(new EventLayout(this, events.get(i).getTitle(), events.get(i).getTime().toString(), events.get(i).getLocation()));
		}
		return eventLayouts;

	}

	@Override
	public void onBackPressed() {
		dbEventDataSource.close();
		super.onBackPressed();	
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean handled = super.dispatchTouchEvent(ev);
		handled = mGesture.onTouchEvent(ev);    
		return handled;
	}

	private OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if(velocityX >= 1000) {
				Date d = date.prevDate();
				Log.v("fling", "FLING to " + d.toString());

				Intent intent = new Intent(DayActivity.this, DayActivity.class);
				intent.putExtra("com.example.fadderukeappen.daylist", d.toString());
				startActivity(intent);
				dbEventDataSource.close();
				return true;
			} else if (velocityX <= -1000) {
				Date d = date.nextDate();
				Log.v("fling", "FLING to " + d.toString());

				Intent intent = new Intent(DayActivity.this, DayActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.putExtra("com.example.fadderukeappen.daylist", d.toString());
				startActivity(intent);
				dbEventDataSource.close();
			}

			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			return false;
		}
	};

}
