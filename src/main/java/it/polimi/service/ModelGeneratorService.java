package it.polimi.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.polimi.Application;
import it.polimi.boot.SecurityWebApplicationInitializer;
import it.polimi.boot.config.AppConfig;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.FieldType;
import it.polimi.model.domain.Relationship;
import it.polimi.repository.domain.EntityRepository;
import it.polimi.repository.domain.RelationshipRepository;
import it.polimi.utils.Utility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RestController;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;


@Service
public class ModelGeneratorService {
	
	@Autowired
	EntityRepository entityRepository;
	
	
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
	
	private void saveFile(JCodeModel codeModel)
	{
		try {
				File file = new File(""); 
				String directory = file.getAbsolutePath()+"\\src\\main\\java";
			   codeModel.build(new File (directory));
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
		
	}
	
	private Class getFieldClass(Field field)
	{
		Class fieldClass=null;
		switch (field.getFieldType())
		{
			case STRING: fieldClass= String.class;
			case INTEGER: fieldClass=Integer.class;
			case DATE : fieldClass = Date.class;
			case DOUBLE : fieldClass= Double.class;

		}
		return fieldClass;
	}
	
	
	public Map<String,JDefinedClass> getModelClass()
	{
		Map<String,JDefinedClass> modelClassMap= new HashMap<String,JDefinedClass>();
		Iterable<Entity> entityIterable = entityRepository.findAll();
		Iterator<Entity> entityIterator = entityIterable.iterator();
		JCodeModel	codeModel = new JCodeModel();
		while (entityIterator.hasNext())
		{
			Entity tempEntity= entityIterator.next();
			String className = Utility.getFirstUpper(tempEntity.getName());
			String lowerClassName = Utility.getFirstLower(className);
			JDefinedClass myClass= null;
			try {
				myClass = codeModel._class("it.generated.domain."+className, ClassType.CLASS);
				modelClassMap.put(tempEntity.getName(), myClass);
			} catch (JClassAlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		entityIterator = entityIterable.iterator();
		while (entityIterator.hasNext())
		{
			Entity tempEntity = entityIterator.next();
			JDefinedClass myClass = modelClassMap.get(tempEntity.getName());
			
			myClass.annotate(javax.persistence.Entity.class);
				for (Field field : tempEntity.getFieldList())
				{
					Class fieldClass = getFieldClass(field);
					JVar classField = myClass.field(JMod.PRIVATE, fieldClass, field.getName());
					generateGetterAndSetter(myClass, field.getName(), fieldClass);
				}
				for (Relationship relationship : tempEntity.getRelationshipList())
				{
					JClass listClass = codeModel.ref(List.class).narrow(modelClassMap.get(relationship.getEntityTarget().getName()));
					JVar listField = myClass.field(JMod.PRIVATE, listClass, relationship.getEntityTarget().getName()+"List");
					generateGetterAndSetter(myClass, relationship.getEntityTarget().getName()+"List", listClass);
				}
				
				
				saveFile(codeModel);
		}
		
		
		return modelClassMap;
	}

}
