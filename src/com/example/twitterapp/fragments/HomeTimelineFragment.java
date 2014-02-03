package com.example.twitterapp.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.example.twitterapp.MyTwitterApp;
import com.example.twitterapp.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	
	TweetsListFragment fragmentTweets;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		
		MyTwitterApp.getRestClient().getTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
			
			public void onFailure(Throwable e, JSONArray error){
				Log.d("DEBUG", "onFailure: " + error.toString());
			}
		});
	}
}
