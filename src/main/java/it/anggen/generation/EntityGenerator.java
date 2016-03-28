package it.anggen.generation;

import it.anggen.reflection.EntityAttributeManager;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.OracleNamingStrategy;
import it.anggen.utils.ReflectionManager;
import it.anggen.utils.Utility;
import it.anggen.utils.annotation.Between;
import it.anggen.utils.annotation.DescriptionField;
import it.anggen.utils.annotation.Embedded;
import it.anggen.utils.annotation.ExcelExport;
import it.anggen.utils.annotation.Filter;
import it.anggen.utils.annotation.IgnoreSearch;
import it.anggen.utils.annotation.IgnoreTableList;
import it.anggen.utils.annotation.IgnoreUpdate;
import it.anggen.utils.annotation.MaxDescendantLevel;
import it.anggen.utils.annotation.Password;
import it.anggen.utils.annotation.Priority;
import it.anggen.utils.annotation.SecurityType;
import it.anggen.model.RelationshipType;
import it.anggen.model.entity.Entity;
import it.anggen.model.field.Annotation;
import it.anggen.model.field.AnnotationAttribute;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;

import java.io.File;
import java.net.PasswordAuthentication;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.codemodel.ClassType;
import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

@Service
public class EntityGenerator {
	
	@Autowired
	private Generator generator;
	
	private Entity entity;
	
	
	public EntityGenerator()
	{
	}
	
	
	public void init (Entity entity)
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
	
	public JDefinedClass getModelClass()
	{
		JCodeModel	codeModel = new JCodeModel();
		String className = Utility.getFirstUpper(entity.getName());
		String lowerClassName = Utility.getFirstLower(className);
		JDefinedClass myClass= null;
		try {
			myClass = codeModel._class(generator.mainPackage+generator.applicationName+".model."+entity.getEntityGroup().getName()+"."+className, ClassType.CLASS);
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		myClass.annotate(javax.persistence.Entity.class);
		JAnnotationUse annotationTable= myClass.annotate(Table.class);
		//from properties
		if (entity.getEntityGroup().getName().equals("security"))
			annotationTable.param("schema", "sso");
		else
			annotationTable.param("schema", generator.schema);
		
		JAnnotationUse securityType= myClass.annotate(SecurityType.class);
		it.anggen.model.SecurityType secType = entity.getSecurityType();
		if (secType==null)
			secType=it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION;
		securityType.param("type", secType);
		
		JAnnotationUse maxDescendantLevel= myClass.annotate(MaxDescendantLevel.class);
		maxDescendantLevel.param("value", entity.getDescendantMaxLevel());
		
		if (entity.getCache())
		{
			JAnnotationUse cacheAnnotation= myClass.annotate(Cache.class);
			cacheAnnotation.param("usage", CacheConcurrencyStrategy.NONSTRICT_READ_WRITE);
		}
		
		JVar entityId= myClass.field(JMod.PUBLIC+JMod.STATIC+JMod.FINAL, Long.class, "staticEntityId");
		entityId.init(JExpr.lit(entity.getEntityId()));
		
		OracleNamingStrategy namingStrategy = new OracleNamingStrategy();
		annotationTable.param("name", namingStrategy.classToTableName(entity.getName()));
		//remove duplicate: TODO fix
		Set<Field> fieldSet = new HashSet<Field>();
		fieldSet.addAll(entity.getFieldList());
		for (Field field : fieldSet)
		{
			JClass fieldClass = EntityAttributeManager.getInstance(field).getFieldClass();
			String fieldName= field.getName();
			JVar classField = myClass.field(JMod.PRIVATE, fieldClass, field.getName());
			JAnnotationUse columnAnnotation = classField.annotate(Column.class);
			columnAnnotation.param("name", namingStrategy.classToTableName(field.getName()));
			generateGetterAndSetter(myClass, field.getName(), fieldClass);
			addValidationAnnotation(field,classField);
		}
		for (Relationship relationship : entity.getRelationshipList())
		{
			JVar listField;
			
			if (EntityAttributeManager.getInstance(relationship).isList())
			{
				JClass listClass = codeModel.ref(List.class).narrow(ReflectionManager.getJDefinedClass(relationship.getEntityTarget()));
				listField = myClass.field(JMod.PRIVATE, listClass, relationship.getEntityTarget().getName()+"List");
				generateGetterAndSetter(myClass, relationship.getName()+"List", listClass);
				if (relationship.getRelationshipType()==RelationshipType.ONE_TO_MANY)
				{
					JAnnotationUse oneToMany = listField.annotate(OneToMany.class);
					oneToMany.param("fetch", FetchType.EAGER);
					JAnnotationUse type = listField.annotate(Type.class);
					type.param("type", ReflectionManager.getJDefinedClass(relationship.getEntityTarget()).fullName());
					JAnnotationUse joinColumn = listField.annotate(JoinColumn.class);
					joinColumn.param("name", namingStrategy.classToTableName(entity.getName())+"_id_"+namingStrategy.classToTableName(entity.getName()));
				}

				if (relationship.getRelationshipType()==RelationshipType.MANY_TO_MANY)
				{
					JAnnotationUse manyToMany = listField.annotate(ManyToMany.class);
					manyToMany.param("fetch", FetchType.EAGER);
					JAnnotationUse type = listField.annotate(Type.class);
					type.param("type", ReflectionManager.getJDefinedClass(relationship.getEntityTarget()).fullName());
					JAnnotationUse joinTable = listField.annotate(JoinTable.class);
					joinTable.param("name", namingStrategy.classToTableName(relationship.getEntity().getName())+"_"+namingStrategy.classToTableName(relationship.getEntityTarget().getName()));
					joinTable.param("schema", generator.schema);
					JAnnotationArrayMember listJoinColumns = joinTable.paramArray("joinColumns");
					listJoinColumns.annotate(JoinColumn.class).param("name", relationship.getEntity().getName().toLowerCase()+"_id");
					
					JAnnotationArrayMember listInverseJoinColumns = joinTable.paramArray("inverseJoinColumns");
					listInverseJoinColumns.annotate(JoinColumn.class).param("name", relationship.getEntityTarget().getName().toLowerCase()+"_id");
					
					

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
				listField = myClass.field(JMod.PRIVATE, ReflectionManager.getJDefinedClass(relationship.getEntityTarget()), relationship.getName());
				if (relationship.getRelationshipType()==RelationshipType.MANY_TO_ONE)
				{
					JAnnotationUse manyToOne = listField.annotate(ManyToOne.class);
					manyToOne.param("fetch", FetchType.EAGER);
					JAnnotationUse joinColumn = listField.annotate(JoinColumn.class);
					joinColumn.param("name", namingStrategy.classToTableName(relationship.getEntityTarget().getName())+"_id_"+namingStrategy.classToTableName(relationship.getName()));
				
				}
				if (relationship.getRelationshipType()==RelationshipType.ONE_TO_ONE)
				{
					JAnnotationUse oneToOne = listField.annotate(OneToOne.class);
					oneToOne.param("fetch", FetchType.EAGER);
					JAnnotationUse type = listField.annotate(Type.class);
					type.param("type", ReflectionManager.getJDefinedClass(relationship.getEntityTarget()).fullName());
					JAnnotationUse joinColumn = listField.annotate(JoinColumn.class);
					joinColumn.param("name", namingStrategy.classToTableName(relationship.getEntityTarget().getName())+"_id_"+namingStrategy.classToTableName(relationship.getName()));
				
				}
				generateGetterAndSetter(myClass, relationship.getName(), ReflectionManager.getJDefinedClass(relationship.getEntityTarget()));
			}
			addValidationAnnotation(relationship,listField);
		}
		for (EnumField enumField: entity.getEnumFieldList())
		{
			JClass fieldClass = EntityAttributeManager.getInstance(enumField).getFieldClass();
			JVar classField = myClass.field(JMod.PRIVATE, fieldClass, enumField.getName());
			JAnnotationUse columnAnnotation = classField.annotate(Column.class);
			columnAnnotation.param("name", namingStrategy.classToTableName(enumField.getName()));
			generateGetterAndSetter(myClass, enumField.getName(), fieldClass);
			addValidationAnnotation(enumField,classField);
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
			case NOT_BLANK: annotationUse = classField.annotate(NotBlank.class);
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
			case EMBEDDED: annotationUse=classField.annotate(Embedded.class);
			break;
			case PRIMARY_KEY: 	annotationUse=classField.annotate(Id.class);
			JAnnotationUse generatedValue= classField.annotate(GeneratedValue.class);
			//generatedValue.param("strategy", GenerationType.SEQUENCE);
			if (!EntityAttributeManager.getInstance(entityAttribute).getDescriptionField())
			{
				classField.annotate(DescriptionField.class);
			}
			break;
			case SIZE:	annotationUse=classField.annotate(Size.class);
				for (AnnotationAttribute annotationAttribute: annotation.getAnnotationAttributeList())
				{
					annotationUse.param(annotationAttribute.getProperty(), Integer.valueOf(annotationAttribute.getValue()));
				}
				break;
			case PASSWORD: annotationUse=classField.annotate(Password.class);
				break;
			case PRIORITY: annotationUse=classField.annotate(Priority.class);
			for (AnnotationAttribute annotationAttribute: annotation.getAnnotationAttributeList())
			{
				annotationUse.param(annotationAttribute.getProperty(), Integer.valueOf(annotationAttribute.getValue()));
			}
				break;
				
			}
		}
		
	}



}
