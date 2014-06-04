package com.psapp.worldcupapp.adapters;

import com.psapp.worldcupapp.fragments.LiveScoreFragment;
import com.psapp.worldcupapp.fragments.NewsFragment;
import com.psapp.worldcupapp.fragments.StatsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter{

	public PageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch(arg0){
		case 0:
			return new LiveScoreFragment();
		case 1:
			return new NewsFragment();
		case 2:
			return new StatsFragment();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// TODO 
		return 3;
	}

}
