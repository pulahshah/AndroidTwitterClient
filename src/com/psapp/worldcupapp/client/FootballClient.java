package com.psapp.worldcupapp.client;



import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


public class FootballClient {
	public static final String REST_URL = "http://ec2-54-186-208-212.us-west-2.compute.amazonaws.com:3000";

	
	public void getLiveScore(){
		AsyncHttpClient client = new AsyncHttpClient();
		String url = REST_URL + "/get/historicalScores?u=dev&p=xxx";
		Log.d("NETWORK", "url: " + url);
		client.get(url, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		        Log.d("NETWORK", "Response:" + response);
		    }
		});
	}
}