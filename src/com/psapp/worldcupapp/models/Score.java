package com.psapp.worldcupapp.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Score {
	private String homeTeam;
	private String awayTeam;
	private String homeScore;
	private String awayScore;
	private String time;
	private String stadium;
	
	public Score() {
		this.homeTeam = "";
		this.awayTeam = "";
		this.homeScore = "";
		this.awayScore = "";
		this.time = "";
		this.stadium = "";
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

	public static Score fromJson(JSONObject jsonObject) {
		Score score = new Score();
		try {
			if (jsonObject.has("HomeTeam")) {
				score.homeTeam = jsonObject.getString("HomeTeam");
			}

			if (jsonObject.has("AwayTeam")) {
				score.awayTeam = jsonObject.getString("AwayTeam");
			}

			if (jsonObject.has("Location")) {
				score.stadium = jsonObject.getString("Location");
			}

			if (jsonObject.has("Date")) {
				// 2014-06-13T08:00:00-08:00
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
				//sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
				//sdf.setTimeZone(TimeZone.getDefault());
				//String formattedDate = sdf.format(jsonObject.getString("Date"));
				
				score.time = jsonObject.getString("Date");
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return score;
	}

	public static ArrayList<Score> fromJson(JSONArray jsonArray) {
		ArrayList<Score> scores = new ArrayList<Score>(jsonArray.length());
		Log.d("DEBUG",
				"Number of fixtures passed in fromJson: " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject scoreJson = null;
			try {
				scoreJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Score score = Score.fromJson(scoreJson);

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

}
