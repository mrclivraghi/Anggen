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
import java.util.Set;

import org.rendersnake.DocType;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;
/**
 * Prepare the html template and fill it with angular code and html elements.
 * @author Marco
 *
 */
public class HtmlGenerator {
	private List<Field> fieldList;
	
	private List<Field> childrenField;
	
	private String entityName;

	private static String directoryViewPages;
	
	private static String directoryAngularFiles;
	
	private ReflectionManager reflectionManager;
	
	private Class classClass;
	
	private DocType docType= DocType.HTML5;
	
	public static final String modelPackage= "it.polimi.model";
	
	
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
	
	/**
	 * Include the functional js scripts
	 * @param html
	 */
	private void includeJavascriptScripts(HtmlCanvas html)
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
			.macros().javascript("../resources/general_theme/js/utility.js")
			.macros().javascript("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js")
			.macros().javascript("http://cdn.jsdelivr.net/alasql/0.2/alasql.min.js");
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void incluseCssFiles(HtmlCanvas html)
	{
		try {
			html.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "http://ui-grid.info/release/ui-grid.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../resources/general_theme/css/main.css"))
			.link((new HtmlAttributes()).add("rel","stylesheet").add("href", "../resources/general_theme/css/jquery-ui.css"))
			.link((new HtmlAttributes()).add("rel","import").add("href", "../resources/general_theme/static/menu.html"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Generate the main template of the page
	 * @throws IllegalAccessException
	 */
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
			.title().content(entityName);
			includeJavascriptScripts(html);
			incluseCssFiles(html);
			jsGenerator.saveJsToFile(directoryAngularFiles);
			html._head()
			.body(htmlAttributes.add("ng-app", Utility.getFirstLower(entityName)+"App"));
			AngularGenerator angularGenerator= new AngularGenerator(classClass, true,new ArrayList<Class>());
			angularGenerator.generateEntityView(html);
			html.script((new HtmlAttributes()).add("type", "text/javascript")).content("loadMenu();	activeMenu(\""+entityName+"\");",false);
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
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * Generate the html menu
	 */
	public static void GenerateMenu()
	{
		HtmlCanvas html = new HtmlCanvas();
		ReflectionManager reflectionManager = new ReflectionManager(Object.class);
		try {
			html.nav(CssGenerator.getNav())
			.div((new HtmlAttributes()).add("class", "container-fluid"))
			.div((new HtmlAttributes()).add("class", "navbar-header")) // start nav header
			.button((new HtmlAttributes()).add("type", "button").add("class", "navbar-toggle collapsed").add("data-toggle", "collapse").add("data-target", "#bs-example-navbar-collapse-1").add("aria-expanded", "false"))
			.span((new HtmlAttributes()).add("class", "sr-only"))
			.content("Toggle navigation")
			.span((new HtmlAttributes()).add("class", "icon-bar"))._span()
			.span((new HtmlAttributes()).add("class", "icon-bar"))._span()
			.span((new HtmlAttributes()).add("class", "icon-bar"))._span()
			._button()
			.a((new HtmlAttributes()).add("class", "navbar-brand").add("href", "#"))
			.content("Brand")
			._div()//end header
			.div((new HtmlAttributes()).add("class", "collapse navbar-collapse").add("id", "bs-example-navbar-collapse-1")) //start real nav menu
			.ul((new HtmlAttributes()).add("class", "nav navbar-nav"));
			List<String> packageList= ReflectionManager.getSubPackages(modelPackage);
			for (String myPackage: packageList)
			{
				html.li((new HtmlAttributes()).add("class", "dropdown"))
				.a((new HtmlAttributes()).add("href", "#").add("class", "dropdown-toggle").add("data-toggle", "dropdown").add("role", "button").add("aria-haspopup", "true").add("aria-expanded", "false"));
				HtmlCanvas caretHtml = new HtmlCanvas();
				
				caretHtml.span((new HtmlAttributes()).add("class", "caret"))
				._span();
				html.content(reflectionManager.parseName(myPackage)+caretHtml.toHtml(),false);
				Set<Class<?>> packageClassList = ReflectionManager.getClassInPackage(myPackage);
				html.ul((new HtmlAttributes()).add("class", "dropdown-menu"));
				for (Class myClass: packageClassList)
				{
					// todo put link
					html.li().a((new HtmlAttributes()).add("href", "../"+reflectionManager.parseName(myClass.getName())+"/")).content(reflectionManager.parseName(myClass.getName()))._li();
				}
				
				html._ul();
				html._li();
			}
			Set<Class<?>> packageClassList = ReflectionManager.getClassInPackage(modelPackage);
			for (Class theClass: packageClassList)
			{
				if (theClass.getPackage().getName().equals(modelPackage))
				{
					html.li().a((new HtmlAttributes()).add("href", "../"+reflectionManager.parseName(theClass.getName())+"/")).content(reflectionManager.parseName(theClass.getName()))._li();
				}
			}
			html._ul()
			._div() //end real nav menu
			._div()
			._nav();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File file = new File(""); 
		String directoryViewPages = file.getAbsolutePath()+"\\src\\main\\webapp\\resources\\theme\\general_theme\\static\\";
		File menuFile=new File(directoryViewPages+"menu.html");
		PrintWriter writer;
		try {
			System.out.println("Written "+menuFile.getAbsolutePath());
			writer = new PrintWriter(menuFile, "UTF-8");
			writer.write(html.toHtml());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
