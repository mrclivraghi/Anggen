package it.anggen.reflection;

import it.anggen.utils.EntityAttribute;
import it.anggen.utils.ReflectionManager;
import it.anggen.model.AnnotationType;
import it.anggen.model.FieldType;
import it.anggen.model.RelationshipType;
import it.anggen.model.entity.Entity;
import it.anggen.model.field.Annotation;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;

public class EntityAttributeManager {

	private EntityAttribute entityAttribute;
	
	private static EntityAttributeManager instance;
	
	public EntityAttributeManager(EntityAttribute entityAttribute) {
		this.entityAttribute=entityAttribute;
	}
	
	public static EntityAttributeManager getInstance(EntityAttribute entityAttribute)
	{
		if (instance==null)
			instance = new EntityAttributeManager(entityAttribute);
		else
			instance.setEntityAttribute(entityAttribute);
		return instance;
	}
	
	public void setEntityAttribute(EntityAttribute entityAttribute) {
		this.entityAttribute=entityAttribute;
	}

	
	public Boolean getIgnoreSearch() {
		return hasAnnotation(AnnotationType.IGNORE_SEARCH);
	}
	
	public Boolean getIgnoreUpdate() {
		return hasAnnotation(AnnotationType.IGNORE_UPDATE);
	}
	
	public Boolean getBetweenFilter() {
		return hasAnnotation(AnnotationType.BETWEEN_FILTER);
	}
	
	public Boolean getDescriptionField() {
		return hasAnnotation(AnnotationType.DESCRIPTION_FIELD);
	}
	
	
	public Boolean getExcelExport() {
		return hasAnnotation(AnnotationType.EXCEL_EXPORT);
	}
	
	
	public Boolean getIgnoreTableList(){
		return hasAnnotation(AnnotationType.IGNORE_TABLE_LIST);
	}
	
	public Boolean getPrimaryKey(){
		return hasAnnotation(AnnotationType.PRIMARY_KEY);
	}
	
	
	public Boolean getPassword(){
		return hasAnnotation(AnnotationType.PASSWORD);
	}
	
	public Boolean isEmbedded(){
		return hasAnnotation(AnnotationType.EMBEDDED);
	}
	
	
	/*  check */
	
	public Boolean isField()
	{
		return entityAttribute instanceof Field;
	}
	
	public Boolean isRelationship()
	{
		return entityAttribute instanceof Relationship;
	}
	
	public Boolean isEnumField()
	{
		return entityAttribute instanceof EnumField;
	}
	
	
	public Relationship asRelationship()
	{
		if (isRelationship())
			return (Relationship) entityAttribute;
		else
			return null;
	}
	
	public Field asField()
	{
		if (isField())
			return (Field) entityAttribute;
		else 
			return null;
	}
	
	
	public EnumField asEnumField()
	{
		if (isEnumField())
			return (EnumField) entityAttribute;
		else
			return null;
	}
	
	
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
	
	public JClass getRightParamClass()
	{
		JCodeModel codeModel= new JCodeModel();
		if (isEnumField())
			return codeModel.ref(Integer.class);
		if (this.asField()!=null && (this.asField().getFieldType()==FieldType.DATE || this.asField().getFieldType()==FieldType.TIME))
			return codeModel.ref(String.class);
		
		return getFieldClass();
	}
	//
	public List<Annotation> getAnnotationList() {
		if (isEnumField())
			return asEnumField().getAnnotationList();
		if (isRelationship())
			return asRelationship().getAnnotationList();
		return asField().getAnnotationList();
	}
	
	public Boolean hasAnnotation(AnnotationType annotationType)
	{
		for (Annotation annotation: getAnnotationList())
		{
			if (annotation.getAnnotationType()==annotationType)
				return true;
		}
		return false;
	}
	
	public Entity getParent() {
		if (isField())
			return asField().getEntity();
		if (isRelationship())
			return asRelationship().getEntity();
		return asEnumField().getEntity();
	}
	
	public List<EntityAttribute> getFilterField()
	{
		List<EntityAttribute> childrenAttributeList = new ArrayList<EntityAttribute>();
		if (isRelationship())
		{
			EntityManager targetManager = new EntityManagerImpl(asRelationship().getEntityTarget());
			for (EntityAttribute entityAttribute: targetManager.getAllAttribute())
			{
				if (EntityAttributeManager.getInstance(entityAttribute).hasAnnotation(AnnotationType.FILTER_FIELD))
					childrenAttributeList.add(entityAttribute);
			}
		}
		
		return childrenAttributeList;
	}

	
	public Boolean isList() {
		if (isRelationship())
			return !(asRelationship().getRelationshipType()==RelationshipType.ONE_TO_ONE || asRelationship().getRelationshipType()==RelationshipType.MANY_TO_ONE);
		return false;
	}

	
	public String getFieldTypeName() {
		if (!isField())
			return null;
		
		return getFieldTypeName(asField().getFieldType());
	}

	public String getFieldTypeName(FieldType fieldType)
	{
		switch (fieldType.getValue())
		{
		case 0: return "String";
		case 1: return "Integer";
		case 2: return "Date";
		case 3: return "Double";
		case 4: return "Time";
		case 5: return "Boolean";
		case 6: return "Long";
		case 7: return "String";
		case 8: return "String";
		}
		return null;
	}
	
	public Class getFieldTypeClass() {
		if (!isField())
			return null;
		
		return getFieldTypeClass(asField().getFieldType());
	}
	
	public Class getFieldTypeClass(FieldType fieldType)
	{
		switch (fieldType.getValue())
		{
		case 0: return String.class;
		case 1: return Integer.class;
		case 2: return Date.class;
		case 3: return Double.class;
		case 4: return Time.class;
		case 5: return Boolean.class;
		case 6: return Long.class;
		case 7: return String.class;
		case 8: return String.class;
		}
		return null;
	}
	

}
