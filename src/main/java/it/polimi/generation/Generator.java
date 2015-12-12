package it.polimi.generation;

import it.polimi.model.entity.Entity;
import it.polimi.model.entity.EntityGroup;
import it.polimi.model.entity.Project;
import it.polimi.model.field.EnumField;
import it.polimi.utils.Field;
import it.polimi.utils.ReflectionManager;
import it.polimi.utils.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.dbcp.BasicDataSource;
import org.reflections.Reflections;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;
/**
 * Main class that runs the generation of the files
 * @author Marco
 *
 */
@Service
public class Generator {
	
	public static String mainPackage;
	
	public static Boolean bootstrapMenu=true;
	
	public static Boolean easyTreeMenu;
	
	public static String schema;
	
	public static String menuName;
	
	public static String menuDirectory;
	
	public static String angularDirectory="/src/main/webapp/js/angular/";
	
	public static String htmlDirectory="/src/main/webapp/WEB-INF/jsp/";
	
	public static String uploadDirectory;
	
	public static String applicationName;

	public static Boolean security;
	
	private Project project;
	
	private List<Entity> modelEntityList;
	
	private List<EnumField> enumFieldList;
	
	private List<EntityGroup> entityGroupList;
	
	public Generator()
	{
		
	}
	
	public Generator(Project project,List<EnumField> enumFieldList)
	{
		this.project=project;
		this.entityGroupList=project.getEntityGroupList();
		this.enumFieldList=enumFieldList;
		this.modelEntityList=new ArrayList<Entity>();
		if (entityGroupList!=null)
		for (EntityGroup entityGroup: entityGroupList)
		{
			this.modelEntityList.addAll(entityGroup.getEntityList());
		}
		init();
	}
	
	private void init()
	{
		File file = new File("src/main/resources/application.properties");
		System.out.println(file.getAbsolutePath());
		FileInputStream fileInput;
		try {
			fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			//Generator.modelPackage=properties.getProperty("application.model.package");
			Generator.mainPackage="it.generated.";
			Generator.easyTreeMenu=Boolean.valueOf(properties.getProperty("application.menu.easytree.enable"));
			Generator.bootstrapMenu=Boolean.valueOf(properties.getProperty("application.menu.bootstrap.enable"));
			Generator.menuDirectory=properties.getProperty("application.menu.directory");
			Generator.angularDirectory=properties.getProperty("application.angular.directory");
			Generator.htmlDirectory=properties.getProperty("application.html.directory");
			Generator.applicationName=project.getName().toLowerCase();
			Generator.menuName=properties.getProperty("application.menu.name");
			Generator.schema=properties.getProperty("application.schema");
			Generator.uploadDirectory=properties.getProperty("application.upload.directory");
			Generator.security=Boolean.valueOf(properties.getProperty("application.security"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private void checkModel() throws Exception
	{
		/*for (Class myClass: allClasses)
		{
			if (myClass.getName().contains("Example"))
				System.out.println("");
			
			ReflectionManager reflectionManager = new ReflectionManager(myClass);
			if (reflectionManager.getKeyClass()== null)
				throw new Exception(myClass.getName()+": there is no primary key");
			
			for (Field field: reflectionManager.getFieldList())
			{
				if (ReflectionManager.hasId(field) && !field.getName().equals(reflectionManager.parseName()+"Id"))
					throw new Exception(myClass.getName()+": primary key name is wrong. it's "+field.getName()+" instead of "+reflectionManager.parseName()+"Id");
				if (ReflectionManager.isListField(field) && !(field.getName().equals(reflectionManager.parseName(field.getFieldClass().getName())+"")))
						throw new Exception(""+myClass.getName()+": list field name is wrong. It's "+field.getName()+"List instead of "+reflectionManager.parseName(field.getFieldClass().getName())+"List");
				if (ReflectionManager.hasBetween(field) && !reflectionManager.isKnownClass(field.getFieldClass()))
					throw new Exception(myClass.getName()+": Between annotation is invalid for type "+field.getFieldClass().getName());
				
			}
		}*/
	}
	
	public static JDefinedClass getJDefinedClass(Entity entity)
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			String thePackage="it.generated."+Generator.applicationName+".model."+entity.getEntityGroup().getName().toLowerCase()+".";
		//	if (entity.getName().endsWith("Repository"))
	//			thePackage=thePackage.replace(".model", ".repository.");
			myClass = codeModel._class(thePackage+Utility.getFirstUpper(entity.getName()), ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		return myClass;
	}
	public static JDefinedClass getJDefinedRepositoryClass(Entity entity)
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			String thePackage="it.generated."+Generator.applicationName+".repository."+entity.getEntityGroup().getName().toLowerCase()+".";
		//	if (entity.getName().endsWith("Repository"))
	//			thePackage=thePackage.replace(".model", ".repository.");
			myClass = codeModel._class(thePackage+Utility.getFirstUpper(entity.getName()+"Repository"), ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		return myClass;
	}
	public static JDefinedClass getJDefinedEnumFieldClass(EnumField enumField)
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			String thePackage="it.generated."+Generator.applicationName+".model.";
		//	if (entity.getName().endsWith("Repository"))
	//			thePackage=thePackage.replace(".model", ".repository.");
			myClass = codeModel._class(thePackage+Utility.getFirstUpper(enumField.getName()), ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		return myClass;
	}
	public static JType getJDefinedCustomClass(String fullName) {
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
	
	public void generate()
	{
		for (EnumField enumField: enumFieldList)
		{
			EnumClassGenerator enumClassGenerator = new EnumClassGenerator(enumField);
			enumClassGenerator.getModelClass();
		}
			for (Entity modelEntity: modelEntityList)
			{
				EntityGenerator entityGenerator = new EntityGenerator(modelEntity);
				entityGenerator.getModelClass();
			}
		
			for (Entity modelEntity: modelEntityList)
			{
				RestGenerator restGenerator = new RestGenerator(modelEntity);
				restGenerator.generateRESTClasses();
			}
			for (Entity modelEntity: modelEntityList)
			{
				HtmlGenerator htmlGenerator = new HtmlGenerator(modelEntity);
				try {
					htmlGenerator.generateJSP();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (Generator.bootstrapMenu)
				HtmlGenerator.GenerateMenu(entityGroupList);
			else
			{
				if (Generator.easyTreeMenu)
					HtmlGenerator.GenerateEasyTreeMenu(entityGroupList);
				else //DEFAULTS
					HtmlGenerator.GenerateMenu(entityGroupList);
			}
			WebappGenerator webappGenerator= new WebappGenerator();
			webappGenerator.generate();
	}

	
	
	
	

}
