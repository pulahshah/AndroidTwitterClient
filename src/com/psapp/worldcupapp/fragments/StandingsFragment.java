package com.psapp.worldcupapp.fragments;

import com.psapp.worldcupapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StandingsFragment extends Fragment {
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
		View view = inflater.inflate(R.layout.fragment_standings, container, false);
		return view;
	}
	
	
	public static StandingsFragment newInstance(String str){
		StandingsFragment sf = new StandingsFragment();
		Bundle b = new Bundle();
		b.putString("msg", str);
		sf.setArguments(b);
		return sf;
	}
}
