package com.psapp.worldcupapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Match implements Serializable, Comparable<Match> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String homeTeam;
	private String awayTeam;
	private String homeScore;
	private String awayScore;

	private String stadium;
	private String spectators;
	private String date;
	private String group;
	private String time;
	private String liveTime;

	// stats
	private String homeShots;
	private String awayShots;
	private String homeYellowCards;
	private String awayYellowCards;
	private String homeRedCards;
	private String awayRedCards;
	private String homeCorners;
	private String awayCorners;
	private String homeFouls;
	private String awayFouls;
	private String homeShotsTarget;
	private String awayShotsTarget;
	private String[] homeGoalDetails;
	private String[] awayGoalDetails;
	private String[] homeYellowCardDetails;
	private String[] awayYellowCardDetails;
	private String[] homeRedCardDetails;
	private String[] awayRedCardDetails;

	// subs
	private String homeSubDetails;
	private String awaySubDetails;

	private String homeFormation;
	private String awayFormation;

	// local
	private TreeMap<Integer, ArrayList<String[]>> eventMap;

	public Match() {
		this.homeTeam = "";
		this.awayTeam = "";
		this.homeScore = "";
		this.awayScore = "";
		this.time = "";
		this.stadium = "";
		this.date = "";
		this.liveTime = "";

		this.eventMap = new TreeMap<Integer, ArrayList<String[]>>(
				Collections.reverseOrder());
	}

	public static Match fromJson(JSONObject jsonObject) {
		Match match = new Match();
		try {
			if (jsonObject.has("id")) {
				match.id = jsonObject.getString("id");
			}

			if (jsonObject.has("hometeam")) {
				match.homeTeam = jsonObject.getString("hometeam");
			}

			if (jsonObject.has("homegoals")) {
				match.homeScore = jsonObject.getString("homegoals");
			}

			if (jsonObject.has("awayteam")) {
				match.awayTeam = jsonObject.getString("awayteam");
			}

			if (jsonObject.has("awaygoals")) {
				match.awayScore = jsonObject.getString("awaygoals");
			}

			if (jsonObject.has("time")) {
				match.liveTime = jsonObject.getString("time");
			}

			if (jsonObject.has("date")) {
				match.date = jsonObject.getString("date");
			}

			if (jsonObject.has("group")) {
				String str = jsonObject.getString("group");
				if (str.charAt(0) == 'g') {
					str = Character.toUpperCase(str.charAt(0))
							+ str.substring(1);
				}
				match.group = str;
			}

			if (jsonObject.has("homegoaldetails")) {

				String tmp = jsonObject.getString("homegoaldetails");

				String[] homeGoalBits = tmp.split(";");

				for (int i = 0; i < homeGoalBits.length; i++) {
					String[] etemp = homeGoalBits[i].split(":");
					String[] tuple = new String[4];
					int min = Integer.parseInt(etemp[0].trim().split("'")[0]);
					tuple[2] = min + "";
					tuple[1] = etemp[1].trim();
					tuple[0] = "goal";
					tuple[3] = "home";

					String full = tuple[1].toString();
					String a = tuple[1].toString().substring(0, 3)
							.toLowerCase();
					if (a.contains("own")) {
						tuple[1] = full.replaceFirst("Own", "").trim();
						tuple[1] = tuple[1] + " (OG)";
					}

					if (tuple[1].contains("penalty")) {
						Log.d("DEBUG", "----------------------------------------");
						Log.d("DEBUG", "1 " + tuple[1].toString());
						
						
						if (tuple[1].contains("shootout")) {
							tuple[1] = tuple[1].replaceFirst("shootout", "").trim();
							Log.d("DEBUG", "3 " + tuple[1].toString());
							
							if(tuple[1].contains("missed")){
								tuple[1] = tuple[1].replaceFirst("missed", "").trim();
								tuple[0] = "penalty_missed";
								tuple[1] = tuple[1].replaceFirst("penalty", "").trim();
								tuple[1] = tuple[1] + " (penalty missed)";
								Log.d("DEBUG", "2 " + tuple[1].toString());
							}
							else if(tuple[1].contains("scored")){
								tuple[1] = tuple[1].replaceFirst("scored", "").trim();
								tuple[0] = "penalty_scored";
								tuple[1] = tuple[1].replaceFirst("penalty", "").trim();
								tuple[1] = tuple[1] + " (penalty)";
								Log.d("DEBUG", "2 " + tuple[1].toString());
							}
							Log.d("DEBUG", "4 " + tuple[1].toString());
						}
						else{
							tuple[1] = tuple[1].replaceFirst("penalty", "").trim();
							tuple[1] = tuple[1] + " (penalty)";
							Log.d("DEBUG", "2 " + tuple[1].toString());
						}
						
					}

					ArrayList<String[]> temp;
					if (match.eventMap.containsKey(min)) {
						temp = match.eventMap.get(min);

					} else {
						temp = new ArrayList<String[]>();
					}
					temp.add(tuple);
					match.eventMap.put(min, temp);
				}
			}

			if (jsonObject.has("awaygoaldetails")) {
				String tmp = jsonObject.getString("awaygoaldetails");
				String[] awayGoalBits = tmp.split(";");

				for (int i = 0; i < awayGoalBits.length; i++) {
					String[] etemp = awayGoalBits[i].split(":");
					String[] tuple = new String[4];
					int min = Integer.parseInt(etemp[0].trim().split("'")[0]);
					tuple[2] = min + "";
					tuple[1] = etemp[1].trim();
					tuple[0] = "goal";
					tuple[3] = "away";

					String full = tuple[1].toString();
					String a = tuple[1].toString().substring(0, 3)
							.toLowerCase();
					if (a.contains("own")) {
						tuple[1] = full.replaceFirst("Own", "").trim();
						tuple[1] = tuple[1] + " (OG)";
					}

					if (tuple[1].contains("penalty")) {
						Log.d("DEBUG", "----------------------------------------");
						Log.d("DEBUG", "1 " + tuple[1].toString());
						
						
						if (tuple[1].contains("shootout")) {
							tuple[1] = tuple[1].replaceFirst("shootout", "").trim();
							Log.d("DEBUG", "3 " + tuple[1].toString());
							
							if(tuple[1].contains("missed")){
								tuple[1] = tuple[1].replaceFirst("missed", "").trim();
								tuple[0] = "penalty_missed";
								tuple[1] = tuple[1].replaceFirst("penalty", "").trim();
								tuple[1] = tuple[1] + " (penalty missed)";
								Log.d("DEBUG", "2 " + tuple[1].toString());
							}
							else if(tuple[1].contains("scored")){
								tuple[1] = tuple[1].replaceFirst("scored", "").trim();
								tuple[0] = "penalty_scored";
								tuple[1] = tuple[1].replaceFirst("penalty", "").trim();
								tuple[1] = tuple[1] + " (penalty)";
								Log.d("DEBUG", "2 " + tuple[1].toString());
							}
							Log.d("DEBUG", "4 " + tuple[1].toString());
						}
						else{
							tuple[1] = tuple[1].replaceFirst("penalty", "").trim();
							tuple[1] = tuple[1] + " (penalty)";
							Log.d("DEBUG", "2 " + tuple[1].toString());
						}
						
					}

					ArrayList<String[]> temp;
					if (match.eventMap.containsKey(min)) {
						temp = match.eventMap.get(min);

					} else {
						temp = new ArrayList<String[]>();
					}
					temp.add(tuple);
					match.eventMap.put(min, temp);
				}
			}

			if (jsonObject.has("hometeamyellowcarddetails")) {
				String tmp = jsonObject.getString("hometeamyellowcarddetails");
				tmp = tmp.replace("&amp;nbsp;", "");
				String[] homeYellowBits = tmp.split(";");

				for (int i = 0; i < homeYellowBits.length; i++) {
					String[] etemp = homeYellowBits[i].split(":");
					String[] tuple = new String[4];
					int min = Integer.parseInt(etemp[0].trim().split("'")[0]);
					tuple[2] = min + "";
					tuple[1] = etemp[1].trim();
					tuple[0] = "yellow";
					tuple[3] = "home";
					ArrayList<String[]> temp;
					if (match.eventMap.containsKey(min)) {
						temp = match.eventMap.get(min);

					} else {
						temp = new ArrayList<String[]>();
					}
					temp.add(tuple);
					match.eventMap.put(min, temp);
				}
			}

			if (jsonObject.has("awayteamyellowcarddetails")) {
				String tmp = jsonObject.getString("awayteamyellowcarddetails");
				tmp = tmp.replace("&amp;nbsp;", "");
				String[] awayYellowBits = tmp.split(";");

				for (int i = 0; i < awayYellowBits.length; i++) {
					String[] etemp = awayYellowBits[i].split(":");
					String[] tuple = new String[4];
					int min = Integer.parseInt(etemp[0].trim().split("'")[0]);
					tuple[2] = min + "";
					tuple[1] = etemp[1].trim();
					tuple[0] = "yellow";
					tuple[3] = "away";
					ArrayList<String[]> temp;
					if (match.eventMap.containsKey(min)) {
						temp = match.eventMap.get(min);

					} else {
						temp = new ArrayList<String[]>();
					}
					temp.add(tuple);
					match.eventMap.put(min, temp);
				}
			}

			if (jsonObject.has("hometeamredcarddetails")) {
				String tmp = jsonObject.getString("hometeamredcarddetails");
				tmp = tmp.replace("&amp;nbsp;", "");
				String[] homeRedBits = tmp.split(";");

				for (int i = 0; i < homeRedBits.length; i++) {
					String[] etemp = homeRedBits[i].split(":");
					String[] tuple = new String[4];
					int min = Integer.parseInt(etemp[0].trim().split("'")[0]);
					tuple[2] = min + "";
					tuple[1] = etemp[1].trim();
					tuple[0] = "red";
					tuple[3] = "home";
					ArrayList<String[]> temp;
					if (match.eventMap.containsKey(min)) {
						temp = match.eventMap.get(min);

					} else {
						temp = new ArrayList<String[]>();
					}
					temp.add(tuple);
					match.eventMap.put(min, temp);
				}
			}

			if (jsonObject.has("awayteamredcarddetails")) {
				String tmp = jsonObject.getString("awayteamredcarddetails");
				tmp = tmp.replace("&amp;nbsp;", "");
				String[] awayRedBits = tmp.split(";");

				for (int i = 0; i < awayRedBits.length; i++) {
					String[] etemp = awayRedBits[i].split(":");
					String[] tuple = new String[4];
					int min = Integer.parseInt(etemp[0].trim().split("'")[0]);
					tuple[2] = min + "";
					tuple[1] = etemp[1].trim();
					tuple[0] = "red";
					tuple[3] = "away";
					ArrayList<String[]> temp;
					if (match.eventMap.containsKey(min)) {
						temp = match.eventMap.get(min);

					} else {
						temp = new ArrayList<String[]>();
					}
					temp.add(tuple);
					match.eventMap.put(min, temp);
				}
			}

			if (jsonObject.has("homesubdetails")) {
				String tmp = jsonObject.getString("homesubdetails");
				String[] homeSubBits = tmp.split(";");

				for (int i = 0; i < homeSubBits.length; i++) {
					String[] etemp = homeSubBits[i].split(":");
					String[] tuple = new String[4];
					int min = Integer.parseInt(etemp[0].trim().split("'")[0]);
					tuple[2] = min + "";
					tuple[1] = etemp[1].trim();

					if (tuple[1].substring(0, 2).equals("in")) {
						tuple[0] = "sub_in";
						tuple[1] = tuple[1].replaceFirst("in", "").trim();
					} else if (tuple[1].substring(0, 3).equals("out")) {
						tuple[0] = "sub_out";
						tuple[1] = tuple[1].replaceFirst("out", "").trim();
					}

					tuple[3] = "home";
					ArrayList<String[]> temp;
					if (match.eventMap.containsKey(min)) {
						temp = match.eventMap.get(min);

					} else {
						temp = new ArrayList<String[]>();
					}
					temp.add(tuple);
					match.eventMap.put(min, temp);
				}
			}

			if (jsonObject.has("awaysubdetails")) {
				String tmp = jsonObject.getString("awaysubdetails");
				String[] awaySubBits = tmp.split(";");

				for (int i = 0; i < awaySubBits.length; i++) {
					String[] etemp = awaySubBits[i].split(":");
					String[] tuple = new String[4];
					int min = Integer.parseInt(etemp[0].trim().split("'")[0]);
					tuple[2] = min + "";
					tuple[1] = etemp[1].trim();

					if (tuple[1].substring(0, 2).equals("in")) {
						tuple[0] = "sub_in";
						tuple[1] = tuple[1].replaceFirst("in", "").trim();
					} else if (tuple[1].substring(0, 3).equals("out")) {
						tuple[0] = "sub_out";
						tuple[1] = tuple[1].replaceFirst("out", "").trim();
					}

					tuple[3] = "away";

					ArrayList<String[]> temp;
					if (match.eventMap.containsKey(min)) {
						temp = match.eventMap.get(min);

					} else {
						temp = new ArrayList<String[]>();
					}
					temp.add(tuple);
					match.eventMap.put(min, temp);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return match;
	}

	public static ArrayList<Match> fromJson(JSONArray jsonArray, String caller) {
		ArrayList<Match> matches = new ArrayList<Match>(jsonArray.length());
		// Log.d("DEBUG",
		// "Number of fixtures passed in fromJson: " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject matchJson = null;
			try {
				matchJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Match match = com.psapp.worldcupapp.models.Match
					.fromJson(matchJson);

			if (match != null) {
				if (caller.equals("fixtures") && match.getLiveTime().equals("")) {
					// fixture
					String date = match.getDate();
					if (!date.equals("")) {

						DateTimeFormatter formatter = DateTimeFormat
								.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
						DateTime matchDate = formatter.parseDateTime(date);

						if (matchDate.isBeforeNow()) { // match happened in the
														// past
														// Log.d("DEBUG",
							// match.getHomeTeam() + " vs "
							// + match.getAwayTeam());
						} else { // upcoming fixture
							matches.add(match);
						}
					}
				} else if (caller.equals("fixtures")
						&& match.getLiveTime().equalsIgnoreCase("not started")) {

				} else { // add all other matches (live and results)
					matches.add(match);
				}
			}
		}

		return matches;
	}

	@Override
	public int compareTo(Match another) {
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
		DateTime anotherDate = formatter.parseDateTime(another.getDate());
		DateTime thisDate = formatter.parseDateTime(this.date);

		if (thisDate.isBefore(anotherDate)) {
			return -1;
		} else if (thisDate.isAfter(anotherDate)) {
			return 1;
		} else {
			return 0;
		}
	}

	public static Comparator<Match> MatchDateComparator = new Comparator<Match>() {

		public int compare(Match m1, Match m2) {

			// ascending order
			// return m1.compareTo(m2);

			// descending order
			return m2.compareTo(m1);
		}

	};

	public static void printEvents(
			TreeMap<Integer, ArrayList<String[]>> eventMap2) {
		for (Entry<Integer, ArrayList<String[]>> entry : eventMap2.entrySet()) {
			int key = entry.getKey();
			ArrayList<String[]> value = entry.getValue();

			for (int i = 0; i < value.size(); i++) {
				Log.d("DEBUG", key + " => " + value.get(i)[0].toString() + " "
						+ value.get(i)[1].toString());
			}

		}
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public String getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(String homeScore) {
		this.homeScore = homeScore;
	}

	public String getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(String awayScore) {
		this.awayScore = awayScore;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStadium() {
		return stadium;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public String getHomeShots() {
		return homeShots;
	}

	public void setHomeShots(String homeShots) {
		this.homeShots = homeShots;
	}

	public String getAwayShots() {
		return awayShots;
	}

	public void setAwayShots(String awayShots) {
		this.awayShots = awayShots;
	}

	public String getHomeYellowCards() {
		return homeYellowCards;
	}

	public void setHomeYellowCards(String homeYellowCards) {
		this.homeYellowCards = homeYellowCards;
	}

	public String getAwayYellowCards() {
		return awayYellowCards;
	}

	public void setAwayYellowCards(String awayYellowCards) {
		this.awayYellowCards = awayYellowCards;
	}

	public String getHomeRedCards() {
		return homeRedCards;
	}

	public void setHomeRedCards(String homeRedCards) {
		this.homeRedCards = homeRedCards;
	}

	public String getAwayRedCards() {
		return awayRedCards;
	}

	public void setAwayRedCards(String awayRedCards) {
		this.awayRedCards = awayRedCards;
	}

	public String getHomeCorners() {
		return homeCorners;
	}

	public void setHomeCorners(String homeCorners) {
		this.homeCorners = homeCorners;
	}

	public String getAwayCorners() {
		return awayCorners;
	}

	public void setAwayCorners(String awayCorners) {
		this.awayCorners = awayCorners;
	}

	public String getHomeFouls() {
		return homeFouls;
	}

	public void setHomeFouls(String homeFouls) {
		this.homeFouls = homeFouls;
	}

	public String getAwayFouls() {
		return awayFouls;
	}

	public void setAwayFouls(String awayFouls) {
		this.awayFouls = awayFouls;
	}

	public String getHomeShotsTarget() {
		return homeShotsTarget;
	}

	public void setHomeShotsTarget(String homeShotsTarget) {
		this.homeShotsTarget = homeShotsTarget;
	}

	public String getAwayShotsTarget() {
		return awayShotsTarget;
	}

	public void setAwayShotsTarget(String awayShotsTarget) {
		this.awayShotsTarget = awayShotsTarget;
	}

	public String[] getHomeGoalDetails() {
		return homeGoalDetails;
	}

	public void setHomeGoalDetails(String homeGoalDetails) {
		// this.homeGoalDetails = homeGoalDetails;
	}

	public String[] getAwayGoalDetails() {
		return awayGoalDetails;
	}

	public void setAwayGoalDetails(String awayGoalDetails) {
		// this.awayGoalDetails = awayGoalDetails;
	}

	public String[] getHomeYellowCardDetails() {
		return homeYellowCardDetails;
	}

	public void setHomeYellowCardDetails(String homeYellowCardDetails) {
		// this.homeYellowCardDetails = homeYellowCardDetails;
	}

	public String[] getAwayYellowCardDetails() {
		return awayYellowCardDetails;
	}

	public void setAwayYellowCardDetails(String awayYellowCardDetails) {
		// this.awayYellowCardDetails = awayYellowCardDetails;
	}

	public String[] getHomeRedCardDetails() {
		return homeRedCardDetails;
	}

	public void setHomeRedCardDetails(String homeRedCardDetails) {
		// this.homeRedCardDetails = homeRedCardDetails;
	}

	public String[] getAwayRedCardDetails() {
		return awayRedCardDetails;
	}

	public void setAwayRedCardDetails(String awayRedCardDetails) {
		// this.awayRedCardDetails = awayRedCardDetails;
	}

	public String getHomeFormation() {
		return homeFormation;
	}

	public void setHomeFormation(String homeFormation) {
		this.homeFormation = homeFormation;
	}

	public String getAwayFormation() {
		return awayFormation;
	}

	public void setAwayFormation(String awayFormation) {
		this.awayFormation = awayFormation;
	}

	public String getHomeSubDetails() {
		return homeSubDetails;
	}

	public void setHomeSubDetails(String homeSubDetails) {
		this.homeSubDetails = homeSubDetails;
	}

	public String getAwaySubDetails() {
		return awaySubDetails;
	}

	public void setAwaySubDetails(String awaySubDetails) {
		this.awaySubDetails = awaySubDetails;
	}

	public String getSpectators() {
		return spectators;
	}

	public void setSpectators(String spectators) {
		this.spectators = spectators;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public TreeMap<Integer, ArrayList<String[]>> getMap() {
		return eventMap;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(String liveTime) {
		this.liveTime = liveTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
