package com.psapp.worldcupapp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Score {
	private String body;
	private long uid;
	private String homeTeam;
	private String awayTeam;
    private Integer homeScore;
    private Integer awayScore;
    private String time;

    
    public String getBody() {
        return body;
    }

    public long getId() {
        return uid;
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

	public Integer getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(Integer homeScore) {
		this.homeScore = homeScore;
	}

	public Integer getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(Integer awayScore) {
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
//        try {
//        	tweet.body = jsonObject.getString("text");
//        	tweet.uid = jsonObject.getLong("id");
//        	tweet.favorited = jsonObject.getBoolean("favorited");
//        	tweet.retweeted = jsonObject.getBoolean("retweeted");
//            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
        return score;
    }

    public static ArrayList<Score> fromJson(JSONArray jsonArray) {
        ArrayList<Score> scores = new ArrayList<Score>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
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


}
