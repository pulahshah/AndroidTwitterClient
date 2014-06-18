package com.psapp.worldcupapp.adapters;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psapp.worldcupapp.PrettyDate;
import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.models.Events;
import com.psapp.worldcupapp.models.Match;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class EventAdapter extends ArrayAdapter<Events> {
	public EventAdapter(Context context, ArrayList<Events> events) {
		super(context, 0, events);

	}

	public View getView(int position, View view, ViewGroup parent) {
		Events e = getItem(position);

		view = null;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			String type = e.getType();
			if (position == 0) { // match snippet

				Match r = e.getMatch();

				if (r.getLiveTime().equals("")) {
					if (e.getSide().equals("home")) {

						view = inflater.inflate(
								R.layout.item_result_detail_home, null);
					} else { // away

						view = inflater.inflate(
								R.layout.item_result_detail_away, null);
					}

					TextView homeName = (TextView) view
							.findViewById(R.id.tvHomeName);
					TextView awayName = (TextView) view
							.findViewById(R.id.tvAwayName);
					homeName.setText(r.getHomeTeam());
					awayName.setText(r.getAwayTeam());

					TextView homeScore = (TextView) view
							.findViewById(R.id.tvHomeScore);
					TextView awayScore = (TextView) view
							.findViewById(R.id.tvAwayScore);
					homeScore.setText(r.getHomeScore().toString());
					awayScore.setText(r.getAwayScore().toString());

					ImageView ivHomeFlag = (ImageView) view
							.findViewById(R.id.ivHomeIcon);
					ImageView ivAwayFlag = (ImageView) view
							.findViewById(R.id.ivAwayIcon);

					ivHomeFlag.setBackground(getFlag(r.getHomeTeam()));
					ivAwayFlag.setBackground(getFlag(r.getAwayTeam()));

					TextView group = (TextView) view.findViewById(R.id.tvGroup);
					group.setText(r.getGroup());

					TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
					tvDate.setText(PrettyDate.getPrettyDate(r.getDate()));

				} else {
					if (e.getSide().equals("home")) {
						view = inflater.inflate(R.layout.item_live_detail_home,
								null);

					} else { // away

						view = inflater.inflate(R.layout.item_live_detail_away,
								null);
					}

					TextView homeName = (TextView) view
							.findViewById(R.id.tvHomeName);
					TextView awayName = (TextView) view
							.findViewById(R.id.tvAwayName);
					homeName.setText(r.getHomeTeam());
					awayName.setText(r.getAwayTeam());

					TextView homeScore = (TextView) view
							.findViewById(R.id.tvHomeScore);
					TextView awayScore = (TextView) view
							.findViewById(R.id.tvAwayScore);
					homeScore.setText(r.getHomeScore().toString());
					awayScore.setText(r.getAwayScore().toString());

					ImageView ivHomeFlag = (ImageView) view
							.findViewById(R.id.ivHomeIcon);
					ImageView ivAwayFlag = (ImageView) view
							.findViewById(R.id.ivAwayIcon);

					ivHomeFlag.setBackground(getFlag(r.getHomeTeam()));
					ivAwayFlag.setBackground(getFlag(r.getAwayTeam()));

					TextView group = (TextView) view.findViewById(R.id.tvGroup);
					group.setText(r.getGroup());

					ImageView ivDot = (ImageView) view.findViewById(R.id.ivDot);
					TextView tvLiveTime = (TextView) view
							.findViewById(R.id.tvLiveTime);
					String tmp = r.getLiveTime();
					if (tmp.equalsIgnoreCase("finished")) {
						tmp = "Full-time";
						ivDot.setVisibility(View.GONE);
					} else if (tmp.equalsIgnoreCase("not started")
							|| tmp.equalsIgnoreCase("notstarted")) {
						// TODO
						tmp = PrettyDate.getPrettyTime(r.getDate(), true);
						ivDot.setVisibility(View.GONE);
					} else if (tmp.equalsIgnoreCase("Halftime")
							|| tmp.equalsIgnoreCase("Half time")) {
						tmp = "Half-time";
						ivDot.setVisibility(View.VISIBLE);
					} else {
						tvLiveTime.setTextSize(20);
					}
					tvLiveTime.setText(tmp);
				}

			} else { // match events

				if (e.getSide().equals("home")) {

					view = inflater
							.inflate(R.layout.item_event_temp_home, null);
				} else { // away

					view = inflater
							.inflate(R.layout.item_event_temp_away, null);
				}

			}

			TextView tvEventTime = (TextView) view
					.findViewById(R.id.tvEventTime);
			tvEventTime.setText(e.getMinute() + "");

			ImageView ivEventType = (ImageView) view
					.findViewById(R.id.ivEventType);
			ivEventType.setBackground(getIcon(type));

			TextView tvEventName = (TextView) view
					.findViewById(R.id.tvEventName);
			tvEventName.setText(e.getName());
		}

		return view;
	}

	private Drawable getIcon(String type) {
		if (type.equals("goal")) {
			return getContext().getResources().getDrawable(
					R.drawable.ic_football_72);
		} else if (type.equals("yellow")) {
			return getContext().getResources()
					.getDrawable(R.drawable.yellow_72);
		} else if (type.equals("red")) {
			return getContext().getResources().getDrawable(R.drawable.red_72);
		} else if (type.equals("sub_in")) {
			return getContext().getResources().getDrawable(R.drawable.sub_in);
		} else if (type.equals("sub_out")) {
			return getContext().getResources().getDrawable(R.drawable.sub_out);
		}
		return getContext().getResources().getDrawable(
				R.drawable.ic_football_72);
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
