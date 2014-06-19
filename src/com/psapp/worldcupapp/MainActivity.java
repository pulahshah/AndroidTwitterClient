package com.psapp.worldcupapp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.psapp.worldcupapp.adapters.PageAdapter;
import com.viewpagerindicator.TitlePageIndicator;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends FragmentActivity{
	private ViewPager vPager;
	private PageAdapter pageAdapter;
	private PagerTitleStrip pagerTitleStrip;
	TitlePageIndicator mIndicator;
	//private ActionBar actionBar;
	private Crouton crouton;
	
	private boolean isConnected;
	private String[] tabs = { "Matches", "Results", "Table", "News" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		
		vPager = (ViewPager) findViewById(R.id.vpPager);
		pageAdapter = new PageAdapter(getSupportFragmentManager());
		vPager.setAdapter(pageAdapter);
		vPager.setPageTransformer(true, new ZoomOutPageTransformer());
		
		
		
//		mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
//        mIndicator.setViewPager(vPager);
		
		
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
		
		
		
		
		
//		
		vPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(int position) {
	                    // When swiping between pages, select the
	                    // corresponding tab.
	                    getActionBar().setSelectedNavigationItem(position);
	                }
	            });
	

		final ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.football_256_white);
		
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
		TextView abTitle = (TextView) findViewById(titleId);
//		abTitle.setTextColor(color.primary_text_dark);
		
//		SpannableString s = new SpannableString("World Cup 2014");
//		s.setSpan(new TypefaceSpan("MyTypeface.otf"), 0, s.length(),
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		actionBar.setTitle(s);
		
	    // Specify that tabs should be displayed in the action bar.
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	    // Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // show the given tab
	        	// When the tab is selected, switch to the
	            // corresponding page in the ViewPager.
	            vPager.setCurrentItem(tab.getPosition());
	        }

	        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // hide the given tab
	        }

	        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // probably ignore this event
	        }

			
	    };

	    // Add 3 tabs, specifying the tab's text and TabListener
	    for (int i = 0; i <= 3; i++) {
	        actionBar.addTab(
	                actionBar.newTab()
	                        .setText(tabs[i])
	                        .setTabListener(tabListener));
	    }

		
		
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


}
