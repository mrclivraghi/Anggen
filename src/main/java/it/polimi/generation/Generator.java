package it.polimi.generation;

import it.polimi.utils.ReflectionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;
/**
 * Main class that runs the generation of the files
 * @author Marco
 *
 */
public class Generator {
	
	public static String modelPackage;
	
	public static Boolean bootstrapMenu;
	
	public static Boolean easyTreeMenu;
	
	public static String menuDirectory;
	
	public static String angularDirectory;
	
	public static String htmlDirectory;
	
	private static void init()
	{
		File file = new File("src/main/resources/application.properties");
		System.out.println(file.getAbsolutePath());
		FileInputStream fileInput;
		try {
			fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			Generator.modelPackage=properties.getProperty("application.model.package");
			Generator.easyTreeMenu=Boolean.valueOf(properties.getProperty("application.menu.easytree.enable"));
			Generator.bootstrapMenu=Boolean.valueOf(properties.getProperty("application.menu.bootstrap.enable"));
			Generator.menuDirectory=properties.getProperty("application.menu.directory");
			Generator.angularDirectory=properties.getProperty("application.angular.directory");
			Generator.htmlDirectory=properties.getProperty("application.html.directory");
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Generator.init();
		
		Reflections reflections = new Reflections(Generator.modelPackage);
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
		/*
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
		if (Generator.bootstrapMenu)
			HtmlGenerator.GenerateMenu();
		else
		{
			if (Generator.easyTreeMenu)
				HtmlGenerator.GenerateEasyTreeMenu();
			else //DEFAULTS
				HtmlGenerator.GenerateMenu();
		}*/
	}

}
