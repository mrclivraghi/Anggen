package it.polimi.model.domain;

import it.polimi.utils.annotation.Tab;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(schema="mustle", name="field")
public class Field {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_field")
	private Long fieldId;
	
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entity_id_entity")
	private Entity entity;
	
	@Column(name ="field_type")
	private FieldType fieldType;
	
	private Boolean primaryKey;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the primaryKey
	 */
	public Boolean getPrimaryKey() {
		return primaryKey;
	}
	/**
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(Boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
}
