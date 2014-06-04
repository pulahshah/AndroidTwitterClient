package com.psapp.worldcupapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseInstallation;
import com.parse.PushService;

import com.psapp.worldcupapp.adapters.PageAdapter;
import com.psapp.worldcupapp.client.FootballClient;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends FragmentActivity implements TabListener {
	private ViewPager vPager;
	private PageAdapter pageAdapter;
	private ActionBar actionBar;
	private Crouton crouton;
	
	private FootballClient fc;
	
	private boolean isConnected;
	private String[] tabs = { "Live Scores", "News", "Stats" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		
//		Parse.initialize(this, "UyBnXScZ9pB5z2aBDIzU57rh6smKbmGijjWICdzB", "konQzSGmn8EkI4VRlCZGPNzSC2xMM4eaa0o4um6g");
		
		// registering activity for push
//		PushService.setDefaultPushCallback(this, MainActivity.class);
//		ParseInstallation.getCurrentInstallation().saveInBackground();
		
		// track notification entry
//		ParseAnalytics.trackAppOpened(getIntent());
		
		// test object
//		ParseObject testObject = new ParseObject("TestObject");
//		testObject.put("foo", "bar");
//		testObject.saveInBackground();
		
		vPager = (ViewPager) findViewById(R.id.vpPager);
		pageAdapter = new PageAdapter(getSupportFragmentManager());
		actionBar = getActionBar();
		
		vPager.setAdapter(pageAdapter);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
		
		vPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			 
		    @Override
		    public void onPageSelected(int position) {
		        actionBar.setSelectedNavigationItem(position);
		    }
		 
		    @Override
		    public void onPageScrolled(int arg0, float arg1, int arg2) {
		    }
		 
		    @Override
		    public void onPageScrollStateChanged(int arg0) {
		    }
		});
		
		
	}
	
	
	@Override
	public void onResume() {
	    super.onResume();

	    isConnected = checkConnection();
	    Log.d("NETWORK", "is connected: " + isConnected);
	}
	
	
	public void showCrouton(){
		crouton = Crouton.makeText(this, "Test", Style.ALERT)
			    .setConfiguration(new Configuration.Builder().setDuration(Configuration.DURATION_INFINITE).build());
		crouton.show();
	}
	
	
	private boolean checkConnection(){
		ConnectivityManager cm =
		        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		vPager.setCurrentItem(tab.getPosition());
		
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
