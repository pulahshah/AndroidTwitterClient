package com.psapp.worldcupapp.adapters;

import java.util.List;
import java.util.TimeZone;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.psapp.worldcupapp.PrettyDate;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.Utilities;
import com.psapp.worldcupapp.models.Match;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class LiveAdapter extends ArrayAdapter<Match> {
	public LiveAdapter(Context context, List<Match> scores) {
		super(context, 0, scores);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// View view = convertView;
		view = null;
		
		
		final Match score = getItem(position);

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (score.getLiveTime().equals("")) { // fixture
				view = inflater.inflate(R.layout.item_score, null);

				TextView tvMore = (TextView) view.findViewById(R.id.tvMore);
				tvMore.setVisibility(View.INVISIBLE);
				
				TextView tvGroup = (TextView) view.findViewById(R.id.tvGroup);
				tvGroup.setText(score.getGroup());

				TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
				String strDate = score.getDate();
				tvDate.setText(PrettyDate.getPrettyDate(strDate));

				TextView tvMatchTime = (TextView) view
						.findViewById(R.id.tvMatchTime);
				
				tvMatchTime.setText(PrettyDate.getPrettyTime(strDate, true));
			} else { 
				view = inflater.inflate(R.layout.item_live, null);
				

				ImageView ivDot = (ImageView) view.findViewById(R.id.ivDot);
				TextView tvLiveTime = (TextView) view
						.findViewById(R.id.tvLiveTime);
				String tmp = score.getLiveTime();
				if (tmp.equalsIgnoreCase("finished")) {
					tmp = "Full-time";
					ivDot.setVisibility(View.GONE);
				} else if (tmp.equalsIgnoreCase("not started")
						|| tmp.equalsIgnoreCase("notstarted")) {
					// TODO
					tmp = PrettyDate.getPrettyTime(score.getDate(), true);
					ivDot.setVisibility(View.GONE);
				} else if (tmp.equalsIgnoreCase("Halftime")
						|| tmp.equalsIgnoreCase("Half time")) {
					tmp = "Half-time";
					ivDot.setVisibility(View.VISIBLE);
				}
				else{
					tvLiveTime.setTextSize(20);
				}
				tvLiveTime.setText(tmp);
			}

		}

		String homeTeam = score.getHomeTeam().toString();
		String awayTeam = score.getAwayTeam().toString();

		ImageView ivHomeFlag = (ImageView) view.findViewById(R.id.ivHomeIcon);
		ImageView ivAwayFlag = (ImageView) view.findViewById(R.id.ivAwayIcon);

		ivHomeFlag.setBackground(Utilities.getFlag(getContext(), homeTeam));
		ivAwayFlag.setBackground(Utilities.getFlag(getContext(), awayTeam));

		TextView homeTeamName = (TextView) view.findViewById(R.id.tvHomeName);
		TextView awayTeamName = (TextView) view.findViewById(R.id.tvAwayName);

		homeTeamName.setText(homeTeam);
		awayTeamName.setText(awayTeam);

		TextView tvHomeScore = (TextView) view.findViewById(R.id.tvHomeScore);
		TextView tvAwayScore = (TextView) view.findViewById(R.id.tvAwayScore);
		tvHomeScore.setText(score.getHomeScore().toString());
		tvAwayScore.setText(score.getAwayScore().toString());

		// String formatterName = "<b>" + tweet.getUser().getName() + "</b>"
		// + " <small><font color='#777777'>"
		// + "@" + tweet.getUser().getScreenName() + "</font></small>";
		//
		// nameView.setText(Html.fromHtml(formatterName));
		//
		// TextView bodyView = (TextView)view.findViewById(R.id.tvBody);
		// bodyView.setText(Html.fromHtml(tweet.getBody()));

		return view;
	}

	
}
