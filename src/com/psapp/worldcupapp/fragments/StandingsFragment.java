package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

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
import com.psapp.worldcupapp.models.Standing;

public class StandingsFragment extends Fragment {
	StandingsAdapter standingsAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<Standing> standings = new ArrayList<Standing>();
	ArrayList<Standing> standingsFinal = new ArrayList<Standing>();
	private String title;
	private int page;

	TreeMap<Integer, ArrayList<Standing>> map = new TreeMap<Integer, ArrayList<Standing>>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceBundle) {
		View view = inflater.inflate(R.layout.fragment_standings, container,
				false);
		return view;
	}

	public static StandingsFragment newInstance(int page, String title) {
		StandingsFragment sf = new StandingsFragment();
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
	}

	public void onResume() {
		super.onResume();
		Log.d("DEBUG", "standings --- onResume");
		getStandings();
	}

	public StandingsAdapter getAdapter() {
		return standingsAdapter;
	}

	public void getStandings() {
		String url = URL + "/standings.json";
		Log.d("DEBUG", url);
		client.get(url, new AsyncHttpResponseHandler() {

			public void onSuccess(String json) {
				try {
					JSONArray standingsArray = new JSONArray(json);
					JSONArray inGroupJson = new JSONArray();
					for (int i = 0; i < standingsArray.length(); i++) { // iterate over groups
						
						JSONObject obj = new JSONObject(standingsArray.get(i)
								.toString());
						
						Iterator<?> keys = obj.keys();

						while (keys.hasNext()) {	// iterate over each standing
							String key = (String) keys.next();
							// Log.d("DEBUG", "Key: " + key);
							if (obj.get(key) instanceof JSONObject) {
								inGroupJson.put(obj.get(key));	// adding individual standing to array
																
							}
								
						}
						
					}
					
					standings = Standing.fromJson(inGroupJson);
					
					sortStandings(standings);
					
					
					standingsAdapter = new StandingsAdapter(getActivity(),
							standingsFinal);
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
	
	
	public void sortStandings(ArrayList<Standing> s){
		
		for(int i=0; i<s.size(); i++){
			int groupid = Integer.parseInt(s.get(i).getGroupId());
			ArrayList<Standing> temp;
			if(map.containsKey(groupid)){
				temp	 = map.get(groupid);
			}
			else{
				temp = new ArrayList<Standing>();
			}
			temp.add(s.get(i));
			map.put(groupid, temp);
		}
		
		// 
		for(Map.Entry<Integer,ArrayList<Standing>> entry : map.entrySet()) {
			  Integer key = entry.getKey();
			  ArrayList<Standing> value = entry.getValue();
			  
//			  for(int i=0; i<value.size(); i++){
//				  Log.d("DEBUG", key + "  ----   " + value.get(i).getTeam() + " - " + value.get(i).getPoints());
//			  }  

			  Collections.sort(value, Standing.StandingComparator);
			
			  standingsFinal.addAll(value);
			}
		
	}
}
