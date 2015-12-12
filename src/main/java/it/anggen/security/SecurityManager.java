package it.anggen.security;

import org.springframework.web.bind.annotation.RequestMethod;

import it.anggen.model.RestrictionType;
import it.anggen.model.entity.Entity;

public interface SecurityManager {
	
	public Boolean canUpdate(Entity entity);
	public Boolean canInsert(Entity entity);
	public Boolean canDelete(Entity entity);
	public Boolean canSearch(Entity entity);
	public Boolean isAllowed(Long entityId, RestrictionType restrictionType);
	
}
