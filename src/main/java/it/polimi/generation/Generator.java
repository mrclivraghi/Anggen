package it.polimi.generation;

import it.polimi.utils.ReflectionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;

public class Generator {
	
	public static final String modelPackage= "it.polimi.model";
	
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
				.a((new HtmlAttributes()).add("href", "#").add("class", "dropdown-toggle").add("data-toggle", "dropdown").add("role", "button").add("aria-haspopup", "true").add("aria-expanded", "false"))
				.span((new HtmlAttributes()).add("class", "caret"))
				._span()
				.content(reflectionManager.parseName(myPackage));
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
		String directoryViewPages = file.getAbsolutePath()+"\\WebContent\\WEB-INF\\jsp\\";
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
	
	

	public static void main(String[] args) {

		Reflections reflections = new Reflections(modelPackage);
		Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);
		List<Class> dependencyClass = new ArrayList<Class>();
		
		for (Class modelClass: allClasses)
		{
			RestGenerator generator = new RestGenerator(modelClass);
			generator.generateRESTClasses(dependencyClass,true);
		}
		for (Class modelClass:dependencyClass)
		{
			RestGenerator generator = new RestGenerator(modelClass);
			generator.generateRESTClasses(dependencyClass,false);
		}
		
		for (Class modelClass: allClasses)
		{
			HtmlGenerator htmlGenerator = new HtmlGenerator(modelClass);
			try {
				htmlGenerator.generateJSP();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Generator.GenerateMenu();
		
	}

}
