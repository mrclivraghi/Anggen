package it.polimi.model.domain;

import it.polimi.utils.annotation.DescriptionField;
import it.polimi.utils.annotation.Tab;

import java.util.List;

import javax.persistence.CascadeType;
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
@Table(schema="mustle", name="entity")
public class Entity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_entity")
	@DescriptionField
	private Long entityId;
	
	@DescriptionField
	private String name;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.Field")
	@JoinColumn(name="entity_id_entity")
	private List<Field> fieldList;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.RelationShip")
	@JoinColumn(name="entity_id_entity")
	private List<Relationship> relationshipList;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.EnumValueField")
	@JoinColumn(name="entity_id_entity")
	private List<EnumField> enumFieldList;
	
	
	/**
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}
	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
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
	 * @return the fieldList
	 */
	public List<Field> getFieldList() {
		return fieldList;
	}
	/**
	 * @param fieldList the fieldList to set
	 */
	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}
	/**
	 * @return the relationshipList
	 */
	public List<Relationship> getRelationshipList() {
		return relationshipList;
	}
	/**
	 * @param relationshipList the relationshipList to set
	 */
	public void setRelationshipList(List<Relationship> relationshipList) {
		this.relationshipList = relationshipList;
	}
}
