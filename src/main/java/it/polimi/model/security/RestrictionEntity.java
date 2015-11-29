package it.polimi.model.security;

import static javax.persistence.GenerationType.IDENTITY;
import it.polimi.model.entity.Entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@javax.persistence.Entity
@Table(schema="sso", name="restriction_entity")
public class RestrictionEntity {



	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "restriction_entity_id")
	private Long restrictionEntityId;
	
	@Column(name="can_create")
	private Boolean canCreate;
	
	@Column(name="can_update")
	private Boolean canUpdate;
	
	@Column(name="can_search")
	private Boolean canSearch;
	
	@Column(name="can_delete")
	private Boolean canDelete;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id_role")
	private Role role;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entity_id_entity")
	private Entity entity;
	
	
	public RestrictionEntity() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Long getRestrictionEntityId() {
		return restrictionEntityId;
	}

	public void setRestrictionEntityId(Long entityrestrictionId) {
		this.restrictionEntityId = entityrestrictionId;
	}

	public Role getRole() {
		return role;
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


	public void setRole(Role role) {
		this.role = role;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
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
