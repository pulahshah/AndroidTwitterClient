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
	LiveScoreFragment lf;
	ResultsFragment rf;
	StandingsFragment sf;
	NewsFragment nf;
	
	private static int NUM_ITEMS = 4;
	
	public PageAdapter(FragmentManager fm) {
		super(fm);
		
		lf = LiveScoreFragment.newInstance(0, "Live");
		rf = ResultsFragment.newInstance(1, "Results");
		sf = StandingsFragment.newInstance(2, "Standings");
		nf = NewsFragment.newInstance(3, "News");
	}

	@Override
	public Fragment getItem(int arg0) {
		switch(arg0){
		case 0:
			return lf;
		case 1:
			return rf;
		case 2:
			return sf;
		case 3:
			return nf;
		
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return NUM_ITEMS;
	}
	
	public CharSequence getPageTitle (int position) {
		switch(position){
		case 0:
			return "Live";
		case 1:
			return "Results";
		case 2:
			return "Standings";
		case 3:
			return "News";
		}
		return "";
    }

}
