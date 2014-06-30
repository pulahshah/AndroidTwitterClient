package com.psapp.worldcupapp;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.psapp.worldcupapp.adapters.EventAdapter;
import com.psapp.worldcupapp.client.FootballClient;
import com.psapp.worldcupapp.models.Events;
import com.psapp.worldcupapp.models.Match;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class DetailActivity extends Activity {
	public static final String URL = "https://wcfootball.firebaseio.com";
	AsyncHttpClient client = new AsyncHttpClient();
	EventAdapter eventAdapter;
	Match r;
	String caller;
	String matchId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		r = (Match) getIntent().getSerializableExtra("temp");
		// Show the Up button in the action bar.
		setupActionBar(r.getHomeTeam(), r.getAwayTeam(), r.getDate(),
				r.getHomeScore(), r.getAwayScore());

		matchId = r.getId();
		updateHeader(r);

		ArrayList<Events> e = new ArrayList<Events>();
		e = loadEvents(r);

		displayEvents(e);
	}

	private void updateHeader(Match r) {
		TextView tvMore = (TextView) findViewById(R.id.tvMore);
		tvMore.setVisibility(View.INVISIBLE);

		TextView homeName = (TextView) findViewById(R.id.tvHomeName);
		TextView awayName = (TextView) findViewById(R.id.tvAwayName);
		homeName.setText(r.getHomeTeam());
		awayName.setText(r.getAwayTeam());

		TextView homeScore = (TextView) findViewById(R.id.tvHomeScore);
		TextView awayScore = (TextView) findViewById(R.id.tvAwayScore);
		homeScore.setText(r.getHomeScore().toString());
		awayScore.setText(r.getAwayScore().toString());

		ImageView ivHomeFlag = (ImageView) findViewById(R.id.ivHomeIcon);
		ImageView ivAwayFlag = (ImageView) findViewById(R.id.ivAwayIcon);

		ivHomeFlag.setBackground(Utilities.getFlag(getApplicationContext(),
				r.getHomeTeam()));
		ivAwayFlag.setBackground(Utilities.getFlag(getApplicationContext(),
				r.getAwayTeam()));

		TextView group = (TextView) findViewById(R.id.tvGroup);
		group.setText(r.getGroup());
		
		ImageView ivDot = (ImageView) findViewById(R.id.ivDot);
		ivDot.setVisibility(View.GONE);
		
		TextView tvLiveTime = (TextView) findViewById(R.id.tvLiveTime);
		String tmp = r.getLiveTime();

		if (tmp.equalsIgnoreCase("finished") || tmp.equals("") || tmp.equalsIgnoreCase("finished ap")) {
			tmp = PrettyDate.getPrettyDate(r.getDate());
		} else if (tmp.equalsIgnoreCase("penalty")) {
			tmp = "Penalties";
		} else if (tmp.equalsIgnoreCase("not started")
				|| tmp.equalsIgnoreCase("notstarted")) {
			tmp = PrettyDate.getPrettyTime(r.getDate(), true);
		} else if (tmp.equalsIgnoreCase("Halftime")
				|| tmp.equalsIgnoreCase("Half time")) {
			tmp = "Half-time";
			ivDot.setVisibility(View.VISIBLE);
		} else {
			// increase size to display live time
			if(tmp.length() <= 3){
				tvLiveTime.setTextSize(20);
			}
			ivDot.setVisibility(View.VISIBLE);
		}
		tvLiveTime.setText(tmp);
	}

	private void displayEvents(ArrayList<Events> updatedEvent) {
		ListView lvEvents = (ListView) findViewById(R.id.lvEvents);
		eventAdapter = new EventAdapter(this, updatedEvent);
		eventAdapter.notifyDataSetChanged();
		lvEvents.setAdapter(eventAdapter);
	}

	private ArrayList<Events> loadEvents(Match r) {
		ArrayList<Events> events = new ArrayList<Events>();
		TreeMap<Integer, ArrayList<String[]>> map = r.getMap();
		for (Entry<Integer, ArrayList<String[]>> entry : map.entrySet()) {

			ArrayList<String[]> value = entry.getValue();
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
		return events;
	}

	Thread thread = null;

	protected void onPause() {
		super.onPause();
		if (thread != null) {
			thread.interrupt();
		}
	}

	protected void onResume() {
		super.onResume();

		caller = getIntent().getStringExtra("caller");
		// if (caller.equals("live")) {
		//
		// Log.d("DEBUG", "entry is from live -- so update");
		//
		// Thread thread = new Thread(){
		// @Override
		// public void run() {
		// try {
		// while(true) {
		// sleep(10000);
		// //mhandler.post(r);
		// fetchLiveUpdates();
		// }
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// };
		//
		// thread.start();
		//
		//
		// }
	}

	private void fetchLiveUpdates() {
		final JSONArray liveJson = new JSONArray();
		String url = URL + FootballClient.LIVE_URL + "/" + matchId + ".json";
		client.get(url, new AsyncHttpResponseHandler() {

			public void onSuccess(String json) {
				if (!json.equals("null")) {
					try {

						JSONObject obj = new JSONObject(json);

						liveJson.put(obj);

						ArrayList<Match> matches = Match.fromJson(liveJson,
								"live");

						for (Match m : matches) {
							setupActionBar(m.getHomeTeam(), m.getAwayTeam(),
									m.getDate(), m.getHomeScore(),
									m.getAwayScore());
							updateHeader(m);

//							ArrayList<Events> e = new ArrayList<Events>();
//							e = loadEvents(m);
//							displayEvents(e);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Log.d("DEBUG", "No live matches");
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("NETWORK", "failure");
			}
		});
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
		if (caller != null && caller.equalsIgnoreCase("results")) {
			return true;
		}
		getMenuInflater().inflate(R.menu.menu, menu);
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
		case R.id.refresh:
			fetchLiveUpdates();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
