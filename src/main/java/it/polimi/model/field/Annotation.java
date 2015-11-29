package it.polimi.model.field;

import it.polimi.model.relationship.Relationship;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema="mustle",name="annotation")
public class Annotation {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_annotation")
	private Long annotationId;
	
	@Column(name="annotation_type")
	private AnnotationType annotationType;
	
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.AnnotationAttribute")
	@JoinColumn(name="annotation_id_annotation")
	private List<AnnotationAttribute> annotationAttributeList;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="field_id_field")
	private Field field;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="relationship_id_relationship")
	private Relationship relationship;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="enum_field_id_enum_field")
	private EnumField enumField;
	
	public Annotation() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the annotationId
	 */
	public Long getAnnotationId() {
		return annotationId;
	}


	/**
	 * @param annotationId the annotationId to set
	 */
	public void setAnnotationId(Long annotationId) {
		this.annotationId = annotationId;
	}


	/**
	 * @return the annotationType
	 */
	public AnnotationType getAnnotationType() {
		return annotationType;
	}


	/**
	 * @param annotationType the annotationType to set
	 */
	public void setAnnotationType(AnnotationType annotationType) {
		this.annotationType = annotationType;
	}


	/**
	 * @return the attributeList
	 */
	public List<AnnotationAttribute> getAnnotationAttributeList() {
		return annotationAttributeList;
	}


	/**
	 * @param attributeList the attributeList to set
	 */
	public void setAnnotationAttributeList(List<AnnotationAttribute> annotationAttributeList) {
		this.annotationAttributeList = annotationAttributeList;
	}


	/**
	 * @return the field
	 */
	public Field getField() {
		return field;
	}


	/**
	 * @param field the field to set
	 */
	public void setField(Field field) {
		this.field = field;
	}


	/**
	 * @return the relationship
	 */
	public Relationship getRelationship() {
		return relationship;
	}


	/**
	 * @param relationship the relationship to set
	 */
	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}


	/**
	 * @return the enumField
	 */
	public EnumField getEnumField() {
		return enumField;
	}


	/**
	 * @param enumField the enumField to set
	 */
	public void setEnumField(EnumField enumField) {
		this.enumField = enumField;
	}

}
