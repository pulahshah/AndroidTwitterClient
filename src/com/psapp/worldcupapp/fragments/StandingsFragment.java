package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.psapp.worldcupapp.MainActivity;
import com.psapp.worldcupapp.NetworkChecker;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.adapters.StandingsAdapter;
import com.psapp.worldcupapp.models.Standing;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class StandingsFragment extends Fragment {
	StandingsAdapter standingsAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<Standing> standings;
	ArrayList<Standing> standingsFinal;
	private String title;
	private int page;
	static StandingsFragment sf;
	long currTime;
	long prevTime;

	long debug_start;
	
	TreeMap<Integer, ArrayList<Standing>> map;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_standings, container,
				false);
		return view;
	}

	public static StandingsFragment newInstance(int page, String title) {
		sf = new StandingsFragment();
		Bundle b = new Bundle();
		b.putInt("someInt", page);
		b.putString("someTitle", title);
		sf.setArguments(b);
		return sf;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// getStandings();

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
			debug_start = System.currentTimeMillis();
			getStandings();
			NetworkChecker.hideCrouton();
		} else {
			NetworkChecker.showCrouton(getActivity());
		}
		currTime = System.currentTimeMillis();
		prevTime = System.currentTimeMillis();
	}

	public StandingsAdapter getAdapter() {
		return standingsAdapter;
	}

	public void getStandings() {
		Log.d("DEBUG", "fetching standings");
		standings = new ArrayList<Standing>();
		standingsFinal = new ArrayList<Standing>();
		map = new TreeMap<Integer, ArrayList<Standing>>();
		String url = URL + "/standings.json";
		client.get(url, new AsyncHttpResponseHandler() {

			public void onSuccess(String json) {
				try {
					JSONArray standingsArray = new JSONArray(json);
					JSONArray inGroupJson = new JSONArray();
					for (int i = 0; i < standingsArray.length(); i++) { // iterate
																		// over
																		// groups

						JSONObject obj = new JSONObject(standingsArray.get(i)
								.toString());

						Iterator<?> keys = obj.keys();

						while (keys.hasNext()) { // iterate over each standing
							String key = (String) keys.next();
							// Log.d("DEBUG", "Key: " + key);
							if (obj.get(key) instanceof JSONObject) {
								inGroupJson.put(obj.get(key)); // adding
																// individual
																// standing to
																// array

							}
						}
					}

					standings = Standing.fromJson(inGroupJson);
					sortStandings(standings);

					if (sf != null && !(sf.isDetached() || sf.isRemoving())) {
						Activity ac = (MainActivity) getActivity();
						if(ac != null && !ac.isFinishing()){
							standingsAdapter = new StandingsAdapter(ac,
									standingsFinal);
							final ListView lvStandings = (ListView) ac.findViewById(R.id.lvStandings);
							lvStandings.setAdapter(standingsAdapter);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Log.d("DEBUG", "Standings Time: " + (System.currentTimeMillis() - debug_start) + "");
				debug_start = System.currentTimeMillis();
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
	}

	public void sortStandings(ArrayList<Standing> s) {
		for (int i = 0; i < s.size(); i++) {
			int groupid = Integer.parseInt(s.get(i).getGroupId());
			ArrayList<Standing> temp;
			if (map.containsKey(groupid)) {
				temp = map.get(groupid);
			} else {
				temp = new ArrayList<Standing>();
			}
			temp.add(s.get(i));
			map.put(groupid, temp);
		}

		for (Map.Entry<Integer, ArrayList<Standing>> entry : map.entrySet()) {
			Integer key = entry.getKey();
			ArrayList<Standing> value = entry.getValue();
			Collections.sort(value, Standing.StandingComparator);
			standingsFinal.addAll(value);
		}
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
					getStandings();
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
