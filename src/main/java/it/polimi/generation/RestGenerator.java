package it.polimi.generation;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EntityAttribute;
import it.polimi.model.domain.FieldType;
import it.polimi.model.domain.Relationship;
import it.polimi.reflection.EntityManager;
import it.polimi.reflection.EntityManagerImpl;
import it.polimi.utils.Field;
import it.polimi.utils.ReflectionManager;
import it.polimi.utils.Utility;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.ManyToOne;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

/**
 * Generates the REST classes to manage the entity
 * @author Marco
 *
 */
public class RestGenerator {
	
	
	private String directory;

	private Entity entity;
	
	private String className;
	
	private String fullClassName;
	
	private String alias;
	
	private List<EntityAttribute> entityAttributeList;
	
	private FieldType keyClass;
	
	private JDefinedClass searchBeanClass;
	
	private JDefinedClass repositoryClass;

	private JDefinedClass serviceInterfaceClass;
	
	private JDefinedClass serviceImplClass;
	
	private EntityManager entityManager;
	
	public void setSearchBeanClass(JDefinedClass searchBeanClass)
	{
		this.searchBeanClass=searchBeanClass;
	}
	
	/**
	 * Constructor
	 * @param classClass
	 */
	public RestGenerator(Entity entity)
	{
		this.entity=entity;
		this.fullClassName=Utility.getFirstLower(entity.getName());
		File file = new File(""); 
		this.directory = file.getAbsolutePath()+"\\src\\main\\java";
		entityManager= new EntityManagerImpl(entity);
		this.className=Utility.getFirstUpper(entity.getName());
		this.alias=this.className.substring(0,1);
		this.entityAttributeList=entityManager.getAttributeList();
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
			
			
			if (entityAttribute.getBetweenFilter())
			{
				JVar param = method.param(ReflectionManager.getRightParamClass(entityAttribute), entityAttribute.getName()+"From");
				JAnnotationUse annotationParam= param.annotate(Param.class);
				annotationParam.param("value", entityAttribute.getName()+"From");
			
				query=query+getFieldSearchQuery(entityAttribute, entityAttribute.getName()+"From",alias+"."+entityAttribute.getName(),"<=");
			
				param = method.param(ReflectionManager.getRightParamClass(entityAttribute), entityAttribute.getName()+"To");
				annotationParam= param.annotate(Param.class);
				annotationParam.param("value", entityAttribute.getName()+"To");
			
				query=query+getFieldSearchQuery(entityAttribute, entityAttribute.getName()+"To",alias+"."+entityAttribute.getName(),">=");
				
			}else
			{
				JVar param = method.param(ReflectionManager.getRightParamClass(entityAttribute), entityAttribute.getName());
				JAnnotationUse annotationParam= param.annotate(Param.class);
				annotationParam.param("value", entityAttribute.getName());
				query=query+getFieldSearchQuery(entityAttribute, entityAttribute.getName(),alias+"."+entityAttribute.getName(),"=");
			}
			
			//TODO filter field
			/*if (entityAttribute.getChildrenFilterList()!=null)
			for (Field filterField: entityAttribute.getChildrenFilterList())
			{
				String filterFieldName=entityManager.parseName(filterField.getOwnerClass().getName())+Utility.getFirstUpper(filterField.getName());
				
				JVar param = method.param(ReflectionManager.getRightParamClass(filterField), filterFieldName);
				JAnnotationUse annotationParam= param.annotate(Param.class);
				annotationParam.param("value", filterFieldName);
				String hibernateField="";
				
				if (ReflectionManager.isListField(entityAttribute))
				{// cerco quel campo in una lista
					String aliasFilterOwnerClass= entityManager.parseName(filterField.getOwnerClass().getName()).substring(0, 1);
					
					query=query+" ( :"+filterFieldName+" is null or cast(:"+filterFieldName+" as string)='' or "+alias+" in (select "+aliasFilterOwnerClass+"."+entityManager.parseName(className)+" from "+Utility.getFirstUpper(entityManager.parseName(filterField.getOwnerClass().getName()))+" "+aliasFilterOwnerClass+" where "+aliasFilterOwnerClass+"."+filterField.getName()+"=cast(:"+filterFieldName+" as string))) and";
				}else // cerco quel campo nell'�entit� collegata
				{
					hibernateField=""+alias+"."+entityManager.parseName(filterField.getOwnerClass().getName())+"."+filterField.getName();
					query=query+getFieldSearchQuery(filterField, filterFieldName,hibernateField,"=");
				}
			}*/
			
			
		}
		query=query.substring(0,query.length()-3);
		return query;
	}
	
	
	private String getFieldSearchQuery(EntityAttribute entityAttribute, String fieldName,String hibernateField, String comparator)
	{
		String query="";
		if (entityAttribute.asField().getFieldType()==FieldType.TIME)
		{
			query= query + " (:"+fieldName+" is null or cast(:"+fieldName+" as string)"+comparator+"cast(date_trunc('seconds',"+hibernateField+") as string)) and";
		}
		else
		{
			if (entityAttribute.asField().getFieldType()==FieldType.DATE)
			{
				query= query+" (:"+fieldName+" is null or cast(:"+fieldName+" as string)"+comparator+"cast(date("+hibernateField+") as string)) and";
			} else
			{
				if (entityAttribute.asField().getFieldType()==FieldType.STRING)
				{
					query= query+" (:"+fieldName+" is null or :"+fieldName+"='' or cast(:"+fieldName+" as string)"+comparator+""+hibernateField+") and";
				} else
				{
					if (entityAttribute.asRelationship()==null)
					{
						query=query+" (:"+fieldName+" is null or cast(:"+fieldName+" as string)"+comparator+"cast("+hibernateField+" as string)) and";

					} else
					{ // Entity or entity list!!!
						if (entityAttribute.asRelationship().isList())
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

			String fullClassName = Utility.getFirstLower(entity.getName());
			JCodeModel codeModel = new JCodeModel();
			JDefinedClass myClass = null;

			try {
				myClass = codeModel._class(""+fullClassName.replace(".model.", ".searchbean.")+"SearchBean", ClassType.CLASS);
				for (EntityAttribute entityAttribute: entityAttributeList)
				{
					if (entityAttribute.getBetweenFilter())
					{
						JVar fieldFromVar = myClass.field(JMod.PUBLIC, entityAttribute.getFieldClass(), entityAttribute.getName()+"From");
						generateGetterAndSetter(myClass, entityAttribute.getName()+"From", entityAttribute.getFieldClass());
						JVar fieldToVar = myClass.field(JMod.PUBLIC, entityAttribute.getFieldClass(), entityAttribute.getName()+"To");
						generateGetterAndSetter(myClass, entityAttribute.getName()+"To", entityAttribute.getFieldClass());
					} else
					{
						if (entityAttribute.asRelationship()!=null && entityAttribute.asRelationship().isList())
						{
							JClass listClass = codeModel.ref(List.class).narrow(entityAttribute.getFieldClass());
							JVar fieldVar = myClass.field(JMod.PUBLIC, listClass, entityAttribute.getName()+"List");
							generateGetterAndSetter(myClass, entityAttribute.getName()+"List", listClass);



						}else
						{
							JVar fieldVar = myClass.field(JMod.PUBLIC, entityAttribute.getFieldClass(), entityAttribute.getName());
							generateGetterAndSetter(myClass, entityAttribute.getName(), entityAttribute.getFieldClass());
						}
						//filter modification
						if (entityAttribute.asRelationship()!=null)
						{/*
							entityManager.addChildrenFilter(entityAttribute);
							for (Field filterField: entityAttribute.getChildrenFilterList())
							{
								String filterFieldName=entityManager.parseName(filterField.getOwnerClass().getName())+Utility.getFirstUpper(filterField.getName());
								JVar fieldVar = myClass.field(JMod.PUBLIC, filterField.getFieldClass(), filterFieldName);
								generateGetterAndSetter(myClass, filterFieldName, filterField.getFieldClass());
							}
							*/
						}
					}
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
	public void generateRESTClasses(List<Entity> dependencyEntities, Boolean jumpDependency)
	{
		System.out.println("working for "+entity.getName());
		String searchMethod="";
		if (jumpDependency && entityManager.hasList())
		{
			System.out.println(entity.getName()+" has list. pass away.");
			dependencyEntities.add(entity);
			return;
		}
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
			myClass = codeModel._class(""+fullClassName.replace(".model.", ".repository.")+"Repository", ClassType.INTERFACE);
			JClass extendedClass = codeModel.ref(CrudRepository.class).narrow(entity,keyClass);
			myClass._extends(extendedClass);
			myClass.annotate(Repository.class);
			JClass listClass=codeModel.ref(List.class).narrow(entity);
			searchMethod="findBy";
			for (EntityAttribute entityAttribute: entityAttributeList)
			{
				if (entityAttribute.asField()!=null)
				{
					JMethod method=myClass.method(JMod.PUBLIC, listClass, "findBy"+entityAttribute.getName().replaceFirst(entityAttribute.getName().substring(0, 1), entityAttribute.getName().substring(0, 1).toUpperCase()));
					method.param(ReflectionManager.getRightParamClass(entityAttribute), entityAttribute.getName());
				} else
				{
					if (!entityAttribute.asRelationship().isList())
					{ // find by entity
						JMethod method= myClass.method(JMod.PUBLIC, listClass, "findBy"+Utility.getFirstUpper(entityAttribute.getName()));
						method.param(entityAttribute.getFieldClass(), entityAttribute.getName());
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
			JMethod method=myClass.method(JMod.PUBLIC, listClass, searchMethod);
			String alias=className.substring(0, 1).toLowerCase();
			String query=getSearchQuery(method);
			JAnnotationUse annotationQuery= method.annotate(Query.class);
			annotationQuery.param("value", query);
			
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		saveFile(codeModel);
		repositoryClass=myClass;
		Generator.repositoryMap.put(entity.getName(), repositoryClass);
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
			JClass listClass = codeModel.ref(List.class).narrow(entity);
			JMethod findById = myClass.method(JMod.PUBLIC, listClass, "findById");
			String lowerClass= className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
			findById.param(keyClass,className+"Id");
			JMethod findLike=myClass.method(JMod.PUBLIC, listClass, "find");
			findLike.param(searchBeanClass, className);
			JMethod deleteById = myClass.method(JMod.PUBLIC, void.class, "deleteById");
			deleteById.param(keyClass, className+"Id");
			JMethod insert= myClass.method(JMod.PUBLIC, entity, "insert");
			insert.param(entity, className);
			JMethod update= myClass.method(JMod.PUBLIC, entity, "update");
			update.param(entity, className);
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
			if (entityAttribute.getBetweenFilter())
			{
				searchMethod=searchMethod+"GreaterThan"+Utility.getFirstUpper(entityAttribute.getName())+"FromAndLessThan"+Utility.getFirstUpper(entityAttribute.getName())+"ToAnd";
			} else
			searchMethod=searchMethod+Utility.getFirstUpper(entityAttribute.getName())+"And";
			//TODO childre filter
		/*	if (entityAttribute.getChildrenFilterList()!=null)
				for (Field filterField: entityAttribute.getChildrenFilterList())
				{
					searchMethod=searchMethod+Utility.getFirstUpper(filterField.getName())+"And";
				}*/
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
			JClass listClass = codeModel.ref(List.class).narrow(entity);
			String lowerClass= className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
			JVar repository = myClass.field(JMod.PUBLIC, repositoryClass, lowerClass+"Repository");
			repository.annotate(Autowired.class);
			for (EntityAttribute entityAttribute: entityAttributeList)
			{
				if (entityAttribute.asRelationship()!=null && entityAttribute.asRelationship().isList() && entityAttribute.getRepositoryClass()!=null)
				{
					JVar fieldListRepository = myClass.field(JMod.PUBLIC, entityAttribute.getRepositoryClass(), entityAttribute.getName()+"Repository");
					fieldListRepository.annotate(Autowired.class);
				}
			}
			
			
			JMethod findById = myClass.method(JMod.PUBLIC, listClass, "findById");
			findById.annotate(Override.class);
			findById.param(keyClass,lowerClass+"Id");
			JBlock findByIdBlock= findById.body();
			findByIdBlock.directStatement("return "+lowerClass+"Repository.findBy"+Utility.getFirstUpper(className)+"Id("+lowerClass+"Id);");
			//search
			JMethod findLike=myClass.method(JMod.PUBLIC, listClass, "find");
			findLike.annotate(Override.class);
			findLike.param(searchBeanClass, lowerClass);
			JBlock findLikeBlock= findLike.body();
			findLikeBlock.directStatement("return "+lowerClass+"Repository."+searchMethod+"("+entityManager.getAllParam()+");");
			//delete
			JMethod deleteById = myClass.method(JMod.PUBLIC, void.class, "deleteById");
			deleteById.annotate(Override.class);
			deleteById.param(keyClass, lowerClass+"Id");
			JBlock deleteBlock= deleteById.body();
			deleteBlock.directStatement(""+lowerClass+"Repository.delete("+lowerClass+"Id);");
			deleteBlock.directStatement("return;");
			
			//insert
			JMethod insert= myClass.method(JMod.PUBLIC, entity, "insert");
			insert.annotate(Override.class);
			insert.param(entity, lowerClass);
			JBlock insertBlock= insert.body();
			insertBlock.directStatement("return "+lowerClass+"Repository.save("+lowerClass+");");
			//update
			JMethod update= myClass.method(JMod.PUBLIC, entity, "update");
			update.annotate(Override.class);
			update.annotate(Transactional.class);
			update.param(entity, lowerClass);
			JBlock updateBlock= update.body();
			for (EntityAttribute entityAttribute: entityAttributeList)
			{
				if (entityAttribute.asRelationship()!=null)
				if (entityAttribute.asRelationship().isList())
				{
					updateBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(entityAttribute.getName())+"List()!=null)");
					updateBlock.directStatement("for ("+(entityAttribute.getFieldClass().getName())+" "+entityAttribute.getName()+": "+lowerClass+".get"+Utility.getFirstUpper(entityAttribute.getName())+"List())");
					updateBlock.directStatement("{");
					if (ReflectionManager.hasManyToManyAssociation(entityAttribute.getFieldClass(), className))
					{
						updateBlock.directStatement(entityAttribute.getFieldClass().getName()+" saved"+Utility.getFirstUpper(entityAttribute.getName())+" = "+entityAttribute.getName()+"Repository.findOne("+entityAttribute.getName()+".get"+Utility.getFirstUpper(entityAttribute.getName())+"Id());");
						updateBlock.directStatement("Boolean found=false; ");
						updateBlock.directStatement("for ("+Utility.getFirstUpper(className)+" temp"+Utility.getFirstUpper(className)+" : saved"+Utility.getFirstUpper(entityAttribute.getName())+".get"+Utility.getFirstUpper(className)+"List())");
						updateBlock.directStatement("{");
						updateBlock.directStatement("if (temp"+Utility.getFirstUpper(className)+".get"+Utility.getFirstUpper(className)+"Id().equals("+lowerClass+".get"+Utility.getFirstUpper(className)+"Id()))");
						updateBlock.directStatement("{");
						updateBlock.directStatement("found=true;");
						updateBlock.directStatement("break;");
						updateBlock.directStatement("}");
						updateBlock.directStatement("}");
						updateBlock.directStatement("if (!found)");
						updateBlock.directStatement("saved"+Utility.getFirstUpper(entityAttribute.getName())+".get"+Utility.getFirstUpper(className)+"List().add("+lowerClass+");");
						
					} else
					{
						updateBlock.directStatement(entityAttribute.getName()+".set"+Utility.getFirstUpper(className)+"("+lowerClass+");");
					}
					updateBlock.directStatement("}");
				} 
			}
			updateBlock.directStatement(Utility.getFirstUpper(className)+" returned"+Utility.getFirstUpper(className)+"="+lowerClass+"Repository.save("+lowerClass+");");
			for (EntityAttribute entityAttribute: entityAttributeList)
			{
				if (entityAttribute.asRelationship()!=null)
				{
					EntityManager fieldEntityManager = new EntityManagerImpl(entityAttribute.asRelationship().getEntityTarget());
					if (!entityAttribute.asRelationship().isList() && fieldEntityManager.containFieldOfEntity(entity))			
					{
						updateBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(entityAttribute.getName())+"()!=null)");
						updateBlock.directStatement("{");
						updateBlock.directStatement("List<"+Utility.getFirstUpper(className)+"> "+className+"List = "+lowerClass+"Repository.findBy"+Utility.getFirstUpper(entityAttribute.getName())+"( "+lowerClass+".get"+Utility.getFirstUpper(entityAttribute.getName())+"());");
						updateBlock.directStatement("if (!"+lowerClass+"List.contains(returned"+Utility.getFirstUpper(className)+"))");
						updateBlock.directStatement(""+lowerClass+"List.add(returned"+Utility.getFirstUpper(className)+");");
						updateBlock.directStatement("returned"+Utility.getFirstUpper(className)+".get"+Utility.getFirstUpper(entityAttribute.getName())+"().set"+Utility.getFirstUpper(className)+"List("+lowerClass+"List);");
						updateBlock.directStatement("}");
					}
				}
			}
			
			updateBlock.directStatement(" return returned"+Utility.getFirstUpper(className)+";");
				
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		saveFile(codeModel);
	
		serviceImplClass=myClass;
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
			JClass listClass = codeModel.ref(List.class).narrow(entity);
			String lowerClass= className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
			myClass.annotate(Controller.class);
			JAnnotationUse requestMappingClass = myClass.annotate(RequestMapping.class);
			requestMappingClass.param("value", "/"+lowerClass);
			//declare service
			JVar repository = myClass.field(JMod.PUBLIC, serviceInterfaceClass, lowerClass+"Service");
			repository.annotate(Autowired.class);
			//private final static Logger log = LoggerFactory.getLogger(Mountain.class);
			JClass factory = codeModel.directClass("org.slf4j.LoggerFactory");
			JVar log = myClass.field(JMod.PRIVATE+JMod.STATIC+JMod.FINAL, Logger.class, "log");
			JClass jClassClass = codeModel.ref(entity);
			log.init(factory.staticInvoke("getLogger").arg(jClassClass.dotclass()));
			//log.assign(factory.staticInvoke("getLogger"));
			
			
			//manage
			JMethod manage = myClass.method(JMod.PUBLIC, String.class, "manage");
			JAnnotationUse requestMappingManage = manage.annotate(RequestMapping.class);
			requestMappingManage.param("method", RequestMethod.GET);
			JBlock manageBlock = manage.body();
			manageBlock.directStatement("return \""+lowerClass+"\";");
			
			
			//search
			JMethod search = myClass.method(JMod.PUBLIC, ResponseEntity.class, "search");
			search.annotate(ResponseBody.class);
			JAnnotationUse requestMappingSearch = search.annotate(RequestMapping.class);
			requestMappingSearch.param("value", "/search");
			requestMappingSearch.param("method",RequestMethod.POST);
			JVar orderParam= search.param(searchBeanClass,lowerClass);
			orderParam.annotate(RequestBody.class);
			JBlock searchBlock= search.body();
			JVar entityList= searchBlock.decl(listClass, lowerClass+"List");
			// log.info("Searching mountain like {}",mountain);
			//TODO ,anage null on description field
			searchBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(lowerClass)+"Id()!=null)");
			searchBlock.directStatement(" log.info(\"Searching "+lowerClass+" like {}\","+entityManager.getDescriptionField(true)+");");
			searchBlock.directStatement(""+lowerClass+"List="+lowerClass+"Service.find("+lowerClass+");");
			searchBlock.directStatement("getRightMapping("+lowerClass+"List);");
			searchBlock.directStatement(" log.info(\"Search: returning {} "+lowerClass+".\","+lowerClass+"List.size());");
			searchBlock.directStatement("return "+response+".body("+lowerClass+"List);");
			//getById  
			JMethod getById=myClass.method(JMod.PUBLIC, ResponseEntity.class, "get"+className+"ById");
			getById.annotate(ResponseBody.class);
			JAnnotationUse requestMappingGetById = getById.annotate(RequestMapping.class);
			requestMappingGetById.param("value", "/{"+lowerClass+"Id}");
			requestMappingGetById.param("method",RequestMethod.GET);
			orderParam= getById.param(String.class,lowerClass+"Id");
			orderParam.annotate(PathVariable.class);
			JBlock getByIdBlock= getById.body();
			getByIdBlock.directStatement("log.info(\"Searching "+lowerClass+" with id {}\","+lowerClass+"Id);");
			getByIdBlock.directStatement("List<"+Utility.getFirstUpper(className)+"> "+lowerClass+"List="+lowerClass+"Service.findById("+keyClass.getName()+".valueOf("+lowerClass+"Id));");
			getByIdBlock.directStatement("getRightMapping("+lowerClass+"List);");
			getByIdBlock.directStatement(" log.info(\"Search: returning {} "+lowerClass+".\","+lowerClass+"List.size());");
			
			getByIdBlock.directStatement("return "+response+".body("+lowerClass+"List);");
			//deleteById
			JMethod delete = myClass.method(JMod.PUBLIC, ResponseEntity.class, "delete"+className+"ById");
			delete.annotate(ResponseBody.class);
			JAnnotationUse requestMappingDelete = delete.annotate(RequestMapping.class);
			requestMappingDelete.param("value", "/{"+Utility.getFirstLower(className)+"Id}");
			requestMappingDelete.param("method",RequestMethod.DELETE);
			orderParam= delete.param(String.class,lowerClass+"Id");
			orderParam.annotate(PathVariable.class);
			JBlock deleteBlock= delete.body();
			deleteBlock.directStatement("log.info(\"Deleting "+lowerClass+" with id {}\","+lowerClass+"Id);");
			
			deleteBlock.directStatement(lowerClass+"Service.deleteById("+keyClass.getName()+".valueOf("+lowerClass+"Id));");
			deleteBlock.directStatement("return "+response+".build();");
			
			//Insert
			JMethod insert = myClass.method(JMod.PUBLIC, ResponseEntity.class, "insert"+className+"");
			insert.annotate(ResponseBody.class);
			JAnnotationUse requestMappingInsert = insert.annotate(RequestMapping.class);
			requestMappingInsert.param("method",RequestMethod.PUT);
			orderParam= insert.param(entity,lowerClass+"");
			orderParam.annotate(RequestBody.class);
			JBlock insertBlock= insert.body();
			insertBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(lowerClass)+"Id()!=null)");
			insertBlock.directStatement("log.info(\"Inserting "+lowerClass+" like {}\","+entityManager.getDescriptionField(true)+");");
			insertBlock.directStatement(Utility.getFirstUpper(className)+" inserted"+className+"="+lowerClass+"Service.insert("+lowerClass+");");
			insertBlock.directStatement("getRightMapping(inserted"+className+");");
			insertBlock.directStatement("log.info(\"Inserted "+lowerClass+" with id {}\",inserted"+lowerClass+".get"+Utility.getFirstUpper(lowerClass)+"Id());");
			
			insertBlock.directStatement("return "+response+".body(inserted"+className+");");
			//UpdateOrder
			JMethod update = myClass.method(JMod.PUBLIC, ResponseEntity.class, "update"+className+"");
			update.annotate(ResponseBody.class);
			JAnnotationUse requestMappingUpdate = update.annotate(RequestMapping.class);
			requestMappingUpdate.param("method",RequestMethod.POST);
			orderParam= update.param(entity,lowerClass+"");
			orderParam.annotate(RequestBody.class);
			JBlock updateBlock= update.body();
			updateBlock.directStatement("log.info(\"Updating "+lowerClass+" with id {}\","+lowerClass+".get"+Utility.getFirstUpper(lowerClass)+"Id());");
			
			updateBlock.directStatement(Utility.getFirstUpper(className)+" updated"+className+"="+lowerClass+"Service.update("+lowerClass+");");
			updateBlock.directStatement("getRightMapping(updated"+className+");");
			updateBlock.directStatement("return "+response+".body(updated"+className+");");
			
			//get Right Mapping -List
			JMethod getRightMappingList = myClass.method(JMod.PRIVATE, listClass, "getRightMapping");
			getRightMappingList.param(listClass, lowerClass+"List");
			JBlock getRightMappingListBlock = getRightMappingList.body();
			getRightMappingListBlock.directStatement("for ("+Utility.getFirstUpper(className)+" "+lowerClass+": "+lowerClass+"List)");
			getRightMappingListBlock.directStatement("{");
			getRightMappingListBlock.directStatement("getRightMapping("+lowerClass+");");
			getRightMappingListBlock.directStatement("}");
			getRightMappingListBlock.directStatement("return "+lowerClass+"List;");
			
			JMethod getRightMapping= myClass.method(JMod.PRIVATE, void.class, "getRightMapping");
			getRightMapping.param(entity, lowerClass);
			JBlock getRightMappingBlock = getRightMapping.body();
			
			RestGenerator.generateRightMapping_v3(entity, getRightMappingBlock);
			
			
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		saveFile(codeModel);
	
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
	
	
	private static void generateRightMapping_v3(Entity entity, JBlock block)
	{
		EntityManager entityManager = new EntityManagerImpl(entity);
		String lowerClass= "";
		for (Relationship relationship: entity.getRelationshipList())
			
		{
			lowerClass=entity.getName();
			if (relationship.isList())
			{
				block.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(relationship.getName())+"List()!=null)");
				block.directStatement("for ("+relationship.getEntityTarget().getName()+" "+Utility.getFirstLower(relationship.getName())+" :"+lowerClass+".get"+Utility.getFirstUpper(relationship.getName())+"List())\n");
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
				if (targetRelationship.isList())
					block.directStatement(lowerClass+".set"+Utility.getFirstUpper(targetRelationship.getName())+"List(null);");
				else
					block.directStatement(lowerClass+".set"+Utility.getFirstUpper(targetRelationship.getName())+"(null);");
			}
			
			
			block.directStatement("}");
			
		}
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
