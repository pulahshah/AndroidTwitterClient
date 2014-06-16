package com.psapp.worldcupapp.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Match implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
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
	private TreeMap<Integer, String[]> eventMap;

	public Match() {
		this.homeTeam = "";
		this.awayTeam = "";
		this.homeScore = "";
		this.awayScore = "";
		this.time = "";
		this.stadium = "";
		this.date = "";
		this.liveTime = "";

		this.eventMap = new TreeMap<Integer, String[]>();
	}

	public static Match fromJson(JSONObject jsonObject) {
		Match match = new Match();
		try {
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
				if(str.charAt(0) == 'g' ){
					str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
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
					String a = tuple[1].toString().substring(0, 3).toLowerCase();
					if(a.contains("own")){
						tuple[1] = full.replaceFirst("Own", "").trim();
						tuple[1] = tuple[1] + " (OG)";
					}
					
					if(tuple[1].contains("penalty")){
						tuple[1] = full.replaceFirst("penalty", "").trim();
						tuple[1] = tuple[1] + " (Penalty)";
					}
					
					match.eventMap.put(min, tuple);
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
					String a = tuple[1].toString().substring(0, 3).toLowerCase();
					if(a.contains("own")){
						tuple[1] = full.replaceFirst("Own", "").trim();
						tuple[1] = tuple[1] + " (OG)";
					}
					
					
					if(tuple[1].contains("penalty")){
						tuple[1] = full.replaceFirst("penalty", "").trim();
						tuple[1] = tuple[1] + " (Penalty)";
					}
					
					match.eventMap.put(min, tuple);
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
					match.eventMap.put(min, tuple);
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
					match.eventMap.put(min, tuple);
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
					tuple[0] = "red_home";
					tuple[3] = "home";
					match.eventMap.put(min, tuple);
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
					match.eventMap.put(min, tuple);
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
					tuple[0] = "sub";
					tuple[3] = "home";
					match.eventMap.put(min, tuple);
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
					tuple[0] = "sub";
					tuple[3] = "away";
					match.eventMap.put(min, tuple);
				}
			}

			printEvents(match.eventMap);

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return match;
	}

	public static ArrayList<Match> fromJson(JSONArray jsonArray) {
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
				matches.add(match);
			}
		}

		return matches;
	}

	public static void printEvents(TreeMap<Integer, String[]> eventMap2) {
		for (Entry<Integer, String[]> entry : eventMap2.entrySet()) {
			int key = entry.getKey();
			String[] value = entry.getValue();

			// Log.d("DEBUG", key + " => " + value[1] + " " + value[0]);
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

	public TreeMap<Integer, String[]> getMap() {
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
}
