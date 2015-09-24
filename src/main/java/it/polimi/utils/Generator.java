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

import org.hibernate.internal.jaxb.mapping.hbm.JaxbParamElement;
import org.hibernate.metamodel.domain.JavaType;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.HTMLCodeGenerator;
import antlr.JavaCodeGenerator;

public class Generator {

	private static String directory;
	
	public static class Field{
		private String name;
		private Class fieldClass;
		private JClass compositeClass;
		public Field(String name, Class fieldClass, JClass compositeClass)
		{
			this.name=name;
			this.fieldClass=fieldClass;
			this.setCompositeClass(compositeClass);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Class getFieldClass() {
			return fieldClass;
		}
		public void setFieldClass(Class fieldClass) {
			this.fieldClass = fieldClass;
		}
		public JClass getCompositeClass() {
			return compositeClass;
		}
		public void setCompositeClass(JClass compositeClass) {
			this.compositeClass = compositeClass;
		}
	}
	
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
							query=query+" (:"+field.getName()+" is null or :"+field.getName()+" in elements("+alias+"."+field.getName()+")) and";
						}else
						{
								query=query+" (:"+field.getName()+" is null :"+field.getName()+"="+alias+"."+field.getName()+") and";
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
			string=string+getFirstLower(className)+".get"+getFirstUpper(field.getName())+"(),";
		}
		return string.substring(0, string.length()-1);
	}
	
	public static String generateRepository(String className,Class classClass, List<Field> fields){
		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		String searchMethod="";
		try {
			myClass = codeModel._class("it.polimi.repository."+className+"Repository", ClassType.INTERFACE);
			JClass extendedClass = codeModel.ref(CrudRepository.class).narrow(classClass,Long.class);
			myClass._extends(extendedClass);
			myClass.annotate(Repository.class);
			searchMethod="findBy";
			for (Field field: fields)
			{
				JMethod method=myClass.method(JMod.PUBLIC, classClass, "findBy"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));
				//if (field.getCompositeClass()==null)
					method.param(field.getFieldClass()==Date.class ? String.class : field.getFieldClass(), field.getName());
				//else
					//method.param(field.getCompositeClass(), field.getName());
				searchMethod=searchMethod+getFirstUpper(field.getName())+"And";
			}
			searchMethod=searchMethod.substring(0,searchMethod.length()-3);
			JClass listClass=codeModel.ref(List.class).narrow(classClass);
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
	
	public static void generateServiceInterface(String className, Class classClass, Class keyClass)
	{

		JCodeModel	codeModel = new JCodeModel();
		JDefinedClass myClass= null;
		try {
			myClass = codeModel._class("it.polimi.service."+className+"Service", ClassType.INTERFACE);
			JClass listClass = codeModel.ref(List.class).narrow(classClass);
			JMethod findById = myClass.method(JMod.PUBLIC, classClass, "findById");
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
	
	
	public static void generateServiceImpl(String className, Class classClass, Class keyClass,List<Field> fields,Class interfaceClass,Class repositoryClass,String searchMethod)
	{
	
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
			
			JMethod findById = myClass.method(JMod.PUBLIC, classClass, "findById");
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
			update.param(classClass, lowerClass);
			JBlock updateBlock= update.body();
			updateBlock.directStatement("return "+lowerClass+"Repository.save("+lowerClass+");");
			
			
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

	public static void generateController(String className, Class classClass, Class keyClass,Class serviceClass)
	{
	
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JCodeModel	codeModel = new JCodeModel();
		JClass placeListClass = codeModel.ref(List.class).narrow(Place.class);
		JClass personClass= codeModel.ref(Person.class);
		

		System.out.println(personClass.fullName()+"-"+personClass.name());
		
		System.out.println(placeListClass.fullName()+"-"+placeListClass.name());
		List<Field> fields = new ArrayList<Field>();
		
		fields.add(new Field("orderId",Long.class,null));
		fields.add(new Field("name",String.class,null));
		fields.add(new Field("timeslotDate",Date.class,null));
		fields.add(new Field("person",Person.class,personClass));
		fields.add(new Field("placeList",Place.class,placeListClass));
		String nameEntity="Order";
		File file = new File(""); 
		directory = file.getAbsolutePath()+"\\src\\main\\java";
		//Generator.generateServiceInterface(nameEntity, Place.class, Long.class);
		String searchMethod=Generator.generateRepository(nameEntity, Order.class, fields);
		//String searchMethod="findByPlaceIdAndDescriptionAndOrder";
		System.out.println(searchMethod);
		//Generator.generateServiceImpl(nameEntity, Place.class, Long.class,fields,PlaceService.class,PlaceRepository.class,searchMethod);
		//Generator.generateController(nameEntity, Place.class, Long.class, PlaceService.class);
	}

}
