package com.example.twitterapp.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.twitterapp.R;
import com.example.twitterapp.Tweet;
import com.example.twitterapp.TweetsAdapter;

public class TweetsListFragment extends Fragment {
	
	TweetsAdapter tweetAdapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceBundle){
		return inflater.inflate(R.layout.fragment_tweets_list, parent, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		tweetAdapter = new TweetsAdapter(getActivity(), tweets);
		ListView lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetAdapter);
	}
	
	public TweetsAdapter getAdapter(){
		return tweetAdapter;
	}
}
