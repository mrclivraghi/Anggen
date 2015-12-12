package it.polimi.model;

import it.polimi.generation.Generator;
import it.polimi.model.entity.Entity;
import it.polimi.model.field.Annotation;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;
import it.polimi.reflection.EntityManager;
import it.polimi.reflection.EntityManagerImpl;
import it.polimi.utils.ReflectionManager;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.springframework.cache.aspectj.JCacheCacheAspect;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;

public class EntityAttribute {

	
	//bad, violating encapsulating
	//@JsonIgnore
	public void setName(String name)
	{
		if (isField())
			this.asField().setName(name);
		else
		{
			if (isRelationship())
				this.asRelationship().setName(name);
			else
				this.asEnumField().setName(name);
		}
	}
	//@JsonIgnore
	public String getName()
	{
		if (isField())
			return this.asField().getName();
		if (this.isRelationship())
			this.asRelationship().getEntityTarget().getName();
		return this.asEnumField().getName();
	}
	
	
	
	@JsonIgnore
	public Boolean getIgnoreSearch() {
		return hasAnnotation(AnnotationType.IGNORE_SEARCH);
	}
	@JsonIgnore
	public Boolean getIgnoreUpdate() {
		return hasAnnotation(AnnotationType.IGNORE_UPDATE);
	}
	@JsonIgnore
	public Boolean getBetweenFilter() {
		return hasAnnotation(AnnotationType.BETWEEN_FILTER);
	}
	@JsonIgnore
	public Boolean getDescriptionField() {
		return hasAnnotation(AnnotationType.DESCRIPTION_FIELD);
	}
	
	@JsonIgnore
	public Boolean getExcelExport() {
		return hasAnnotation(AnnotationType.EXCEL_EXPORT);
	}
	
	@JsonIgnore
	public Boolean getIgnoreTableList(){
		return hasAnnotation(AnnotationType.IGNORE_TABLE_LIST);
	}
	@JsonIgnore
	public Boolean getPrimaryKey(){
		return hasAnnotation(AnnotationType.PRIMARY_KEY);
	}
	
	@JsonIgnore
	public Boolean getPassword(){
		return hasAnnotation(AnnotationType.PASSWORD);
	}
	
	
	/*  check */
	@JsonIgnore
	public Boolean isField()
	{
		return this instanceof Field;
	}
	@JsonIgnore
	public Boolean isRelationship()
	{
		return this instanceof Relationship;
	}
	@JsonIgnore
	public Boolean isEnumField()
	{
		return this instanceof EnumField;
	}
	
	@JsonIgnore
	public Relationship asRelationship()
	{
		if (isRelationship())
			return (Relationship) this;
		else
			return null;
	}
	@JsonIgnore
	public Field asField()
	{
		if (isField())
			return (Field) this;
		else 
			return null;
	}
	
	@JsonIgnore
	public EnumField asEnumField()
	{
		if (isEnumField())
			return (EnumField) this;
		else
			return null;
	}
	
	@JsonIgnore
	public JClass getFieldClass()
	{
		if (this.isField())
		{
			JCodeModel codeModel = new JCodeModel();
			switch (this.asField().getFieldType())
			{
			case STRING: return codeModel.ref(String.class);
			case INTEGER: return codeModel.ref(Integer.class);
			case DATE: return codeModel.ref(Date.class);
			case DOUBLE: return codeModel.ref(Double.class);
			case TIME: return codeModel.ref(Time.class);
			case BOOLEAN: return codeModel.ref(Boolean.class);
			case LONG: return codeModel.ref(Long.class);
			case FILE: return codeModel.ref(String.class);
			case PHOTO: return codeModel.ref(String.class);
			default: return null;
			}
		} else
		{
			if (this.isEnumField())
			{
				return ReflectionManager.getJDefinedEnumFieldClass(this.asEnumField());
			}else
			{
				return ReflectionManager.getJDefinedClass(this.asRelationship().getEntityTarget());
			}
		}
	}
	@JsonIgnore
	public JClass getRightParamClass()
	{
		JCodeModel codeModel= new JCodeModel();
		if (isEnumField())
			return codeModel.ref(Integer.class);
		if (this.asField()!=null && this.asField().getFieldType()==FieldType.DATE)
			return codeModel.ref(String.class);
		
		return getFieldClass();
	}
	//@JsonIgnore
	public List<Annotation> getAnnotationList() {
		if (isEnumField())
			return asEnumField().getAnnotationList();
		if (isRelationship())
			return asRelationship().getAnnotationList();
		return asField().getAnnotationList();
	}
	@JsonIgnore
	public Boolean hasAnnotation(AnnotationType annotationType)
	{
		for (Annotation annotation: getAnnotationList())
		{
			if (annotation.getAnnotationType()==annotationType)
				return true;
		}
		return false;
	}
	@JsonIgnore
	public Entity getParent() {
		if (isField())
			return asField().getEntity();
		if (isRelationship())
			return asRelationship().getEntity();
		return asEnumField().getEntity();
	}
	@JsonIgnore
	public List<EntityAttribute> getFilterField()
	{
		List<EntityAttribute> childrenAttributeList = new ArrayList<EntityAttribute>();
		if (isRelationship())
		{
			EntityManager targetManager = new EntityManagerImpl(asRelationship().getEntityTarget());
			for (EntityAttribute entityAttribute: targetManager.getAllAttribute())
			{
				if (entityAttribute.hasAnnotation(AnnotationType.FILTER_FIELD))
					childrenAttributeList.add(entityAttribute);
			}
		}
		
		return childrenAttributeList;
	}
	

}
