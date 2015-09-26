package it.polimi.utils;

import it.polimi.model.Order;
import it.polimi.model.Place;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;

public class ReflectionManager {

	public static Boolean isKnownClass(Class myClass)
	{
		if (myClass==Long.class) return true;
		if (myClass==String.class) return true;
		if (myClass==Date.class) return true;
		if (myClass==java.sql.Date.class) return true;
		
		return false;
	}
	
	
	public static String parseName(String fieldName)
	{// it.polimi.model.Person
		while (fieldName.indexOf(".")>-1)
			fieldName=fieldName.substring(fieldName.indexOf(".")+1,fieldName.length());
		if (fieldName.indexOf("List")==fieldName.length()-4)
			fieldName=fieldName.substring(0,fieldName.length()-4);
		return Generator.getFirstLower(fieldName);
		
	}
	public static List<Field> generateField(Object obj)
	{
		java.lang.reflect.Field[] fields=obj.getClass().getDeclaredFields();
		
		List<Field> fieldList=new ArrayList<Field>();
		JCodeModel	codeModel = new JCodeModel();
		
		for (java.lang.reflect.Field field : fields)
		{
			Class fieldClass= null;
			JClass jClass=null;
			Class repositoryClass=null;
			if (isKnownClass(field.getType()))
				fieldClass=field.getType();
			else
			{
				jClass=codeModel.ref(field.getType());
				if (field.getType()==List.class)
				{//entityList
					Type type=field.getGenericType();
					if (type instanceof ParameterizedType)
					{
						Type elementType= ((ParameterizedType)type).getActualTypeArguments()[0];
						try {
							fieldClass=Class.forName(elementType.getTypeName());
							jClass=jClass.narrow(fieldClass);
							repositoryClass=Class.forName(elementType.getTypeName().replace(".model.", ".repository.")+"Repository");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else
				{//entity
					fieldClass=field.getType();
				}
			}
			Field myField= new Field(parseName(field.getName()), fieldClass, jClass, repositoryClass);
			fieldList.add(myField);
		}
		return fieldList;
	}
	
	public static Boolean hasList(Object obj)
	{
		Boolean hasList=false;

		java.lang.reflect.Field[] fields=obj.getClass().getDeclaredFields();

		List<Field> fieldList=new ArrayList<Field>();

		for (java.lang.reflect.Field field : fields)
		{
			if (!isKnownClass(field.getType()))
			{
				if (field.getType()==List.class)
				{
					hasList=true;
					break;

				}
			}
		}
		return hasList;
	}
	
	public static void main(String[] args)
	{
		List<Field> fieldList=ReflectionManager.generateField(new Order());
		for (Field field: fieldList)
			System.out.println(field.getName()+"-"+field.getFieldClass()+"-"+(field.getCompositeClass()==null ? "" : field.getCompositeClass().fullName())+"-"+(field.getRepositoryClass()==null ? "" : field.getRepositoryClass().getName()));//+field.getCompositeClass().toString()+"-"+field.getRepositoryClass().toString());
	}
}
