package com.psapp.worldcupapp.adapters;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psapp.worldcupapp.R;
import com.psapp.worldcupapp.Utilities;
import com.psapp.worldcupapp.models.Events;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class EventAdapter extends ArrayAdapter<Events> {
	public EventAdapter(Context context, ArrayList<Events> events) {
		super(context, 0, events);

		Log.d("DEBUG", "Logging all passed events");
		for (Events e : events) {
			Log.d("DEBUG",
					e.getMinute() + " " + e.getName() + " " + e.getType()
							+ " || " + e.getSide());
		}
	}

	public View getView(int position, View view, ViewGroup parent) {

		Events e = getItem(position);
		view = null;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			if (e.getSide().equals("home")) {
				view = inflater.inflate(R.layout.item_event_temp_home, null);
				
			} else { // away
				view = inflater.inflate(R.layout.item_event_temp_away, null);
			}

			String type = e.getType();
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
