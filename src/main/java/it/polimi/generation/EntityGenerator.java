package it.polimi.generation;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.Relationship;
import it.polimi.model.domain.RelationshipType;
import it.polimi.utils.Utility;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

public class EntityGenerator {
	
	
	private Entity entity;
	
	public EntityGenerator(Entity entity)
	{
		this.entity=entity;
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
	
	private void saveFile(JCodeModel codeModel)
	{
		try {
				File file = new File(""); 
				String directory = file.getAbsolutePath()+"\\src\\main\\java";
				System.out.println(directory);
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
	
	
	
	public JDefinedClass getModelClass()
	{
		JCodeModel	codeModel = new JCodeModel();
		String className = Utility.getFirstUpper(entity.getName());
		String lowerClassName = Utility.getFirstLower(className);
		JDefinedClass myClass= null;
		try {
			myClass = codeModel._class("it.generated.domain."+className, ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		myClass.annotate(javax.persistence.Entity.class);
		for (Field field : entity.getFieldList())
		{
			Class fieldClass = getFieldClass(field);
			JVar classField = myClass.field(JMod.PRIVATE, fieldClass, field.getName());
			generateGetterAndSetter(myClass, field.getName(), fieldClass);
		}
		for (Relationship relationship : entity.getRelationshipList())
		{
			if (relationship.isList())
			{
				JClass listClass = codeModel.ref(List.class).narrow(Generator.getJDefinedClass(relationship.getEntityTarget().getName()));
				JVar listField = myClass.field(JMod.PRIVATE, listClass, relationship.getEntityTarget().getName()+"List");
				generateGetterAndSetter(myClass, relationship.getEntityTarget().getName()+"List", listClass);
			}
			else
			{
				JVar listField = myClass.field(JMod.PRIVATE, Generator.getJDefinedClass(relationship.getEntityTarget().getName()), relationship.getEntityTarget().getName());
				generateGetterAndSetter(myClass, relationship.getEntityTarget().getName(), Generator.getJDefinedClass(relationship.getEntityTarget().getName()));
			}
		}
		saveFile(codeModel);
		return myClass;
	}



}
