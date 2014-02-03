package com.example.twitterapp.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.example.twitterapp.MyTwitterApp;
import com.example.twitterapp.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsFragment extends TweetsListFragment {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		
		MyTwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler(){
			public void onSuccess(JSONArray jsonTweets){
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
			
			public void onFailure(Throwable e, JSONArray error){
				Log.d("DEBUG", "onFailure: " + error.toString());
			}
		});
	}
	
	
}
