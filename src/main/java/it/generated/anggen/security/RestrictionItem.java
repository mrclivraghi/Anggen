package it.generated.anggen.security;

import it.generated.anggen.model.entity.Entity;

import java.util.Map;

public class RestrictionItem {

	private Boolean canCreate;
	
	private Boolean canUpdate;
	
	private Boolean canSearch;
	
	private Boolean canDelete;
	
	private Map<String,Boolean> restrictionFieldMap;
	
	private Entity entity;
	
	public RestrictionItem() {
		// TODO Auto-generated constructor stub
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

	public Map<String,Boolean> getRestrictionFieldMap() {
		return restrictionFieldMap;
	}

	public void setRestrictionFieldList(Map<String,Boolean> restrictionFieldMap) {
		this.restrictionFieldMap = restrictionFieldMap;
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

}
