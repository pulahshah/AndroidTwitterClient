package com.psapp.worldcupapp.fragments;

import java.util.ArrayList;

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

import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.ScoreDetailActivity;
import com.psapp.worldcupapp.adapters.ScoresAdapter;
import com.psapp.worldcupapp.client.FootballClient;
import com.psapp.worldcupapp.models.Score;

public class LiveScoreFragment extends Fragment {
	ScoresAdapter scoreAdapter;
	
	private FootballClient fc;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle){
		View view = inflater.inflate(R.layout.fragment_scores_list, container, false);
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		fc = new FootballClient();
		
		// get live scores
		fc.getLiveScore();
		
		
		ArrayList<Score> liveScores = new ArrayList<Score>();
		
		Score s = new Score();
		for(int i=0; i<20; i++){
			//home
			s.setHomeTeam("Brazil");
			s.setHomeScore(2);
			
			
			// away
			s.setAwayTeam("Argentina");
			s.setAwayScore(1);
			
			liveScores.add(s);
		}
		scoreAdapter = new ScoresAdapter(getActivity(), liveScores);
		ListView lvLiveScore = (ListView) getActivity().findViewById(R.id.lvLiveScore);
		
		lvLiveScore.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				Log.d("DEBUG", "tapped on list item " + position);
				Intent i = new Intent(v.getContext(), ScoreDetailActivity.class);
				v.getContext().startActivity(i);
			}
			
		});

		
		lvLiveScore.setAdapter(scoreAdapter);
	}
	
	public ScoresAdapter getAdapter(){
		return scoreAdapter;
	}
}
