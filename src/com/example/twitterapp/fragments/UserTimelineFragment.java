package com.example.twitterapp.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.example.twitterapp.MyTwitterApp;
import com.example.twitterapp.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	
	
	public static UserTimelineFragment newInstance(String passedName){
		UserTimelineFragment frag = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putString("screen_name", passedName);
		frag.setArguments(args);
		return frag;
	}
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		String screenName = getArguments().getString("screen_name");
		
		
		Log.d("DEBUG", "Screen: UserTimeFragment || screenName: " + screenName);
		
		MyTwitterApp.getRestClient().getUserTimeline(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
			
			public void onFailure(Throwable e, JSONArray error){
				Log.d("DEBUG", "onFailure: " + error.toString());
			}
		}, screenName);
	}
}
