package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.psapp.worldcupapp.DetailActivity;
import com.psapp.worldcupapp.NetworkChecker;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.adapters.ResultsAdapter;
import com.psapp.worldcupapp.models.Match;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ResultsFragment extends Fragment {
	ResultsAdapter resultAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<Match> matches;
	ListView lvResults;
	private String title;
	private int page;
	long requestTime;
	long prevTime;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_results, container,
				false);
		setHasOptionsMenu(true);
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

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 2);
		title = getArguments().getString("someTitle");
		prevTime = 0;
//		Log.d("DEBUG", "results --- onCreate");
	}
	
	private Crouton crouton;
	public boolean checkConnection(){
		ConnectivityManager cm =
		        (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
	}
	
	
	public void showCrouton(){
		crouton = Crouton.makeText(getActivity(), "No network connection", Style.ALERT)
			    .setConfiguration(new Configuration.Builder().setDuration(Configuration.DURATION_LONG).build());
		crouton.show();
	}
	
	public void hideCrouton(){
		if(crouton != null){
			crouton.hide();
		}
	}
	

	public void onResume() {
		super.onResume();
//		Log.d("DEBUG", "results --- onResume");

		if (NetworkChecker.checkConnection(getActivity())) {
			getResults("default");
			NetworkChecker.hideCrouton();
		} else {
			NetworkChecker.showCrouton(getActivity());
		}
		
	}

	public ResultsAdapter getAdapter() {
		return resultAdapter;
	}

	public void getResults(String caller) {
		requestTime = Calendar.getInstance().getTimeInMillis();

		// if(caller.equals("refresh")){
		// makeCall();
		// }
		// else if (requestTime - prevTime > 300000) {
		// makeCall();
		//
		// } else {
		// Log.d("DEBUG", "Not too soon!");
		// }

		makeCall();

		prevTime = requestTime;
	}

	private void makeCall() {
		matches = new ArrayList<Match>();
		String url = URL + "/historicalscores.json";
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

					resultAdapter = new ResultsAdapter(getActivity(), matches);
					lvResults = (ListView) getActivity().findViewById(
							R.id.lvResults);

					lvResults.setAdapter(resultAdapter);
					lvResults.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							Match match = (Match) lvResults
									.getItemAtPosition(position);
							// Log.d("DEBUG",
							// tempResult.getHomeTeam().toString());

							Intent intent = new Intent(getActivity(),
									DetailActivity.class);
							intent.putExtra("temp", match);
							intent.putExtra("caller", "results");
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

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.refresh:
			
			if(NetworkChecker.checkConnection(getActivity())){
				getResults("refresh");
				NetworkChecker.hideCrouton();
			}
			else{
				NetworkChecker.showCrouton(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
