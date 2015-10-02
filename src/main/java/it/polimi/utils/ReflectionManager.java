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
	
	public String parseName(String name)
	{
		while (name.indexOf(".")>-1)
			name=name.substring(name.indexOf(".")+1,name.length());
		if (name.indexOf("List")==name.length()-4 && name.length()>3)
			name=name.substring(0,name.length()-4);
		return Utility.getFirstLower(name);
		
	}
	
	public List<Field> getChildrenFieldList()
	{
		List<Field> fieldList= getFieldList();
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
	
	
	public List<Field> getFieldList()
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
		List<Field> fieldList= getFieldList();
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
			fieldList= reflectionManager.getFieldList();
		} else
			fieldList = getFieldList();
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
	
	
	public String getAllParam()
	{
		String string="";
		List<Field> fields = getFieldList();
		String className = parseName();
		for (Field field: fields)
		{
			if (field.getFieldClass()==Date.class || field.getFieldClass()==java.sql.Date.class)
			{
				string=string+"it.polimi.utils.Utility.formatDate("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(field.getName())+"()),";
			}else
			{
				if (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List"))
					string=string+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(field.getName())+"List()==null? null :"+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(field.getName())+"List().get(0),";
				else
					string=string+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(field.getName())+"(),";
			}
		}
		return string.substring(0, string.length()-1);
	}
	
	
	
	public static void main(String[] args)
	{
		ReflectionManager reflectionManager= new ReflectionManager(Order.class);
		List<Field> fieldList=reflectionManager.getFieldList();
		/*for (Field field: fieldList)
			System.out.println(field.getName()+"-"+field.getFieldClass()+"-"+(field.getCompositeClass()==null ? "" : field.getCompositeClass().fullName())+"-"+(field.getRepositoryClass()==null ? "" : field.getRepositoryClass().getName()));//+field.getCompositeClass().toString()+"-"+field.getRepositoryClass().toString());
		
		System.out.println("descriptionFields");
		System.out.println(getDescriptionField(Order.class));
		System.out.println(getDescriptionField(Place.class));
		System.out.println(getDescriptionField(Person.class));
		*/
		
		
	}
}
