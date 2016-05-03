package it.anggen.utils;

import it.anggen.model.GenerationRun;
import it.anggen.model.entity.Entity;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

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
				//cut=i;
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
	
	public static String saveMultipartFile(MultipartFile file, String destination)
	{
		String filePath=null;
		if (file==null || file.isEmpty())
		{
			filePath="";
		} else
		{
			
			try {
				
				File savedFile = new File(destination);
				if (!savedFile.exists())
					savedFile.mkdirs();
				savedFile=new File(destination+file.getOriginalFilename());
				file.transferTo(savedFile);
				filePath=savedFile.getAbsolutePath();
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return filePath;
	}
	
	
	public static String camelCaseToMinus(String str)
	{
		String temp= str.substring(0, 1);
		for (int i=1; i<str.length(); i++)
		{
			if (Character.isUpperCase(str.charAt(i)))
			{
				temp=temp+"-"+Character.toLowerCase(str.charAt(i));
			} else
				temp=temp+str.charAt(i);
		}
		
		return temp;
	}
	
	
	public static void main (String[] args)
	{
		Date myDate= new Date();
		Time myTime= new Time(myDate.getTime());
		System.out.println(Utility.formatTime(myTime));
		System.out.println(Utility.getEntityCallName("entityTarget"));
		
	}

	public static void orderByPriority(List<EntityAttribute> entityAttributeList) {
		Collections.sort(entityAttributeList, Comparator.comparing(EntityAttribute::getPriority));
		}
	public static void orderByStartDate(List<GenerationRun> generationRunList) {
		Collections.sort(generationRunList, Comparator.comparing(GenerationRun::getStartDate));
		}
	
	public static void orderById(List<Entity> entityList){
		Collections.sort(entityList, Comparator.comparing(Entity::getEntityId));
	}
	public static Long getFirstEntityId(List<Entity> entityList){
		Utility.orderById(entityList);
		if (entityList.size()==0)
			return 1L;
		else
			return entityList.get(entityList.size()-1).getEntityId()+1;
	}

	
}
