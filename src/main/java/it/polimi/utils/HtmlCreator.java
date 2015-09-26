package it.polimi.utils;


import it.polimi.model.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
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
import org.rendersnake.Renderable;
import org.rendersnake.StringResource;

public class HtmlCreator {

	public static HtmlCreator htmlCreator;
	
	private List<Field> fieldList;
	
	private List<Field> childrenField;
	
	private String entityName;

	public static String directory;
	
	public static HtmlCreator getInstance()
	{
		if (htmlCreator==null)
			htmlCreator= new HtmlCreator();
		return htmlCreator;
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
	
	private String buildJS()
	{
		StringBuilder buildJS= new StringBuilder();
		buildJS.append("angular.module(\"orderApp\",[])\n");
		List<JsGenerator> jsGeneratorList= new ArrayList<JsGenerator>();
		jsGeneratorList.add(new JsGenerator(entityName, true,childrenField,null,null));
		buildJS.append(jsGeneratorList.get(0).generateService());
		for (Field field: childrenField)
		{
			jsGeneratorList.add(new JsGenerator(field.getName(), false,null,field.getCompositeClass(),entityName));
			buildJS.append(jsGeneratorList.get(jsGeneratorList.size()-1).generateService());
		}
		for (JsGenerator jsGenerator: jsGeneratorList)
		{
			buildJS.append(jsGenerator.generateController());
		}
		buildJS.append(";");
		return buildJS.toString();
	}
	
	private void generateJSP(Class entityClass)
	{
		entityName=ReflectionManager.parseName(entityClass.getName());
		childrenField= new ArrayList<Field>();
		try {
			fieldList=ReflectionManager.generateField(entityClass.newInstance());
			if (fieldList==null) return;
			for (Field field: fieldList)
			{
				if (field.getCompositeClass()!=null)
					childrenField.add(field);
			}
		} catch (InstantiationException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
					HtmlGenerator htmlGenerator= new HtmlGenerator(entityClass, true);
					htmlGenerator.generateEntityView(html);
					for (Field field : childrenField)
					{
						HtmlGenerator childrenHtmlGenerator = new HtmlGenerator(field.getFieldClass(), false);
						childrenHtmlGenerator.generateEntityView(html);
						//generateEntityView(html, false,field.getName());
					}
					html._body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File myJsp=new File(directory+ReflectionManager.parseName(entityClass.getName())+".jsp");
		PrintWriter writer;
		try {
			System.out.println("Written "+myJsp.getAbsolutePath());
			writer = new PrintWriter(myJsp, "UTF-8");
			writer.write(html.toString());
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
			HtmlCreator.getInstance().generateJSP(modelClass);
		}
		//System.out.println(StringResource.get("orderApp.js"));
	}


}
