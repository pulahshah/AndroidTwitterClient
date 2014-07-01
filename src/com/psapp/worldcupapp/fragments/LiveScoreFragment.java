package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
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
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.psapp.worldcupapp.DetailActivity;
import com.psapp.worldcupapp.MainActivity;
import com.psapp.worldcupapp.NetworkChecker;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.adapters.LiveAdapter;
import com.psapp.worldcupapp.client.FootballClient;
import com.psapp.worldcupapp.models.Match;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class LiveScoreFragment extends Fragment {
	// ScoresAdapter scoreAdapter;
	LiveAdapter liveAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	private String title;
	private int page;
	private Crouton crouton;
	static LiveScoreFragment lsf;
	long currTime;
	long prevTime;
	ImageView ivNoMatches;
	
	long debug_start;
	
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_live, container, false);
		return view;
	}

	public static LiveScoreFragment newInstance(int page, String title) {
		lsf = new LiveScoreFragment();
		Bundle b = new Bundle();
		b.putInt("someInt", page);
		b.putString("someTitle", title);
		lsf.setArguments(b);
		return lsf;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onCreate(Bundle savedInstanceState) {
		Log.d("DEBUG", "LF onCreate");
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 0);
		title = getArguments().getString("someTitle");
		setHasOptionsMenu(true);
	}

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
		Log.d("DEBUG", "LF onResume");
		super.onResume();
		if (NetworkChecker.checkConnection(getActivity())) {
//			startAnimation();
			debug_start = System.currentTimeMillis();
			getLiveScores();
			NetworkChecker.hideCrouton();
		} else {
			NetworkChecker.showCrouton(getActivity());
		}
		currTime = System.currentTimeMillis();
		prevTime = System.currentTimeMillis();
	}

	public void getLiveScores() {
		Log.d("DEBUG", "LF get live scores called");
		final JSONArray fixturesJson = new JSONArray();
		String url = URL + FootballClient.LIVE_URL + ".json";
		client.get(url, new AsyncHttpResponseHandler() {
			public void onSuccess(String json) {
				if (!json.equals("null")) {
					try {
						JSONObject obj = new JSONObject(json);
						Iterator<?> keys = obj.keys();
						while (keys.hasNext()) {
							String key = (String) keys.next();
							if (obj.get(key) instanceof JSONObject) {
								fixturesJson.put(obj.get(key));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Log.d("DEBUG", "No live matches");
				}
				getFixtures(fixturesJson);
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
	}

	public void getFixtures(JSONArray live) {
		final JSONArray fixJson = live;
		Log.d("DEBUG", "LF get fixtures scores called");
		String url_fixtures = URL + "/fixtureswc.json";
		client.get(url_fixtures, new JsonHttpResponseHandler() {
			public void onSuccess(int code, JSONObject json) {
				try {
					JSONArray temp = json.getJSONArray("table");
					for (int i = 0; i < temp.length(); i++) {
						fixJson.put(temp.get(i));
					}
					ArrayList<Match> matches = Match.fromJson(fixJson, "fixtures");
					displayMatches(matches);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
	}
	
		public void displayMatches(ArrayList<Match> matches) {
			Log.d("DEBUG", "display matches called -----------");
		if(matches.size() > 0){
			LinearLayout llNoMatches = (LinearLayout) getActivity().findViewById(R.id.llNoMatch);
			llNoMatches.setVisibility(View.GONE);
			
			if (lsf != null && !(lsf.isDetached() || lsf.isRemoving())) {
				Activity ac = (MainActivity) getActivity();
				if (ac != null && !ac.isFinishing()) {
					final ListView lvLiveScores = (ListView) ac.findViewById(R.id.lvLiveScore);
					Parcelable state = lvLiveScores.onSaveInstanceState();
					liveAdapter = new LiveAdapter(ac, matches);
					lvLiveScores.setAdapter(liveAdapter);
					liveAdapter.notifyDataSetChanged();
					lvLiveScores.onRestoreInstanceState(state);
					lvLiveScores.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {

							Match f = (Match) lvLiveScores
									.getItemAtPosition(position);
							if (f.getLiveTime().equals("")) {

							} else {
								Intent intent = new Intent(getActivity(),
										DetailActivity.class);
								intent.putExtra("temp", f);
								intent.putExtra("caller", "live");
								startActivity(intent);
							}
						}
					});
				}
			}
		}
		else{
			TextView tvNoMatches = (TextView) getActivity().findViewById(R.id.tvNoMatches);
			tvNoMatches.setText("No live matches :(");
		}
		Log.d("DEBUG", "Live Time: " + (System.currentTimeMillis() - debug_start) + "");
		debug_start = System.currentTimeMillis();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.refresh:
			if (NetworkChecker.checkConnection(getActivity())) {
				currTime = System.currentTimeMillis();
				if(prevTime + 2000 < currTime){
					getLiveScores();
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
