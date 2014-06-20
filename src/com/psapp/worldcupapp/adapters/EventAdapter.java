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
import com.psapp.worldcupapp.Utilities;
import com.psapp.worldcupapp.models.Events;
import com.psapp.worldcupapp.models.Match;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class EventAdapter extends ArrayAdapter<Events> {
	public EventAdapter(Context context, ArrayList<Events> events) {
		super(context, 0, events);

		Log.d("DEBUG", "Logging all passed events");
		for (Events e : events) {
			Log.d("DEBUG",
					e.getMinute() + " " + e.getName() + " " + e.getType());
		}
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

					ivHomeFlag.setBackground(Utilities.getFlag(getContext(),
							r.getHomeTeam()));
					ivAwayFlag.setBackground(Utilities.getFlag(getContext(),
							r.getAwayTeam()));

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

					ivHomeFlag.setBackground(Utilities.getFlag(getContext(),
							r.getHomeTeam()));
					ivAwayFlag.setBackground(Utilities.getFlag(getContext(),
							r.getAwayTeam()));

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
			ivEventType.setBackground(Utilities
					.getEventIcon(getContext(), type));

			TextView tvEventName = (TextView) view
					.findViewById(R.id.tvEventName);
			tvEventName.setText(e.getName());
		}

		return view;
	}

}
