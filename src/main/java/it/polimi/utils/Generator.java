package it.polimi.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

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

import antlr.HTMLCodeGenerator;

public class Generator {

	private String directory;

	private Class classClass;
	
	private String className;
	
	private String fullClassName;
	
	private String alias;
	
	private List<Field> fields;
	
	private Class keyClass;
	
	private ReflectionManager reflectionManager;
	
	public Generator(Class classClass)
	{
		this.classClass=classClass;
		this.fullClassName=Generator.getFirstLower(classClass.getName());
		File file = new File(""); 
		this.directory = file.getAbsolutePath()+"\\src\\main\\java";
		reflectionManager= new ReflectionManager(classClass);
		this.className=reflectionManager.parseName();
		this.alias=this.className.substring(0,1);
		this.fields=reflectionManager.generateField();
		keyClass= reflectionManager.getKeyClass();
		
	}
	
	
	public static String getFirstLower(String string)
	{
		return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toLowerCase());
	}
	
	public static String getFirstUpper(String string)
	{
		return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toUpperCase());
	}
	
	public String getSearchQuery(JMethod method)
	{
		String query="select "+alias+" from "+className+" "+alias+ " where ";
		for (Field field: fields)
		{
			JVar param = method.param(field.getFieldClass()==Date.class ? String.class : field.getFieldClass(), field.getName());
			JAnnotationUse annotationParam= param.annotate(Param.class);
			annotationParam.param("value", field.getName());
			if (field.getFieldClass()==Date.class)
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
	
	public String getAllParam()
	{
		String string="";
		for (Field field: fields)
		{
			if (field.getFieldClass()==Date.class)
			{
				string=string+"it.polimi.utils.Utility.formatDate("+getFirstLower(className)+".get"+getFirstUpper(field.getName())+"()),";
			}else
			{
				if (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List"))
					string=string+getFirstLower(className)+".get"+getFirstUpper(field.getName())+"List()==null? null :"+getFirstLower(className)+".get"+getFirstUpper(field.getName())+"List().get(0),";
				else
					string=string+getFirstLower(className)+".get"+getFirstUpper(field.getName())+"(),";
			}
		}
		return string.substring(0, string.length()-1);
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
					method.param(field.getFieldClass()==Date.class ? String.class : field.getFieldClass(), field.getName());
					//else
					//method.param(field.getCompositeClass(), field.getName());
				}
				searchMethod=searchMethod+getFirstUpper(field.getName())+"And";
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
			findByIdBlock.directStatement("return "+lowerClass+"Repository.findBy"+Generator.getFirstUpper(className)+"Id("+lowerClass+"Id);");
			//findByIdBlock._return(findByIdExpression);
			
			//search
			JMethod findLike=myClass.method(JMod.PUBLIC, listClass, "find");
			findLike.annotate(Override.class);
			findLike.param(classClass, lowerClass);
			JBlock findLikeBlock= findLike.body();
			findLikeBlock.directStatement("return "+lowerClass+"Repository."+searchMethod+"("+getAllParam()+");");
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
			updateBlock.directStatement(Generator.getFirstUpper(className)+" returned"+getFirstUpper(className)+"="+lowerClass+"Repository.save("+lowerClass+");");
			for (Field field: fields)
			{
				if (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List"))
				{
					updateBlock.directStatement("if ("+lowerClass+".get"+getFirstUpper(field.getName())+"List()!=null)");
					updateBlock.directStatement("for ("+(field.getFieldClass().getName())+" "+field.getName()+": "+lowerClass+".get"+getFirstUpper(field.getName())+"List())");
					updateBlock.directStatement("{");
					updateBlock.directStatement(field.getName()+".set"+Generator.getFirstUpper(className)+"("+lowerClass+");");
					updateBlock.directStatement(field.getName()+"Repository.save("+field.getName()+");");
					updateBlock.directStatement("}");
				}
			}
			updateBlock.directStatement("return returned"+getFirstUpper(className)+";");
			
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
			searchBlock.directStatement("return "+response+".body("+lowerClass+"List);");
			//findByIdBlock._return(findByIdExpression);
			
			//getOrderById  
			JMethod getById=myClass.method(JMod.PUBLIC, ResponseEntity.class, "get"+className+"ById");
			getById.annotate(ResponseBody.class);
			JAnnotationUse requestMappingGetById = getById.annotate(RequestMapping.class);
			requestMappingGetById.param("value", "/{"+lowerClass+"Id}");
			requestMappingGetById.param("method",RequestMethod.GET);
			orderParam= getById.param(String.class,lowerClass+"Id");
			orderParam.annotate(PathVariable.class);
			JBlock getByIdBlock= getById.body();
			getByIdBlock.directStatement("return "+response+".body("+lowerClass+"Service.findById(Long.valueOf("+lowerClass+"Id)));");
			//findByIdBlock._return(findByIdExpression);
			
			//deleteOrderById
			JMethod delete = myClass.method(JMod.PUBLIC, ResponseEntity.class, "delete"+className+"ById");
			delete.annotate(ResponseBody.class);
			JAnnotationUse requestMappingDelete = delete.annotate(RequestMapping.class);
			requestMappingDelete.param("value", "/{"+getFirstLower(className)+"Id}");
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
			
			insertBlock.directStatement(Generator.getFirstUpper(className)+" inserted"+className+"="+lowerClass+"Service.insert("+lowerClass+");");
			insertBlock.directStatement("return "+response+".body(inserted"+className+");");
			//UpdateOrder
			JMethod update = myClass.method(JMod.PUBLIC, ResponseEntity.class, "update"+className+"");
			update.annotate(ResponseBody.class);
			JAnnotationUse requestMappingUpdate = update.annotate(RequestMapping.class);
			requestMappingUpdate.param("method",RequestMethod.POST);
			orderParam= update.param(classClass,lowerClass+"");
			orderParam.annotate(RequestBody.class);
			JBlock updateBlock= update.body();
			updateBlock.directStatement(Generator.getFirstUpper(className)+" updated"+className+"="+lowerClass+"Service.update("+lowerClass+");");
			updateBlock.directStatement("return "+response+".body(updated"+className+");");
			
			
			
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
			fieldList = reflectionManager.generateField();
			searchMethod=generateRepository();
			//String searchMethod="findByOrderIdAndNameAndTimeslotDateAndPersonAndPlace";
			//System.out.println(searchMethod);
			//Class repositoryClass=Class.forName(modelClass.getName().replace(".model.", ".repository.")+"Repository");
			String filePath=classClass.getName().replace(".model.", ".repository.");
			filePath=filePath.replace(".", "\\");
			File fileRepository = new File(directory+"\\"+filePath+"Repository.java");
			File fileService = new File(directory+"\\"+filePath.replace("repository", "service")+"Service.java");
			File testFile= new File(directory+"\\"+"it\\polimi\\utils\\Field.java");
			Class repositoryClass=null;
			Class serviceClass=null;
			if (fileRepository.exists())
			{
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
					classLoader= URLClassLoader.newInstance(new URL[] {fileRepository.toURL(),testFile.toURL()});
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Class test=classLoader.loadClass("it.polimi.utils.Field");

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
	
	public static void main(String[] args) {

		Reflections reflections = new Reflections("it.polimi.model");
		Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);
		List<Class> dependencyClass = new ArrayList<Class>();
		
		for (Class modelClass: allClasses)
		{
			Generator generator = new Generator(modelClass);
			generator.generateRESTClasses(dependencyClass,true);
		}
		for (Class modelClass:dependencyClass)
		{
			Generator generator = new Generator(modelClass);
			generator.generateRESTClasses(dependencyClass,false);
		}
		
	}

}
