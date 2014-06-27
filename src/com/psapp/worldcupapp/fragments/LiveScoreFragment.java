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
import android.os.Handler;
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
	static AsyncHttpClient client = new AsyncHttpClient();
	static ArrayList<Match> matches;
	static JSONArray fixturesJson;
	ListView lvLiveScores;
	private String title;
	private int page;
	private Crouton crouton;
	static LiveScoreFragment lsf;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_live, container, false);
		setHasOptionsMenu(true);
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
			getLiveScores();
			getFixtures();
			NetworkChecker.hideCrouton();
		} else {
			NetworkChecker.showCrouton(getActivity());
		}

		final Handler mHandler = new Handler();

		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// while (true) {
		// try {
		// Thread.sleep(15000);
		// mHandler.post(new Runnable() {
		//
		// @Override
		// public void run() {
		// LiveScoreFragment.getLiveScores();
		// getFixtures();
		// }
		// });
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		// }
		// }
		// }).start();
	}

	public static void getLiveScores() {
		Log.d("DEBUG", "LF get live scores called");
		fixturesJson = new JSONArray();
		matches = new ArrayList<Match>();
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

			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
	}

	public void getFixtures() {
		Log.d("DEBUG", "LF get fixtures scores called");
		String url = URL + "/fixtureswc.json";
		client.get(url, new JsonHttpResponseHandler() {
			public void onSuccess(int code, JSONObject json) {
				try {
					JSONArray temp = json.getJSONArray("table");
					for (int i = 0; i < temp.length(); i++) {
						fixturesJson.put(temp.get(i));
					}
					matches = Match.fromJson(fixturesJson, "fixtures");

					if (lsf != null
							&& !(lsf.isDetached() || lsf.isRemoving())) {
						Activity ac = (MainActivity) getActivity();
						
						if(ac != null && !ac.isFinishing()){
							lvLiveScores = (ListView) ac.findViewById(R.id.lvLiveScore);
							Parcelable state = lvLiveScores.onSaveInstanceState();
							liveAdapter = new LiveAdapter(ac, matches);
							lvLiveScores.setAdapter(liveAdapter);
							liveAdapter.notifyDataSetChanged();
							lvLiveScores.onRestoreInstanceState(state);
							lvLiveScores
									.setOnItemClickListener(new OnItemClickListener() {
										@Override
										public void onItemClick(
												AdapterView<?> parent, View view,
												int position, long id) {

											Match f = (Match) lvLiveScores
													.getItemAtPosition(position);
											if (f.getLiveTime().equals("")) {

											} else {
												Intent intent = new Intent(
														getActivity(),
														DetailActivity.class);

												intent.putExtra("temp", f);
												intent.putExtra("caller", "live");
												startActivity(intent);
											}

										}
									});
						}
					}
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

	protected MenuItem refreshItem = null;

	protected void setRefreshItem(MenuItem item) {
		refreshItem = item;
	}

	public void stopRefresh() {
		if (refreshItem != null) {
			refreshItem.setActionView(null);
		}
	}

	public void runRefresh() {
		if (refreshItem != null) {
			refreshItem.setActionView(R.layout.indeterminate_progress_action);
		}
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
				getLiveScores();
				getFixtures();
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
