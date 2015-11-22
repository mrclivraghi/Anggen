package it.polimi.model.domain;

import it.polimi.generation.Generator;
import it.polimi.utils.ReflectionManager;

import java.sql.Time;
import java.util.Date;

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
	@JsonIgnore
	public void setName(String name)
	{
		if (isField())
			this.asField().setName(name);
		else
			this.asRelationship().setName(name);
	}
	@JsonIgnore
	public String getName()
	{
		if (isField())
			return this.asField().getName();
		return this.asRelationship().getEntityTarget().getName();
	}
	
	
	
	@JsonIgnore
	public Boolean getIgnoreSearch() {
		if (isField())
			return this.asField().getIgnoreSearch();
		return this.asRelationship().getIgnoreSearch();
	}
	@JsonIgnore
	public Boolean getIgnoreUpdate() {
		if (isField())
			return this.asField().getIgnoreUpdate();
		return this.asRelationship().getIgnoreUpdate();
	}
	@JsonIgnore
	public Boolean getBetweenFilter() {
		if (isField())
			return this.asField().getBetweenFilter();
		return this.asRelationship().getBetweenFilter();
	}
	@JsonIgnore
	public Boolean getDescriptionField() {
		if (isField())
			return this.asField().getDescriptionField();
		return this.asRelationship().getDescriptionField();
	}
	
	@JsonIgnore
	public Boolean getExcelExport() {
		return false;
	}
	
	@JsonIgnore
	public Boolean getIgnoreTableList(){
		return false;
	}
	@JsonIgnore
	public Boolean getPrimaryKey(){
		return false;
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
	public JClass getFieldClass()
	{
		if (this.asField()!=null)
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
			default: return null;
			}
		} else
		{
			return Generator.getJDefinedClass(this.asRelationship().getEntityTarget().getName());
		}
	}
	@JsonIgnore
	public JClass getRightParamClass()
	{
		//TODO enum
	//	if (field.getIsEnum())
	//		return Integer.class;
		JCodeModel codeModel= new JCodeModel();
		if (this.asField()!=null && this.asField().getFieldType()==FieldType.DATE)
			return codeModel.ref(String.class);
		
		return getFieldClass();
	}
	

}
