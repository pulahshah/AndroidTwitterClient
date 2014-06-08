package com.psapp.worldcupapp.adapters;

import com.psapp.worldcupapp.fragments.LiveScoreFragment;
import com.psapp.worldcupapp.fragments.NewsFragment;
import com.psapp.worldcupapp.fragments.ResultsFragment;
import com.psapp.worldcupapp.fragments.StandingsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class PageAdapter extends FragmentPagerAdapter{

	public PageAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch(arg0){
		case 0:
			return LiveScoreFragment.newInstance("Live Score");
		case 1:
			return ResultsFragment.newInstance("Results");
		case 2:
			return StandingsFragment.newInstance("Standings");
		case 3:
			return NewsFragment.newInstance("News");
		
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		// TODO 
		return 4;
	}

}
