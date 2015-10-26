package it.polimi.generation;

import it.polimi.model.mountain.Mountain;
import it.polimi.model.ospedale.Ambulatorio;
import it.polimi.model.ospedale.Paziente;
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

	private Class classClass;
	
	private String className;
	
	private String fullClassName;
	
	private String alias;
	
	private List<Field> fields;
	
	private Class keyClass;
	
	private ReflectionManager reflectionManager;
	
	/**
	 * Constructor
	 * @param classClass
	 */
	public RestGenerator(Class classClass)
	{
		this.classClass=classClass;
		this.fullClassName=Utility.getFirstLower(classClass.getName());
		File file = new File(""); 
		this.directory = file.getAbsolutePath()+"\\src\\main\\java";
		reflectionManager= new ReflectionManager(classClass);
		this.className=reflectionManager.parseName();
		this.alias=this.className.substring(0,1);
		this.fields=reflectionManager.getFieldList();
		keyClass= reflectionManager.getKeyClass();
		
	}
	
	/**
	 * Creates the search query with not null options
	 * @param method
	 * @return
	 */
	private String getSearchQuery(JMethod method)
	{
		String query="select "+alias+" from "+Utility.getFirstUpper(className)+" "+alias+ " where ";
		for (Field field: fields)
		{
			JVar param = method.param(ReflectionManager.getRightParamClass(field), field.getName());
			JAnnotationUse annotationParam= param.annotate(Param.class);
			annotationParam.param("value", field.getName());
			if (ReflectionManager.isTimeField(field))
			{
				query= query + " (:"+field.getName()+" is null or cast(:"+field.getName()+" as string)=cast(date_trunc('seconds',e."+field.getName()+") as string)) and";
			}
			else
			{
				if (ReflectionManager.isDateField(field))
				{
					query= query+" (:"+field.getName()+" is null or cast(:"+field.getName()+" as string)=cast(date("+alias+"."+field.getName()+") as string)) and";
				} else
				{
					if (field.getFieldClass()==String.class)
					{
						query= query+" (:"+field.getName()+" is null or :"+field.getName()+"='' or cast(:"+field.getName()+" as string)="+alias+"."+field.getName()+") and";
					} else
					{
						if (field.getCompositeClass()==null)
						{
							query=query+" (:"+field.getName()+" is null or cast(:"+field.getName()+" as string)=cast("+alias+"."+field.getName()+" as string)) and";

						} else
						{ // Entity or entity list!!!
							if (field.getCompositeClass().fullName().contains("java.util.List"))
							{
								query=query+" (:"+field.getName()+" in elements("+alias+"."+field.getName()+"List)  or :"+field.getName()+" is null) and";
							}else
							{
								query=query+" (:"+field.getName()+"="+alias+"."+field.getName()+" or :"+field.getName()+" is null) and";
							}

						}

					}

				}}
		}
		query=query.substring(0,query.length()-3);
		return query;
	}
	
	/**
	 * Main method that generates the classes
	 * @param dependencyClass
	 * @param jumpDependency
	 */
	public void generateRESTClasses(List<Class> dependencyClass, Boolean jumpDependency)
	{
		System.out.println("working for "+classClass.getName());
		String searchMethod="";
		if (jumpDependency && reflectionManager.hasList())
		{
			System.out.println(classClass.getName()+" has list. pass away.");
			dependencyClass.add(classClass);
			return;
		}
		generateServiceInterface();
		List<Field> fieldList=null;
		try {
			fieldList = reflectionManager.getFieldList();
			searchMethod=generateRepository();
			String filePath=classClass.getName().replace(".model.", ".repository.");
			filePath=filePath.replace(".", "\\");
			File fileRepository = new File(directory+"\\"+filePath+"Repository.java");
			File fileService = new File(directory+"\\"+filePath.replace("repository", "service")+"Service.java");
			Class repositoryClass=null;
			Class serviceClass=null;
			if (fileRepository.exists())
			{
				//TODO improve...
				System.out.println("esiste!");
				URLClassLoader classLoader=null;
				URL urlRepository=null;
				try {
					urlRepository=fileRepository.toURL();
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				
				try {
					classLoader= URLClassLoader.newInstance(new URL[] {fileRepository.toURL()});
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				repositoryClass=Class.forName(classClass.getName().replace(".model.", ".repository.")+"Repository", true, classLoader);
				serviceClass=Class.forName(classClass.getName().replace(".model.", ".service.")+"Service", true, classLoader);
				
			}
			else
				System.out.println(fileRepository.getAbsolutePath()+" does not exists!");
			generateServiceImpl(serviceClass,repositoryClass,searchMethod);
			generateController(serviceClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generate the repository class
	 * @return
	 */
	private String generateRepository(){
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		String searchMethod="";
		try {
			myClass = codeModel._class(""+fullClassName.replace(".model.", ".repository.")+"Repository", ClassType.INTERFACE);
			JClass extendedClass = codeModel.ref(CrudRepository.class).narrow(classClass,keyClass);
			myClass._extends(extendedClass);
			myClass.annotate(Repository.class);
			JClass listClass=codeModel.ref(List.class).narrow(classClass);
			searchMethod="findBy";
			for (Field field: fields)
			{
				if (field.getCompositeClass()==null)
				{
					JMethod method=myClass.method(JMod.PUBLIC, listClass, "findBy"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));
					method.param(ReflectionManager.getRightParamClass(field), field.getName());
				} else
				{
					if (!field.getCompositeClass().fullName().contains("java.util.List"))
					{ // find by entity
						JMethod method= myClass.method(JMod.PUBLIC, listClass, "findBy"+Utility.getFirstUpper(field.getName()));
						method.param(field.getFieldClass(), field.getName());
					}
				}
				searchMethod=searchMethod+Utility.getFirstUpper(field.getName())+"And";
			}
			searchMethod=searchMethod.substring(0,searchMethod.length()-3);
			JMethod method=myClass.method(JMod.PUBLIC, listClass, searchMethod);
			String alias=className.substring(0, 1).toLowerCase();
			String query=getSearchQuery(method);
			JAnnotationUse annotationQuery= method.annotate(Query.class);
			annotationQuery.param("value", query);
			
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		saveFile(codeModel);
		return searchMethod;
	}
	/**
	 * Generates the service interface
	 */
	private void generateServiceInterface()
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			myClass = codeModel._class(""+fullClassName.replace(".model.", ".service.")+"Service", ClassType.INTERFACE);
			className=reflectionManager.parseName();
			JClass listClass = codeModel.ref(List.class).narrow(classClass);
			JMethod findById = myClass.method(JMod.PUBLIC, listClass, "findById");
			String lowerClass= className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
			findById.param(keyClass,className+"Id");
			JMethod findLike=myClass.method(JMod.PUBLIC, listClass, "find");
			findLike.param(classClass, className);
			JMethod deleteById = myClass.method(JMod.PUBLIC, void.class, "deleteById");
			deleteById.param(keyClass, className+"Id");
			JMethod insert= myClass.method(JMod.PUBLIC, classClass, "insert");
			insert.param(classClass, className);
			JMethod update= myClass.method(JMod.PUBLIC, classClass, "update");
			update.param(classClass, className);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		saveFile(codeModel);
	
	}
	
	/**
	 * Generates the serviceImpl class
	 * 
	 * @param interfaceClass
	 * @param repositoryClass
	 * @param searchMethod
	 */
	private void generateServiceImpl(Class interfaceClass,Class repositoryClass,String searchMethod)
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		ReflectionManager reflectionManager = new ReflectionManager(classClass);
		try {
			myClass = codeModel._class(""+fullClassName.replace(".model.", ".service.")+"ServiceImpl", ClassType.CLASS);
			className=reflectionManager.parseName();
			myClass._implements(interfaceClass);
			myClass.annotate(Service.class);
			JClass listClass = codeModel.ref(List.class).narrow(classClass);
			String lowerClass= className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
			JVar repository = myClass.field(JMod.PUBLIC, repositoryClass, lowerClass+"Repository");
			repository.annotate(Autowired.class);
			for (Field field: fields)
			{
				if (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List") && field.getRepositoryClass()!=null)
				{
					JVar fieldListRepository = myClass.field(JMod.PUBLIC, field.getRepositoryClass(), field.getName()+"Repository");
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
			findLike.param(classClass, lowerClass);
			JBlock findLikeBlock= findLike.body();
			findLikeBlock.directStatement("return "+lowerClass+"Repository."+searchMethod+"("+reflectionManager.getAllParam()+");");
			//delete
			JMethod deleteById = myClass.method(JMod.PUBLIC, void.class, "deleteById");
			deleteById.annotate(Override.class);
			deleteById.param(keyClass, lowerClass+"Id");
			JBlock deleteBlock= deleteById.body();
			deleteBlock.directStatement(""+lowerClass+"Repository.delete("+lowerClass+"Id);");
			deleteBlock.directStatement("return;");
			
			//insert
			JMethod insert= myClass.method(JMod.PUBLIC, classClass, "insert");
			insert.annotate(Override.class);
			insert.param(classClass, lowerClass);
			JBlock insertBlock= insert.body();
			insertBlock.directStatement("return "+lowerClass+"Repository.save("+lowerClass+");");
			//update
			JMethod update= myClass.method(JMod.PUBLIC, classClass, "update");
			update.annotate(Override.class);
			update.annotate(Transactional.class);
			update.param(classClass, lowerClass);
			JBlock updateBlock= update.body();
			for (Field field: fields)
			{
				if (field.getCompositeClass()!=null)
				if (field.getCompositeClass().fullName().contains("java.util.List"))
				{
					updateBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"List()!=null)");
					updateBlock.directStatement("for ("+(field.getFieldClass().getName())+" "+field.getName()+": "+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"List())");
					updateBlock.directStatement("{");
					/*
					 *  for (it.polimi.model.ospedale.Paziente paziente: ambulatorio.getPazienteList())
        {
        	Paziente savedPaziente = pazienteRepository.findOne(paziente.getPazienteId());
        	Boolean found= false;
        	for (Ambulatorio tempAmb : savedPaziente.getAmbulatorioList())
        	{
        		if (tempAmb.getAmbulatorioId().equals(ambulatorio.getAmbulatorioId()))
        		{
        			found=true;
        			break;
        		}
        	}
        	
        	if (!found)
        		savedPaziente.getAmbulatorioList().add(ambulatorio);
        }
					 */
					//lowerclass:  ambulatorio, field.getName: paziente
					if (ReflectionManager.hasManyToManyAssociation(field.getFieldClass(), className))
					{
						updateBlock.directStatement(field.getFieldClass().getName()+" saved"+Utility.getFirstUpper(field.getName())+" = "+field.getName()+"Repository.findOne("+field.getName()+".get"+Utility.getFirstUpper(field.getName())+"Id());");
						updateBlock.directStatement("Boolean found=false; ");
						updateBlock.directStatement("for ("+Utility.getFirstUpper(className)+" temp"+Utility.getFirstUpper(className)+" : saved"+Utility.getFirstUpper(field.getName())+".get"+Utility.getFirstUpper(className)+"List())");
						updateBlock.directStatement("{");
						updateBlock.directStatement("if (temp"+Utility.getFirstUpper(className)+".get"+Utility.getFirstUpper(className)+"Id().equals("+lowerClass+".get"+Utility.getFirstUpper(className)+"Id()))");
						updateBlock.directStatement("{");
						updateBlock.directStatement("found=true;");
						updateBlock.directStatement("break;");
						updateBlock.directStatement("}");
						updateBlock.directStatement("}");
						updateBlock.directStatement("if (!found)");
						updateBlock.directStatement("saved"+Utility.getFirstUpper(field.getName())+".get"+Utility.getFirstUpper(className)+"List().add("+lowerClass+");");
						
					} else
					{
						updateBlock.directStatement(field.getName()+".set"+Utility.getFirstUpper(className)+"("+lowerClass+");");
					}
					updateBlock.directStatement("}");
				} 
			}
			updateBlock.directStatement(Utility.getFirstUpper(className)+" returned"+Utility.getFirstUpper(className)+"="+lowerClass+"Repository.save("+lowerClass+");");
			for (Field field: fields)
			{
				if (field.getCompositeClass()!=null)
				{
					ReflectionManager fieldReflectionManager = new ReflectionManager(field.getFieldClass());
					if (!field.getCompositeClass().fullName().contains("java.util.List") && fieldReflectionManager.containFieldWithClass(classClass))			
					{
						updateBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"()!=null)");
						updateBlock.directStatement("{");
						updateBlock.directStatement("List<"+Utility.getFirstUpper(className)+"> "+className+"List = "+lowerClass+"Repository.findBy"+Utility.getFirstUpper(field.getName())+"( "+lowerClass+".get"+Utility.getFirstUpper(field.getName())+"());");
						updateBlock.directStatement("if (!"+lowerClass+"List.contains(returned"+Utility.getFirstUpper(className)+"))");
						updateBlock.directStatement(""+lowerClass+"List.add(returned"+Utility.getFirstUpper(className)+");");
						updateBlock.directStatement("returned"+Utility.getFirstUpper(className)+".get"+Utility.getFirstUpper(field.getName())+"().set"+Utility.getFirstUpper(className)+"List("+lowerClass+"List);");
						updateBlock.directStatement("}");
					}
				}
			}
			
			updateBlock.directStatement(" return returned"+Utility.getFirstUpper(className)+";");
				
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		saveFile(codeModel);
	
	}
	/**
	 * Generates the controller class
	 * @param serviceClass
	 */
	private void generateController(Class serviceClass)
	{
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		String response="ResponseEntity.ok()";
		
		try {
			myClass = codeModel._class(""+fullClassName.replace(".model.", ".controller.")+"Controller", ClassType.CLASS);
			className=reflectionManager.parseName();
			JClass listClass = codeModel.ref(List.class).narrow(classClass);
			String lowerClass= className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
			myClass.annotate(Controller.class);
			JAnnotationUse requestMappingClass = myClass.annotate(RequestMapping.class);
			requestMappingClass.param("value", "/"+lowerClass);
			//declare service
			JVar repository = myClass.field(JMod.PUBLIC, serviceClass, lowerClass+"Service");
			repository.annotate(Autowired.class);
			//private final static Logger log = LoggerFactory.getLogger(Mountain.class);
			JClass factory = codeModel.directClass("org.slf4j.LoggerFactory");
			JVar log = myClass.field(JMod.PRIVATE+JMod.STATIC+JMod.FINAL, Logger.class, "log");
			JClass jClassClass = codeModel.ref(classClass);
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
			JVar orderParam= search.param(classClass,lowerClass);
			orderParam.annotate(RequestBody.class);
			JBlock searchBlock= search.body();
			JVar entityList= searchBlock.decl(listClass, lowerClass+"List");
			// log.info("Searching mountain like {}",mountain);
			//TODO ,anage null on description field
			searchBlock.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(lowerClass)+"Id()!=null)");
			searchBlock.directStatement(" log.info(\"Searching "+lowerClass+" like {}\","+reflectionManager.getDescriptionField(true)+");");
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
			orderParam= insert.param(classClass,lowerClass+"");
			orderParam.annotate(RequestBody.class);
			JBlock insertBlock= insert.body();
			insertBlock.directStatement("log.info(\"Inserting "+lowerClass+" like {}\","+reflectionManager.getDescriptionField(true)+");");
			insertBlock.directStatement(Utility.getFirstUpper(className)+" inserted"+className+"="+lowerClass+"Service.insert("+lowerClass+");");
			insertBlock.directStatement("getRightMapping(inserted"+className+");");
			insertBlock.directStatement("log.info(\"Inserted "+lowerClass+" with id {}\",inserted"+lowerClass+".get"+Utility.getFirstUpper(lowerClass)+"Id());");
			
			insertBlock.directStatement("return "+response+".body(inserted"+className+");");
			//UpdateOrder
			JMethod update = myClass.method(JMod.PUBLIC, ResponseEntity.class, "update"+className+"");
			update.annotate(ResponseBody.class);
			JAnnotationUse requestMappingUpdate = update.annotate(RequestMapping.class);
			requestMappingUpdate.param("method",RequestMethod.POST);
			orderParam= update.param(classClass,lowerClass+"");
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
			getRightMapping.param(classClass, lowerClass);
			JBlock getRightMappingBlock = getRightMapping.body();
			
			RestGenerator.generateRightMapping_v3(classClass, getRightMappingBlock);
			
			
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
	
	
	private static void generateRightMapping_v3(Class theClass, JBlock block)
	{
		ReflectionManager reflectionManager = new ReflectionManager(theClass);
		String lowerClass= "";
		for (Field mainField: reflectionManager.getChildrenFieldList())
		{
			lowerClass=reflectionManager.parseName();
			if (mainField.getCompositeClass().fullName().contains("java.util.List"))
			{
				block.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(mainField.getName())+"List()!=null)");
				block.directStatement("for ("+mainField.getFieldClass().getName()+" "+Utility.getFirstLower(mainField.getName())+" :"+lowerClass+".get"+Utility.getFirstUpper(mainField.getName())+"List())\n");
				block.directStatement("{\n");
				lowerClass=mainField.getName();
			}
			 else
			 {
				 
				 block.directStatement("if ("+lowerClass+".get"+Utility.getFirstUpper(mainField.getName())+"()!=null)");
				 block.directStatement("{");
				 lowerClass=lowerClass+".get"+Utility.getFirstUpper(mainField.getName())+"()";
			 }
			ReflectionManager fieldReflectionManager = new ReflectionManager(mainField.getFieldClass());
			for (Field field: fieldReflectionManager.getChildrenFieldList())
			{
				if (field.getCompositeClass().fullName().contains("java.util.List"))
					block.directStatement(lowerClass+".set"+Utility.getFirstUpper(field.getName())+"List(null);");
				else
					block.directStatement(lowerClass+".set"+Utility.getFirstUpper(field.getName())+"(null);");
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
	private static void generateRightMapping_v2(Class theClass, JBlock block,List<Class> parentClass,String entityName)
	{
		ReflectionManager reflectionManager = new ReflectionManager(theClass);
		parentClass.add(theClass);
		String lowerClass=entityName!=null? entityName:reflectionManager.parseName();
		for (Field field: reflectionManager.getChildrenFieldList())
		{
			String newEntityName= lowerClass;
			if (field.getCompositeClass().fullName().contains("java.util.List"))
				newEntityName=Utility.getFirstLower(field.getName());
			else
				newEntityName=newEntityName+".get"+Utility.getFirstUpper(field.getName())+"()";
			ArrayList<Class> oldParentClassList = (ArrayList<Class>) ((ArrayList<Class>) parentClass).clone();
			if (field.getCompositeClass().fullName().contains("java.util.List"))
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
	}
	
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
