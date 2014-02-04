package com.example.twitterapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.twitterapp.fragments.HomeTimelineFragment;
import com.example.twitterapp.fragments.MentionsFragment;

public class TimelineActivity extends FragmentActivity implements TabListener{

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		setNavTabs();
	}

	
	private void setNavTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("Twitter");
		
		Tab tabHome = actionBar.newTab().setText("Home").setTag("HomeTimelineFragment").setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsTimelineFragment").setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.profile:
			Log.d("DEBUG", "Clicked profile");
			Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
			startActivity(i);
			return true;
		case R.id.compose_tweet:
			Log.d("DEBUG", "Clicked compose tweet");
			Intent i1 = new Intent(TimelineActivity.this, ComposeActivity.class);
			startActivity(i1);
			return true;
		case R.id.refresh_tweets:
			Log.d("DEBUG", "Clicked refresh timeline");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		
		if(tab.getTag() == "HomeTimelineFragment"){
			fts.replace(R.id.fl_container, new HomeTimelineFragment());
		}
		else{
			fts.replace(R.id.fl_container, new MentionsFragment());
		}
		fts.commit();
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}

}
