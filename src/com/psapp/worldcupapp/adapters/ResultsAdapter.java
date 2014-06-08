package com.psapp.worldcupapp.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.models.Result;

public class ResultsAdapter extends ArrayAdapter<Result>{

	public ResultsAdapter (Context context, List<Result> results) {
		super(context, 0, results);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_result, null);
		}

		final Result Result = getItem(position);
		
		TextView homeTeamName = (TextView)view.findViewById(R.id.tvHomeName);
		TextView awayTeamName = (TextView)view.findViewById(R.id.tvAwayName);
		
		homeTeamName.setText(Result.getHomeTeam().toString());
		awayTeamName.setText(Result.getAwayTeam().toString());
		
		TextView homeResult = (TextView)view.findViewById(R.id.tvHomeScore);
		TextView awayResult = (TextView)view.findViewById(R.id.tvAwayScore);
		
		homeResult.setText(Result.getHomeResult().toString());
		awayResult.setText(Result.getAwayResult().toString());
		
		TextView date = (TextView)view.findViewById(R.id.tvMatchTime);
		date.setText(Result.getDate().toString());
		
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

