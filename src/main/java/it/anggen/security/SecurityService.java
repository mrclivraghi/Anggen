package it.anggen.security;

import it.anggen.model.RestrictionType;
import it.anggen.model.entity.Entity;
import it.anggen.model.security.RestrictionEntity;
import it.anggen.model.security.RestrictionEntityGroup;
import it.anggen.model.security.User;
import it.anggen.repository.security.UserRepository;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;


@Service
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

	public Boolean hasRestriction(Long entityId, RestrictionType restrictionType) {
		if (1==1)
			return false;
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<User> userList = userRepository.findByUsername(username);
		if (userList==null || userList.size()==0) return true;
		User user = userList.get(0);
		//check entity group restriction
		UserManager userManager = new UserManager(user);
		for (RestrictionEntityGroup restrictionEntityGroup: userManager.getRestrictionEntityGroupList())
		{
			for (Entity entity: restrictionEntityGroup.getEntityGroup().getEntityList())
			{
				if (entity.getEntityId().equals(entityId) && (!isAllowed(restrictionEntityGroup,restrictionType))) 
					return true;
			}
		}
		for (RestrictionEntity restrictionEntity: userManager.getRestrictionEntityList())
		{
			if (restrictionEntity.getEntity().getEntityId().equals(entityId) && (!isAllowed(restrictionEntity,restrictionType))) return true;
		}
		return false;
	}
	
	
	public Boolean hasPermission(Long entityId, RestrictionType restrictionType) {
		if (1==1)
			return true;
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<User> userList = userRepository.findByUsername(username);
		if (userList==null || userList.size()==0) return false;
		User user = userList.get(0);
		//check entity group restriction
		UserManager userManager = new UserManager(user);
		Boolean hasPermission=false;
		for (RestrictionEntityGroup restrictionEntityGroup: userManager.getRestrictionEntityGroupList())
		{
			if (restrictionEntityGroup.getEntityGroup()!=null && restrictionEntityGroup.getEntityGroup().getEntityList()!=null)
			for (Entity entity: restrictionEntityGroup.getEntityGroup().getEntityList())
			{
				if (entity.getEntityId().equals(entityId) && (isAllowed(restrictionEntityGroup,restrictionType))) 
					hasPermission=true;
			}
		}
		//no permission on entitygroup so return false
		if (!hasPermission) return false;
		hasPermission=false;
		for (RestrictionEntity restrictionEntity: userManager.getRestrictionEntityList())
		{
			if (restrictionEntity.getEntity().getEntityId().equals(entityId) && isAllowed(restrictionEntity,restrictionType))
					hasPermission=true;
		}
		return hasPermission;
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
