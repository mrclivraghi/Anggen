package it.anggen.generation;

import it.anggen.utils.Field;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.generation.frontend.FrontHtmlGenerator;
import it.anggen.model.entity.Entity;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.entity.EnumEntity;
import it.anggen.model.entity.Project;
import it.anggen.model.field.EnumField;
import it.anggen.repository.entity.EnumEntityRepository;
import it.anggen.repository.entity.ProjectRepository;
import it.anggen.repository.field.EnumFieldRepository;

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
	
	@Value("${generate.rest}")
	private Boolean generateRest;

	@Value("${generate.view}")
	private Boolean generateView;
	
	private Project project;
	
	private List<Entity> modelEntityList;
	
	private List<EnumEntity> enumEntityList;
	
	private List<EntityGroup> entityGroupList;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	EnumEntityRepository enumEntityRepository;
	
	@Autowired
	RestGenerator restGenerator;
	
	@Autowired
	EntityGenerator entityGenerator;
	
	@Autowired
	EnumClassGenerator enumClassGenerator;
	
	@Autowired
	HtmlGenerator htmlGenerator;
	
	@Autowired
	FrontHtmlGenerator frontHtmlGenerator;
	
	@Autowired
	WebappGenerator webappGenerator;
	
	public static String appName;
	
	public static String generatedPackage;
	
	public Generator()
	{
		
	}
	
	public Generator(Project project,List<EnumField> enumFieldList)
	{
		
		
	}
	
	private void init() throws Exception
	{
		Generator.appName=applicationName;
		Generator.generatedPackage=mainPackage;
		List<Project> projectList=projectRepository.findByName(applicationName);
		if (projectList.size()==0)
			throw new Exception();
		this.project=projectList.get(0);
		this.entityGroupList=project.getEntityGroupList();
		this.enumEntityList=project.getEnumEntityList();
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
		if (generateRest)
		{
			for (EnumEntity enumEntity: enumEntityList)
			{
				enumClassGenerator.init(enumEntity);
				enumClassGenerator.getModelClass();
			}
			for (Entity modelEntity: modelEntityList)
			{
				if (!isAngGenSecurity(modelEntity))
				{
					entityGenerator.init(modelEntity);
					entityGenerator.getModelClass();
				}
			}

			for (Entity modelEntity: modelEntityList)
			{
				if (!isAngGenSecurity(modelEntity))
				{
					restGenerator.init(modelEntity);
					restGenerator.generateRESTClasses();
				}
			}
			webappGenerator.generate();
		}
		if (generateView)
		{
			for (Entity modelEntity: modelEntityList)
			{
				if (modelEntity.getDisableViewGeneration()) continue;
				htmlGenerator.init(modelEntity);
				try {
					htmlGenerator.generateJSP();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(" "+modelEntity.getName()+" "+modelEntity.getGenerateFrontEnd());
				if (modelEntity.getGenerateFrontEnd())
				{
					frontHtmlGenerator.init(modelEntity);
					frontHtmlGenerator.generateJSP();
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
		}
		
	}

	private Boolean isAngGenSecurity(Entity entity)
	{
		if (appName.equals("anggen"))
			return false;
		if (entity.getEntityGroup()==null)
			return true;
		if (!entity.getEntityGroup().getName().equals("security"))
			return false;
		if (entity.getName().equals("restrictionField") || 
				entity.getName().equals("restrictionEntityGroup") || 
				entity.getName().equals("restrictionEntity") || 
				entity.getName().equals("user") || 
				entity.getName().equals("role") )
			return true;
		return false;
	}
	
	
	public List<Entity> getEntityList()
	{
		return modelEntityList;
	}

}
