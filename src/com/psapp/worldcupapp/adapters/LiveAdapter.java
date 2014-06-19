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
import android.widget.Spinner;
import android.widget.TextView;

import com.psapp.worldcupapp.PrettyDate;
import com.psapp.worldcupapp.R;
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

				TextView tvGroup = (TextView) view.findViewById(R.id.tvGroup);
				tvGroup.setText(score.getGroup());

				TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
				String strDate = score.getDate();
				tvDate.setText(PrettyDate.getPrettyDate(strDate));

				TextView tvMatchTime = (TextView) view
						.findViewById(R.id.tvMatchTime);
				tvMatchTime.setText(PrettyDate.getPrettyTime(strDate, true));
			} else { // live match TODO
			// if(position == 0){
			// view = inflater.inflate(R.layout.item_live_first, null);
			// }
			// else{
				view = inflater.inflate(R.layout.item_live, null);
				
				if(score.getMap().isEmpty()){
					TextView tvMore = (TextView) view.findViewById(R.id.tvMore);
					tvMore.setVisibility(View.INVISIBLE);
				}
				

				// }
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

		ivHomeFlag.setBackground(getFlag(homeTeam));
		ivAwayFlag.setBackground(getFlag(awayTeam));

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

	private Drawable getFlag(String country) {
		if (country.equals("Cameroon")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_cameroon_256);
		}
		if (country.equals("Croatia")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_croatia_256);
		}
		if (country.equals("Mexico")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_mexico_256);
		}
		if (country.equals("Brazil")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_brazil_256);
		}
		if (country.equals("Chile")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_chile_256);
		}
		if (country.equals("Australia")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_australia_256);
		}
		if (country.equals("Spain")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_spain_256);
		}
		if (country.equals("Netherlands")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_netherlands_256);
		}
		if (country.equals("Greece")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_greece_256);
		}
		if (country.equals("Japan")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_japan_256);
		}
		if (country.equals("Ivory Coast")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_ivorycoast_256);
		}
		if (country.equals("Colombia")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_columbia_256);
		}
		if (country.equals("Uruguay")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_uruguay_256);
		}
		if (country.equals("England")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_england_256);
		}
		if (country.equals("Costa Rica")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_costarica_256);
		}
		if (country.equals("Italy")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_italy_256);
		}
		if (country.equals("Honduras")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_honduras_256);
		}
		if (country.equals("Ecuador")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_ecuador_256);
		}
		if (country.equals("France")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_france_256);
		}
		if (country.equals("Switzerland")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_switzerland_256);
		}
		if (country.equals("Nigeria")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_nigeria_256);
		}
		if (country.equals("Iran")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_iran_256);
		}
		if (country.contains("Bosnia")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_bosnia_256);
		}
		if (country.equals("Argentina")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_argentina_256);
		}
		if (country.equals("Germany")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_germany_256);
		}
		if (country.equals("Ghana")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_ghana_256);
		}
		if (country.equals("Portugal")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_portugal_256);
		}
		if (country.equals("USA")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_usa_256);
		}
		if (country.equals("Belgium")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_belgium_256);
		}
		if (country.equals("Algeria")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_algeria_256);
		}
		if (country.equals("Russia")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_russia_256);
		}
		if (country.contains("Korea")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_southkorea_256);
		}

		return getContext().getResources().getDrawable(
				R.drawable.ic_football_256);
	}
}
