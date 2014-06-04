package com.psapp.worldcupapp.fragments;

import com.psapp.worldcupapp.R;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

public class NewsFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
		View view = inflater.inflate(R.layout.fragment_scores_list, container, false);
		return view;
	}
}
