package com.psapp.worldcupapp.models;

public class Events {
	private String minute;
	private String type;
	private String name;
	private String side;
	private Match m;

	public Events() {
		this.minute = "";
		this.type = "";
		this.name = "";
		this.side = "";
		this.m = new Match();
	}

	public String getMinute() {
		return minute + "'";
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public void setMatch(Match m) {
		this.m = m;
	}

	public Match getMatch() {
		return m;
	}
}
