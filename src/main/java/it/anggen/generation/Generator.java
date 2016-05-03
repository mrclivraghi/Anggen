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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.reflections.Reflections;
import org.rendersnake.HtmlAttributes;
import org.rendersnake.HtmlCanvas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

import io.swagger.models.properties.UUIDProperty;
/**
 * Main class that runs the generation of the files
 * @author Marco
 *
 */
@Service
public class Generator {
	
	
	@Value("${application.package.main}")
	private String mainPackage;
	
	@Value("${application.menu.bootstrap.enable}")
	private  Boolean bootstrapMenu=true;
	
	@Value("${application.menu.easytree.enable}")
	private  Boolean easyTreeMenu;
	
	@Value("${application.schema}")
	private String schema;
	
	@Value("${application.menu.name}")
	private String menuName;
	
	@Value("${application.menu.directory}")
	private  String menuDirectory;
	
	@Value("${application.angular.directory}")
	private String angularDirectory;
	
	@Value("${application.html.directory}")
	private String htmlDirectory;
	
	@Value("${application.upload.directory}")
	private String uploadDirectory;
	
	@Value("${application.name}")
	private String applicationName;

	@Value("${application.security}")
	private Boolean security;
	
	@Value("application.name")
	private String test;
	
	@Value("${generate.rest}")
	private Boolean generateRest;

	@Value("${generate.view}")
	private Boolean generateView;
	
	@Value("${application.rest.url}")
	private String restUrl;
	
	@Value("${application.cors.origin}")
	private String corsOrigin;
	
	@Value("${application.db}")
	private String database;
	
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
	
	public static String targetSchema;
	
	public static Boolean enableSecurity;
	
	public static String generateAngularDirectory;
	
	public static Boolean easyTreeMenuProperty;
	
	public static Boolean bootstrapMenuProperty;
	
	public static String corsOriginProperty;
	
	public static String htmlDirectoryProperty;
	
	public static String menuNameProperty;
	
	public static String menuDirectoryProperty;
	
	public static String restUrlProperty;
	
	public static String uploadDirectoryProperty;
	
	public static String databaseProperty;
	
	private Git git;
	private String generationBranchName;
	private Repository repo;
	
	public Generator()
	{
		
	}
	
	public Generator(Project project,List<EnumField> enumFieldList)
	{
		
		
	}
	
	//@Transactional
	private void init() throws Exception
	{
		Generator.appName=applicationName.toLowerCase();
		Generator.generatedPackage=mainPackage;
		Generator.targetSchema=schema;
		Generator.enableSecurity=security;
		Generator.generateAngularDirectory=angularDirectory;
		Generator.easyTreeMenuProperty=easyTreeMenu;
		Generator.bootstrapMenuProperty=bootstrapMenu;
		Generator.corsOriginProperty=corsOrigin;
		Generator.htmlDirectoryProperty=htmlDirectory;
		Generator.menuNameProperty=menuName;
		Generator.menuDirectoryProperty=menuDirectory;
		Generator.restUrlProperty=restUrl;
		Generator.uploadDirectoryProperty=uploadDirectory;
		Generator.databaseProperty=database;
		
		List<Project> projectList=projectRepository.findByName(applicationName);
		if (projectList.size()==0)
			throw new Exception();
		this.relationshipList=relationshipRepository.findByRelationshipIdAndNameAndPriorityAndRelationshipTypeAndAnnotationAndEntityAndEntityAndTab(null, null, null, null, null, null, null, null);
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

	//@Transactional
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
	
	private void initBranch()
	{
			
			File test = new File("");
			
			repo = null;
			try {
				repo = new FileRepositoryBuilder()
						.setGitDir(new File(test.getAbsolutePath()+"/.git"))
						.build();

			git = new Git(repo);
			
			// Get a reference
			Ref develop = repo.getRef(git.getRepository().getBranch());

			// Get the object the reference points to
			ObjectId developTip = develop.getObjectId();

			// Create a branch
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			generationBranchName="refs/heads/generation/gen_"+sdf.format(new Date());
			Ref testBranch= repo.getRef(generationBranchName);
			if (testBranch==null)
			{

				RefUpdate createBranch1 = repo.updateRef(generationBranchName);
				createBranch1.setNewObjectId(developTip);
				createBranch1.update();
				testBranch= repo.getRef(generationBranchName);
			}
			
				
				git.checkout().setName(testBranch.getName()).call();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RefAlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RefNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidRefNameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CheckoutConflictException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void closeBranch()
	{
		try {
			git.add().addFilepattern("test.txt").call();
			
			RevCommit lastCommit = git.commit().setMessage("new test txt").call();

			git.checkout().setName("refs/heads/feature/JGit").call();
			git.merge().setCommit(false).include(lastCommit).call();



			// Delete a branch
			RefUpdate deleteBranch1 = repo.updateRef(generationBranchName);
			deleteBranch1.setForceUpdate(true);
			deleteBranch1.delete();
			
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}
	
	@Transactional
	public void generate() throws Exception
	{
		
		initBranch();
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
			jsGenerator.generateMainApp();
			jsGenerator.generateServiceFile();
			jsGenerator.generateControllerFile();
			jsGenerator.generateDirectiveFile();
			jsGenerator.generateUtilityService();
			jsGenerator.generateNavbarDirective();
			jsGenerator.generateLoginDirective();
			jsGenerator.generateBowerFile();
			
			htmlGenerator.setDirectory();
			//htmlGenerator.generateTemplate();
			htmlGenerator.generateHomePage();
			htmlGenerator.generateMain();
			htmlGenerator.generateLogin();
			
			CssGenerator.generateMain(angularDirectory);
			CssGenerator.generateLoginSCSS(angularDirectory);
			for (Entity modelEntity: modelEntityList)
			{
				if (modelEntity.getDisableViewGeneration()) continue;
				htmlGenerator.init(modelEntity);
				try {
					htmlGenerator.generateSearchView();
					htmlGenerator.generateDetailView();
					htmlGenerator.generatePageContent();
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
		
		closeBranch();
	}

	private Boolean isAngGenSecurity(Entity entity)
	{
		if (appName.equals("serverTest"))
			return false;
		if (entity.getEntityGroup()==null)
			return true;
		if (!entity.getEntityGroup().getName().equals("security") && !entity.getEntityGroup().getName().equals("log"))
			return false;
		if (entity.getName().equals("restrictionField") || 
				entity.getName().equals("restrictionEntityGroup") || 
				entity.getName().equals("restrictionEntity") || 
				entity.getName().equals("user") || 
				entity.getName().equals("role") ||
				entity.getName().equals("logEntry"))
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
