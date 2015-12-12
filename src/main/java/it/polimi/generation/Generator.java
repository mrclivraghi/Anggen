package it.polimi.generation;

import it.polimi.model.entity.Entity;
import it.polimi.model.entity.EntityGroup;
import it.polimi.model.entity.Project;
import it.polimi.model.field.EnumField;
import it.polimi.repository.entity.ProjectRepository;
import it.polimi.repository.field.EnumFieldRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	@Value("${application.package.main}")
	public String mainPackage;
	
	@Value("${application.menu.bootstrap.enable}")
	public Boolean bootstrapMenu=true;
	
	@Value("${application.menu.easytree.enable}")
	public Boolean easyTreeMenu;
	
	@Value("${application.schema}")
	public String schema;
	
	@Value("${application.menu.name}")
	public String menuName;
	
	@Value("${application.menu.directory}")
	public  String menuDirectory;
	
	@Value("${application.angular.directory}")
	public String angularDirectory;
	
	@Value("${application.html.directory}")
	public String htmlDirectory;
	
	@Value("${application.upload.directory}")
	public String uploadDirectory;
	
	@Value("${application.name}")
	public String applicationName;

	@Value("${application.security}")
	public Boolean security;
	
	@Value("application.name")
	public String test;
	
	private Project project;
	
	private List<Entity> modelEntityList;
	
	private List<EnumField> enumFieldList;
	
	private List<EntityGroup> entityGroupList;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	EnumFieldRepository enumFieldRepository;
	
	@Autowired
	RestGenerator restGenerator;
	
	@Autowired
	EntityGenerator entityGenerator;
	
	@Autowired
	EnumClassGenerator enumClassGenerator;
	
	@Autowired
	HtmlGenerator htmlGenerator;
	
	@Autowired
	WebappGenerator webappGenerator;
	
	public static String appName;
	
	public Generator()
	{
		
	}
	
	public Generator(Project project,List<EnumField> enumFieldList)
	{
		
		
	}
	
	private void init() throws Exception
	{
		Generator.appName=applicationName;
		List<Project> projectList=projectRepository.findByName(applicationName);
		if (projectList.size()==0)
			throw new Exception();
		this.project=projectList.get(0);
		this.entityGroupList=project.getEntityGroupList();
		List<EnumField> enumFieldList = enumFieldRepository.findByEnumFieldIdAndNameAndEnumValueAndEntityAndAnnotationAndTab(null, null, null, null, null, null);
		this.enumFieldList=enumFieldList;
		this.modelEntityList=new ArrayList<Entity>();
		if (entityGroupList!=null)
		for (EntityGroup entityGroup: entityGroupList)
		{
			this.modelEntityList.addAll(entityGroup.getEntityList());
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
	
	
	
	public void generate() throws Exception
	{
		init();
		for (EnumField enumField: enumFieldList)
		{
			enumClassGenerator.init(enumField);
			enumClassGenerator.getModelClass();
		}
		for (Entity modelEntity: modelEntityList)
		{
			entityGenerator.init(modelEntity);
			entityGenerator.getModelClass();
		}

		for (Entity modelEntity: modelEntityList)
		{
			restGenerator.init(modelEntity);
			restGenerator.generateRESTClasses();
		}
		for (Entity modelEntity: modelEntityList)
		{
			htmlGenerator.init(modelEntity);
			try {
				htmlGenerator.generateJSP();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (bootstrapMenu)
			htmlGenerator.GenerateMenu(entityGroupList);
		else
		{
			if (easyTreeMenu)
				htmlGenerator.GenerateEasyTreeMenu(entityGroupList);
			else //DEFAULTS
				htmlGenerator.GenerateMenu(entityGroupList);
		}
		webappGenerator.generate();
	}

	
	
	
	

}
