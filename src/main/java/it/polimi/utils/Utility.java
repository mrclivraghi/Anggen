package it.polimi.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Utility {
	public static String formatDate(Date date){
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
	
	public static String getFirstLower(String string)
	{
		return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toLowerCase());
	}
	
	public static String getFirstUpper(String string)
	{
		return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toUpperCase());
	}
	
	
}
