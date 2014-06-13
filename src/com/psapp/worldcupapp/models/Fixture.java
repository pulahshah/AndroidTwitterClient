package com.psapp.worldcupapp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.psapp.worldcupapp.PrettyDate;

public class Fixture {
	
	private String time;
	private String homeTeam;
	private String awayTeam;
	private String homeScore;
	private String awayScore;
	
	private String stadium;
	private String date;
	private String group;
	
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
	
	
	public Fixture() {
		this.homeTeam = "";
		this.awayTeam = "";
		this.homeScore = "";
		this.awayScore = "";
		this.time = "";
		this.stadium = "";
		this.date = "";
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public static Fixture fromJson(JSONObject jsonObject) {
		Fixture score = new Fixture();
		try {
			if (jsonObject.has("hometeam")) {
				score.homeTeam = jsonObject.getString("hometeam");
			}

			if (jsonObject.has("awayteam")) {
				score.awayTeam = jsonObject.getString("awayteam");
			}
			
			if (jsonObject.has("homegoals")) {
				score.homeScore = jsonObject.getString("homegoals");
			}
			
			if (jsonObject.has("awaygoals")) {
				score.awayScore = jsonObject.getString("awaygoals");
			}

			if (jsonObject.has("group")) {
				 String str = jsonObject.getString("group");
				
				 if(str.charAt(0) == 'g'){
					 score.group = str.replace(str.charAt(0), 'G');
				 }
				 else{
					 score.group = str;
				 }
			}

			if (jsonObject.has("date")) {
				score.date = jsonObject.getString("date");
			}
			
			if (jsonObject.has("time")){
				score.time = jsonObject.getString("time");
			}
			
			if (jsonObject.has("homegoaldetails")) {
				String tmp = jsonObject.getString("homegoaldetails");
				String[] goalBits = tmp.split(";");
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return score;
	}

	public static ArrayList<Fixture> fromJson(JSONArray jsonArray) {
		ArrayList<Fixture> scores = new ArrayList<Fixture>(jsonArray.length());
//		Log.d("DEBUG",
//				"Number of fixtures passed in fromJson: " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject scoreJson = null;
			try {
				scoreJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Fixture score = Fixture.fromJson(scoreJson);

			if (score != null) {
				scores.add(score);
			}
		}

		return scores;
	}

	public String getStadium() {
		return stadium;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public void setHomeGoalDetails(String[] homeGoalDetails) {
		this.homeGoalDetails = homeGoalDetails;
	}

	public String[] getAwayGoalDetails() {
		return awayGoalDetails;
	}

	public void setAwayGoalDetails(String[] awayGoalDetails) {
		this.awayGoalDetails = awayGoalDetails;
	}

	public String[] getHomeYellowCardDetails() {
		return homeYellowCardDetails;
	}

	public void setHomeYellowCardDetails(String[] homeYellowCardDetails) {
		this.homeYellowCardDetails = homeYellowCardDetails;
	}

	public String[] getAwayYellowCardDetails() {
		return awayYellowCardDetails;
	}

	public void setAwayYellowCardDetails(String[] awayYellowCardDetails) {
		this.awayYellowCardDetails = awayYellowCardDetails;
	}

	public String[] getHomeRedCardDetails() {
		return homeRedCardDetails;
	}

	public void setHomeRedCardDetails(String[] homeRedCardDetails) {
		this.homeRedCardDetails = homeRedCardDetails;
	}

	public String[] getAwayRedCardDetails() {
		return awayRedCardDetails;
	}

	public void setAwayRedCardDetails(String[] awayRedCardDetails) {
		this.awayRedCardDetails = awayRedCardDetails;
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
