package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
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
import com.loopj.android.http.JsonHttpResponseHandler;
import com.psapp.worldcupapp.DetailActivity;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.adapters.LiveAdapter;
import com.psapp.worldcupapp.models.Match;

public class LiveScoreFragment extends Fragment {
	// ScoresAdapter scoreAdapter;
	LiveAdapter liveAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<Match> matches = new ArrayList<Match>();
	ListView lvLiveScores;
	private String title;
	private int page;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_live, container, false);
		
		return view;
	}

	public static LiveScoreFragment newInstance(int page, String title) {
		LiveScoreFragment lsf = new LiveScoreFragment();
		Bundle b = new Bundle();
		b.putInt("someInt", page);
		b.putString("someTitle", title);
		lsf.setArguments(b);
		return lsf;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		lvLiveScores = (ListView) getActivity().findViewById(R.id.lvLiveScore);
//		getLiveScores();
//		getFixtures();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		page = getArguments().getInt("someInt", 0);
		title = getArguments().getString("someTitle");
	}
	
	public void onResume(){
		super.onResume();
		Log.d("DEBUG", "live score --- onResume");
		getLiveScores();
		getFixtures();
	}

	JSONArray fixturesJson;

	public void getLiveScores() {
		String url = URL + "/livescorestemp.json";
		Log.d("DEBUG", url);
		client.get(url, new AsyncHttpResponseHandler() {
			public void onSuccess(String json) {
				try {
					fixturesJson = new JSONArray();
					JSONObject obj = new JSONObject(json);
//					Log.d("DEBUG", "Live: \n" + obj.toString());
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
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
	}

	public void getFixtures() {
		String url = URL + "/fixtureswc.json";
		Log.d("DEBUG", url);
		client.get(url, new JsonHttpResponseHandler() {
			public void onSuccess(int code, JSONObject json) {
				try {
					JSONArray temp = json.getJSONArray("table");
//					Log.d("DEBUG", "Upcoming: \n" + temp.toString());
					for (int i = 0; i < temp.length(); i++) {
						fixturesJson.put(temp.get(i));
					}
					matches = Match.fromJson(fixturesJson);
					liveAdapter = new LiveAdapter(getActivity(), matches);
					
					if(lvLiveScores!=null){
						lvLiveScores.setAdapter(liveAdapter);

						lvLiveScores
								.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(AdapterView<?> parent,
											View view, int position, long id) {
										
										Match f = (Match) lvLiveScores.getItemAtPosition(position);
										if(f.getLiveTime().equals("")){
											
										}
										else{

											Intent intent = new Intent(getActivity(),
													DetailActivity.class);
											
											intent.putExtra("temp", f);
											
											startActivity(intent);
										}
										
									}
								});
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
}
