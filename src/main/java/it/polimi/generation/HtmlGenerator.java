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

import org.rendersnake.DocType;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;

public class HtmlGenerator {
	private List<Field> fieldList;
	
	private List<Field> childrenField;
	
	private String entityName;

	public static String directoryViewPages;
	
	public static String directoryAngularFiles;
	
	private ReflectionManager reflectionManager;
	
	private Class classClass;
	
	private DocType docType= DocType.HTML5;
	
	
	public HtmlGenerator(Class classClass)
	{
		this.classClass=classClass;
		this.reflectionManager = new ReflectionManager(classClass);
		this.entityName=reflectionManager.parseName();
		this.fieldList=reflectionManager.getFieldList();
		this.childrenField=reflectionManager.getChildrenFieldList();
		File file = new File(""); 
		directoryViewPages = file.getAbsolutePath()+"\\WebContent\\WEB-INF\\jsp\\";
		directoryAngularFiles=file.getAbsolutePath()+"\\src\\main\\webapp\\resources\\theme\\general_theme\\js\\angular\\";
	}
	
	
	private void includeScripts(HtmlCanvas html)
	{
		
		try {
			//js
			html
			.macros().javascript("http://code.jquery.com/jquery-1.9.1.js")
			.macros().javascript("http://code.jquery.com/ui/1.11.4/jquery-ui.js")
			.macros().javascript("http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular.js")
			.macros().javascript("http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-touch.js")
			.macros().javascript("http://ajax.googleapis.com/ajax/libs/angularjs/1.4.3/angular-animate.js")
			.macros().javascript("http://ui-grid.info/docs/grunt-scripts/csv.js")
			.macros().javascript("http://ui-grid.info/docs/grunt-scripts/pdfmake.js")
			.macros().javascript("http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js")
			.macros().javascript("http://ui-grid.info/release/ui-grid.js")
			.macros().javascript("../resources/general_theme/js/angular/"+entityName+".js")
			.macros().javascript("../resources/general_theme/js/date.js")
			.macros().javascript("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js");
			
			
			//css
			html.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "http://ui-grid.info/release/ui-grid.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../resources/general_theme/css/main.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../resources/general_theme/css/jquery-ui.css"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generateJSP() throws IllegalAccessException
	{
		HtmlCanvas html = new HtmlCanvas();
		HtmlAttributes htmlAttributes= new HtmlAttributes();
		JsGenerator jsGenerator = new JsGenerator(classClass, true, null, null);
		
		try {
			html.render(docType);
			html.
			html()
					.head()
						.title().content("test order");
						
						includeScripts(html);
						
						
						jsGenerator.saveJsToFile(directoryAngularFiles);
						//html.script().content(jsGenerator.buildJS(),false)
					html._head()
					.body(htmlAttributes.add("ng-app", Utility.getFirstLower(entityName)+"App"));
					AngularGenerator angularGenerator= new AngularGenerator(classClass, true,new ArrayList<Class>());
					angularGenerator.generateEntityView(html);
					/*for (Field field : childrenField)
					{
						HtmlGenerator childrenHtmlGenerator = new HtmlGenerator(field.getFieldClass(), false);
						childrenHtmlGenerator.generateEntityView(html);
						//generateEntityView(html, false,field.getName());
					}*/
					html._body()._html();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File myJsp=new File(directoryViewPages+reflectionManager.parseName(classClass.getName())+".jsp");
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
