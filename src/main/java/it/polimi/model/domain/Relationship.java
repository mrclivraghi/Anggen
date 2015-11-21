package it.polimi.model.domain;

import it.polimi.utils.annotation.IgnoreSearch;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema="mustle", name="relationship")
public class Relationship extends EntityAttribute{
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_relationship")
	private Long relationshipId;
	
	@Column(name="name")
	private String name;
	
	
	@OneToOne(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.Entity")
	@JoinColumn(name="entity_id_entity")
	private Entity entity;
	
	@OneToOne(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.Entity")
	@JoinColumn(name="entity_id_target_entity")
	private Entity entityTarget;
	
	@Column(name ="relationship_type")
	private RelationshipType relationshipType;
	
	
	@Column(name="description_field")
	private Boolean descriptionField;
	
	@Column(name="excel_export")
	private Boolean excelExport;
	
	private Boolean filter;
	
	@Column(name="ignore_search")
	private Boolean ignoreSearch;
	
	@Column(name="ignore_table_list")
	private Boolean ignoreTableList;
	
	@Column(name="ignore_update")
	private Boolean ignoreUpdate;
	
	@Column(name="between_filter")
	private Boolean betweenFilter;
	
	
	/**
	 * @return the relationshipId
	 */
	public Long getRelationshipId() {
		return relationshipId;
	}
	/**
	 * @param relationshipId the relationshipId to set
	 */
	public void setRelationshipId(Long relationshipId) {
		this.relationshipId = relationshipId;
	}
	/**
	 * @return the entitySource
	 */
	public Entity getEntity() {
		return entity;
	}
	/**
	 * @param entitySource the entitySource to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	/**
	 * @return the entityTarget
	 */
	public Entity getEntityTarget() {
		return entityTarget;
	}
	/**
	 * @param entityTarget the entityTarget to set
	 */
	public void setEntityTarget(Entity entityTarget) {
		this.entityTarget = entityTarget;
	}
	/**
	 * @return the relationshipType
	 */
	public RelationshipType getRelationshipType() {
		return relationshipType;
	}
	/**
	 * @param relationshipType the relationshipType to set
	 */
	public void setRelationshipType(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}
	
	
	
	/* custom methods */
	public Boolean isList()
	{
		return !(relationshipType==RelationshipType.ONE_TO_ONE || relationshipType==RelationshipType.MANY_TO_ONE);
	}
	
}
