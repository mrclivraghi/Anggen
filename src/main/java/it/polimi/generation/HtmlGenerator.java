package it.polimi.generation;

import it.polimi.utils.Field;
import it.polimi.utils.ReflectionManager;
import it.polimi.utils.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;

public class HtmlGenerator {
	private List<Field> fieldList;
	
	private List<Field> childrenField;
	
	private String entityName;

	public static String directory;
	
	private ReflectionManager reflectionManager;
	
	private Class classClass;
	
	
	public HtmlGenerator(Class classClass)
	{
		this.classClass=classClass;
		this.reflectionManager = new ReflectionManager(classClass);
		this.entityName=reflectionManager.parseName();
		this.fieldList=reflectionManager.getFieldList();
		this.childrenField=reflectionManager.getChildrenFieldList();
		File file = new File(""); 
		directory = file.getAbsolutePath()+"\\WebContent\\WEB-INF\\jsp\\";
	}
	
	
	public void generateJSP() throws IllegalAccessException
	{
		HtmlCanvas html = new HtmlCanvas();
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		JsGenerator jsGenerator = new JsGenerator(classClass, true, null, null);
		try {
			html
					.head()
						.title().content("test order")
						.macros().javascript("https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js")
						.script().content(jsGenerator.buildJS(),false)
					._head()
					.body(htmlAttributes.add("ng-app", Utility.getFirstLower(entityName)+"App"));
					AngularGenerator angularGenerator= new AngularGenerator(classClass, true,new ArrayList<Class>());
					angularGenerator.generateEntityView(html);
					/*for (Field field : childrenField)
					{
						HtmlGenerator childrenHtmlGenerator = new HtmlGenerator(field.getFieldClass(), false);
						childrenHtmlGenerator.generateEntityView(html);
						//generateEntityView(html, false,field.getName());
					}*/
					html._body();
		} catch (IOException e) {
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
		
	}
}
