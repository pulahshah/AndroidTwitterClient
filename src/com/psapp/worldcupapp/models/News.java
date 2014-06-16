package com.psapp.worldcupapp.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class News {
	private String headline;
	private String date;
	private String snippet;
	private String link;
	private String content;
	
	
	public String getHeadline() {
		return headline;
	}
	
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getSnippet() {
		return snippet;
	}
	
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	
	public static News fromJson(JSONObject jsonObject) {
		News newsItem = new News();
		try {
			if (jsonObject.has("publishedDate")) {
				newsItem.date = jsonObject.getString("publishedDate");
			}

			if (jsonObject.has("contentSnippet")) {
				newsItem.snippet = jsonObject.getString("contentSnippet");
			}

			if (jsonObject.has("title")) {
				newsItem.headline = jsonObject.getString("title");
			}
			
			if (jsonObject.has("link")) {
				newsItem.link = jsonObject.getString("link");
			}
			
			if (jsonObject.has("content")) {
				newsItem.content = jsonObject.getString("content");
			}

			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return newsItem;
	}
	
	public static ArrayList<News> fromJson(JSONArray jsonArray) {
		ArrayList<News> news = new ArrayList<News>(jsonArray.length());
//		Log.d("DEBUG",
//				"Number of news passed in fromJson: " + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject newsJson = null;
			try {
				newsJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			News newsItem = News.fromJson(newsJson);

			if (newsItem != null) {
				news.add(newsItem);
			}
		}

		return news;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
