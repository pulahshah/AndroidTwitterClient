package com.example.twitterapp;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		Log.d("DEBUG", "onCreate in the TimelineActivity");
		
		//loadTweets();
		
	}

	
	protected void onResume(){
		super.onResume();
		
		loadTweets();
	}
	
	
	public void loadTweets(){
		MyTwitterApp.getRestClient().getTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				Log.d("DEBUG", "onSuccess: Yay!");
				Log.d("DEBUG", jsonTweets.toString());
				
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
				TweetAdapter2 tweetAdapter = new TweetAdapter2(getBaseContext(), tweets);
				lvTweets.setAdapter(tweetAdapter);
			}
			
			public void onFailure(Throwable e, JSONArray error){
				Log.d("DEBUG", "onFailure: " + error.toString());
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.compose_tweet:
			Log.d("DEBUG", "Clicked compose tweet");
			Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
			startActivity(i);
			return true;
		case R.id.refresh_tweets:
			Log.d("DEBUG", "Clicked refresh timeline");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
