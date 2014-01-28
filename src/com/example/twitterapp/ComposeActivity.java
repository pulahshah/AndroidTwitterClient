package com.example.twitterapp;

import org.json.JSONArray;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_post_tweet:
			Log.d("DEBUG", "Clicked post tweet");
			/*
			 * 1. get tweet text 
			 * 2. post tweet 
			 * 3. go to timeline
			 */
			//postTweet();
			
			EditText etComposeTweet = (EditText) findViewById(R.id.etComposeTweet);
			Editable etweetText = etComposeTweet.getText();
			String tweetText = etweetText.toString();
			
			if(tweetText.isEmpty()){
				Toast.makeText(getApplicationContext(), "Tweet can not be empty!", Toast.LENGTH_SHORT).show();
			}
			else{
				MyTwitterApp.getRestClient().postTweet(etweetText, new AsyncHttpResponseHandler(){
					
				});
				
				finish();
				
			}
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
