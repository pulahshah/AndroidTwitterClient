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
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.ScoreDetailActivity;
import com.psapp.worldcupapp.adapters.LiveAdapter;
import com.psapp.worldcupapp.adapters.ScoresAdapter;
import com.psapp.worldcupapp.models.Fixture;
import com.psapp.worldcupapp.models.LiveScore;

public class LiveScoreFragment extends Fragment {
	// ScoresAdapter scoreAdapter;
	LiveAdapter liveAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<Fixture> fixturesTemp = new ArrayList<Fixture>();
	ListView lvLiveScores;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_live, container, false);
		
		return view;
	}

	public static LiveScoreFragment newInstance(String str) {
		LiveScoreFragment lsf = new LiveScoreFragment();
		Bundle b = new Bundle();
		b.putString("msg", str);
		lsf.setArguments(b);
		return lsf;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		lvLiveScores = (ListView) getActivity().findViewById(R.id.lvLiveScore);
		getLiveScores();
		getFixtures();
	}

	JSONArray fixturesJson;

	public void getLiveScores() {
		String url = URL + "/livescorestemp.json";
		client.get(url, new AsyncHttpResponseHandler() {
			public void onSuccess(String json) {
				try {
					fixturesJson = new JSONArray();
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
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
	}

	public void getFixtures() {
		String url = URL + "/fixtureswc.json";
		client.get(url, new JsonHttpResponseHandler() {
			public void onSuccess(int code, JSONObject json) {
				try {
					JSONArray temp = json.getJSONArray("table");
					for (int i = 0; i < temp.length(); i++) {
						fixturesJson.put(temp.get(i));
					}
					fixturesTemp = Fixture.fromJson(fixturesJson);
					liveAdapter = new LiveAdapter(getActivity(), fixturesTemp);
					
					if(lvLiveScores!=null){
						lvLiveScores.setAdapter(liveAdapter);

						lvLiveScores
								.setOnItemClickListener(new OnItemClickListener() {
									@Override
									public void onItemClick(AdapterView<?> parent,
											View view, int position, long id) {
										Intent intent = new Intent(getActivity(),
												ScoreDetailActivity.class);
										String message = "abc";
										intent.putExtra("EXTRA_MESSAGE", message);
										startActivity(intent);
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
