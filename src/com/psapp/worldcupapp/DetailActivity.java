package com.psapp.worldcupapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.psapp.worldcupapp.adapters.EventAdapter;
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

		ArrayList<Events> e = new ArrayList<Events>();
		e = loadEvents(r);

		displayEvents(e);
	}

	private void displayEvents(ArrayList<Events> updatedEvent) {
		Log.d("DEBUG", "display events called --------------");
		
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
		return events;
	}

	Thread thread = null;
	
	protected void onPause(){
		super.onPause();
		if(thread != null){
			thread.interrupt();
		}
	}
	
	
	protected void onResume() {
		super.onResume();

//		caller = getIntent().getStringExtra("caller");
//		if (caller.equals("live")) {
//			
//			Log.d("DEBUG", "entry is from live -- so update");
//			
//			Thread thread = new Thread(){
//			    @Override
//			    public void run() {
//			        try {
//			            while(true) {
//			                sleep(10000);
//			                //mhandler.post(r);
//			                fetchLiveUpdates();
//			            }
//			        } catch (InterruptedException e) {
//			            e.printStackTrace();
//			        }
//			    }
//			};
//
//			thread.start();
//			
//		
//		}
	}

	private void fetchLiveUpdates() {
		final JSONArray liveJson = new JSONArray();

		String url = URL + "/livescorestemp/" + matchId + ".json";
		client.get(url, new AsyncHttpResponseHandler() {

			public void onSuccess(String json) {
				if (!json.equals("null")) {
					try {

						JSONObject obj = new JSONObject(json);
						
						liveJson.put(obj);

						ArrayList<Match> matches = Match.fromJson(liveJson,
								"live");
//						Log.d("DEBUG", "Matches being returned --- " + matches.size() );
						
						for(Match m : matches){
							r = m;
							setupActionBar(r.getHomeTeam(), r.getAwayTeam(), r.getDate(),
									r.getHomeScore(), r.getAwayScore());
							
//							Log.d("DEBUG", "******************** away score: " + match.getAwayScore());
						}
						if(r != null){
							ArrayList<Events> e = new ArrayList<Events>();
							e = loadEvents(r);
							displayEvents(e);
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

	private void printEvents(ArrayList<Events> e) {
		Log.d("DEBUG", "passing events to the adapter");

		for (int i = 0; i < e.size(); i++) {
			Events t = e.get(i);
			Log.d("DEBUG",
					t.getMinute() + " ==> " + t.getName() + "    "
							+ t.getType() + "    " + t.getSide());
		}
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
		case R.id.refresh:
			fetchLiveUpdates();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
