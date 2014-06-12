package com.psapp.worldcupapp.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class LiveScore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String homeTeam;
	private String awayTeam;
	private String time;
	
	// stadium
	private String date;
	private String stadium;
	private String spectators;
	
	// stats
	private String homeScore;
	private String awayScore;
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
	
	// squad
	private String homeFormation;
	private String awayFormation;
	
	// subs
	private String homeSubDetails;
	private String awaySubDetails;

	public LiveScore() {
		this.homeTeam = "";
		this.awayTeam = "";
		this.homeScore = "";
		this.awayScore = "";
		this.date = "";
		this.stadium = "";
		this.time = "";
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

	public String gethomeScore() {
		return homeScore;
	}

	public void sethomeScore(String homeScore) {
		this.homeScore = homeScore;
	}

	public String getawayScore() {
		return awayScore;
	}

	public void setawayScore(String awayScore) {
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
	

	public static LiveScore fromJson(JSONObject jsonObject) {
		LiveScore livescore = new LiveScore();
		try {
			if (jsonObject.has("hometeam")) {
				livescore.homeTeam = jsonObject.getString("hometeam");
			}

			if (jsonObject.has("homegoals")) {
				livescore.homeScore = jsonObject.getString("homegoals");
			}

			if (jsonObject.has("awayteam")) {
				livescore.awayTeam = jsonObject.getString("awayteam");
			}

			if (jsonObject.has("awaygoals")) {
				livescore.awayScore = jsonObject.getString("awaygoals");
			}

			if (jsonObject.has("date")) {
				livescore.date = jsonObject.getString("date");
			}
			
			if (jsonObject.has("homegoaldetails")) {
				String tmp = jsonObject.getString("homegoaldetails");
				String[] goalBits = tmp.split(";");
			}
			
			if (jsonObject.has("time")) {
				livescore.time = jsonObject.getString("time");
				
			}
			

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return livescore;
	}

	public static ArrayList<LiveScore> fromJson(JSONArray jsonArray) {
		ArrayList<LiveScore> livescores = new ArrayList<LiveScore>(jsonArray.length());
		Log.d("DEBUG",
				"Number of fixtures passed in fromJson: " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject livescoreJson = null;
			try {
				livescoreJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			LiveScore livescore = com.psapp.worldcupapp.models.LiveScore
					.fromJson(livescoreJson);

			if (livescore != null) {
				livescores.add(livescore);
			}
		}

		return livescores;
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
		//this.homeGoalDetails = homeGoalDetails;
	}

	public String[] getAwayGoalDetails() {
		return awayGoalDetails;
	}

	public void setAwayGoalDetails(String awayGoalDetails) {
		//this.awayGoalDetails = awayGoalDetails;
	}

	public String[] getHomeYellowCardDetails() {
		return homeYellowCardDetails;
	}

	public void setHomeYellowCardDetails(String homeYellowCardDetails) {
		//this.homeYellowCardDetails = homeYellowCardDetails;
	}

	public String[] getAwayYellowCardDetails() {
		return awayYellowCardDetails;
	}

	public void setAwayYellowCardDetails(String awayYellowCardDetails) {
		//this.awayYellowCardDetails = awayYellowCardDetails;
	}

	public String[] getHomeRedCardDetails() {
		return homeRedCardDetails;
	}

	public void setHomeRedCardDetails(String homeRedCardDetails) {
		//this.homeRedCardDetails = homeRedCardDetails;
	}

	public String[] getAwayRedCardDetails() {
		return awayRedCardDetails;
	}

	public void setAwayRedCardDetails(String awayRedCardDetails) {
		//this.awayRedCardDetails = awayRedCardDetails;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


}
