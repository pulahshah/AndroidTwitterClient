package com.psapp.worldcupapp.adapters;

import java.util.List;

import android.content.Context;
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
			view = inflater.inflate(R.layout.score_item, null);
		}

		final Score score = getItem(position);
		
//		view.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
////				Intent i = new Intent(v.getContext(), ProfileActivity.class);
//				
//				Log.d("DEBUG", "application context: " + v.getContext());
//				
//				if(v.getContext().getClass() != ProfileActivity.class){	// to prevent firing up new activities from profile activity
//					i.putExtra("id", tweet.getUser().getId());
//					i.putExtra("screenName", tweet.getUser().getScreenName());
//					v.getContext().startActivity(i);
//				}
//				
//				Log.d("DEBUG", "image clicked -- " + tweet.getUser().getId());
//			}
//		});
		
		TextView homeTeamName = (TextView)view.findViewById(R.id.tvHomeName);
		TextView homeTeamScore = (TextView)view.findViewById(R.id.tvHomeScore);
		
		homeTeamName.setText(score.getHomeTeam().toString());
		homeTeamScore.setText(score.getHomeScore().toString());
		
		TextView awayTeamName = (TextView)view.findViewById(R.id.tvAwayName);
		TextView awayTeamScore = (TextView)view.findViewById(R.id.tvAwayScore);
		
		awayTeamName.setText(score.getAwayTeam().toString());
		awayTeamScore.setText(score.getAwayScore().toString());
		
		
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
