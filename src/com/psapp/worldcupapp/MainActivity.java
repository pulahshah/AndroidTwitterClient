package com.psapp.worldcupapp;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.psapp.worldcupapp.adapters.PageAdapter;
import com.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends FragmentActivity {
	private ViewPager vPager;
	private PageAdapter pageAdapter;
	TitlePageIndicator mIndicator;
	private String[] tabs = { "Matches", "Results", "Table", "News" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		vPager = (ViewPager) findViewById(R.id.vpPager);
		pageAdapter = new PageAdapter(getSupportFragmentManager());
		vPager.setAdapter(pageAdapter);
		vPager.setPageTransformer(true, new ZoomOutPageTransformer());
		vPager.setOffscreenPageLimit(4);

		vPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				getActionBar().setSelectedNavigationItem(position);
			}
		});

		final ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.football_256_white);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				vPager.setCurrentItem(tab.getPosition());
			}

			public void onTabUnselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
			}

			public void onTabReselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
			}
		};

		// Add 3 tabs, specifying the tab's text and TabListener
		for (int i = 0; i <= 3; i++) {
			actionBar.addTab(actionBar.newTab().setText(tabs[i])
					.setTabListener(tabListener));
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}
}
