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
import it.anggen.model.relationship.Relationship;
import it.anggen.reflection.EntityAttributeManager;
import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.repository.entity.EnumEntityRepository;
import it.anggen.repository.entity.ProjectRepository;
import it.anggen.repository.field.EnumFieldRepository;
import it.anggen.repository.relationship.RelationshipRepository;
import it.anggen.service.relationship.RelationshipServiceImpl;

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
import org.hibernate.loader.plan.build.internal.returns.EntityAttributeFetchImpl;
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
	
	private List<Relationship> relationshipList;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	EnumEntityRepository enumEntityRepository;
	
	@Autowired
	RelationshipRepository relationshipRepository;
	
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
	
	@Autowired
	JsGenerator jsGenerator;
	
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
		this.relationshipList=relationshipRepository.findByRelationshipIdAndPriorityAndNameAndRelationshipTypeAndAnnotationAndEntityAndEntityAndTab(null, null, null, null, null, null, null, null);
		this.project=projectList.get(0);
		this.entityGroupList=project.getEntityGroupList();
		this.enumEntityList=project.getEnumEntityList();
		this.modelEntityList=new ArrayList<Entity>();
		if (entityGroupList!=null)
		for (EntityGroup entityGroup: entityGroupList)
		{
			this.modelEntityList.addAll(entityGroup.getEntityList());
		}
		checkModel();
	}

	
	private void checkModel() throws Exception
	{
		
		for (Entity entity: modelEntityList)
		{
			EntityManager entityManager = new EntityManagerImpl(entity);
			if (entityManager.getKeyClass()==null)
					throw new Exception(entity.getName()+": there is no primary key");
				
			for (it.anggen.model.field.Field field: entity.getFieldList())
			{
				EntityAttributeManager entityAttributeManager = new EntityAttributeManager(field);
				
				if (entityAttributeManager.getPrimaryKey() && !field.getName().equals(Utility.getFirstLower(entity.getName())+"Id") )
					throw new Exception(entity.getName()+": primary key name is wrong. it's "+field.getName()+" instead of "+Utility.getFirstLower(entity.getName())+"Id");
				
				if (entityAttributeManager.getBetweenFilter() && !entityAttributeManager.getFieldTypeName().equals(null))
					throw new Exception(entity.getName()+": Between annotation is invalid for type "+entityAttributeManager.getFieldTypeName());
			
			}	
		}
		
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
			jsGenerator.generateServiceFile();
			jsGenerator.generateControllerFile();
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
	
	public List<Relationship> getRelationshipList()
	{
		return relationshipList;
	}

}
