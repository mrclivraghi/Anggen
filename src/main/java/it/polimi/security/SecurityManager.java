package it.polimi.security;

import org.springframework.web.bind.annotation.RequestMethod;

import it.polimi.model.RestrictionType;
import it.polimi.model.entity.Entity;

public interface SecurityManager {
	
	public Boolean canUpdate(Entity entity);
	public Boolean canInsert(Entity entity);
	public Boolean canDelete(Entity entity);
	public Boolean canSearch(Entity entity);
	public Boolean isAllowed(Long entityId, RestrictionType restrictionType);
	
}
