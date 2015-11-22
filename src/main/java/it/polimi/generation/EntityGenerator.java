package it.polimi.generation;

import it.polimi.boot.OracleNamingStrategy;
import it.polimi.model.domain.Annotation;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EntityAttribute;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.Relationship;
import it.polimi.model.domain.RelationshipType;
import it.polimi.utils.Utility;
import it.polimi.utils.annotation.Between;
import it.polimi.utils.annotation.DescriptionField;
import it.polimi.utils.annotation.ExcelExport;
import it.polimi.utils.annotation.Filter;
import it.polimi.utils.annotation.IgnoreSearch;
import it.polimi.utils.annotation.IgnoreTableList;
import it.polimi.utils.annotation.IgnoreUpdate;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

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
		JAnnotationUse annotationTable= myClass.annotate(Table.class);
		//from properties
		annotationTable.param("schema", "public");
		OracleNamingStrategy namingStrategy = new OracleNamingStrategy();
		annotationTable.param("name", namingStrategy.classToTableName(entity.getName()));
		for (Field field : entity.getFieldList())
		{
			Class fieldClass = getFieldClass(field);
			JVar classField = myClass.field(JMod.PRIVATE, fieldClass, field.getName());
			JAnnotationUse columnAnnotation = classField.annotate(Column.class);
			columnAnnotation.param("name", namingStrategy.classToTableName(field.getName()));
			generateGetterAndSetter(myClass, field.getName(), fieldClass);
			addValidationAnnotation(field,classField);
		}
		for (Relationship relationship : entity.getRelationshipList())
		{
			JVar listField;
			
			if (relationship.isList())
			{
				JClass listClass = codeModel.ref(List.class).narrow(Generator.getJDefinedClass(relationship.getEntityTarget().getName()));
				listField = myClass.field(JMod.PRIVATE, listClass, relationship.getEntityTarget().getName()+"List");
				generateGetterAndSetter(myClass, relationship.getEntityTarget().getName()+"List", listClass);
				if (relationship.getRelationshipType()==RelationshipType.ONE_TO_MANY)
				{
					JAnnotationUse oneToMany = listField.annotate(OneToMany.class);
					oneToMany.param("fetch", FetchType.EAGER);
					JAnnotationUse type = listField.annotate(Type.class);
					type.param("type", Generator.getJDefinedClass(relationship.getEntityTarget().getName()).fullName());
					JAnnotationUse joinColumn = listField.annotate(JoinColumn.class);
					joinColumn.param("name", namingStrategy.classToTableName(relationship.getEntityTarget().getName())+"_id_"+namingStrategy.classToTableName(relationship.getEntityTarget().getName()));
				}

				if (relationship.getRelationshipType()==RelationshipType.MANY_TO_MANY)
				{
				    /*@ManyToMany(fetch=FetchType.EAGER)
				    @Type(type="it.polimi.boot.domain.UserRole")
				    @JoinTable(name="user_role", schema="sso", joinColumns = {
				            @JoinColumn(name="user_id") },
				            inverseJoinColumns= {
				            @JoinColumn(name="role_id")
				            
				    })*/
					JAnnotationUse manyToMany = listField.annotate(ManyToMany.class);
					manyToMany.param("fetch", FetchType.EAGER);
					JAnnotationUse type = listField.annotate(Type.class);
					type.param("type", Generator.getJDefinedClass(relationship.getEntityTarget().getName()).fullName());
					JAnnotationUse joinTable = listField.annotate(JoinTable.class);
					joinTable.param("name", namingStrategy.classToTableName(relationship.getEntity().getName())+"_"+namingStrategy.classToTableName(relationship.getEntityTarget().getName()));
					joinTable.param("schema", "public");
					joinTable.param("joinColumns","");
					joinTable.param("inverseJoinColumns", "");
					

				}
				if (relationship.getRelationshipType()==RelationshipType.MANY_TO_MANY_BACK)
				{
					JAnnotationUse manyToMany = listField.annotate(ManyToMany.class);
					manyToMany.param("fetch", FetchType.EAGER);
					manyToMany.param("mappedBy", entity.getName()+"List");
				}

				
				
			}
			else
			{
				listField = myClass.field(JMod.PRIVATE, Generator.getJDefinedClass(relationship.getEntityTarget().getName()), relationship.getEntityTarget().getName());
				if (relationship.getRelationshipType()==RelationshipType.MANY_TO_ONE)
				{
					JAnnotationUse manyToOne = listField.annotate(ManyToOne.class);
					manyToOne.param("fetch", FetchType.EAGER);
					JAnnotationUse joinColumn = listField.annotate(JoinColumn.class);
					joinColumn.param("name", namingStrategy.classToTableName(relationship.getEntityTarget().getName())+"_id_"+namingStrategy.classToTableName(relationship.getEntityTarget().getName()));
				
				}
				if (relationship.getRelationshipType()==RelationshipType.ONE_TO_ONE)
				{
					JAnnotationUse oneToOne = listField.annotate(OneToOne.class);
					oneToOne.param("fetch", FetchType.EAGER);
					JAnnotationUse type = listField.annotate(Type.class);
					type.param("type", Generator.getJDefinedClass(relationship.getEntityTarget().getName()).fullName());
					JAnnotationUse joinColumn = listField.annotate(JoinColumn.class);
					joinColumn.param("name", namingStrategy.classToTableName(relationship.getEntityTarget().getName())+"_id_"+namingStrategy.classToTableName(relationship.getEntityTarget().getName()));
				
				}
				generateGetterAndSetter(myClass, relationship.getEntityTarget().getName(), Generator.getJDefinedClass(relationship.getEntityTarget().getName()));
			}
			addValidationAnnotation(relationship,listField);
		}
		saveFile(codeModel);
		return myClass;
	}


	private void addValidationAnnotation(EntityAttribute entityAttribute, JVar classField) {
		for (Annotation annotation : entityAttribute.getAnnotationList())
		{
			JAnnotationUse annotationUse;
			switch (annotation.getAnnotationType())
			{
			case BETWEEN_FILTER:	annotationUse = classField.annotate(Between.class);
			break;
			case BOT_BLANK: annotationUse = classField.annotate(NotBlank.class);
			break;
			case DESCRIPTION_FIELD: annotationUse = classField.annotate(DescriptionField.class);
			break;
			case EXCEL_EXPORT: annotationUse = classField.annotate(ExcelExport.class);
			break;
			case FILTER_FIELD: annotationUse = classField.annotate(Filter.class);
			break;
			case IGNORE_SEARCH: annotationUse = classField.annotate(IgnoreSearch.class);
			break;
			case IGNORE_UPDATE: annotationUse = classField.annotate(IgnoreUpdate.class);
			break;
			case NOT_NULL:	annotationUse = classField.annotate(NotNull.class);
			break;
			case IGNORE_TABLE_LIST: annotationUse = classField.annotate(IgnoreTableList.class);
			break;
			default:
			case PRIMARY_KEY: 	annotationUse=classField.annotate(Id.class);
			JAnnotationUse generatedValue= classField.annotate(GeneratedValue.class);
			generatedValue.param("strategy", GenerationType.SEQUENCE);
			if (!entityAttribute.getDescriptionField())
			{
				JAnnotationUse descrField= classField.annotate(DescriptionField.class);
			}
			break;
			}
		}
		
	}



}
