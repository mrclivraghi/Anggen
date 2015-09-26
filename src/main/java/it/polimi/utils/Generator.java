package it.polimi.utils;

import it.polimi.model.Order;
import it.polimi.model.Person;
import it.polimi.model.Place;
import it.polimi.repository.PersonRepository;
import it.polimi.repository.PlaceRepository;
import it.polimi.service.OrderService;
import it.polimi.service.PersonService;
import it.polimi.service.PlaceService;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.hibernate.internal.jaxb.mapping.hbm.JaxbParamElement;
import org.hibernate.metamodel.domain.JavaType;
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
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JForLoop;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPrimitiveType;
import com.sun.codemodel.JType;
import com.sun.codemodel.JTypeVar;
import com.sun.codemodel.JVar;

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

import antlr.HTMLCodeGenerator;
import antlr.JavaCodeGenerator;

public class Generator {

	private static String directory;
	public static final Class keyClass=Long.class;
	
	public static String getFirstLower(String string)
	{
		return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toLowerCase());
	}
	
	public static String getFirstUpper(String string)
	{
		return string.replaceFirst(string.substring(0, 1), string.substring(0, 1).toUpperCase());
	}
	
	public static String getSearchQuery(String alias,String className,JMethod method, List<Field> fields)
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
	
	public static String getAllParam(List<Field> fields, String className)
	{
		String string="";
		for (Field field: fields)
		{
			if (field.getFieldClass()==Date.class)
			{
				string=string+"Utility.formatDate("+getFirstLower(className)+".get"+getFirstUpper(field.getName())+"()),";
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
	
	public static String generateRepository(Class classClass, List<Field> fields){
		String className=getFirstLower(classClass.getName());
		JCodeModel	codeModel = new JCodeModel();
		
		JDefinedClass myClass= null;
		String searchMethod="";
		try {
			myClass = codeModel._class("it.polimi.repository."+className+"Repository", ClassType.INTERFACE);
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
			String query=getSearchQuery(alias, className, method, fields);
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
	
	public static void generateServiceInterface(Class classClass)
	{
		String className=getFirstLower(classClass.getName());
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			myClass = codeModel._class("it.polimi.service."+className+"Service", ClassType.INTERFACE);
			JClass listClass = codeModel.ref(List.class).narrow(classClass);
			JMethod findById = myClass.method(JMod.PUBLIC, listClass, "findById");
			String lowerClass= className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toLowerCase());
			findById.param(keyClass,lowerClass+"Id");
			JMethod findLike=myClass.method(JMod.PUBLIC, listClass, "find");
			findLike.param(classClass, lowerClass);
			JMethod deleteById = myClass.method(JMod.PUBLIC, void.class, "deleteById");
			deleteById.param(keyClass, lowerClass+"Id");
			JMethod insert= myClass.method(JMod.PUBLIC, classClass, "insert");
			insert.param(classClass, lowerClass);
			JMethod update= myClass.method(JMod.PUBLIC, classClass, "update");
			update.param(classClass, lowerClass);
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
	
	
	public static void generateServiceImpl(Class classClass,List<Field> fields,Class interfaceClass,Class repositoryClass,String searchMethod)
	{
		String className=getFirstLower(classClass.getName());
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			myClass = codeModel._class("it.polimi.service."+className+"ServiceImpl", ClassType.CLASS);
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
			findByIdBlock.directStatement("return "+lowerClass+"Repository.findBy"+className+"Id("+lowerClass+"Id);");
			//findByIdBlock._return(findByIdExpression);
			
			//search
			JMethod findLike=myClass.method(JMod.PUBLIC, listClass, "find");
			findLike.annotate(Override.class);
			findLike.param(classClass, lowerClass);
			JBlock findLikeBlock= findLike.body();
			findLikeBlock.directStatement("return "+lowerClass+"Repository."+searchMethod+"("+getAllParam(fields, className)+");");
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
			/*
			 * Order returnedOrder=orderRepository.save(order);
		if (order.getPlaceList()!=null)
		for (Place place: order.getPlaceList())
		{
			place.setOrder(order);
			placeRepository.save(place);
		}
		return returnedOrder;
			 */
			updateBlock.directStatement(className+" returned"+className+"="+lowerClass+"Repository.save("+lowerClass+");");
			for (Field field: fields)
			{
				if (field.getCompositeClass()!=null && field.getCompositeClass().fullName().contains("java.util.List"))
				{
					updateBlock.directStatement("if ("+lowerClass+".getPlaceList()!=null)");
					updateBlock.directStatement("for ("+getFirstUpper(field.getName())+" "+field.getName()+": "+lowerClass+".get"+getFirstUpper(field.getName())+"List())");
					updateBlock.directStatement("{");
					updateBlock.directStatement(field.getName()+".set"+className+"("+lowerClass+");");
					updateBlock.directStatement(field.getName()+"Repository.save("+field.getName()+");");
					updateBlock.directStatement("}");
					/**/
				}
			}
			updateBlock.directStatement("return returned"+className+";");
			
		} catch (JClassAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			   codeModel.build(new File(directory));
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
	
	}

	public static void generateController(Class classClass,Class serviceClass)
	{
		String className=getFirstLower(classClass.getName());
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		String response="ResponseEntity.ok()";
		
		try {
			myClass = codeModel._class("it.polimi.controller."+className+"Controller", ClassType.CLASS);
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
			searchBlock.directStatement("return "+response+".body("+lowerClass+"Service.find("+lowerClass+"));");
			//findByIdBlock._return(findByIdExpression);
			
			//getOrderById  -- TODO
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
			insertBlock.directStatement(className+" insertedEntity="+lowerClass+"Service.insert("+lowerClass+");");
			insertBlock.directStatement("return "+response+".body(insertedEntity);");
			//UpdateOrder
			JMethod update = myClass.method(JMod.PUBLIC, ResponseEntity.class, "update"+className+"");
			update.annotate(ResponseBody.class);
			JAnnotationUse requestMappingUpdate = update.annotate(RequestMapping.class);
			requestMappingUpdate.param("method",RequestMethod.POST);
			orderParam= update.param(classClass,lowerClass+"");
			orderParam.annotate(RequestBody.class);
			JBlock updateBlock= update.body();
			updateBlock.directStatement(className+" updatedEntity="+lowerClass+"Service.update("+lowerClass+");");
			updateBlock.directStatement("return "+response+".body(updatedEntity);");
			
			
			
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

	
	public static void generateJSP()
	{
		HTMLCodeGenerator codeGenerator = new HTMLCodeGenerator();
	}
	
	
	public static void generateRESTClasses(Class modelClass,List<Class> dependencyClass)
	{
		String searchMethod="";
		try {
			if (ReflectionManager.hasList(modelClass.newInstance()))
			{
				dependencyClass.add(modelClass);
				return;
			}
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Generator.generateServiceInterface(modelClass);
		List<Field> fieldList=null;
		try {
			fieldList = ReflectionManager.generateField(modelClass.newInstance());
			searchMethod=Generator.generateRepository(Order.class, fieldList);
			//String searchMethod="findByOrderIdAndNameAndTimeslotDateAndPersonAndPlace";
			//System.out.println(searchMethod);
			Class repositoryClass=Class.forName(modelClass.getName().replace(".model.", ".repository.")+"Repository");
			Class serviceClass=Class.forName(modelClass.getName().replace(".model.", ".service.")+"Service");
			Generator.generateServiceImpl( modelClass, fieldList,serviceClass,repositoryClass,searchMethod);
			Generator.generateController(modelClass, serviceClass);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		Reflections reflections = new Reflections("it.polimi.model");
		Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);
		List<Class> dependencyClass = new ArrayList<Class>();
		File file = new File(""); 
		directory = file.getAbsolutePath()+"\\src\\main\\java";
		for (Class modelClass: allClasses)
		{
			generateRESTClasses(modelClass, dependencyClass);
		}
		for (Class modelClass:dependencyClass)
		{
			generateRESTClasses(modelClass, dependencyClass);
		}
		
	}

}
