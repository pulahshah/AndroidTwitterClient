package com.psapp.worldcupapp;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.psapp.worldcupapp.models.Result;

public class ResultDetailActivity extends Activity {
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	Result r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_detail);
		r = (Result) getIntent().getSerializableExtra("temp");
		// Show the Up button in the action bar.
		setupActionBar(r.getHomeTeam(), r.getAwayTeam(), r.getDate());

		
		displayData(r);

		//getData();
	}
	
	private void displayData(Result r){
		TextView homeName = (TextView) findViewById(R.id.tvHomeName);
		TextView awayName = (TextView) findViewById(R.id.tvAwayName);
		homeName.setText(r.getHomeTeam().toString());
		awayName.setText(r.getAwayTeam().toString());
		
		TextView homeScore = (TextView) findViewById(R.id.tvHomeScore);
		TextView awayScore = (TextView) findViewById(R.id.tvAwayScore);
		homeScore.setText(r.getHomeResult().toString());
		awayScore.setText(r.getAwayResult().toString());
		
	}

	private void getData() {
		String url = URL + "/historicalscores/65715.json";
		Log.d("DEBUG", "url: " + url);
		client.get(url, new JsonHttpResponseHandler() {
			public void onSuccess(int code, JSONObject json) {
				try {
					Log.d("DEBUG", code + "\n" + json.toString());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar(String home, String away, String date) {
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle(home + " vs " + away);
		ab.setSubtitle(date);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_detail, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

}
