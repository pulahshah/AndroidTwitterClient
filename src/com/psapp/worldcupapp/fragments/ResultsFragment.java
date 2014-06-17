package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.psapp.worldcupapp.DetailActivity;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.adapters.ResultsAdapter;
import com.psapp.worldcupapp.models.Match;

public class ResultsFragment extends Fragment {
	ResultsAdapter resultAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<Match> matches = new ArrayList<Match>();
	ListView lvResults;
	private String title;
	private int page;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_results, container,
				false);
		return view;
	}

	public static ResultsFragment newInstance(int page, String title) {
		ResultsFragment rf = new ResultsFragment();
		Bundle b = new Bundle();
		b.putInt("someInt", page);
		b.putString("someTitle", title);
		rf.setArguments(b);
		return rf;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

//		getResults();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 2);
		title = getArguments().getString("someTitle");
	}

	
	public void onResume(){
		super.onResume();
		Log.d("DEBUG", "results --- onResume");
		getResults();
	}

	public ResultsAdapter getAdapter() {
		return resultAdapter;
	}

	public void getResults() {
		String url = URL + "/historicalscores.json";
		Log.d("DEBUG", "url: " + url);
		client.get(url, new AsyncHttpResponseHandler() {
			public void onSuccess(String json) {
				try {
					JSONObject obj = new JSONObject(json);
					JSONArray resultsJson = new JSONArray();
					Iterator<?> keys = obj.keys();
					while (keys.hasNext()) {
						String key = (String) keys.next();
						if (obj.get(key) instanceof JSONObject) {
							resultsJson.put(obj.get(key));
						}
					}
					matches = Match.fromJson(resultsJson, "results");
					
					Collections.sort(matches, Match.MatchDateComparator);
					
					
					resultAdapter = new ResultsAdapter(getActivity(),
							matches);
					lvResults = (ListView) getActivity().findViewById(
							R.id.lvResults);
					
					lvResults.setAdapter(resultAdapter);
					lvResults.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							
							Match match = (Match) lvResults.getItemAtPosition(position);
//							Log.d("DEBUG", tempResult.getHomeTeam().toString());
							
							Intent intent = new Intent(getActivity(),
									DetailActivity.class);
							intent.putExtra("temp", match);
							startActivity(intent);

						}

					});

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
	}
	
	
	public void sortResults(ArrayList<Match> m){
		TreeMap<DateTime, Match> sortMap = new TreeMap<DateTime, Match>();
		
	}
}
