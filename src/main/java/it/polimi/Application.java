package it.polimi;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Entity;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import it.polimi.boot.config.AppConfig;
import it.polimi.generation.Generator;
import it.polimi.utils.ReflectionManager;

import org.apache.commons.dbcp.BasicDataSource;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@RestController
public class Application extends SpringBootServletInitializer{

	 public static  String driverClassName;
	 public static  String jdbcString;
	 public static  String dbUrl;
	 public static  String dbPort;
	 public static  String dbName;
	 public static  String dbUser;
	 public static  String dbPassword;
	 public static  String modelPackage;
	
    @Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		Application.saveEntities(servletContext);
		super.onStartup(servletContext);
	}


	public static void main(String[] args) {
		Application.saveEntities(null);
        SpringApplication.run(Application.class, args);
    }
    
    
    @RequestMapping(value="/user")
    public Principal user(Principal user) {
    	System.out.println("user");
      return user;
    }
    
    public static void saveEntities(ServletContext servletContext)
    {
    	String filePath="src/main/resources/application.properties";
    	if (servletContext!=null)
    		filePath=servletContext.getRealPath("/")+"/WEB-INF/classes/application.properties";
    	File file = new File(filePath);
    	System.out.println(file.getAbsolutePath());
    	FileInputStream fileInput;
    	try {
    		fileInput = new FileInputStream(file);
    		Properties properties = new Properties();
    		properties.load(fileInput);
    		fileInput.close();

    		Application.driverClassName=properties.getProperty("datasource.driver.class.name");
    		Application.jdbcString=properties.getProperty("datasource.jdbc");
    		Application.dbUrl=properties.getProperty("datasource.url");
    		Application.dbPort=properties.getProperty("datasource.port");
    		Application.dbName=properties.getProperty("datasource.db.name");
    		Application.dbUser=properties.getProperty("datasource.username");
    		Application.dbPassword=properties.getProperty("datasource.password");
    		Application.modelPackage=properties.getProperty("application.model.package");

    		BasicDataSource ds = new BasicDataSource();
    		ds.setDriverClassName(driverClassName);
    		ds.setUrl(jdbcString+"://"+dbUrl+":"+dbPort+"/"+dbName);
    		ds.setUsername(dbUser);
    		ds.setPassword(dbPassword);

    		ReflectionManager reflectionManager = new ReflectionManager(Generator.class);
    		Reflections reflections = new Reflections(Application.modelPackage);
    		Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);

    		try {
    			Connection connection=ds.getConnection();
    			PreparedStatement adminRole= connection.prepareStatement("select role_id from sso.role where role='ADMIN'");
    			ResultSet rsRole = adminRole.executeQuery();
    			Integer roleAdminId=null;
    			if (rsRole.next())
    				roleAdminId=rsRole.getInt("role_id");
    			rsRole.close();
    			adminRole.close();
    			for (Class modelClass: allClasses)
    			{
    				PreparedStatement checkExisting = connection.prepareStatement("select entity_name from sso.entity where entity_name=?");
    				checkExisting.setString(1, reflectionManager.parseName(modelClass.getName()));
    				ResultSet rs = checkExisting.executeQuery();
    				if (rs.next())
    					continue;
    				PreparedStatement stmt = connection.prepareStatement("insert into sso.entity (entity_name) values (?)",Statement.RETURN_GENERATED_KEYS);
    				stmt.setString(1, reflectionManager.parseName(modelClass.getName()));
    				stmt.executeUpdate();
    				ResultSet keyEntity= stmt.getGeneratedKeys();
    				if (keyEntity.next() && roleAdminId!=null)
    				{
    					PreparedStatement associateWithAdmin = connection.prepareStatement("insert into sso.role_entity (role_id,entity_id) values (?,?)");
    					associateWithAdmin.setLong(1, roleAdminId);
    					associateWithAdmin.setLong(2, keyEntity.getLong(1));
    					associateWithAdmin.executeUpdate();
    				}
    				System.out.println("Inserted entity with name "+reflectionManager.parseName(modelClass.getName()));
    			}

    			connection.close();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	} catch (FileNotFoundException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

}