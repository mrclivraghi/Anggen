package it.polimi.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;

public class HtmlCreator {

	private List<Field> fieldList;
	
	private List<Field> childrenField;
	
	private String entityName;

	public static String directory;
	
	private ReflectionManager reflectionManager;
	
	private Class classClass;
	
	
	public HtmlCreator(Class classClass)
	{
		this.classClass=classClass;
		this.reflectionManager = new ReflectionManager(classClass);
		this.entityName=reflectionManager.parseName();
		this.fieldList=reflectionManager.getFieldList();
	}
	
	private String setTempEntityForm()
	{
		StringBuilder sb = new StringBuilder();
		for (Field field: fieldList)
		{
			sb.append("temp"+Generator.getFirstUpper(entityName)+"."+Generator.getFirstLower(field.getName())+"=data[i]."+Generator.getFirstLower(field.getName())+";\n");
		}
		
		return sb.toString();
	}
	
	private void generateChildrenJs(StringBuilder sb,List<JsGenerator> jsGeneratorList, List<Class> parentClassList)
	{
		if (fieldList==null) return;
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()!=null && !parentClassList.contains(field.getFieldClass()))
			{
				ReflectionManager reflectionManager = new ReflectionManager(field.getFieldClass());
				List<Field> childrenList= reflectionManager.getChildrenFieldList();
				JsGenerator jsGenerator = new JsGenerator(field.getName(), false, childrenList, field.getCompositeClass(), entityName);
				sb.append(jsGenerator.generateService());
				jsGeneratorList.add(jsGenerator);
			}
		}
		//vado in ricorsione
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()!=null && !parentClassList.contains(field.getFieldClass()))
			{
				HtmlCreator htmlCreator = new HtmlCreator(field.getFieldClass());
				parentClassList.add(field.getFieldClass());
				htmlCreator.generateChildrenJs(sb,jsGeneratorList, parentClassList);
			}
		}
	}
	
	
	private String buildJS()
	{
		StringBuilder buildJS= new StringBuilder();
		buildJS.append("angular.module(\""+entityName+"App\",[])\n");
		List<JsGenerator> jsGeneratorList= new ArrayList<JsGenerator>();
		jsGeneratorList.add(new JsGenerator(entityName, true,childrenField,null,null));
		buildJS.append(jsGeneratorList.get(0).generateService());
		List<Class> parentClass= new ArrayList<Class>();
		parentClass.add(classClass);
		generateChildrenJs(buildJS,jsGeneratorList,parentClass);
		/*for (Field field: childrenField)
		{
			jsGeneratorList.add(new JsGenerator(field.getName(), false,null,field.getCompositeClass(),entityName));
			buildJS.append(jsGeneratorList.get(jsGeneratorList.size()-1).generateService());
		}*/
		
		for (JsGenerator jsGenerator: jsGeneratorList)
		{
			buildJS.append(jsGenerator.generateController());
		}
		buildJS.append(";");
		return buildJS.toString();
	}
	
	private void generateJSP() throws IllegalAccessException
	{
		childrenField= new ArrayList<Field>();
		if (fieldList==null) return;
		for (Field field: fieldList)
		{
			if (field.getCompositeClass()!=null)
				childrenField.add(field);
		}
		HtmlCanvas html = new HtmlCanvas();
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		
		try {
			html
					.head()
						.title().content("test order")
						.macros().javascript("https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js")
						.script().content(buildJS(),false)
					._head()
					.body(htmlAttributes.add("ng-app", Generator.getFirstLower(entityName)+"App"));
					HtmlGenerator htmlGenerator= new HtmlGenerator(classClass, true,new ArrayList<Class>());
					htmlGenerator.generateEntityView(html);
					/*for (Field field : childrenField)
					{
						HtmlGenerator childrenHtmlGenerator = new HtmlGenerator(field.getFieldClass(), false);
						childrenHtmlGenerator.generateEntityView(html);
						//generateEntityView(html, false,field.getName());
					}*/
					html._body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File myJsp=new File(directory+reflectionManager.parseName(classClass.getName())+".jsp");
		PrintWriter writer;
		try {
			System.out.println("Written "+myJsp.getAbsolutePath());
			writer = new PrintWriter(myJsp, "UTF-8");
			writer.write(html.toHtml());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(html.toHtml());
		
	}

	

	public static void main(String[] args)
	{
		Reflections reflections = new Reflections("it.polimi.model");
		Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);
		List<Class> dependencyClass = new ArrayList<Class>();
		File file = new File(""); 
		directory = file.getAbsolutePath()+"\\WebContent\\WEB-INF\\jsp\\";
		for (Class modelClass: allClasses)
		{
			HtmlCreator htmlCreator = new HtmlCreator(modelClass);
			try {
				htmlCreator.generateJSP();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//System.out.println(StringResource.get("orderApp.js"));
	}


}
