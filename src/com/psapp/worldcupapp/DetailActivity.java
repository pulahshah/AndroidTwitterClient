package com.psapp.worldcupapp;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.psapp.worldcupapp.adapters.EventAdapter;
import com.psapp.worldcupapp.models.Events;
import com.psapp.worldcupapp.models.Match;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class DetailActivity extends Activity {
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	ArrayList<Events> events;
	EventAdapter eventAdapter;
	Match r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		r = (Match) getIntent().getSerializableExtra("temp");
		// Show the Up button in the action bar.
		setupActionBar(r.getHomeTeam(), r.getAwayTeam(), r.getDate(),
				r.getHomeScore(), r.getAwayScore());

		events = new ArrayList<Events>();
		
		TreeMap<Integer, ArrayList<String[]>> map = r.getMap();
		for (Entry<Integer, ArrayList<String[]>> entry : map.entrySet()) {
			
			ArrayList<String[]> value = entry.getValue();
			// Log.d("DEBUG", "**********************************");
			for (int i = 0; i < value.size(); i++) {
				Events e = new Events();
				String[] tuple = value.get(i);
				e.setType(tuple[0]);
				e.setName(tuple[1]);
				e.setMinute(tuple[2]);
				e.setSide(tuple[3]);
				e.setMatch(r);
				events.add(e);
				
				 
			}
		}
		ListView lvEvents = (ListView) findViewById(R.id.lvEvents);
		eventAdapter = new EventAdapter(this, events);
		lvEvents.setAdapter(eventAdapter);

	}

	private void displayData(Match r) {

		ListView lvEvents = (ListView) findViewById(R.id.lvEvents);

		// createDummylist();

		printEvents(events);

		eventAdapter = new EventAdapter(this, events);
		lvEvents.setAdapter(eventAdapter);

	}

	private void printEvents(ArrayList<Events> e) {
		Log.d("DEBUG", "passing events to the adapter");

		for (int i = 0; i < e.size(); i++) {
			Events t = e.get(i);
			Log.d("DEBUG",
					t.getMinute() + " ==> " + t.getName() + "    "
							+ t.getType() + "    " + t.getSide());
		}
	}

	private void loadEvents(Match r) {
		TreeMap<Integer, ArrayList<String[]>> map = new TreeMap<Integer, ArrayList<String[]>>();
		map = r.getMap();
		for (Entry<Integer, ArrayList<String[]>> entry : map.entrySet()) {
			Events e = new Events();
			ArrayList<String[]> value = entry.getValue();

			for (int i = 0; i < value.size(); i++) {
				String[] tuple = value.get(i);
				e.setType(tuple[0]);
				e.setName(tuple[1]);
				e.setMinute(tuple[2]);
				e.setSide(tuple[3]);
				e.setMatch(r);
				events.add(e);
				// Log.d("DEBUG", "**********************************");
				// Log.d("DEBUG", tuple[2] + " => " + tuple[1] + " " + tuple[0]
				// + " " + tuple[3] +
				// "--------------------------------------------------");
			}
		}
	}

	private Drawable getFlag(String country) {
		if (country.equals("Cameroon")) {
			return getResources().getDrawable(R.drawable.ic_cameroon_256);
		}
		if (country.equals("Croatia")) {
			return getResources().getDrawable(R.drawable.ic_croatia_256);
		}
		if (country.equals("Mexico")) {
			return getResources().getDrawable(R.drawable.ic_mexico_256);
		}
		if (country.equals("Brazil")) {
			return getResources().getDrawable(R.drawable.ic_brazil_256);
		}
		if (country.equals("Chile")) {
			return getResources().getDrawable(R.drawable.ic_chile_256);
		}
		if (country.equals("Australia")) {
			return getResources().getDrawable(R.drawable.ic_australia_256);
		}
		if (country.equals("Spain")) {
			return getResources().getDrawable(R.drawable.ic_spain_256);
		}
		if (country.equals("Netherlands")) {
			return getResources().getDrawable(R.drawable.ic_netherlands_256);
		}
		if (country.equals("Greece")) {
			return getResources().getDrawable(R.drawable.ic_greece_256);
		}
		if (country.equals("Japan")) {
			return getResources().getDrawable(R.drawable.ic_japan_256);
		}
		if (country.equals("Ivory Coast")) {
			return getResources().getDrawable(R.drawable.ic_ivorycoast_256);
		}
		if (country.equals("Colombia")) {
			return getResources().getDrawable(R.drawable.ic_columbia_256);
		}
		if (country.equals("Uruguay")) {
			return getResources().getDrawable(R.drawable.ic_uruguay_256);
		}
		if (country.equals("England")) {
			return getResources().getDrawable(R.drawable.ic_england_256);
		}
		if (country.equals("Costa Rica")) {
			return getResources().getDrawable(R.drawable.ic_costarica_256);
		}
		if (country.equals("Italy")) {
			return getResources().getDrawable(R.drawable.ic_italy_256);
		}
		if (country.equals("Honduras")) {
			return getResources().getDrawable(R.drawable.ic_honduras_256);
		}
		if (country.equals("Ecuador")) {
			return getResources().getDrawable(R.drawable.ic_ecuador_256);
		}
		if (country.equals("France")) {
			return getResources().getDrawable(R.drawable.ic_france_256);
		}
		if (country.equals("Switzerland")) {
			return getResources().getDrawable(R.drawable.ic_switzerland_256);
		}
		if (country.equals("Nigeria")) {
			return getResources().getDrawable(R.drawable.ic_nigeria_256);
		}
		if (country.equals("Iran")) {
			return getResources().getDrawable(R.drawable.ic_iran_256);
		}
		if (country.contains("Bosnia")) {
			return getResources().getDrawable(R.drawable.ic_bosnia_256);
		}
		if (country.equals("Argentina")) {
			return getResources().getDrawable(R.drawable.ic_argentina_256);
		}
		if (country.equals("Germany")) {
			return getResources().getDrawable(R.drawable.ic_germany_256);
		}
		if (country.equals("Ghana")) {
			return getResources().getDrawable(R.drawable.ic_ghana_256);
		}
		if (country.equals("Portugal")) {
			return getResources().getDrawable(R.drawable.ic_portugal_256);
		}
		if (country.equals("USA")) {
			return getResources().getDrawable(R.drawable.ic_usa_256);
		}
		if (country.equals("Belgium")) {
			return getResources().getDrawable(R.drawable.ic_belgium_256);
		}
		if (country.equals("Algeria")) {
			return getResources().getDrawable(R.drawable.ic_algeria_256);
		}
		if (country.equals("Russia")) {
			return getResources().getDrawable(R.drawable.ic_russia_256);
		}
		if (country.contains("Korea")) {
			return getResources().getDrawable(R.drawable.ic_southkorea_256);
		}

		return getResources().getDrawable(R.drawable.ic_football_256);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar(String home, String away, String date,
			String hscore, String ascore) {
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setTitle(home + " " + hscore + " - " + ascore + " " + away);
		ab.setIcon(R.drawable.football_256_white);
		// ab.setSubtitle(PrettyDate.getPrettyDate(date, false));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
