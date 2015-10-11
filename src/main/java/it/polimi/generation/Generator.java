package it.polimi.generation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;

public class Generator {

	public static void main(String[] args) {

		Reflections reflections = new Reflections("it.polimi.model");
		Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);
		List<Class> dependencyClass = new ArrayList<Class>();
		/*
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
		*/
		
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
		
	}

}
