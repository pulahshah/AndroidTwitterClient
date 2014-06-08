package com.psapp.worldcupapp.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.models.Score;

public class ScoresAdapter extends ArrayAdapter<Score>{

	public ScoresAdapter (Context context, List<Score> scores) {
		super(context, 0, scores);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_score, null);
		}

		final Score score = getItem(position);
		
		TextView homeTeamName = (TextView)view.findViewById(R.id.tvHomeName);
		TextView awayTeamName = (TextView)view.findViewById(R.id.tvAwayName);
		
		homeTeamName.setText(score.getHomeTeam().toString());
		awayTeamName.setText(score.getAwayTeam().toString());
		
//		TextView stadiumLocation = (TextView)view.findViewById(R.id.tvMatchLocation);  
//		stadiumLocation.setText(score.getStadium().toString());
		
		TextView date = (TextView)view.findViewById(R.id.tvLiveTime);
		date.setText(score.getTime().toString());
		
//		String formatterName = "<b>" + tweet.getUser().getName() + "</b>"
//				+ " <small><font color='#777777'>" 
//				+ "@" + tweet.getUser().getScreenName() + "</font></small>";
//
//		nameView.setText(Html.fromHtml(formatterName));
//
//		TextView bodyView = (TextView)view.findViewById(R.id.tvBody);
//		bodyView.setText(Html.fromHtml(tweet.getBody()));

		return view;
	}

	
}
