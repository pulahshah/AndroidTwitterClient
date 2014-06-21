package com.psapp.worldcupapp;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class PrettyDate {
	
	public static void getTimeDiff(String t){
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
		LocalDateTime dt = parser2.parseDateTime(t).toLocalDateTime();
		
		
		
	}
	
	public static String getPrettyTime(String d, Boolean format24){
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
		
		LocalDateTime dt = parser2.parseDateTime(d).toLocalDateTime();
		System.out.println("Local: " + dt.toString());
		
		
		
		int hour = dt.getHourOfDay();
		int min = dt.getMinuteOfHour();
		String minutes = min+"";
		if(min == 0){
			minutes = "00";
		}
		
		String pm = "";
		
		if(!format24){
			if(hour>12){
				hour = hour - 12;
				pm = "PM";
			}
			else{
				pm = "AM";
			}
			
			if(hour==12){
				pm = "PM";
			}
			
		}
		return hour + ":" + minutes + " " + pm;
	}
	
	public static String getPrettyDate(String d){
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
		LocalDateTime dt = parser2.parseDateTime(d).toLocalDateTime();
		String month = "Jun";
		String date = dt.getDayOfMonth()+"";
		String pm = "AM";
		String year = dt.getYear()+"";
		int hour = dt.getHourOfDay();
		
		if(dt.getMonthOfYear() == 7){
			month = "Jul";
		}
		
		return date + " " + month + " " + year;
	}
	
	public static String getPrettyDate(String d, Boolean y){
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
		LocalDateTime dt = parser2.parseDateTime(d).toLocalDateTime();
		String month = "Jun";
		String date = dt.getDayOfMonth()+"";
		String pm = "AM";
		String year = dt.getYear()+"";
		int hour = dt.getHourOfDay();
		
		if(dt.getMonthOfYear() == 7){
			month = "Jul";
		}
		
		return date + " " + month;
	}
	
	public static String getRelativeDate(String d){
		return "";
	}
}
