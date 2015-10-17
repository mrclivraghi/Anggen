package it.polimi.utils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
	public static String formatDate(java.sql.Date date){
		if (date==null) return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(date);
	}
	
	public static String formatDate(java.util.Date date){
		if (date==null) return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate=simpleDateFormat.format(date);
		return formattedDate;
	}
	
	public static String formatTime(Time time) {
		if (time==null) return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedTime=simpleDateFormat.format(time);
		return formattedTime;
	}
	public static String getFirstLower(String string)
	{
		return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toLowerCase());
	}
	
	public static String getFirstUpper(String string)
	{
		return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toUpperCase());
	}
	
	public static void main (String[] args)
	{
		Date myDate= new Date();
		Time myTime= new Time(myDate.getTime());
		System.out.println(Utility.formatTime(myTime));
	}

	
}
