package it.polimi.utils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
	
	public static String getEntityCallName(String string)
	{
		Integer cut=string.length();
		for (int i=0; i<string.length(); i++)
		{
			if (Character.isUpperCase(string.charAt(i)))
			{
				cut=i;
				break;
			}
		}
		return string.substring(0, cut);
	}
	
	public static String encodePassword(String password)
	{
		BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}
	
	public static void main (String[] args)
	{
		Date myDate= new Date();
		Time myTime= new Time(myDate.getTime());
		System.out.println(Utility.formatTime(myTime));
		System.out.println(Utility.getEntityCallName("entityTarget"));
		
	}

	
}
