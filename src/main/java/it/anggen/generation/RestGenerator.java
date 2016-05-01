package it.anggen.generation;

import it.anggen.model.RestrictionType;
import it.anggen.reflection.EntityAttributeManager;
import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.repository.relationship.RelationshipRepository;
import it.anggen.security.SecurityService;
import it.anggen.service.log.LogEntryService;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.Field;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.model.FieldType;
import it.anggen.model.LogType;
import it.anggen.model.OperationType;
import it.anggen.model.RelationshipType;
import it.anggen.model.SecurityType;
import it.anggen.model.entity.Entity;
import it.anggen.model.relationship.Relationship;
import it.anggen.model.security.Role;
import it.anggen.model.security.User;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codahale.metrics.annotation.Timed;
import com.sun.codemodel.ClassType;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JFieldRef;
import com.sun.codemodel.JFormatter;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;
import com.sun.codemodel.JTypeVar;
import com.sun.codemodel.JVar;

import io.swagger.annotations.ApiOperation;

/**
 * Generates the REST classes to manage the entity
 * @author Marco
 *
 */
@Service
public class RestGenerator {
	
	@Autowired
	private Generator generator;
	
	@Autowired
	private RelationshipRepository relationshipRepository;
	
	private String directory;

	private Entity entity;
	
	private String className;
	
	private String fullClassName;
	
	private String alias;
	
	private List<EntityAttribute> entityAttributeList;
	
	private FieldType keyClass;
	
	private JDefinedClass searchBeanClass;
	
	private JDefinedClass serviceInterfaceClass;
	
	private EntityManager entityManager;
	
	
	public RestGenerator()
	{
		
	}
	
	
	public void init(Entity entity)
	{
		this.entity=entity;
		this.fullClassName=Generator.generatedPackage+Generator.appName.replace("serverTest","anggen")+".model."+entity.getEntityGroup().getName().toLowerCase()+"."+Utility.getFirstUpper(entity.getName());
		File file = new File(""); 
		this.directory = file.getAbsolutePath()+"\\src\\main\\java";
		entityManager= new EntityManagerImpl(entity);
		this.className=Utility.getFirstUpper(entity.getName());
		this.alias=this.className.substring(0,1).toLowerCase();
		this.entityAttributeList=entityManager.getAllAttribute();
		keyClass= entityManager.getKeyClass();
		
	}
	
	/**
	 * Creates the search query with not null options
	 * @param method
	 * @return
	 */
	private String getSearchQuery(JMethod method)
	{
		String query="select "+alias+" from "+Utility.getFirstUpper(className)+" "+alias+ " where ";
		for (EntityAttribute entityAttribute: entityAttributeList)
		{
			
			String entityAttributeName= EntityAttributeManager.getInstance(entityAttribute).asField()!=null ? entityAttribute.getName() : (EntityAttributeManager.getInstance(entityAttribute).isRelationship()? EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName(): EntityAttributeManager.getInstance(entityAttribute).asEnumField().getName());
						
			if (EntityAttributeManager.getInstance(entityAttribute).getBetweenFilter())
			{
				JVar param = method.param(EntityAttributeManager.getInstance(entityAttribute).getRightParamClass(), entityAttributeName+"From");
				JAnnotationUse annotationParam= param.annotate(Param.class);
				annotationParam.param("value", entityAttributeName+"From");
			
				query=query+getFieldSearchQuery(entityAttribute, entityAttributeName+"From",alias+"."+entityAttributeName,"<=");
			
				param = method.param(EntityAttributeManager.getInstance(entityAttribute).getRightParamClass(), entityAttributeName+"To");
				annotationParam= param.annotate(Param.class);
				annotationParam.param("value", entityAttributeName+"To");
			
				query=query+getFieldSearchQuery(entityAttribute, entityAttributeName+"To",alias+"."+entityAttributeName,">=");
				
			}else
			{
				//JClass entityAttributeClass= EntityAttributeManagerImpl.getInstance(entityAttribute).asField()!=null ? entityAttribute.getRightParamClass() : (Generator.getJDefinedClass(entityAttributeName+"SearchBean"));
				JVar param = method.param(EntityAttributeManager.getInstance(entityAttribute).getRightParamClass(), entityAttributeName);
				JAnnotationUse annotationParam= param.annotate(Param.class);
				annotationParam.param("value", entityAttributeName);
				query=query+getFieldSearchQuery(entityAttribute, entityAttributeName,alias+"."+entityAttributeName,"=");
			}
			
			
			
			
		}
		for (EntityAttribute filterField: entityManager.getChildrenFilter())
		{
			String filterFieldName=(EntityAttributeManager.getInstance(filterField).getParent().getName())+Utility.getFirstUpper(filterField.getName());
			
			JVar param = method.param(EntityAttributeManager.getInstance(filterField).getRightParamClass(), filterFieldName);
			JAnnotationUse annotationParam= param.annotate(Param.class);
			annotationParam.param("value", filterFieldName);
			String hibernateField="";
			
			if (EntityAttributeManager.getInstance(filterField).getParent().getEntityId()!=entity.getEntityId())
			{// cerco quel campo in una lista
				String aliasFilterOwnerClass= (EntityAttributeManager.getInstance(filterField).getParent().getName()).substring(0, 1);
				query=query+" ( :"+filterFieldName+" is null or cast(:"+filterFieldName+" as string)='' or "+alias+" in (select "+aliasFilterOwnerClass+"."+Utility.getFirstLower(entity.getName())+" from "+Utility.getFirstUpper((EntityAttributeManager.getInstance(filterField).getParent().getName()))+" "+aliasFilterOwnerClass+" where "+aliasFilterOwnerClass+"."+filterField.getName()+"=cast(:"+filterFieldName+" as string))) and";
			}else // cerco quel campo nell'�entit� collegata
			{
				hibernateField=""+alias+"."+(EntityAttributeManager.getInstance(filterField).getParent().getName())+"."+filterField.getName();
				query=query+getFieldSearchQuery(filterField, filterFieldName,hibernateField,"=");
			}
		}
		query=query.substring(0,query.length()-3);
		return query;
	}
	
	
	private String getFieldSearchQuery(EntityAttribute entityAttribute, String fieldName,String hibernateField, String comparator)
	{
		String query="";
		if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.TIME)
		{
			if (Generator.databaseProperty.equals("mysql"))
				//date_format(o.cutoffTime,'%H:%i:%S')
				query= query + " (:"+fieldName+" is null or cast(:"+fieldName+" as string)"+comparator+"cast(date_format("+hibernateField+",'%H:%i:%S') as string)) and";
			else
				query= query + " (:"+fieldName+" is null or cast(:"+fieldName+" as string)"+comparator+"cast(date_trunc('seconds',"+hibernateField+") as string)) and";
		}
		else
		{
			if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.DATE)
			{
				query= query+" (:"+fieldName+" is null or cast(:"+fieldName+" as string)"+comparator+"cast(date("+hibernateField+") as string)) and";
			} else
			{
				if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null && (EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.STRING || EntityAttributeManager.getInstance(entityAttribute).asField().getFieldType()==FieldType.FILE  ))
				{
					query= query+" (:"+fieldName+" is null or :"+fieldName+"='' or cast(:"+fieldName+" as string)"+comparator+""+hibernateField+") and";
				} else
				{
					if (EntityAttributeManager.getInstance(entityAttribute).asRelationship()==null)
					{
						query=query+" (:"+fieldName+" is null or cast(:"+fieldName+" as string)"+comparator+"cast("+hibernateField+" as string)) and";

					} else
					{ // Entity or entity list!!!
						if (EntityAttributeManager.getInstance(entityAttribute).isList())
						{
							query=query+" (:"+fieldName+" in elements("+hibernateField+"List)  or :"+fieldName+" is null) and";
						}else
						{
							query=query+" (:"+fieldName+""+comparator+""+hibernateField+" or :"+fieldName+" is null) and";
						}

					}

				}

			}}
		
		/*if (field.getChildrenFilterList()!=null)
			for (Field filterField: field.getChildrenFilterList())
			{
				String filterFieldName=reflectionManager.parseName(filterField.getOwnerClass().getName())+Utility.getFirstUpper(filterField.getName());
				
				hibernateField="";
				
				if (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List"))
				{// cerco quel campo in una lista
					String aliasFilterOwnerClass= reflectionManager.parseName(filterField.getOwnerClass().getName()).substring(0, 1);
					
					query=query+" ( :"+filterFieldName+" is null or cast(:"+filterFieldName+" as string)='' or "+alias+" in (select "+aliasFilterOwnerClass+"."+reflectionManager.parseName(className)+" from "+Utility.getFirstUpper(reflectionManager.parseName(filterField.getOwnerClass().getName()))+" "+aliasFilterOwnerClass+" where "+aliasFilterOwnerClass+"."+filterField.getName()+"=cast(:"+filterFieldName+" as string))) and";
				}else // cerco quel campo nell'�entit� collegata
				{
					hibernateField=""+alias+"."+reflectionManager.parseName(filterField.getOwnerClass().getName())+"."+filterField.getName();
					query=query+getFieldSearchQuery(filterField, filterFieldName,hibernateField,"=");
				}
			}*/
		
		return query;
	}
	
	
	

	private void generateGetterAndSetter(JDefinedClass myClass, String varName, Class varClass)
	{
		JMethod getter= myClass.method(JMod.PUBLIC, varClass, "get"+Utility.getFirstUpper(varName));
		JBlock getterBlock = getter.body();
		getterBlock.directStatement("return this."+varName+";");
		
		JMethod setter= myClass.method(JMod.PUBLIC, void.class, "set"+Utility.getFirstUpper(varName));
		setter.param(varClass, varName);
		JBlock setterBlock = setter.body();
		setterBlock.directStatement("this."+varName+"="+varName+";");
		
	}
	private void generateGetterAndSetter(JDefinedClass myClass, String varName, JClass varClass)
	{
		JMethod getter= myClass.method(JMod.PUBLIC, varClass, "get"+Utility.getFirstUpper(varName));
		JBlock getterBlock = getter.body();
		getterBlock.directStatement("return this."+varName+";");
		
		JMethod setter= myClass.method(JMod.PUBLIC, void.class, "set"+Utility.getFirstUpper(varName));
		setter.param(varClass, varName);
		JBlock setterBlock = setter.body();
		setterBlock.directStatement("this."+varName+"="+varName+";");
		
	}
	
	
	public void generateSearchBean() {

			JCodeModel codeModel = new JCodeModel();
			JDefinedClass myClass = null;

			try {
				myClass = codeModel._class(""+fullClassName.replace(".model.", ".searchbean.")+"SearchBean", ClassType.CLASS);
				for (EntityAttribute entityAttribute: entityAttributeList)
				{
					String entityAttributeName= EntityAttributeManager.getInstance(entityAttribute).asField()!=null ? entityAttribute.getName() : (EntityAttributeManager.getInstance(entityAttribute).isRelationship()? EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName(): EntityAttributeManager.getInstance(entityAttribute).asEnumField().getName());
										
					if (EntityAttributeManager.getInstance(entityAttribute).getBetweenFilter())
					{
						JVar fieldFromVar = myClass.field(JMod.PUBLIC, EntityAttributeManager.getInstance(entityAttribute).getFieldClass(), entityAttributeName+"From");
						generateGetterAndSetter(myClass, entityAttributeName+"From", EntityAttributeManager.getInstance(entityAttribute).getFieldClass());
						JVar fieldToVar = myClass.field(JMod.PUBLIC, EntityAttributeManager.getInstance(entityAttribute).getFieldClass(), entityAttributeName+"To");
						generateGetterAndSetter(myClass, entityAttributeName+"To", EntityAttributeManager.getInstance(entityAttribute).getFieldClass());
					} else
					{
						
						if (EntityAttributeManager.getInstance(entityAttribute).asRelationship()!=null && EntityAttributeManager.getInstance(entityAttribute).isList())
						{
							JClass listClass = codeModel.ref(List.class).narrow(EntityAttributeManager.getInstance(entityAttribute).getFieldClass());
							JVar fieldVar = myClass.field(JMod.PUBLIC, listClass, EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName()+"List");
							generateGetterAndSetter(myClass, EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName()+"List", listClass);



						}else
						{
							JVar fieldVar = myClass.field(JMod.PUBLIC, EntityAttributeManager.getInstance(entityAttribute).getFieldClass(), entityAttributeName);
							generateGetterAndSetter(myClass, entityAttributeName, EntityAttributeManager.getInstance(entityAttribute).getFieldClass());
						}
						/*//filter modification
						if (EntityAttributeManagerImpl.getInstance(entityAttribute).asRelationship()!=null)
						{
							entityManager.addChildrenFilter(entityAttribute);
							for (Field filterField: entityAttribute.getChildrenFilterList())
							{
								String filterFieldName=entityManager.parseName(filterField.getOwnerClass().getName())+Utility.getFirstUpper(filterField.getName());
								JVar fieldVar = myClass.field(JMod.PUBLIC, filterField.getFieldClass(), filterFieldName);
								generateGetterAndSetter(myClass, filterFieldName, filterField.getFieldClass());
							}
							
						}*/
					}
				}
				for (EntityAttribute entityAttribute: entityManager.getChildrenFilter())
				{
					String filterFieldName=EntityAttributeManager.getInstance(entityAttribute).getParent().getName()+Utility.getFirstUpper(entityAttribute.getName());
					JVar fieldVar = myClass.field(JMod.PUBLIC, EntityAttributeManager.getInstance(entityAttribute).getFieldClass(), filterFieldName);
					generateGetterAndSetter(myClass, filterFieldName, EntityAttributeManager.getInstance(entityAttribute).getFieldClass());
				
				}
				saveFile(codeModel);
				searchBeanClass=myClass;
			} catch (JClassAlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	}
	
	
	
	
	/**
	 * Main method that generates the classes
	 * @param dependencyEntities
	 * @param jumpDependency
	 */
	public void generateRESTClasses()
	{
		System.out.println("working for "+entity.getName());
		if (entity.getName().equals("logEntry"))
			return;
		generateRepository();
		generateSearchBean();
		generateServiceInterface();
		generateServiceImpl();
		generateController();
	}
	
	/**
	 * Generate the repository class
	 * @return
	 */
	public String generateRepository(){
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		String searchMethod="";
		try {
			myClass = codeModel._class(ReflectionManager.getJDefinedRepositoryClass(entity).fullName(), ClassType.INTERFACE);
			JClass extendedClass = codeModel.ref(JpaRepository.class).narrow(ReflectionManager.getJDefinedClass(entity)).narrow(EntityAttributeManager.getInstance(null).getFieldTypeClass(keyClass));//,codeModel._ref(EntityAttributeManagerImpl.getInstance(null).getFieldTypeClass(keyClass))); //.narrow(ciao,EntityAttributeManagerImpl.getInstance(null).getFieldTypeClass(keyClass));
			myClass._extends(extendedClass);
			myClass.annotate(Repository.class);
			JClass listClass=codeModel.ref(List.class).narrow(ReflectionManager.getJDefinedClass(entity));
			searchMethod="findBy";
			//find all
			JMethod findAllMethod=myClass.method(JMod.PUBLIC, listClass, "findAll");
			JAnnotationUse annotationQueryFindAll= findAllMethod.annotate(Query.class);
			annotationQueryFindAll.param("value", "select "+alias+" from "+Utility.getFirstUpper(entity.getName())+" "+alias);
			
			for (EntityAttribute entityAttribute: entityAttributeList)
			{
				if (EntityAttributeManager.getInstance(entityAttribute).asField()!=null)
				{
					JMethod method=myClass.method(JMod.PUBLIC, listClass, "findBy"+entityAttribute.getName().replaceFirst(entityAttribute.getName().substring(0, 1), entityAttribute.getName().substring(0, 1).toUpperCase()));
					method.param(EntityAttributeManager.getInstance(entityAttribute).getRightParamClass(), entityAttribute.getName());
				} else
				{
					if (EntityAttributeManager.getInstance(entityAttribute).isEnumField())
					{
						JMethod method= myClass.method(JMod.PUBLIC, listClass, "findBy"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asEnumField().getName()));
						method.param(EntityAttributeManager.getInstance(entityAttribute).getRightParamClass(), EntityAttributeManager.getInstance(entityAttribute).asEnumField().getName());
						
					}else
					if (!EntityAttributeManager.getInstance(entityAttribute).isList())
					{ // find by entity
						JMethod method= myClass.method(JMod.PUBLIC, listClass, "findBy"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName()));
						method.param(ReflectionManager.getJDefinedClass(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget()), EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName());
						
					}
				}
				//if (ReflectionManager.hasBetween(field))
				//{
					//searchMethod=searchMethod+"GreaterThan"+Utility.getFirstUpper(field.getName())+"FromAndLessThan"+Utility.getFirstUpper(field.getName())+"ToAnd";
				//}
				//searchMethod=searchMethod+Utility.getFirstUpper(field.getName())+"And";
				
				//TODO childre filter
				//entityManager.addChildrenFilter(entityAttribute);
			}
			searchMethod=getRepositorySearchMethod();
			if (entityAttributeList.size()>1)
			{
				JMethod method=myClass.method(JMod.PUBLIC, listClass, searchMethod);
				String alias=className.substring(0, 1).toLowerCase();
				String query=getSearchQuery(method);
				JAnnotationUse annotationQuery= method.annotate(Query.class);
				annotationQuery.param("value", query);
			}
			
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		saveFile(codeModel);
		return searchMethod;
	}
	
	
	
	
	/**
	 * Generates the service interface
	 * @throws ClassNotFoundException 
	 */
	public void generateServiceInterface()
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			myClass = codeModel._class(""+fullClassName.replace(".model.", ".service.")+"Service", ClassType.INTERFACE);
			JClass listClass = codeModel.ref(List.class).narrow(ReflectionManager.getJDefinedClass(entity));
			JMethod findById = myClass.method(JMod.PUBLIC, listClass, "findById");
			String lowerClass= className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
			findById.param(EntityAttributeManager.getInstance(null).getFieldTypeClass(keyClass),className+"Id");
			JMethod findLike=myClass.method(JMod.PUBLIC, listClass, "find");
			findLike.param(searchBeanClass, className);
			JMethod deleteById = myClass.method(JMod.PUBLIC, void.class, "deleteById");
			deleteById.param(EntityAttributeManager.getInstance(null).getFieldTypeClass(keyClass), className+"Id");
			JMethod insert= myClass.method(JMod.PUBLIC, ReflectionManager.getJDefinedClass(entity), "insert");
			insert.param(ReflectionManager.getJDefinedClass(entity), className);
			JMethod update= myClass.method(JMod.PUBLIC, ReflectionManager.getJDefinedClass(entity), "update");
			update.param(ReflectionManager.getJDefinedClass(entity), className);
			
			JClass pageClass = codeModel.ref(Page.class).narrow(ReflectionManager.getJDefinedClass(entity));
			
			
			JMethod findByPage= myClass.method(JMod.PUBLIC,pageClass, "findByPage");
			JVar param=findByPage.param(Integer.class, "pageNumber");
			param.annotate(PathVariable.class);
			
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		saveFile(codeModel);

		serviceInterfaceClass=myClass;
	}
	
	private String getRepositorySearchMethod()
	{
		String searchMethod="findBy";
		for (EntityAttribute entityAttribute: entityAttributeList)
		{
			String entityAttributeName= EntityAttributeManager.getInstance(entityAttribute).asField()!=null ? entityAttribute.getName() : (EntityAttributeManager.getInstance(entityAttribute).isRelationship()? EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName(): EntityAttributeManager.getInstance(entityAttribute).asEnumField().getName());
			if (EntityAttributeManager.getInstance(entityAttribute).getBetweenFilter())
			{
				searchMethod=searchMethod+"GreaterThan"+Utility.getFirstUpper(entityAttributeName)+"FromAndLessThan"+Utility.getFirstUpper(entityAttributeName)+"ToAnd";
			} else
			searchMethod=searchMethod+Utility.getFirstUpper(entityAttributeName)+"And";
		}
		for(EntityAttribute entityAttribute: entityManager.getChildrenFilter())
		{
			searchMethod=searchMethod+Utility.getFirstUpper(entityAttribute.getName())+"And";
		}
		searchMethod=searchMethod.substring(0,searchMethod.length()-3);
		return searchMethod;
	}
	
	
	/**
	 * Generates the serviceImpl class
	 * 
	 * @param interfaceClass
	 * @param repositoryClass
	 * @param searchMethod
	 * @throws ClassNotFoundException 
	 */
	public void generateServiceImpl()
	{
		String searchMethod=getRepositorySearchMethod();
		
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			myClass = codeModel._class(""+fullClassName.replace(".model.", ".service.")+"ServiceImpl", ClassType.CLASS);
			myClass._implements(serviceInterfaceClass);
			myClass.annotate(Service.class);
			JClass listClass = codeModel.ref(List.class).narrow(ReflectionManager.getJDefinedClass(entity));
			JClass pageClass = codeModel.ref(Page.class).narrow(ReflectionManager.getJDefinedClass(entity));
			String lowerClass= className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
			JDefinedClass repositoryClass= ReflectionManager.getJDefinedRepositoryClass(entity);
			JVar repository = myClass.field(JMod.PUBLIC, repositoryClass, lowerClass+"Repository");
			repository.annotate(Autowired.class);
			for (EntityAttribute entityAttribute: entityAttributeList)
			{
				if (EntityAttributeManager.getInstance(entityAttribute).asRelationship()!=null && EntityAttributeManager.getInstance(entityAttribute).isList())
				{
					JVar fieldListRepository = myClass.field(JMod.PUBLIC, ReflectionManager.getJDefinedRepositoryClass(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget()), entityAttribute.getName()+"Repository");
					fieldListRepository.annotate(Autowired.class);
				}
			}
			
			JVar pageSize = myClass.field(JMod.PRIVATE+JMod.STATIC, Integer.class, "PAGE_SIZE");
			pageSize.init(JExpr.direct("5"));
			
			JMethod findById = myClass.method(JMod.PUBLIC, listClass, "findById");
			findById.annotate(Override.class);
			findById.param(EntityAttributeManager.getInstance(null).getFieldTypeClass(keyClass),lowerClass+"Id");
			JBlock findByIdBlock= findById.body();
			findByIdBlock.directStatement("return "+lowerClass+"Repository.findBy"+Utility.getFirstUpper(className)+"Id("+lowerClass+"Id);");
			
			JMethod findByPage = myClass.method(JMod.PUBLIC, pageClass, "findByPage");
			findByPage.annotate(Override.class);
			findByPage.param(Integer.class,"pageNumber");
			JBlock findByPageBlock= findByPage.body();
			findByPageBlock.directStatement(PageRequest.class.getName()+" pageRequest = new "+PageRequest.class.getName()+"(pageNumber - 1, PAGE_SIZE, "+Sort.class.getName()+".Direction.DESC, \""+lowerClass+"Id\");");
			findByPageBlock.directStatement("return "+lowerClass+"Repository.findAll(pageRequest);");
			
			
			
			//search
			JMethod findLike=myClass.method(JMod.PUBLIC, listClass, "find");
			findLike.annotate(Override.class);
			findLike.param(searchBeanClass, lowerClass);
			JBlock findLikeBlock= findLike.body();
			findLikeBlock.directStatement("return "+lowerClass+"Repository."+searchMethod+"("+entityManager.getAllParam()+");");
			//delete
			JMethod deleteById = myClass.method(JMod.PUBLIC, void.class, "deleteById");
			deleteById.annotate(Override.class);
			deleteById.param(EntityAttributeManager.getInstance(null).getFieldTypeClass(keyClass), lowerClass+"Id");
			JBlock deleteBlock= deleteById.body();
			deleteBlock.directStatement(""+lowerClass+"Repository.delete("+lowerClass+"Id);");
			deleteBlock.directStatement("return;");
			
			//insert
			JMethod insert= myClass.method(JMod.PUBLIC, ReflectionManager.getJDefinedClass(entity), "insert");
			insert.annotate(Override.class);
			insert.param(ReflectionManager.getJDefinedClass(entity), lowerClass);
			JBlock insertBlock= insert.body();
			insertBlock.directStatement("return "+lowerClass+"Repository.save("+lowerClass+");");
			//update
			JMethod update= myClass.method(JMod.PUBLIC, ReflectionManager.getJDefinedClass(entity), "update");
			update.annotate(Override.class);
			update.annotate(Transactional.class);
			update.param(ReflectionManager.getJDefinedClass(entity), lowerClass);
			JBlock updateBlock= update.body();
			for (EntityAttribute entityAttribute: entityAttributeList)
			{
				if (EntityAttributeManager.getInstance(entityAttribute).asRelationship()!=null)
				if (EntityAttributeManager.getInstance(entityAttribute).isList())
				{
					EntityManager entityManager = new EntityManagerImpl(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget());
					updateBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName())+"List()!=null)");
					
					String childrenEntityName=EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget().getName();
					if (childrenEntityName.equals(entity.getName()))
						childrenEntityName=childrenEntityName+"1";
					
					List<Relationship> directRelationshipList=relationshipRepository.findByRelationshipIdAndNameAndPriorityAndRelationshipTypeAndAnnotationAndEntityAndEntityAndTab(null, null, null, null, null, EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget(), entity, null);
					String directName=Utility.getFirstUpper(className);
					if (directRelationshipList.size()>0)
					{
						directName=Utility.getFirstUpper(directRelationshipList.get(0).getName());
					}
					
					
					List<Relationship> inverseRelationshipList=relationshipRepository.findByRelationshipIdAndNameAndPriorityAndRelationshipTypeAndAnnotationAndEntityAndEntityAndTab(null, null, null, null, null, entity, EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget(), null);
					String inverseName=Utility.getFirstUpper(className);
					if (inverseRelationshipList.size()>0)
					{
						inverseName=Utility.getFirstUpper(inverseRelationshipList.get(0).getName());
					}
					
					
					updateBlock.directStatement("for ("+ReflectionManager.getJDefinedClass(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget()).fullName()+" "+childrenEntityName+": "+lowerClass+".get"+directName+"List())");
					updateBlock.directStatement("{");
					if (EntityAttributeManager.getInstance(entityAttribute).asRelationship().getRelationshipType()!=RelationshipType.ONE_TO_ONE && EntityAttributeManager.getInstance(entityAttribute).asRelationship().getRelationshipType()!=RelationshipType.ONE_TO_MANY)
					{
						updateBlock.directStatement(ReflectionManager.getJDefinedClass(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget()).fullName()+" saved"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget().getName())+" = "+entityAttribute.getName()+"Repository.findOne("+childrenEntityName+".get"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget().getName())+"Id());");
						updateBlock.directStatement("Boolean found=false; ");
						updateBlock.directStatement("for ("+ReflectionManager.getJDefinedClass(entity).fullName()+" temp"+Utility.getFirstUpper(className)+" : saved"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget().getName())+".get"+inverseName+"List())");
						updateBlock.directStatement("{");
						updateBlock.directStatement("if (temp"+Utility.getFirstUpper(className)+".get"+Utility.getFirstUpper(className)+"Id().equals("+lowerClass+".get"+Utility.getFirstUpper(className)+"Id()))");
						updateBlock.directStatement("{");
						updateBlock.directStatement("found=true;");
						updateBlock.directStatement("break;");
						updateBlock.directStatement("}");
						updateBlock.directStatement("}");
						updateBlock.directStatement("if (!found)");
						updateBlock.directStatement("saved"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget().getName())+".get"+inverseName+"List().add("+lowerClass+");");
						
					} else
					{
						
						
						
						updateBlock.directStatement(childrenEntityName+".set"+inverseName+"("+lowerClass+");");
					}
					updateBlock.directStatement("}");
				} 
			}
			updateBlock.directStatement(ReflectionManager.getJDefinedClass(entity).fullName()+" returned"+Utility.getFirstUpper(className)+"="+lowerClass+"Repository.save("+lowerClass+");");
			for (EntityAttribute entityAttribute: entityAttributeList)
			{
				if (EntityAttributeManager.getInstance(entityAttribute).asRelationship()!=null)
				{
					EntityManager fieldEntityManager = new EntityManagerImpl(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget());
					if (!EntityAttributeManager.getInstance(entityAttribute).isList() && fieldEntityManager.containFieldOfEntity(entity))			
					{
						updateBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName())+"()!=null)");
						updateBlock.directStatement("{");
						updateBlock.directStatement("List<"+ReflectionManager.getJDefinedClass(entity).fullName()+"> "+entity.getName()+"List = "+lowerClass+"Repository.findBy"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName())+"( "+lowerClass+".get"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName())+"());");
						updateBlock.directStatement("if (!"+lowerClass+"List.contains(returned"+Utility.getFirstUpper(className)+"))");
						updateBlock.directStatement(""+lowerClass+"List.add(returned"+Utility.getFirstUpper(className)+");");
						
						List<Relationship> inverseRelationship=relationshipRepository.findByRelationshipIdAndNameAndPriorityAndRelationshipTypeAndAnnotationAndEntityAndEntityAndTab(null, null, null, RelationshipType.ONE_TO_MANY.getValue(), null, entity, EntityAttributeManager.getInstance(entityAttribute).asRelationship().getEntityTarget(), null);
						String inverseName=Utility.getFirstUpper(className);
						if (inverseRelationship.size()>0)
						{
							inverseName=Utility.getFirstUpper(inverseRelationship.get(0).getName());
						}
						
						updateBlock.directStatement("returned"+Utility.getFirstUpper(className)+".get"+Utility.getFirstUpper(EntityAttributeManager.getInstance(entityAttribute).asRelationship().getName())+"().set"+inverseName+"List("+lowerClass+"List);");
						updateBlock.directStatement("}");
					}
				}
			}
			
			updateBlock.directStatement(" return returned"+Utility.getFirstUpper(className)+";");
				
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		saveFile(codeModel);
	
		//serviceImplClass=myClass;
	}
	/**
	 * Generates the controller class
	 * @param serviceClass
	 * @throws ClassNotFoundException 
	 */
	public void generateController()
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		String response="ResponseEntity.ok()";
		
		try {
			myClass = codeModel._class(""+fullClassName.replace(".model.", ".controller.")+"Controller", ClassType.CLASS);
			JClass listClass = codeModel.ref(List.class).narrow(ReflectionManager.getJDefinedClass(entity));
			String lowerClass= className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
			myClass.annotate(RestController.class);
			JAnnotationUse requestMappingClass = myClass.annotate(RequestMapping.class);
			requestMappingClass.param("value", "/"+lowerClass);
			//declare service
			JVar repository = myClass.field(JMod.PRIVATE, serviceInterfaceClass, lowerClass+"Service");
			repository.annotate(Autowired.class);
			JVar securityService= myClass.field(JMod.PRIVATE,SecurityService.class, "securityService");
			securityService.annotate(Autowired.class);
			
			if (!fullClassName.contains("LogEntry"))
			{
				JVar logEntryService= myClass.field(JMod.PRIVATE,LogEntryService.class, "logEntryService");
				logEntryService.annotate(Autowired.class);
			}
			
			if (entity.getEntityGroup()!=null && !entity.getEntityGroup().getName().equals("restrictiondata") && entity.getEnableRestrictionData())
			{
				String restrictionDataRepositoryName=ReflectionManager.getJDefinedClass(entity).fullName().replaceAll("."+Utility.getFirstUpper(entity.getName()),".Restriction"+Utility.getFirstUpper(entity.getName()) ).replaceAll("."+entity.getEntityGroup().getName()+".", ".restrictiondata.").replaceAll(".model.", ".repository.")+"Repository";
				JVar repositoryRestrictionData = myClass.field(JMod.PRIVATE, ReflectionManager.getJDefinedCustomClass(restrictionDataRepositoryName), "restriction"+Utility.getFirstUpper(entity.getName())+"Repository");
				repositoryRestrictionData.annotate(Autowired.class);
			}
			
			JClass factory = codeModel.directClass("org.slf4j.LoggerFactory");
			JVar log = myClass.field(JMod.PRIVATE+JMod.STATIC+JMod.FINAL, Logger.class, "log");
			JClass jClassClass =ReflectionManager.getJDefinedClass(entity);
			log.init(factory.staticInvoke("getLogger").arg(jClassClass.dotclass()));
			//log.assign(factory.staticInvoke("getLogger"));
			JVar securityEnable = myClass.field(JMod.PRIVATE, Boolean.class, "securityEnabled");
			JAnnotationUse valueSecurityEnable= securityEnable.annotate(Value.class);
			valueSecurityEnable.param("value", "${application.security}");
			
			/*if (!entity.getDisableViewGeneration())
			{

				//manage
				JMethod manage = myClass.method(JMod.PUBLIC, String.class, "manage");
				manage.annotate(Timed.class);
				JAnnotationUse requestMappingManage = manage.annotate(RequestMapping.class);
				requestMappingManage.param("method", RequestMethod.GET);
				JBlock manageBlock = manage.body();
				String check="";
				if (entity.getSecurityType()==null || entity.getSecurityType()==SecurityType.ACCESS_WITH_PERMISSION)
					check="if (securityEnabled && !securityService.hasPermission("+ReflectionManager.getJDefinedClass(entity).fullName()+".staticEntityId, "+RestrictionType.class.getName()+"."+RestrictionType.SEARCH.toString()+")) \n";
				else
					if (entity.getSecurityType()==SecurityType.BLOCK_WITH_RESTRICTION)
						check="if (securityEnabled && securityService.hasRestriction("+ReflectionManager.getJDefinedClass(entity).fullName()+".staticEntityId, "+RestrictionType.class.getName()+"."+RestrictionType.SEARCH.toString()+")) \n";

				check+="return \"forbidden\"; \n";

				manageBlock.directStatement(check);

				manageBlock.directStatement("return \""+lowerClass+"\";");
				
				if (entity.getGenerateFrontEnd())
				{
					JMethod manageFront = myClass.method(JMod.PUBLIC, String.class, "manageFront");
					manageFront.annotate(Timed.class);
					JAnnotationUse requestMappingManageFront = manageFront.annotate(RequestMapping.class);
					requestMappingManageFront.param("method", RequestMethod.GET);
					requestMappingManageFront.param("value", "/front");
					JBlock manageFrontBlock = manageFront.body();
					manageFrontBlock.directStatement(check);

					manageFrontBlock.directStatement("return \""+lowerClass+"-front\";");
				}
				

			}*/
			
			//getpage

			JMethod getPage = myClass.method(JMod.PUBLIC, ResponseEntity.class, "findPage");
			annotateAsSwaggerOperation(getPage, "Return a page of "+entity.getName(), "Return a single page of "+entity.getName(), ReflectionManager.getJDefinedClass(entity), "List");
			getPage.annotate(Timed.class);
			JAnnotationUse reqMapping = getPage.annotate(RequestMapping.class);
			reqMapping.param("value", "/pages/{pageNumber}");
			reqMapping.param("method", RequestMethod.GET);
			getPage.annotate(ResponseBody.class);
			
			JVar param = getPage.param(Integer.class, "pageNumber");
			param.annotate(PathVariable.class);
			JBlock getPageBlock = getPage.body();
			getPageBlock.directStatement(Page.class.getName()+"<"+ReflectionManager.getJDefinedClass(entity).fullName()+"> page = "+lowerClass+"Service.findByPage(pageNumber);");
			getPageBlock.directStatement("getRightMapping(page.getContent());");
			getPageBlock.directStatement("return ResponseEntity.ok().body(page);");
			
			
			
			//search
			JMethod search = myClass.method(JMod.PUBLIC, ResponseEntity.class, "search");
			annotateAsSwaggerOperation(search, "Return a list of "+entity.getName(), "Return a list of "+entity.getName()+" based on the search bean requested", ReflectionManager.getJDefinedClass(entity), "List");
			
			search.annotate(Timed.class);
			
			search.annotate(ResponseBody.class);
			JAnnotationUse requestMappingSearch = search.annotate(RequestMapping.class);
			requestMappingSearch.param("value", "/search");
			requestMappingSearch.param("method",RequestMethod.POST);
			JVar orderParam= search.param(searchBeanClass,lowerClass);
			orderParam.annotate(RequestBody.class);
			JBlock searchBlock= search.body();
			searchBlock.directStatement(addSecurityCheck(RestrictionType.SEARCH));
			
			JVar entityList= searchBlock.decl(listClass, lowerClass+"List");
			// log.info("Searching mountain like {}",mountain);
			searchBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(lowerClass)+"Id()!=null)");
			searchBlock.directStatement(" log.info(\"Searching "+lowerClass+" like {}\","+entityManager.getDescription(true)+");");
			
			searchBlock.directStatement("logEntryService.addLogEntry( \"Searching entity like \"+"+entityManager.getDescription(true) +",");
			searchBlock.directStatement(LogType.class.getName()+".INFO, "+OperationType.class.getName()+".SEARCH_ENTITY, "+ReflectionManager.getJDefinedClass(entity).fullName()+".staticEntityId, securityService.getLoggedUser(),log);");
       
			
			searchBlock.directStatement(""+lowerClass+"List="+lowerClass+"Service.find("+lowerClass+");");
			searchBlock.directStatement("getSecurityMapping("+lowerClass+"List);");
			searchBlock.directStatement("getRightMapping("+lowerClass+"List);");
			searchBlock.directStatement(" log.info(\"Search: returning {} "+lowerClass+".\","+lowerClass+"List.size());");
			searchBlock.directStatement("return "+response+".body("+lowerClass+"List);");
			//getById  
			JMethod getById=myClass.method(JMod.PUBLIC, ResponseEntity.class, "get"+className+"ById");
			annotateAsSwaggerOperation(getById, "Return a the "+entity.getName()+" identified by the given id", "Return a the "+entity.getName()+" identified by the given id", ReflectionManager.getJDefinedClass(entity), "List");
			getById.annotate(Timed.class);
			
			getById.annotate(ResponseBody.class);
			JAnnotationUse requestMappingGetById = getById.annotate(RequestMapping.class);
			requestMappingGetById.param("value", "/{"+lowerClass+"Id}");
			requestMappingGetById.param("method",RequestMethod.GET);
			orderParam= getById.param(String.class,lowerClass+"Id");
			orderParam.annotate(PathVariable.class);
			JBlock getByIdBlock= getById.body();
			getByIdBlock.directStatement(addSecurityCheck(RestrictionType.SEARCH));
			//getByIdBlock.directStatement("log.info();");
			
			getByIdBlock.directStatement("logEntryService.addLogEntry( \"Searching "+lowerClass+" with id \"+"+lowerClass+"Id,");
			getByIdBlock.directStatement(LogType.class.getName()+".INFO, "+OperationType.class.getName()+".SEARCH_ENTITY, "+ReflectionManager.getJDefinedClass(entity).fullName()+".staticEntityId, securityService.getLoggedUser(),log);");
       
			
			getByIdBlock.directStatement("List<"+ReflectionManager.getJDefinedClass(entity).fullName()+"> "+lowerClass+"List="+lowerClass+"Service.findById("+EntityAttributeManager.getInstance(null).getFieldTypeName(keyClass)+".valueOf("+lowerClass+"Id));");
			getByIdBlock.directStatement("getSecurityMapping("+lowerClass+"List);");
			getByIdBlock.directStatement("getRightMapping("+lowerClass+"List);");
			getByIdBlock.directStatement(" log.info(\"Search: returning {} "+lowerClass+".\","+lowerClass+"List.size());");
			
			getByIdBlock.directStatement("return "+response+".body("+lowerClass+"List);");
			//deleteById
			JMethod delete = myClass.method(JMod.PUBLIC, ResponseEntity.class, "delete"+className+"ById");
			annotateAsSwaggerOperation(delete, "Delete the "+entity.getName()+" identified by the given id", "Delete the "+entity.getName()+" identified by the given id", null, "");
			
			delete.annotate(Timed.class);
			
			delete.annotate(ResponseBody.class);
			JAnnotationUse requestMappingDelete = delete.annotate(RequestMapping.class);
			requestMappingDelete.param("value", "/{"+Utility.getFirstLower(className)+"Id}");
			requestMappingDelete.param("method",RequestMethod.DELETE);
			orderParam= delete.param(String.class,lowerClass+"Id");
			orderParam.annotate(PathVariable.class);
			JBlock deleteBlock= delete.body();
			deleteBlock.directStatement(addSecurityCheck(RestrictionType.DELETE));
			
			deleteBlock.directStatement("log.info(\"Deleting "+lowerClass+" with id \"+"+lowerClass+"Id);");
			
			deleteBlock.directStatement("logEntryService.addLogEntry( \"Deleting "+lowerClass+" with id {}\"+"+lowerClass+"Id,");
			deleteBlock.directStatement(LogType.class.getName()+".INFO, "+OperationType.class.getName()+".DELETE_ENTITY, "+ReflectionManager.getJDefinedClass(entity).fullName()+".staticEntityId, securityService.getLoggedUser(),log);");
       
			
			deleteBlock.directStatement(lowerClass+"Service.deleteById("+EntityAttributeManager.getInstance(null).getFieldTypeName(keyClass)+".valueOf("+lowerClass+"Id));");
			deleteBlock.directStatement("return "+response+".build();");
			
			//Insert
			JMethod insert = myClass.method(JMod.PUBLIC, ResponseEntity.class, "insert"+className+"");
			annotateAsSwaggerOperation(insert, "Insert the "+entity.getName()+" given", "Insert the "+entity.getName()+" given ", ReflectionManager.getJDefinedClass(entity), "");
			insert.annotate(Timed.class);
			
			insert.annotate(ResponseBody.class);
			JAnnotationUse requestMappingInsert = insert.annotate(RequestMapping.class);
			requestMappingInsert.param("method",RequestMethod.PUT);
			orderParam= insert.param(ReflectionManager.getJDefinedClass(entity),lowerClass+"");
			orderParam.annotate(RequestBody.class);
			JBlock insertBlock= insert.body();
			insertBlock.directStatement(addSecurityCheck(RestrictionType.INSERT));
			
			insertBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(lowerClass)+"Id()!=null)");
			insertBlock.directStatement("log.info(\"Inserting "+lowerClass+" like \"+"+entityManager.getDescription(true)+");");
			
			for (it.anggen.model.field.Field field : entityManager.getPasswordField())
			{
				insertBlock.directStatement(lowerClass+".set"+Utility.getFirstUpper(field.getName())+"(Utility.encodePassword("+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"()));");
		        
			}
			
			insertBlock.directStatement(ReflectionManager.getJDefinedClass(entity).fullName()+" inserted"+className+"="+lowerClass+"Service.insert("+lowerClass+");");
			insertBlock.directStatement("getRightMapping(inserted"+className+");");
			
			insertBlock.directStatement("logEntryService.addLogEntry( \"Inserted "+lowerClass+" with id \"+ inserted"+className+".get"+Utility.getFirstUpper(lowerClass)+"Id(),");
			insertBlock.directStatement(LogType.class.getName()+".INFO, "+OperationType.class.getName()+".CREATE_ENTITY, "+ReflectionManager.getJDefinedClass(entity).fullName()+".staticEntityId, securityService.getLoggedUser(),log);");
       
			insertBlock.directStatement("return "+response+".body(inserted"+className+");");
			//Update
			JMethod update = myClass.method(JMod.PUBLIC, ResponseEntity.class, "update"+className+"");
			annotateAsSwaggerOperation(update, "Update the "+entity.getName()+" given", "Update the "+entity.getName()+" given ", ReflectionManager.getJDefinedClass(entity), "");
			
			update.annotate(Timed.class);
			
			update.annotate(ResponseBody.class);
			JAnnotationUse requestMappingUpdate = update.annotate(RequestMapping.class);
			requestMappingUpdate.param("method",RequestMethod.POST);
			orderParam= update.param(ReflectionManager.getJDefinedClass(entity),lowerClass+"");
			orderParam.annotate(RequestBody.class);
			JBlock updateBlock= update.body();
			updateBlock.directStatement(addSecurityCheck(RestrictionType.UPDATE));
			
			updateBlock.directStatement("logEntryService.addLogEntry( \"Updating "+lowerClass+" with id \"+"+lowerClass+".get"+Utility.getFirstUpper(lowerClass)+"Id(),");
			updateBlock.directStatement(LogType.class.getName()+".INFO, "+OperationType.class.getName()+".UPDATE_ENTITY, "+ReflectionManager.getJDefinedClass(entity).fullName()+".staticEntityId, securityService.getLoggedUser(),log);");
       
			for (it.anggen.model.field.Field field : entityManager.getPasswordField())
			{
				updateBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"().length()<59)");
				updateBlock.directStatement(lowerClass+".set"+Utility.getFirstUpper(field.getName())+"(Utility.encodePassword("+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"()));");
		        
			}
			
			updateBlock.directStatement("rebuildSecurityMapping("+lowerClass+");");
			updateBlock.directStatement(ReflectionManager.getJDefinedClass(entity).fullName()+" updated"+className+"="+lowerClass+"Service.update("+lowerClass+");");
			updateBlock.directStatement("getSecurityMapping(updated"+className+");");
			updateBlock.directStatement("getRightMapping(updated"+className+");");
			updateBlock.directStatement("return "+response+".body(updated"+className+");");
			
			//get Right Mapping -List
			JMethod getRightMappingList = myClass.method(JMod.PRIVATE, listClass, "getRightMapping");
			getRightMappingList.param(listClass, lowerClass+"List");
			JBlock getRightMappingListBlock = getRightMappingList.body();
			getRightMappingListBlock.directStatement("for ("+ReflectionManager.getJDefinedClass(entity).fullName()+" "+lowerClass+": "+lowerClass+"List)");
			getRightMappingListBlock.directStatement("{");
			getRightMappingListBlock.directStatement("getRightMapping("+lowerClass+");");
			getRightMappingListBlock.directStatement("}");
			getRightMappingListBlock.directStatement("return "+lowerClass+"List;");
			
			JMethod getRightMapping= myClass.method(JMod.PRIVATE, void.class, "getRightMapping");
			getRightMapping.param(ReflectionManager.getJDefinedClass(entity), lowerClass);
			JBlock getRightMappingBlock = getRightMapping.body();
			
			generateRightMapping_v3(entity, getRightMappingBlock);
			
			// rebuild
			/*
			 *   private void rebuildSecurityMapping(Example example)
    			{
    				List<Place> placeList=exampleService.findById(example.getExampleId()).get(0).getPlaceList();
    				example.setPlaceList(placeList);
    			}
			 * 
			 */
			JMethod reBuildMethod = myClass.method(JMod.PRIVATE, void.class, "rebuildSecurityMapping");
			reBuildMethod.param(ReflectionManager.getJDefinedClass(entity), lowerClass);
			JBlock reBuildBlock=reBuildMethod.body();
			for (Relationship relationship: entity.getRelationshipList())
			{
				
				if (entity.getSecurityType()==null || entity.getSecurityType()==SecurityType.ACCESS_WITH_PERMISSION)
					reBuildBlock.directStatement("if (securityEnabled && !securityService.hasPermission("+ReflectionManager.getJDefinedClass(relationship.getEntityTarget()).fullName()+".staticEntityId, "+RestrictionType.class.getName()+".SEARCH))");
				else
					if (entity.getSecurityType()==SecurityType.BLOCK_WITH_RESTRICTION)
						reBuildBlock.directStatement("if (securityEnabled && securityService.hasRestriction("+ReflectionManager.getJDefinedClass(relationship.getEntityTarget()).fullName()+".staticEntityId, "+RestrictionType.class.getName()+".SEARCH))");
					
				if (EntityAttributeManager.getInstance(relationship).isList())
					reBuildBlock.directStatement(entity.getName()+".set"+Utility.getFirstUpper(relationship.getName())+"List("+entity.getName()+"Service.findById("+entity.getName()+".get"+Utility.getFirstUpper(entity.getName())+"Id()).get(0).get"+Utility.getFirstUpper(relationship.getName())+"List());");
				else
					reBuildBlock.directStatement(entity.getName()+".set"+Utility.getFirstUpper(relationship.getName())+"("+entity.getName()+"Service.findById("+entity.getName()+".get"+Utility.getFirstUpper(entity.getName())+"Id()).get(0).get"+Utility.getFirstUpper(relationship.getName())+"());");
			}
			
			//get security Mapping -List
			JMethod getSecurityMappingList = myClass.method(JMod.PRIVATE, listClass, "getSecurityMapping");
			getSecurityMappingList.param(listClass, lowerClass+"List");
			JBlock getSecurityMappingListBlock = getSecurityMappingList.body();
			
			if (entity.getEnableRestrictionData() && entity.getEntityGroup()!=null && !entity.getEntityGroup().getName().equals("restrictiondata"))
			{
				getSecurityMappingListBlock.directStatement(User.class.getName()+" loggedUser = securityService.getLoggedUser();");
				getSecurityMappingListBlock.directStatement("for ("+Role.class.getName()+" role : loggedUser.getRoleList())");
				getSecurityMappingListBlock.directStatement("{");
				String restrictionDataEntityName=ReflectionManager.getJDefinedClass(entity).fullName().replaceAll("."+Utility.getFirstUpper(entity.getName()),".Restriction"+Utility.getFirstUpper(entity.getName()) ).replaceAll("."+entity.getEntityGroup().getName()+".", ".restrictiondata.");

				getSecurityMappingListBlock.directStatement("List<"+restrictionDataEntityName+"> restrictedEntityList = restriction"+Utility.getFirstUpper(entity.getName())+"Repository.findByRole(role);");
				getSecurityMappingListBlock.directStatement("for ("+restrictionDataEntityName+" restriction"+Utility.getFirstUpper(entity.getName())+": restrictedEntityList)");
				getSecurityMappingListBlock.directStatement("{");
				getSecurityMappingListBlock.directStatement(entity.getName()+"List.remove(restriction"+Utility.getFirstUpper(entity.getName())+".get"+Utility.getFirstUpper(entity.getName())+"());");
				getSecurityMappingListBlock.directStatement("}");
				getSecurityMappingListBlock.directStatement("}");

			}	
	    		
	    		getSecurityMappingListBlock.directStatement("for ("+ReflectionManager.getJDefinedClass(entity).fullName()+" "+lowerClass+": "+lowerClass+"List)");
			getSecurityMappingListBlock.directStatement("{");
			getSecurityMappingListBlock.directStatement("getSecurityMapping("+lowerClass+");");
			getSecurityMappingListBlock.directStatement("}");
			getSecurityMappingListBlock.directStatement("return "+lowerClass+"List;");
			
			JMethod getSecurityMapping= myClass.method(JMod.PRIVATE, void.class, "getSecurityMapping");
			getSecurityMapping.param(ReflectionManager.getJDefinedClass(entity), lowerClass);
			JBlock getSecurityMappingBlock = getSecurityMapping.body();
			
			generateSecurityMapping(entity,getSecurityMappingBlock);
			
			for (it.anggen.model.field.Field field: entity.getFieldList())
			{
				if (field.getFieldType()==FieldType.FILE)
				{
					JMethod loadFileMethod= myClass.method(JMod.PRIVATE, ResponseEntity.class, "loadFile"+Utility.getFirstUpper(field.getName()));
					loadFileMethod.annotate(Timed.class);
					loadFileMethod.annotate(ResponseBody.class);
					JAnnotationUse requestMappingLoadfile=loadFileMethod.annotate(RequestMapping.class);
					requestMappingLoadfile.param("value", "/{"+Utility.getFirstLower(className)+"Id}/load"+Utility.getFirstUpper(field.getName()));
					requestMappingLoadfile.param("method",RequestMethod.POST);
					JVar idParam= loadFileMethod.param(String.class,lowerClass+"Id");
					idParam.annotate(PathVariable.class);
					JVar fileParam =loadFileMethod.param(MultipartFile.class, "file");
					JAnnotationUse reqParam = fileParam.annotate(RequestParam.class);
					reqParam.param("value", "file");
					reqParam.param("required",false);
					JBlock loadFileBlock =loadFileMethod.body();
					loadFileBlock.directStatement(ReflectionManager.getJDefinedClass(entity).fullName()+" "+lowerClass+" = "+lowerClass+"Service.findById("+EntityAttributeManager.getInstance(null).getFieldTypeClass(keyClass).getName()+".valueOf("+lowerClass+"Id)).get(0);");
					loadFileBlock.directStatement(ReflectionManager.getJDefinedClass(entity).fullName()+" updated"+Utility.getFirstUpper(lowerClass)+"=null;");
					loadFileBlock.directStatement("String destination=\""+Generator.uploadDirectoryProperty+lowerClass+"/\"+"+lowerClass+"Id+\"/\";");
					loadFileBlock.directStatement("String filePath = "+Utility.class.getName()+".saveMultipartFile(file, destination);");
					loadFileBlock.directStatement(""+lowerClass+".set"+Utility.getFirstUpper(field.getName())+"(filePath);");
					loadFileBlock.directStatement("updated"+Utility.getFirstUpper(lowerClass)+"="+lowerClass+"Service.update("+lowerClass+");");
					loadFileBlock.directStatement("return  ResponseEntity.ok().body(updated"+Utility.getFirstUpper(lowerClass)+");");
					
				}
			}
			
			
			
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		saveFile(codeModel);
	
	}
	private String addSecurityCheck(RestrictionType restrictionType) {
		String check= "";
		
			if (entity.getSecurityType()==null || entity.getSecurityType()==SecurityType.ACCESS_WITH_PERMISSION)
				check+="if (securityEnabled && !securityService.hasPermission("+ReflectionManager.getJDefinedClass(entity).fullName()+".staticEntityId, "+RestrictionType.class.getName()+"."+restrictionType.toString()+")) \n";
			else
				if (entity.getSecurityType()==SecurityType.BLOCK_WITH_RESTRICTION)
					check+="if (securityEnabled && securityService.hasRestriction("+ReflectionManager.getJDefinedClass(entity).fullName()+".staticEntityId, "+RestrictionType.class.getName()+"."+restrictionType.toString()+")) \n";
				
		check+="return ResponseEntity.status("+HttpStatus.class.getName()+".FORBIDDEN).build(); \n";
		return check;
	}

	/**
	 * Save codeModel to file
	 * @param codeModel
	 */
	private void saveFile(JCodeModel codeModel)
	{
		try {
			   codeModel.build(new File (directory));
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
		
	}
	
	
	private void generateRightMapping_v3(Entity entity, JBlock block)
	{
		EntityManager entityManager = new EntityManagerImpl(entity);
		String lowerClass= "";
		for (Relationship relationship: entity.getRelationshipList())
			
		{
			lowerClass=entity.getName();
			if (EntityAttributeManager.getInstance(relationship).isList())
			{
				block.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(relationship.getName())+"List()!=null)");
				block.directStatement("for ("+ReflectionManager.getJDefinedClass(relationship.getEntityTarget()).fullName()+" "+Utility.getFirstLower(relationship.getName())+" :"+lowerClass+".get"+Utility.getFirstUpper(relationship.getName())+"List())\n");
				block.directStatement("{\n");
				lowerClass=relationship.getName();
			}
			 else
			 {
				 
				 block.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(relationship.getName())+"()!=null)");
				 block.directStatement("{");
				 lowerClass=lowerClass+".get"+Utility.getFirstUpper(relationship.getName())+"()";
			 }
			EntityManager targetEntityManager = new EntityManagerImpl(relationship.getEntityTarget());
			for (Relationship targetRelationship: relationship.getEntityTarget().getRelationshipList())
			{
				if (EntityAttributeManager.getInstance(targetRelationship).isList())
					block.directStatement(lowerClass+".set"+Utility.getFirstUpper(targetRelationship.getName())+"List(null);");
				else
					block.directStatement(lowerClass+".set"+Utility.getFirstUpper(targetRelationship.getName())+"(null);");
			}
			
			
			block.directStatement("}");
			
		}
	}
	
	
	private void generateSecurityMapping(Entity entity, JBlock block)
	{
		EntityManager entityManager = new EntityManagerImpl(entity);
		String lowerClass= "";
		// if (example.getPlaceList()!=null && !securityService.isAllowed(Place.entityId, RestrictionType.SEARCH))
		for (Relationship relationship: entity.getRelationshipList())
			
		{
			lowerClass=entity.getName();
			if (EntityAttributeManager.getInstance(relationship).isList())
			{
				if (entity.getSecurityType()==null || entity.getSecurityType()==SecurityType.ACCESS_WITH_PERMISSION)
					block.directStatement("if (securityEnabled && "+lowerClass+".get"+Utility.getFirstUpper(relationship.getName())+"List()!=null && !securityService.hasPermission("+ReflectionManager.getJDefinedClass(relationship.getEntityTarget()).fullName()+".staticEntityId, "+RestrictionType.class.getName()+".SEARCH) )");
				else
					if (entity.getSecurityType()==SecurityType.BLOCK_WITH_RESTRICTION)
						block.directStatement("if (securityEnabled && "+lowerClass+".get"+Utility.getFirstUpper(relationship.getName())+"List()!=null && securityService.hasRestriction("+ReflectionManager.getJDefinedClass(relationship.getEntityTarget()).fullName()+".staticEntityId, "+RestrictionType.class.getName()+".SEARCH) )");
				block.directStatement(""+lowerClass+".set"+Utility.getFirstUpper(relationship.getName())+"List(null);\n");
			}
			 else
			 {
				 
				 if (entity.getSecurityType()==null || entity.getSecurityType()==SecurityType.ACCESS_WITH_PERMISSION)
						block.directStatement("if (securityEnabled && "+lowerClass+".get"+Utility.getFirstUpper(relationship.getName())+"()!=null  && !securityService.hasPermission("+ReflectionManager.getJDefinedClass(relationship.getEntityTarget()).fullName()+".staticEntityId, "+RestrictionType.class.getName()+".SEARCH) )");
				 else
						if (entity.getSecurityType()==SecurityType.BLOCK_WITH_RESTRICTION)
							block.directStatement("if (securityEnabled && "+lowerClass+".get"+Utility.getFirstUpper(relationship.getName())+"()!=null  && securityService.hasRestriction("+ReflectionManager.getJDefinedClass(relationship.getEntityTarget()).fullName()+".staticEntityId, "+RestrictionType.class.getName()+".SEARCH) )");
				block.directStatement(""+lowerClass+".set"+Utility.getFirstUpper(relationship.getName())+"(null);\n");
			 }
		}
	}
	
	
	private void annotateAsSwaggerOperation(JMethod method,String value, String note,JClass theClass, String responseContainer)
	{
		JAnnotationUse annotation = method.annotate(ApiOperation.class);
		annotation.param("value", value);
		annotation.param("notes", note);
		if (theClass!=null)
			annotation.param("response",theClass);
		if (!responseContainer.isEmpty())
			annotation.param("responseContainer", responseContainer);
		
	}
	
	/**
	 * Nullify the coming back relationships
	 * @param theClass
	 * @param block
	 * @param parentClass
	 * @param entityName
	 */
	/*private static void generateRightMapping_v2(Class theClass, JBlock block,List<Class> parentClass,String entityName)
	{
		ReflectionManager reflectionManager = new ReflectionManager(theClass);
		parentClass.add(theClass);
		String lowerClass=entityName!=null? entityName:reflectionManager.parseName();
		for (Field field: reflectionManager.getChildrenFieldList())
		{
			String newEntityName= lowerClass;
			if (ReflectionManager.isListField(field))
				newEntityName=Utility.getFirstLower(field.getName());
			else
				newEntityName=newEntityName+".get"+Utility.getFirstUpper(field.getName())+"()";
			ArrayList<Class> oldParentClassList = (ArrayList<Class>) ((ArrayList<Class>) parentClass).clone();
			if (ReflectionManager.isListField(field))
			{
				block.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"List()!=null)");
				block.directStatement("{");
				if (parentClass.contains(field.getFieldClass()))
					block.directStatement(""+lowerClass+".set"+Utility.getFirstUpper(field.getName())+"List(null);");
				else
				{
					block.directStatement("for ("+field.getFieldClass().getName()+" "+Utility.getFirstLower(field.getName())+" : "+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"List())");
					block.directStatement("{");
					if (!reflectionManager.isKnownClass(field.getFieldClass()))
					RestGenerator.generateRightMapping_v2(field.getFieldClass(), block,parentClass,newEntityName);
					parentClass=oldParentClassList;
					block.directStatement("}");
				}
				block.directStatement("}");
			}else
			{
				block.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"()!=null)");
				block.directStatement("{");
				
				if (parentClass.contains(field.getFieldClass()))
				{
					block.directStatement("//"+newEntityName);
					block.directStatement(""+lowerClass+".set"+Utility.getFirstUpper(field.getName())+"(null);");
				}
				else
				{

					if (!reflectionManager.isKnownClass(field.getFieldClass()))
						RestGenerator.generateRightMapping_v2(field.getFieldClass(), block,parentClass,newEntityName);
					parentClass=oldParentClassList;
				}
				block.directStatement("}");

			}
		}
	}*/
	
	/*private static void generateRightMapping(Class theClass,JBlock block,Class parentClass,String entityName)
	{
		ReflectionManager reflectionManager = new ReflectionManager(theClass);
		String lowerClass=entityName!=null? entityName:reflectionManager.parseName();
		for (Field field: reflectionManager.getFieldList())
		{
			if (field.getCompositeClass()!=null && RestGenerator.isBackRelationship(field) && field.getFieldClass()!=parentClass)
			{
				//TODO change with annotation
				if (field.getCompositeClass().fullName().contains("java.util.List"))
				{
					block.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"List()!=null)");
					block.directStatement("for ("+field.getFieldClass().getName()+" "+Utility.getFirstLower(field.getName())+" : "+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"List())");
					block.directStatement("{");
					block.directStatement(""+Utility.getFirstLower(field.getName())+".set"+Utility.getFirstUpper(lowerClass)+"(null);");
					RestGenerator.generateRightMapping(field.getFieldClass(), block,theClass,lowerClass+".get"+Utility.getFirstUpper(field.getName())+"List()");
					block.directStatement("}");
					
				}else
				{
					block.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"()!=null)");
					block.directStatement(""+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"().set"+Utility.getFirstUpper(reflectionManager.parseName())+"List(null);");
					RestGenerator.generateRightMapping(field.getFieldClass(), block,theClass,lowerClass+".get"+Utility.getFirstUpper(field.getName())+"()");
				}
			}
		}
	}
	*/
	/**
	 * Check if a relationship is a coming back one
	 * @param field
	 * @return
	 */
	/*
	private static Boolean isBackRelationship(Field field)
	{
		if (field.getAnnotationList()==null || field.getAnnotationList().length==0) return false;
		for (int i=0; i<field.getAnnotationList().length; i++)
		{
			if (field.getAnnotationList()[i].annotationType()==ManyToOne.class)
				return true;
		}
		
		return false;
	}*/
	
}
