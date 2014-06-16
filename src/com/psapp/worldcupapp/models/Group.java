package com.psapp.worldcupapp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Group {
	private String group;
	private String points;
	private String won;
	private String draw;
	private String goalFor;
	private String played;
	private String goalAgainst;
	private String lost;
	private String team;
	private String groupId;
	private String goalDifference;
	
	public Group(){
		this.group = "";
		this.points = "0";
		this.won = "0";
		this.draw = "0";
		this.goalFor = "0";
		this.played = "0";
		this.goalAgainst = "0";
		this.lost = "0";
		this.team = "";
		this.groupId = "";
		this.goalDifference = "0";
	}
	
	
	public static Group fromJson(JSONObject jsonObject) {
		Group group = new Group();
		try {
			if (jsonObject.has("played")) {
				group.setPlayed(jsonObject.getString("played"));
			}
			
			if (jsonObject.has("group")) {
				group.setGroup(jsonObject.getString("group"));
			}
			
			if (jsonObject.has("team")){
				group.setTeam(jsonObject.getString("team"));
			}
			
			if (jsonObject.has("won")){
				group.setWon(jsonObject.getString("won"));
			}
			
			if (jsonObject.has("draw")){
				group.setDraw(jsonObject.getString("draw"));
			}
			
			if (jsonObject.has("lost")){
				group.setLost(jsonObject.getString("lost"));
			}
			
			if (jsonObject.has("points")){
				group.setPoints(jsonObject.getString("points"));
			}
			
			if (jsonObject.has("goal_difference")){
				group.setGoalDifference(jsonObject.getString("goal_difference"));
			}
			
			if (jsonObject.has("goal_for")){
				group.setGoalFor(jsonObject.getString("goal_for"));
			}
			
			if (jsonObject.has("goal_against")){
				group.setGoalAgainst(jsonObject.getString("goal_against"));
			}

			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return group;
	}
	
	
	public static ArrayList<Group> fromJson(JSONArray jsonArray) {
		ArrayList<Group> groups = new ArrayList<Group>(jsonArray.length());
		Log.d("DEBUG",
				"Number of teams passed in fromJson: " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject groupJson = null;
			try {
				groupJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Group group = com.psapp.worldcupapp.models.Group
					.fromJson(groupJson);

			if (group != null) {
				groups.add(group);
			}
		}

		return groups;
	}


	public String getGroup() {
		return group;
	}


	public void setGroup(String group) {
		this.group = group;
	}


	public String getPoints() {
		return points;
	}


	public void setPoints(String points) {
		this.points = points;
	}


	public String getWon() {
		return won;
	}


	public void setWon(String won) {
		this.won = won;
	}


	public String getDraw() {
		return draw;
	}


	public void setDraw(String draw) {
		this.draw = draw;
	}


	public String getGoalFor() {
		return goalFor;
	}


	public void setGoalFor(String goalsFor) {
		this.goalFor = goalsFor;
	}


	public String getPlayed() {
		return played;
	}


	public void setPlayed(String played) {
		this.played = played;
	}


	public String getGoalAgainst() {
		return goalAgainst;
	}


	public void setGoalAgainst(String goalAgainst) {
		this.goalAgainst = goalAgainst;
	}


	public String getLost() {
		return lost;
	}


	public void setLost(String lost) {
		this.lost = lost;
	}


	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getGoalDifference() {
		return goalDifference;
	}


	public void setGoalDifference(String goalDifference) {
		this.goalDifference = goalDifference;
	}
}
