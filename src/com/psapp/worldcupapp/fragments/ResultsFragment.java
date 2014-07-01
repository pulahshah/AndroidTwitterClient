package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.psapp.worldcupapp.DetailActivity;
import com.psapp.worldcupapp.MainActivity;
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
	static ResultsFragment rf;
	long currTime;
	long prevTime;
	
	long debug_start;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_results, container,
				false);
				return view;
	}

	public static ResultsFragment newInstance(int page, String title) {
		rf = new ResultsFragment();
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
		setHasOptionsMenu(true);
	}

	private Crouton crouton;

	public boolean checkConnection() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return (activeNetwork != null && activeNetwork
				.isConnectedOrConnecting());
	}

	public void showCrouton() {
		crouton = Crouton.makeText(getActivity(), "No network connection",
				Style.ALERT).setConfiguration(
				new Configuration.Builder().setDuration(
						Configuration.DURATION_LONG).build());
		crouton.show();
	}

	public void hideCrouton() {
		if (crouton != null) {
			crouton.hide();
		}
	}

	public void onResume() {
		super.onResume();
		if (NetworkChecker.checkConnection(getActivity())) {
			getResults("default");
			NetworkChecker.hideCrouton();
		} else {
			NetworkChecker.showCrouton(getActivity());
		}
		currTime = System.currentTimeMillis();
		prevTime = System.currentTimeMillis();
	}

	public ResultsAdapter getAdapter() {
		return resultAdapter;
	}

	public void getResults(String caller) {
		debug_start = System.currentTimeMillis();
		makeCall();
	}

	private void makeCall() {
		Log.d("DEBUG", "fetching results");
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

					if (rf != null && !(rf.isDetached() || rf.isRemoving())) {
						Activity ac = (MainActivity) getActivity();

						if (ac != null && !ac.isFinishing()) {
							resultAdapter = new ResultsAdapter(ac, matches);
							lvResults = (ListView) ac
									.findViewById(R.id.lvResults);
							Parcelable state = lvResults.onSaveInstanceState();
							lvResults.setAdapter(resultAdapter);
							resultAdapter.notifyDataSetChanged();
							lvResults.onRestoreInstanceState(state);
							lvResults
									.setOnItemClickListener(new OnItemClickListener() {
										@Override
										public void onItemClick(
												AdapterView<?> parent,
												View view, int position, long id) {

											Match match = (Match) lvResults
													.getItemAtPosition(position);

											Intent intent = new Intent(
													getActivity(),
													DetailActivity.class);
											intent.putExtra("temp", match);
											intent.putExtra("caller", "results");
											startActivity(intent);
										}
									});
						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d("DEBUG", "Results Time: " + (System.currentTimeMillis() - debug_start) + "");
				debug_start = System.currentTimeMillis();
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.refresh:
			if (NetworkChecker.checkConnection(getActivity())) {
				currTime = System.currentTimeMillis();
				if(prevTime + 3000 < currTime){
					getResults("refresh");
				}
				prevTime = currTime;
				NetworkChecker.hideCrouton();
			} else {
				NetworkChecker.showCrouton(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
