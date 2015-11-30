package it.generated.anggen.security;

import it.generated.anggen.model.entity.Entity;
import it.generated.anggen.model.security.RestrictionEntity;
import it.generated.anggen.model.security.RestrictionEntityGroup;
import it.generated.anggen.model.security.User;
import it.generated.anggen.repository.security.UserRepository;
import it.polimi.model.security.RestrictionType;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@Service
public class SecurityService{

	@Autowired
	UserRepository userRepository;
	
	private User user;
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	public Boolean isAllowed(Long entityId, RestrictionType restrictionType) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<User> userList = userRepository.findByUsername(username);
		if (userList==null || userList.size()==0) return false;
		User user = userList.get(0);
		//check entity group restriction
		UserManager userManager = new UserManager(user);
		for (RestrictionEntityGroup restrictionEntityGroup: userManager.getRestrictionEntityGroupList())
		{
			for (Entity entity: restrictionEntityGroup.getEntityGroup().getEntityList())
			{
				if (entity.getEntityId().equals(entityId) && (!isAllowed(restrictionEntityGroup,restrictionType))) 
					return false;
			}
		}
		for (RestrictionEntity restrictionEntity: userManager.getRestrictionEntityList())
		{
			if (restrictionEntity.getEntity().getEntityId().equals(entityId) && (!isAllowed(restrictionEntity,restrictionType))) return false;
		}
		return true;
	}
	
	public Boolean isAllowed(RestrictionEntity restrictionEntity,RestrictionType restrictionType)
	{
		if (restrictionType==RestrictionType.SEARCH && !restrictionEntity.getCanSearch()) return false;
		if (restrictionType==RestrictionType.DELETE && !restrictionEntity.getCanDelete()) return false;
		if (restrictionType==RestrictionType.INSERT && !restrictionEntity.getCanCreate()) return false;
		if (restrictionType==RestrictionType.UPDATE && !restrictionEntity.getCanUpdate()) return false;
		
		return true;
	}
	
	public Boolean isAllowed(RestrictionEntityGroup restrictionEntityGroup,RestrictionType restrictionType)
	{
		if (restrictionType==RestrictionType.SEARCH && !restrictionEntityGroup.getCanSearch()) return false;
		if (restrictionType==RestrictionType.DELETE && !restrictionEntityGroup.getCanDelete()) return false;
		if (restrictionType==RestrictionType.INSERT && !restrictionEntityGroup.getCanCreate()) return false;
		if (restrictionType==RestrictionType.UPDATE && !restrictionEntityGroup.getCanUpdate()) return false;
		
		return true;
	}

}
