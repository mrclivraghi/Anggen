package it.polimi.model.entity;

import it.polimi.model.field.EnumField;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;
import it.polimi.model.security.RestrictionEntity;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema="mustle", name="entity")
public class Entity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="entity_id")
	@DescriptionField
	private Long entityId;
	
	@DescriptionField
	private String name;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.Field")
	@JoinColumn(name="entity_id_entity")
	private List<Field> fieldList;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.Relationship")
	@JoinColumn(name="entity_id_entity")
	private List<Relationship> relationshipList;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.EnumField")
	@JoinColumn(name="entity_id_entity")
	private List<EnumField> enumFieldList;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.Tab")
	@JoinColumn(name="entity_id_entity")
	private List<it.polimi.model.entity.Tab> tabList;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.RestrictionEntity")
	@JoinColumn(name="entity_id_entity")
	private List<RestrictionEntity> restrictionEntityList;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entity_group_id_entity_group")
	private EntityGroup entityGroup;
	
	@Column(name="security_type")
	private SecurityType securityType;
	
	
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
	/**
	 * @return the enumFieldList
	 */
	public List<EnumField> getEnumFieldList() {
		return enumFieldList;
	}
	/**
	 * @param enumFieldList the enumFieldList to set
	 */
	public void setEnumFieldList(List<EnumField> enumFieldList) {
		this.enumFieldList = enumFieldList;
	}
	public List<it.polimi.model.entity.Tab> getTabList() {
		return tabList;
	}
	public void setTabList(List<it.polimi.model.entity.Tab> tabList) {
		this.tabList = tabList;
	}
	public List<RestrictionEntity> getRestrictionEntityList() {
		return restrictionEntityList;
	}
	public void setRestrictionEntityList(List<RestrictionEntity> restrictionEntityList) {
		this.restrictionEntityList = restrictionEntityList;
	}
	
	
	/**
	 * @return the entityGroup
	 */
	public EntityGroup getEntityGroup() {
		return entityGroup;
	}
	/**
	 * @param entityGroup the entityGroup to set
	 */
	public void setEntityGroup(EntityGroup entityGroup) {
		this.entityGroup = entityGroup;
	}
	/**
	 * @return the securityType
	 */
	public SecurityType getSecurityType() {
		return securityType;
	}
	/**
	 * @param securityType the securityType to set
	 */
	public void setSecurityType(SecurityType securityType) {
		this.securityType = securityType;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Entity)) return false;
		return ((Entity) obj).getEntityId()==entityId;
	}
}
