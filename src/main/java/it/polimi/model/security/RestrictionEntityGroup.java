package it.polimi.model.security;

import it.polimi.model.entity.EntityGroup;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@javax.persistence.Entity
@Table(schema="mustle",name="restriction_entity_group")
public class RestrictionEntityGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_restriction_entity_group")
	private Long restrictionEntityGroupId;
	
	
	@Column(name="can_create")
	private Boolean canCreate;
	
	@Column(name="can_update")
	private Boolean canUpdate;
	
	@Column(name="can_search")
	private Boolean canSearch;
	
	@Column(name="can_delete")
	private Boolean canDelete;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entity_group_id_entity_group")
	private EntityGroup entityGroup;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id_role")
	private Role role;
	
	
	public RestrictionEntityGroup() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the restrictionEntityGroupId
	 */
	public Long getRestrictionEntityGroupId() {
		return restrictionEntityGroupId;
	}

	/**
	 * @param restrictionEntityGroupId the restrictionEntityGroupId to set
	 */
	public void setRestrictionEntityGroupId(Long restrictionEntityGroupId) {
		this.restrictionEntityGroupId = restrictionEntityGroupId;
	}

	public Boolean getCanCreate() {
		return canCreate;
	}

	public void setCanCreate(Boolean canCreate) {
		this.canCreate = canCreate;
	}

	public Boolean getCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(Boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	public Boolean getCanSearch() {
		return canSearch;
	}

	public void setCanSearch(Boolean canSearch) {
		this.canSearch = canSearch;
	}

	public Boolean getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
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
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	
	@JsonIgnore
	public Boolean isAllowed(RestrictionType restrictionType)
	{
		if (restrictionType==RestrictionType.SEARCH && !canSearch) return false;
		if (restrictionType==RestrictionType.DELETE && !canDelete) return false;
		if (restrictionType==RestrictionType.INSERT && !canCreate) return false;
		if (restrictionType==RestrictionType.UPDATE && !canUpdate) return false;
		
		return true;
	}

}
