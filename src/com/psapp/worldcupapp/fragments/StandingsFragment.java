package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.adapters.StandingsAdapter;
import com.psapp.worldcupapp.models.Group;

public class StandingsFragment extends Fragment {
	StandingsAdapter standingsAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<Group> standingsTemp = new ArrayList<Group>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_standings, container,
				false);
		getStandings();
		return view;
	}

	public static StandingsFragment newInstance(String str) {
		StandingsFragment sf = new StandingsFragment();
		Bundle b = new Bundle();
		b.putString("msg", str);
		sf.setArguments(b);
		return sf;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		
	}

	public StandingsAdapter getAdapter() {
		return standingsAdapter;
	}

	public void getStandings() {
		String url = URL + "/standings.json";
		client.get(url, new AsyncHttpResponseHandler() {
			
			public void onSuccess(String json) {
				try {
					JSONArray standingsArray = new JSONArray(json);
					JSONArray inGroupJson = new JSONArray();
					int count = 1;
					for (int i = 0; i < standingsArray.length(); i++) {
						
						JSONObject obj = new JSONObject(standingsArray.get(i)
								.toString());
//						Log.d("DEBUG", "Obj: " + obj.toString());
						Iterator<?> keys = obj.keys();
						while (keys.hasNext()) {
							String key = (String) keys.next();
//							Log.d("DEBUG", "Key: " + key);
							if (obj.get(key) instanceof JSONObject) {
								inGroupJson.put(obj.get(key));
								Log.d("DEBUG", count+"");
								count++;
							}
						}
					}
					standingsTemp = Group.fromJson(inGroupJson);
					standingsAdapter = new StandingsAdapter(getActivity(),
							standingsTemp);
					final ListView lvStandings = (ListView) getActivity()
							.findViewById(R.id.lvStandings);
					lvStandings.setAdapter(standingsAdapter);

				} catch (JSONException e) {
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
