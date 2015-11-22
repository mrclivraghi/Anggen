package it.polimi.model.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@Type(type="it.polimi.model.domain.AnnotationAttribute")
	@JoinColumn(name="annotation_id_annotation")
	private List<AnnotationAttribute> attributeList;
	
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
	public List<AnnotationAttribute> getAttributeList() {
		return attributeList;
	}


	/**
	 * @param attributeList the attributeList to set
	 */
	public void setAttributeList(List<AnnotationAttribute> attributeList) {
		this.attributeList = attributeList;
	}

}
