package it.polimi.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Utility {
	public static String formatDate(Date date){
		if (date==null) return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(date);
	}
}
