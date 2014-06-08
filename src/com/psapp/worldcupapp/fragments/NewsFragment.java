package com.psapp.worldcupapp.fragments;

import com.psapp.worldcupapp.R;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

public class NewsFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
		View view = inflater.inflate(R.layout.fragment_news, container, false);
		return view;
	}
	
	public static NewsFragment newInstance(String str){
		NewsFragment nf = new NewsFragment();
		Bundle b = new Bundle();
		b.putString("msg", str);
		nf.setArguments(b);
		return nf;
	}
}
