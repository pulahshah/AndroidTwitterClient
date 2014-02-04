package com.example.twitterapp;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	
	ImageView ivProfileIm;
	TextView tvUserName, tvScreenName, tvFollowers, tvFollowing;
	int id;
	String userid, screenName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		
		
		// Show the Up button in the action bar.
		setupActionBar();
		setupViews();
		
		MyTwitterApp.getRestClient().getMyInfo(new JsonHttpResponseHandler(){
			public void onSuccess(JSONObject obj){
				User u = User.fromJson(obj);
				getActionBar().setTitle("@" + u.getScreenName());
				
				Log.d("DEBUG", u.getName());
				
				//ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
				ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileIm);
				tvUserName.setText(u.getName());
				tvScreenName.setText(u.getTagline());
				tvFollowers.setText("" + u.getFollowersCount() + " Followers");
				tvFollowing.setText("" + u.getFriendsCount() + " Following");
			}
		});
	}
	
	public static int safeLongToInt(long l) {
	    if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
	        throw new IllegalArgumentException
	            (l + " cannot be cast to int without changing its value.");
	    }
	    return (int) l;
	}
	
	public void setupViews(){
		ivProfileIm = (ImageView) findViewById(R.id.ivProfileIm);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvScreenName = (TextView) findViewById(R.id.tvScreenName);
		tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		tvFollowing = (TextView) findViewById(R.id.tvFollowing);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
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
