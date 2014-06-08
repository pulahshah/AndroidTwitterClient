package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;

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
import com.loopj.android.http.JsonHttpResponseHandler;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.ScoreDetailActivity;
import com.psapp.worldcupapp.adapters.ScoresAdapter;
import com.psapp.worldcupapp.models.Score;

public class LiveScoreFragment extends Fragment {
	ScoresAdapter scoreAdapter;
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<Score> liveScoresTemp = new ArrayList<Score>();

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

		getFixtures();
	}

	public ScoresAdapter getAdapter() {
		return scoreAdapter;
	}

	public void getFixtures() {
		String url = URL + "/fixtureswc.json";
		Log.d("DEBUG", "url: " + url);
		client.get(url, new JsonHttpResponseHandler() {
			public void onSuccess(int code, JSONObject json) {
				try {
					// Log.d("DEBUG", code + "\n" + json.toString());
					JSONArray fixturesJson = json.getJSONArray("table");
					liveScoresTemp = Score.fromJson(fixturesJson);
					Log.d("DEBUG", "liveScores -------------------------"
							+ liveScoresTemp.size());

					scoreAdapter = new ScoresAdapter(getActivity(),
							liveScoresTemp);
					ListView lvLiveScore = (ListView) getActivity()
							.findViewById(R.id.lvLiveScore);
					lvLiveScore.setAdapter(scoreAdapter);

					lvLiveScore
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
