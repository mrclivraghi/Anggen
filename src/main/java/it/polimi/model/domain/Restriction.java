package it.polimi.model.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(schema="mustle", name="restriction")
public class Restriction {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "restriction_id")
	private Long restrictionId;
	
	private Boolean create;
	
	private Boolean update;
	
	private Boolean search;
	
	private Boolean delete;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id_role")
	private Role role;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entity_id_entity")
	private Entity entity;
	
	
	public Restriction() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Long getRestrictionId() {
		return restrictionId;
	}

	public void setRestrictionId(Long restrictionId) {
		this.restrictionId = restrictionId;
	}

	public Boolean getCreate() {
		return create;
	}

	public void setCreate(Boolean create) {
		this.create = create;
	}

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public Boolean getSearch() {
		return search;
	}

	public void setSearch(Boolean search) {
		this.search = search;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Role getRole() {
		return role;
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



}
