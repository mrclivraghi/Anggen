package it.anggen.security.view;

import it.anggen.model.entity.Entity;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.security.RestrictionField;

import java.util.List;
import java.util.Map;

public class RestrictionGroup {

	private Boolean canCreate;
	
	private Boolean canUpdate;
	
	private Boolean canSearch;
	
	private Boolean canDelete;
	
	private Map<String,RestrictionItem> restrictionItemMap;
	
	
	public RestrictionGroup() {
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

	public Map<String,RestrictionItem> getRestrictionItemMap() {
		return restrictionItemMap;
	}

	public void setRestrictionItemMap(Map<String,RestrictionItem> restrictionItemMap) {
		this.restrictionItemMap = restrictionItemMap;
	}


}
