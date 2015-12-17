package it.anggen.utils;

import it.anggen.generation.Generator;
import it.anggen.utils.annotation.Between;
import it.anggen.utils.annotation.DescriptionField;
import it.anggen.utils.annotation.ExcelExport;
import it.anggen.utils.annotation.Filter;
import it.anggen.utils.annotation.IgnoreSearch;
import it.anggen.utils.annotation.IgnoreTableList;
import it.anggen.utils.annotation.IgnoreUpdate;
import it.anggen.utils.annotation.Tab;
import it.anggen.model.field.EnumField;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.mapping.Array;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationParameter;
import org.reflections.Reflections;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.query.Param;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;

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
		if (myClass==int.class) return true;
		if (myClass==double.class) return true;
		if (myClass==Double.class) return true;
		if (myClass==BigDecimal.class) return true;
		if (myClass==Boolean.class) return true;
		if (myClass==Time.class) return true;
		if (myClass==File.class) return true;
		
		return false;
	}

	public static Boolean isListField(Field field)
	{
		return (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List"));
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
		if (name.endsWith(">"))
			name=name.substring(0, name.length()-1);
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
				addChildrenFilter(field);
				childrenList.add(field);
			}
		}
		return childrenList;
	}
	
	public void addChildrenFilter(Field field)
	{
		if (field.getCompositeClass()==null) return;
		ReflectionManager reflectionManager = new ReflectionManager(field.getFieldClass());
		field.setChildrenFilterList(new ArrayList<Field>());
		for (Field childrenField: reflectionManager.getFieldList())
		{
			if (ReflectionManager.hasFilter(childrenField))
				field.getChildrenFilterList().add(childrenField);
		}
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
			JDefinedClass repositoryClass=null;
			Boolean isEnum=field.getType().isEnum();
			List<String> enumValuesList= new ArrayList<String>();
			if (isKnownClass(field.getType()))
				fieldClass=field.getType();
			else
			{
				if (isEnum)
				{
					Object[] enumValues=field.getType().getEnumConstants();
					for (int i=0; i<enumValues.length; i++)
						enumValuesList.add(enumValues[i].toString());
					
					fieldClass=field.getType();
					
				} else
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
								//repositoryClass=Generator.getJDefinedClass(elementType.getTypeName());
							} catch (ClassNotFoundException e) {
								//e.printStackTrace();
							}
						}
					}else
					{//entity
						fieldClass=field.getType();
					}
				}
			}
			Field myField= new Field(parseName(field.getName()), fieldClass, jClass, repositoryClass);
			myField.setAnnotationList(field.getAnnotations());
			myField.setIsEnum(isEnum);
			myField.setEnumValuesList(enumValuesList);
			myField.setOwnerClass(classClass);
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
	
	//recursive
	public static List<ClassDetail> getDescendantClassList(Class theClass,List<Class> parentClassList)
	{
		ReflectionManager reflectionManager = new ReflectionManager(theClass);
		List<ClassDetail> returnedClassList = new ArrayList<ClassDetail>();
		List<Field> childrenFieldList = reflectionManager.getChildrenFieldList();
		if (childrenFieldList.size()==0) return returnedClassList;
		List<Field> thisRunFields= new ArrayList<Field>();
		
		for (Field field: childrenFieldList)
		{
			if (!parentClassList.contains(field.getFieldClass()))
			{
				ClassDetail classDetail = new ClassDetail();
				classDetail.setClassClass(field.getFieldClass());
				classDetail.setCompositeClass(field.getCompositeClass());
				classDetail.setParentName(reflectionManager.parseName());;
				returnedClassList.add(classDetail);
				parentClassList.add(field.getFieldClass());
				thisRunFields.add(field);
				
			}
		}
		
		for (Field field : thisRunFields)
		{
				returnedClassList.addAll(ReflectionManager.getDescendantClassList(field.getFieldClass(), parentClassList));
		}
		
		return returnedClassList;
		
		
		
	}
	
	public List<ClassDetail> getSubClassList()
	{
		List<ClassDetail> subClassList = null;
		List<Class> parentClassList = new ArrayList<Class>();
		parentClassList.add(classClass);
		subClassList=ReflectionManager.getDescendantClassList(classClass,parentClassList);
		return subClassList;
	}
	
	public String getDescriptionField()
	{
		return getDescriptionField(classClass,false,parseName());
	}
	
	public String getDescriptionField(Boolean withGetter)
	{
		return getDescriptionField(classClass,withGetter,parseName());
	}
	
	public String getDescriptionField(Class classClass,Boolean withGetter,String entityName)
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
			for (Annotation annotation: field.getAnnotationList())
			{
				if (annotation.annotationType()==DescriptionField.class)
				{
					if (withGetter)
						descriptionFields=descriptionFields+" "+entityName+".get"+Utility.getFirstUpper(field.getName())+"()+' '+";
					else
						descriptionFields=descriptionFields+" "+entityName+"."+field.getName()+"+' '+";

				}
			}
		}
		if (descriptionFields.length()>5)
			descriptionFields=descriptionFields.substring(0, descriptionFields.length()-5);
		else
		{
			if (withGetter)
				descriptionFields=entity+".toString()";
			else
				descriptionFields=entity+"."+entity+"Id";
		}
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
			if (ReflectionManager.hasBetween(field))
			{
				string=string+manageSingleParam(className, field,  field.getName()+"From");
				string=string+manageSingleParam(className, field,  field.getName()+"To");
			}else
				string=string+manageSingleParam(className, field,  field.getName());
			addChildrenFilter(field);
			if (field.getChildrenFilterList()!=null)
				for (Field filterField: field.getChildrenFilterList())
				{
					ReflectionManager reflectionManager = new ReflectionManager(filterField.getOwnerClass());
					String filterFieldName=reflectionManager.parseName(filterField.getOwnerClass().getName())+Utility.getFirstUpper(filterField.getName());
					string = string+manageSingleParam(className, filterField, filterFieldName);
					
				}
			
		}
		return string.substring(0, string.length()-1);
	}
	
	private String manageSingleParam(String className,Field field, String fieldName)
	{
		String string="";
		if (field.getIsEnum())
		{
			string=string+" ("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"()==null)? null : "+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"().getValue(),";
		}else
		{
			if (ReflectionManager.isTimeField(field))
			{
				string=string+"it.anggen.utils.Utility.formatTime("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"()),";
			}
			else
			{
				if (ReflectionManager.isDateField(field))
				{
					string=string+"it.anggen.utils.Utility.formatDate("+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"()),";
				}else
				{
					if (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List"))
						string=string+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"List()==null? null :"+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"List().get(0),";
					else
						string=string+Utility.getFirstLower(className)+".get"+Utility.getFirstUpper(fieldName)+"(),";
				}
			}}
		return string;
	}
	
	
	public static Boolean isDateField(Field field)
	{
		Annotation[] annotationList=field.getAnnotationList();
		for (int i=0; i<annotationList.length;i++)
		{
			if (annotationList[i].annotationType()==Temporal.class)
				return true;
		}
		if (field.getFieldClass()==Date.class || field.getFieldClass()==java.sql.Date.class || field.getFieldClass()==Time.class)
			return true;
		return false;
	}
	
	public static Boolean isTimeField(Field field)
	{
		if (!isDateField(field)) return false;
		Annotation[] annotationList=field.getAnnotationList();
		for (int i=0; i<annotationList.length;i++)
		{
		/*	if (annotationList[i].annotationType()==Temporal.class)
				{
				
				for (Method method : annotationList[i].annotationType().getDeclaredMethods()) {
					if (method.getName().equals("value"))
					{
						Object value;
							try {
								value = method.invoke(annotationList[i], (Object[])null);
								if (((TemporalType)value).name().equals("TIME"))
									return true;
							} catch (IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
		}*/
		}
		if (field.getFieldClass()==Time.class) return true;
		return false;
	}
	
	public static Boolean hasIgnoreSearch(Field field)
	{
		return hasAnnotation(field, IgnoreSearch.class);
	}
	
	public static Boolean hasTab(Field field)
	{
		return hasAnnotation(field, Tab.class);
	}
	
	
	public static Boolean hasId(Field field)
	{
		return hasAnnotation(field, javax.persistence.Id.class);
	}
	
	public static Boolean hasFilter(Field field)
	{
		return hasAnnotation(field, Filter.class);
	}
	
	public static Boolean hasExcelExport(Field field)
	{
		return hasAnnotation(field, ExcelExport.class);
	}
	
	public static Boolean hasIgnoreUpdate(Field field)
	{
		return hasAnnotation(field, IgnoreUpdate.class);
	}
	
	public static Boolean hasIgnoreTableList(Field field)
	{
		return hasAnnotation(field, IgnoreTableList.class);
	}
	
	
	
	public static Boolean hasBetween(Field field)
	{
		return hasAnnotation(field, Between.class);
	}
	
	public static Boolean hasOneToOne(Field field)
	{
		return hasAnnotation(field, OneToOne.class);
	}
	
	public static boolean hasOneToMany(Field field)
	{
		return hasAnnotation(field, OneToMany.class);
	}
	
	public static Boolean hasManyToOne(Field field)
	{
		return hasAnnotation(field, ManyToOne.class);
	}
	public static Boolean hasManyToMany(Field field)
	{
		return hasAnnotation(field, ManyToMany.class);
	}
	public static Boolean hasBackManyToMany(Field field)
	{
		//return hasManyToMany(field);
		Annotation annotation = ReflectionManager.getAnnotation(field, ManyToMany.class);
		if (annotation!=null)
		{

			for (Method method : annotation.annotationType().getDeclaredMethods()) {
				if (method.getName().equals("mappedBy"))
				{
					Object value=null;
					try {
						value = method.invoke(annotation, (Object[])null);
						if (value!=null && !value.equals(""))
							return true;
					} catch (IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		
			return false;
		} else
			return false;
	}
	
	private static Boolean hasAnnotation(Field field,Class ignoreAnnotationClass)
	{
		Annotation[] annotationList= field.getAnnotationList();
		for (int i=0; i<annotationList.length; i++)
		{
			if (annotationList[i].annotationType()==ignoreAnnotationClass)
				return true;
		}
		return false;
	}
	
	public static Annotation getAnnotation(Field field, Class annotationClass)
	{
		Annotation[] annotationList= field.getAnnotationList();
		for (int i=0; i<annotationList.length; i++)
		{
			if (annotationList[i].annotationType()==annotationClass)
				return annotationList[i];
		}
		return null;
	}
	
	public static Boolean hasManyToManyAssociation (Class theClass,String parentClass)
	{
		ReflectionManager reflectionManager = new ReflectionManager(theClass);
		
		for (Field field: reflectionManager.getFieldList())
		{
			if ((reflectionManager.parseName(field.getFieldClass().getName()).equals(parentClass) || field.getFieldClass().getName().equals(parentClass))&& ReflectionManager.hasManyToMany(field))
				return true;
		}
		return false;
	}
	
	
	public static List<String> getSubPackages(String mainPackage)
	{
		List<String> packageList= new ArrayList<String>();
		Reflections reflections = new Reflections(mainPackage);
		Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);
		for (Class theClass: allClasses)
		{
			if (!packageList.contains(theClass.getPackage().getName()) && !theClass.getPackage().getName().equals(mainPackage))
				packageList.add(theClass.getPackage().getName());
		}
		if (packageList.size()==0)
			packageList.add(mainPackage);
		return packageList;
	}
	
	public static List<String> getMainMenuItem(String mainPackage)
	{
		List<String> packageList= ReflectionManager.getSubPackages(mainPackage);
		List<String> menuItemList= new ArrayList<String>();
		ReflectionManager reflectionManager= new ReflectionManager(Object.class);
		for (String myPackage: packageList)
		{
			menuItemList.add(reflectionManager.parseName(myPackage));
		}
		
		return menuItemList;
	}
	


	public static Class getRightParamClass(Field field) {
		if (field.getIsEnum())
			return Integer.class;
		
		if (ReflectionManager.isDateField(field))
			return String.class;
		
		return field.getFieldClass();
	}
	
	
	public static Set<Class<?>> getClassInPackage(String thePackage)
	{
		Reflections reflections = new Reflections(thePackage);
		Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);
		return allClasses;
	}
	
	
	public Boolean containFieldWithClass(Class fieldClass)
	{
		List<Field> fieldList = getFieldList();
		for (Field field: fieldList)
			if (field.getFieldClass()==fieldClass)
				return true;
		
		return false;
	}
	
	
	public List<String> getTabsName()
	{
		List<String> tabNameList = new ArrayList<String>();
		for (Field field: getFieldList())
		{
			for (Annotation annotation: field.getAnnotationList())
			{
				if (annotation.annotationType()==Tab.class)
				{
					for (Method method : annotation.annotationType().getDeclaredMethods()) {
						if (method.getName().equals("name"))
						{
							Object value=null;
							try {
								value = method.invoke(annotation, (Object[])null);
							} catch (IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							if (!tabNameList.contains(value))
								tabNameList.add((String) value);
						}
					}
				}
			}
		}
		if (tabNameList.size()==0)
			tabNameList.add("Detail");
		return tabNameList;
	}
	
	public List<Field> getFieldByTabName(String tabName)
	{
		List<Field> fieldList = new ArrayList<Field>();
		for (Field field: getFieldList())
		{
			if (ReflectionManager.hasTab(field))
			{
				for (Annotation annotation: field.getAnnotationList())
				{
					for (Method method : annotation.annotationType().getDeclaredMethods()) {
						if (method.getName().equals("name"))
						{
							Object value=null;
							try {
								value = method.invoke(annotation, (Object[])null);
								if (tabName.equals(""+value))
									fieldList.add(field);

							} catch (IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		if (fieldList.size()==0  && tabName.equals("Detail")) //default every field
			return getFieldList();
		return fieldList;
	}
	
	
	
	public static JDefinedClass getJDefinedClass(it.anggen.model.entity.Entity entity)
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			String thePackage=Generator.generatedPackage+Generator.appName+".model."+entity.getEntityGroup().getName().toLowerCase()+".";
			if (entity.getEntityGroup().getName().equals("security"))
				thePackage=thePackage.replaceAll("."+Generator.appName+".", ".anggen.");
			//	if (entity.getName().endsWith("Repository"))
	//			thePackage=thePackage.replace(".model", ".repository.");
			myClass = codeModel._class(thePackage+Utility.getFirstUpper(entity.getName()), ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		return myClass;
	}
	public  static  JDefinedClass getJDefinedRepositoryClass(it.anggen.model.entity.Entity entity)
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			String thePackage=Generator.generatedPackage+Generator.appName+".repository."+entity.getEntityGroup().getName().toLowerCase()+".";
			if (entity.getEntityGroup().getName().equals("security"))
				thePackage=thePackage.replaceAll("."+Generator.appName+".", ".anggen.");
		//	if (entity.getName().endsWith("Repository"))
	//			thePackage=thePackage.replace(".model", ".repository.");
			myClass = codeModel._class(thePackage+Utility.getFirstUpper(entity.getName()+"Repository"), ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		return myClass;
	}
	public static  JDefinedClass getJDefinedEnumFieldClass(EnumField enumField)
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			String thePackage=Generator.generatedPackage+Generator.appName+".model.";
		//	if (entity.getName().endsWith("Repository"))
	//			thePackage=thePackage.replace(".model", ".repository.");
			myClass = codeModel._class(thePackage+Utility.getFirstUpper(enumField.getName()), ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		return myClass;
	}
	public static  JType getJDefinedCustomClass(String fullName) {
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			
		//	if (entity.getName().endsWith("Repository"))
	//			thePackage=thePackage.replace(".model", ".repository.");
			myClass = codeModel._class(fullName, ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		return myClass;
	}
	
	
	public static void main(String[] args)
	{
		ReflectionManager reflectionManager= new ReflectionManager(it.anggen.model.entity.Entity.class);
		for (Field field: reflectionManager.getFieldList())
		{
			System.out.println(field.getName()+"-"+field.getFieldClass());
			Annotation[] list= field.getAnnotationList();
			for (int i=0; i<list.length; i++)
				System.out.println(field.getName()+"-"+list[i].annotationType().getName());
			//System.out.println("isEnum: "+field.getIsEnum());
			if (field.getEnumValuesList()!=null)
			for (String string: field.getEnumValuesList())
				System.out.println("ENUM VALUeS: "+string);
		}
		List<ClassDetail> classList = reflectionManager.getSubClassList();
		for (ClassDetail myClass : classList)
		{
			System.out.println(myClass.getClassClass().getName()+"---"+myClass.getCompositeClass().fullName());
		}
		
		/*reflectionManager= new ReflectionManager(Sex.class);
		//List<Field> fieldList=reflectionManager.getFieldList();
		//System.out.println(Sex.class.getEnumConstants());
		Sex[] sec= Sex.class.getEnumConstants();
		System.out.println("Package: "+Sex.class.getPackage().toString());
		for (int i=0; i<sec.length; i++)
			System.out.println("EC: "+sec[i].toString());
		
		for (java.lang.reflect.Field field : fields)
		{
			System.out.println(field.getName());
		} 

		//Reflections reflections = new Reflections("it.anggen.model");
		List<String> subPackage= ReflectionManager.getMainMenuItem("it.anggen.model");
		for (String string: subPackage)
		{
			System.out.println(string);
		}
		
		
	//	System.out.println(reflectionManager.getDescriptionField(Mountain.class, false));
	//	System.out.println(reflectionManager.getDescriptionField(Mountain.class, true));
		
		System.out.println("-----");
	//	System.out.println(ReflectionManager.hasManyToManyAssociation(Paziente.class, Ambulatorio.class.getName()));
	*/}
}
