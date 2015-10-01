package it.polimi.utils;

import it.polimi.oldmodel.Order;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;

public class ReflectionManager {
	
	private Class classClass;
	
	private Object obj;

	
	public ReflectionManager(Class myClass)
	{
		this.classClass=myClass;
		try {
			this.obj=this.classClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean isKnownClass()
	{
		return isKnownClass(classClass);
	}
	
	public Boolean isKnownClass(Class myClass)
	{
		if (myClass==Long.class) return true;
		if (myClass==String.class) return true;
		if (myClass==Date.class) return true;
		if (myClass==java.sql.Date.class) return true;
		if (myClass==Integer.class) return true;
		
		return false;
	}
	
	public String parseName()
	{
		return parseName(classClass.getName());
	}
	
	public String parseName(String fieldName)
	{
		while (fieldName.indexOf(".")>-1)
			fieldName=fieldName.substring(fieldName.indexOf(".")+1,fieldName.length());
		if (fieldName.indexOf("List")==fieldName.length()-4 && fieldName.length()>3)
			fieldName=fieldName.substring(0,fieldName.length()-4);
		return Generator.getFirstLower(fieldName);
		
	}
	
	public List<Field> getChildrenField()
	{
		List<Field> fieldList= generateField();
		List<Field> childrenList = new ArrayList<Field>();
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()!=null)
			{
				childrenList.add(field);
			}
		}
		return childrenList;
	}
	
	
	public List<Field> generateField()
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
	
	public Boolean hasList()
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
	
	public List<Class> getChildrenClasses()
	{
		List<Class> classList= new ArrayList<Class>();
		List<Field> fieldList= generateField();
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()!=null)
				classList.add(field.getFieldClass());
		}
		
		
		return classList;
	}
	
	
	public String getDescriptionField()
	{
		return getDescriptionField(classClass);
	}
	
	public String getDescriptionField(Class classClass)
	{
		String descriptionFields="";
		String entity=parseName(classClass.getName());
		List<Field> fieldList = null;
		if (classClass!=this.classClass)
		{
			ReflectionManager reflectionManager= new ReflectionManager(classClass);
			fieldList= reflectionManager.generateField();
		} else
			fieldList = generateField();
		for (Field field: fieldList)
		{
			if (field.getFieldClass()==String.class)
			{
				descriptionFields=descriptionFields+" "+entity+"."+field.getName()+"+' '+";
			}
		}
		descriptionFields=descriptionFields.substring(0, descriptionFields.length()-5);
		return descriptionFields;
	}
	
	
	public Class getKeyClass()
	{
		
		java.lang.reflect.Field[] fields=obj.getClass().getDeclaredFields();
		
		for (java.lang.reflect.Field field : fields)
		{
			Annotation[] annotationList = field.getAnnotations();
			for (int i=0; i<annotationList.length; i++)
			{
				if (annotationList[i].annotationType()==javax.persistence.Id.class)
					return field.getType();
			}
		}
		return null;
			
	}
	
	
	public static void main(String[] args)
	{
		ReflectionManager reflectionManager= new ReflectionManager(Order.class);
		List<Field> fieldList=reflectionManager.generateField();
		/*for (Field field: fieldList)
			System.out.println(field.getName()+"-"+field.getFieldClass()+"-"+(field.getCompositeClass()==null ? "" : field.getCompositeClass().fullName())+"-"+(field.getRepositoryClass()==null ? "" : field.getRepositoryClass().getName()));//+field.getCompositeClass().toString()+"-"+field.getRepositoryClass().toString());
		
		System.out.println("descriptionFields");
		System.out.println(getDescriptionField(Order.class));
		System.out.println(getDescriptionField(Place.class));
		System.out.println(getDescriptionField(Person.class));
		*/
		
		
	}
}
