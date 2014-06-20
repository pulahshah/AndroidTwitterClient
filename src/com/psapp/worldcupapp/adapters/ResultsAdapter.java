package com.psapp.worldcupapp.adapters;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psapp.worldcupapp.PrettyDate;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.Utilities;
import com.psapp.worldcupapp.models.Match;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class ResultsAdapter extends ArrayAdapter<Match>{

	public ResultsAdapter (Context context, List<Match> results) {
		super(context, 0, results);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_result, null);
		}

		final Match result = getItem(position);
		
		String homeTeam = result.getHomeTeam().toString();
		String awayTeam = result.getAwayTeam().toString();
		
		ImageView ivHomeFlag = (ImageView) view.findViewById(R.id.ivHomeIcon);
		ImageView ivAwayFlag = (ImageView) view.findViewById(R.id.ivAwayIcon);
		
		ivHomeFlag.setBackground(Utilities.getFlag(getContext(), homeTeam));
		ivAwayFlag.setBackground(Utilities.getFlag(getContext(), awayTeam));
		
		TextView group = (TextView) view.findViewById(R.id.tvGroup);
		group.setText(result.getGroup());
		
		TextView homeTeamName = (TextView)view.findViewById(R.id.tvHomeName);
		TextView awayTeamName = (TextView)view.findViewById(R.id.tvAwayName);
		
		homeTeamName.setText(homeTeam);
		awayTeamName.setText(awayTeam);
		
		TextView tvHomeScore = (TextView) view.findViewById(R.id.tvHomeScore);
		TextView tvAwayScore = (TextView) view.findViewById(R.id.tvAwayScore);
		tvHomeScore.setText(result.getHomeScore().toString());
		tvAwayScore.setText(result.getAwayScore().toString());
		
		TextView date = (TextView)view.findViewById(R.id.tvDate);
		date.setText(PrettyDate.getPrettyDate(result.getDate()));
		
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

