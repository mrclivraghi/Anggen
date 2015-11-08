package it.polimi.generation;

import it.polimi.repository.security.UserRepository;
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
import java.util.Properties;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.dbcp.BasicDataSource;
import org.reflections.Reflections;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;
import org.springframework.beans.factory.annotation.Value;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;
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
	
	public static String applicationName;
	
	private Set<Class<?>> allClasses;
	
	private Reflections reflections;
	
	public List<Class> dependencyClass;
	
	public static HashMap<String, JDefinedClass> repositoryMap = new HashMap<String, JDefinedClass>();
	
	 @Value("${}")
	 public static  String driverClassName;
	 
	 @Value("${}")
	 public static  String jdbcString;
	 
	 @Value("${datasource.url}")
	 public static  String dbUrl;
	 
	 @Value("${datasource.port}")
	 public static  String dbPort;
	 
	 @Value("${datasource.db.name}")
	 public static  String dbName;
	 
	 @Value("${datasource.username}")
	 public static  String dbUser;
	 
	 @Value("${datasource.password}")
	 public static  String dbPassword;
	
	public Generator()
	{
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
			Generator.modelPackage=properties.getProperty("application.model.package");
			Generator.easyTreeMenu=Boolean.valueOf(properties.getProperty("application.menu.easytree.enable"));
			Generator.bootstrapMenu=Boolean.valueOf(properties.getProperty("application.menu.bootstrap.enable"));
			Generator.menuDirectory=properties.getProperty("application.menu.directory");
			Generator.angularDirectory=properties.getProperty("application.angular.directory");
			Generator.htmlDirectory=properties.getProperty("application.html.directory");
			Generator.applicationName=properties.getProperty("application.name");
			Generator.driverClassName=properties.getProperty("datasource.driver.class.name");
			Generator.jdbcString=properties.getProperty("datasource.jdbc");
			Generator.dbUrl=properties.getProperty("datasource.url");
			Generator.dbPort=properties.getProperty("datasource.port");
			Generator.dbName=properties.getProperty("datasource.db.name");
			Generator.dbUser=properties.getProperty("datasource.username");
			Generator.dbPassword=properties.getProperty("datasource.password");
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		reflections = new Reflections(Generator.modelPackage);
		 allClasses = reflections.getTypesAnnotatedWith(Entity.class);
		 dependencyClass = new ArrayList<Class>();
		
		
	}

	
	private void checkModel() throws Exception
	{
		for (Class myClass: allClasses)
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
		}
	}
	

	
		
	public void generate()
	{
			for (Class modelClass: allClasses)
			{
				RestGenerator restGenerator = new RestGenerator(modelClass);
				//restGenerator.generateRESTClasses(dependencyClass, true);
			}
			for (Class modelClass:dependencyClass)
			{
				RestGenerator restGenerator = new RestGenerator(modelClass);
				//restGenerator.generateRESTClasses(dependencyClass, false);
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
			if (Generator.bootstrapMenu)
				HtmlGenerator.GenerateMenu();
			else
			{
				if (Generator.easyTreeMenu)
					HtmlGenerator.GenerateEasyTreeMenu();
				else //DEFAULTS
					HtmlGenerator.GenerateMenu();
			}
	}
	
	public void saveEntities()
	{
		BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driverClassName);
	ds.setUrl(jdbcString+"://"+dbUrl+":"+dbPort+"/"+dbName);
	ds.setUsername(dbUser);
	ds.setPassword(dbPassword);
	ReflectionManager reflectionManager = new ReflectionManager(Generator.class);
	
	try {
		Connection connection=ds.getConnection();
		for (Class modelClass: allClasses)
		{
			PreparedStatement checkExisting = connection.prepareStatement("select entity_name from sso.entity where entity_name=?");
			checkExisting.setString(1, reflectionManager.parseName(modelClass.getName()));
			ResultSet rs = checkExisting.executeQuery();
			if (rs.next())
				continue;
			PreparedStatement stmt = connection.prepareStatement("insert into sso.entity (entity_name) values (?)");
			stmt.setString(1, reflectionManager.parseName(modelClass.getName()));
			stmt.executeUpdate();
			System.out.println("Inserted entity with name "+reflectionManager.parseName(modelClass.getName()));
		}
		
		connection.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public static void main(String[] args) {
		
		Generator generator = new Generator();
		try {
			generator.checkModel();
			generator.generate();
			generator.saveEntities();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
