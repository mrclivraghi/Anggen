package it.polimi.generation;

import it.polimi.model.Photo;
import it.polimi.model.SeedQuery;
import it.polimi.utils.Field;
import it.polimi.utils.ReflectionManager;
import it.polimi.utils.Utility;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToOne;

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
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

public class RestGenerator {
	
	
	private String directory;

	private Class classClass;
	
	private String className;
	
	private String fullClassName;
	
	private String alias;
	
	private List<Field> fields;
	
	private Class keyClass;
	
	private ReflectionManager reflectionManager;
	
	
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
	
	public String getSearchQuery(JMethod method)
	{
		String query="select "+alias+" from "+Utility.getFirstUpper(className)+" "+alias+ " where ";
		for (Field field: fields)
		{
			JVar param = method.param(field.getFieldClass()==Date.class || field.getFieldClass()==java.util.Date.class? String.class : field.getFieldClass(), field.getName());
			JAnnotationUse annotationParam= param.annotate(Param.class);
			annotationParam.param("value", field.getName());
			if (field.getFieldClass()==Date.class || field.getFieldClass()==java.util.Date.class)
			{
				query= query+" (:"+field.getName()+" is null or cast(:"+field.getName()+" as string)=cast("+alias+"."+field.getName()+" as string)) and";
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
					
			}
		}
		query=query.substring(0,query.length()-3);
		return query;
	}
	
	
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
			//String searchMethod="findByOrderIdAndNameAndTimeslotDateAndPersonAndPlace";
			//System.out.println(searchMethod);
			//Class repositoryClass=Class.forName(modelClass.getName().replace(".model.", ".repository.")+"Repository");
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					classLoader= URLClassLoader.newInstance(new URL[] {fileRepository.toURL()});
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				repositoryClass=Class.forName(classClass.getName().replace(".model.", ".repository.")+"Repository", true, classLoader);
				serviceClass=Class.forName(classClass.getName().replace(".model.", ".service.")+"Service", true, classLoader);
				
			}
			else
				System.out.println(fileRepository.getAbsolutePath()+" does not exists!");
			//repositoryClass=ClassLoader.getSystemClassLoader().loadClass(modelClass.getName().replace(".model.", ".repository.")+"Repository");
			
			//Class serviceClass=Class.forName(modelClass.getName().replace(".model.", ".service.")+"Service");
			generateServiceImpl(serviceClass,repositoryClass,searchMethod);
			generateController(serviceClass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String generateRepository(){
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		String searchMethod="";
		try {
			myClass = codeModel._class(""+fullClassName.replace(".model.", ".repository.")+"Repository", ClassType.INTERFACE);
			JClass extendedClass = codeModel.ref(CrudRepository.class).narrow(classClass,Long.class);
			myClass._extends(extendedClass);
			myClass.annotate(Repository.class);
			JClass listClass=codeModel.ref(List.class).narrow(classClass);
			searchMethod="findBy";
			for (Field field: fields)
			{
				if (field.getCompositeClass()==null)
				{
					JMethod method=myClass.method(JMod.PUBLIC, listClass, "findBy"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));
					//if (field.getCompositeClass()==null)
					method.param((field.getFieldClass()==Date.class || field.getFieldClass()==java.util.Date.class )? String.class : field.getFieldClass(), field.getName());
					//else
					//method.param(field.getCompositeClass(), field.getName());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			   codeModel.build(new File (directory));
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
		return searchMethod;
	}
	
	public void generateServiceInterface()
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			   codeModel.build(new File (directory));
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
	
	}
	
	
	public void generateServiceImpl(Class interfaceClass,Class repositoryClass,String searchMethod)
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
			//findByIdBlock._return(findByIdExpression);
			
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
					updateBlock.directStatement(field.getName()+".set"+Utility.getFirstUpper(className)+"("+lowerClass+");");
					updateBlock.directStatement("}");
				} 
			}
			updateBlock.directStatement(Utility.getFirstUpper(className)+" returned"+Utility.getFirstUpper(className)+"="+lowerClass+"Repository.save("+lowerClass+");");
			for (Field field: fields)
			{
				if (field.getCompositeClass()!=null)
					if (!field.getCompositeClass().fullName().contains("java.util.List"))			
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
			
			updateBlock.directStatement(" return returned"+Utility.getFirstUpper(className)+";");
				
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		try {
			   codeModel.build(new File(directory));
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
	
	}

	public void generateController(Class serviceClass)
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
			//searchBlock.de
			searchBlock.directStatement(""+lowerClass+"List="+lowerClass+"Service.find("+lowerClass+");");
			searchBlock.directStatement("getRightMapping("+lowerClass+"List);");
			
			searchBlock.directStatement("return "+response+".body("+lowerClass+"List);");
			//findByIdBlock._return(findByIdExpression);
			
			//getById  
			JMethod getById=myClass.method(JMod.PUBLIC, ResponseEntity.class, "get"+className+"ById");
			getById.annotate(ResponseBody.class);
			JAnnotationUse requestMappingGetById = getById.annotate(RequestMapping.class);
			requestMappingGetById.param("value", "/{"+lowerClass+"Id}");
			requestMappingGetById.param("method",RequestMethod.GET);
			orderParam= getById.param(String.class,lowerClass+"Id");
			orderParam.annotate(PathVariable.class);
			JBlock getByIdBlock= getById.body();
			getByIdBlock.directStatement("List<"+Utility.getFirstUpper(className)+"> "+lowerClass+"List="+lowerClass+"Service.findById(Long.valueOf("+lowerClass+"Id));");
			getByIdBlock.directStatement("getRightMapping("+lowerClass+"List);");
			getByIdBlock.directStatement("return "+response+".body("+lowerClass+"List);");
			//findByIdBlock._return(findByIdExpression);
			
			//deleteOrderById
			JMethod delete = myClass.method(JMod.PUBLIC, ResponseEntity.class, "delete"+className+"ById");
			delete.annotate(ResponseBody.class);
			JAnnotationUse requestMappingDelete = delete.annotate(RequestMapping.class);
			requestMappingDelete.param("value", "/{"+Utility.getFirstLower(className)+"Id}");
			requestMappingDelete.param("method",RequestMethod.DELETE);
			orderParam= delete.param(String.class,lowerClass+"Id");
			orderParam.annotate(PathVariable.class);
			JBlock deleteBlock= delete.body();
			deleteBlock.directStatement(lowerClass+"Service.deleteById(Long.valueOf("+lowerClass+"Id));");
			deleteBlock.directStatement("return "+response+".build();");
			
			//InsertOrder
			JMethod insert = myClass.method(JMod.PUBLIC, ResponseEntity.class, "insert"+className+"");
			insert.annotate(ResponseBody.class);
			JAnnotationUse requestMappingInsert = insert.annotate(RequestMapping.class);
			requestMappingInsert.param("method",RequestMethod.PUT);
			orderParam= insert.param(classClass,lowerClass+"");
			orderParam.annotate(RequestBody.class);
			JBlock insertBlock= insert.body();
			
			insertBlock.directStatement(Utility.getFirstUpper(className)+" inserted"+className+"="+lowerClass+"Service.insert("+lowerClass+");");
			insertBlock.directStatement("getRightMapping(inserted"+className+");");
			insertBlock.directStatement("return "+response+".body(inserted"+className+");");
			//UpdateOrder
			JMethod update = myClass.method(JMod.PUBLIC, ResponseEntity.class, "update"+className+"");
			update.annotate(ResponseBody.class);
			JAnnotationUse requestMappingUpdate = update.annotate(RequestMapping.class);
			requestMappingUpdate.param("method",RequestMethod.POST);
			orderParam= update.param(classClass,lowerClass+"");
			orderParam.annotate(RequestBody.class);
			JBlock updateBlock= update.body();
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
			/*
			 *  private void getRightMapping(SeedQuery seedQuery)
    {
    	if (seedQuery.getPhotoList()!=null)
    	for (Photo photo : seedQuery.getPhotoList())
    	{
    		photo.setSeedQuery(null);
    	}
    	if (seedQuery.getMountain()!=null)
    		seedQuery.getMountain().setSeedQueryList(null);
    }
			 */
			JMethod getRightMapping= myClass.method(JMod.PRIVATE, void.class, "getRightMapping");
			getRightMapping.param(classClass, lowerClass);
			JBlock getRightMappingBlock = getRightMapping.body();
			RestGenerator.generateRightMapping_v2(classClass, getRightMappingBlock,new ArrayList<Class>(),null);
			
			
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			   codeModel.build(new File (directory));
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
	
	}
	
	private static void generateRightMapping_v2(Class theClass, JBlock block,List<Class> parentClass,String entityName)
	{
		ReflectionManager reflectionManager = new ReflectionManager(theClass);
		parentClass.add(theClass);
		String lowerClass=entityName!=null? entityName:reflectionManager.parseName();
		for (Field field: reflectionManager.getChildrenFieldList())
		{
			//generate entity.getField() or entity.getfieldList(); 
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
					//block.directStatement(""+Utility.getFirstLower(field.getName())+".set"+Utility.getFirstUpper(lowerClass)+"(null);");
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

					RestGenerator.generateRightMapping_v2(field.getFieldClass(), block,parentClass,newEntityName);
					parentClass=oldParentClassList;
				}
				block.directStatement("}");

			}
		}
	}
	
	private static void generateRightMapping(Class theClass,JBlock block,Class parentClass,String entityName)
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
	
	private static Boolean isBackRelationship(Field field)
	{
		if (field.getAnnotationList()==null || field.getAnnotationList().length==0) return false;
		for (int i=0; i<field.getAnnotationList().length; i++)
		{
			if (field.getAnnotationList()[i].annotationType()==ManyToOne.class)
				return true;
		}
		
		return false;
	}
	
}