package it.polimi.model.domain;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
@Table(schema="mustle", name="field")
@AttributeOverrides({
    @AttributeOverride(name="name", column=@Column(name="name"))
})
public class Field extends EntityAttribute{


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_field")
	private Long fieldId;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entity_id_entity")
	private Entity entity;
	
	@Column(name ="field_type")
	private FieldType fieldType;
	
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.Annotation")
	@JoinColumn(name="field_id_field")
	private List<Annotation> annotationList;
	
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.ValidationRestriction")
	@JoinColumn(name="field_id_field")
	private List<ValidationRestriction> validationRestrictionList;
	
	
	@ManyToOne
	@JoinColumn(name="tab_id_tab")
	private Tab tab;
	
	/**
	 * @return the fieldId
	 */
	public Long getFieldId() {
		return fieldId;
	}
	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}
	/**
	 * @param entity the entity to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	/**
	 * @return the fieldType
	 */
	public FieldType getFieldType() {
		return fieldType;
	}
	/**
	 * @param fieldType the fieldType to set
	 */
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	/**
	 * @return the annotationList
	 */
	public List<Annotation> getAnnotationList() {
		return annotationList;
	}
	/**
	 * @param annotationList the annotationList to set
	 */
	public void setAnnotationList(List<Annotation> annotationList) {
		this.annotationList = annotationList;
	}
	/**
	 * @return the tab
	 */
	public Tab getTab() {
		return tab;
	}
	/**
	 * @param tab the tab to set
	 */
	public void setTab(Tab tab) {
		this.tab = tab;
	}
	/**
	 * @return the validationRestrictionList
	 */
	public List<ValidationRestriction> getValidationRestrictionList() {
		return validationRestrictionList;
	}
	/**
	 * @param validationRestrictionList the validationRestrictionList to set
	 */
	public void setValidationRestrictionList(
			List<ValidationRestriction> validationRestrictionList) {
		this.validationRestrictionList = validationRestrictionList;
	}
	
}
