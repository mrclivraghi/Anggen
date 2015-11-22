package it.polimi.model.domain;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(schema="mustle",name="annotation_attribute")
public class AnnotationAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_annotation_attribute")
	private Long annotationAttributeId;
	
	private String property;
	
	private String value;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="annotation_id_annotation")
	private Annotation annotation;
	
	public AnnotationAttribute() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the annotationAttributeId
	 */
	public Long getAnnotationAttributeId() {
		return annotationAttributeId;
	}

	/**
	 * @param annotationAttributeId the annotationAttributeId to set
	 */
	public void setAnnotationAttributeId(Long annotationAttributeId) {
		this.annotationAttributeId = annotationAttributeId;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the annotation
	 */
	public Annotation getAnnotation() {
		return annotation;
	}

	/**
	 * @param annotation the annotation to set
	 */
	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}

}
