package it.polimi.service;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import it.polimi.model.entity.Entity;
import it.polimi.model.security.RestrictionEntity;
import it.polimi.model.security.RestrictionEntityGroup;
import it.polimi.model.security.RestrictionType;
import it.polimi.model.security.User;
import it.polimi.repository.UserRepository;

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

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public Boolean canUpdate(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean canInsert(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean canDelete(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean canSearch(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}


	public Boolean isAllowed(Long entityId, RestrictionType restrictionType) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<User> userList = userRepository.findByUsername(username);
		if (userList==null || userList.size()==0) return false;
		User user = userList.get(0);
		//check entity group restriction
		for (RestrictionEntityGroup restrictionEntityGroup: user.getRestrictionEntityGroupList())
		{
			for (Entity entity: restrictionEntityGroup.getEntityGroup().getEntityList())
			{
				if (entity.getEntityId().equals(entityId) && (!restrictionEntityGroup.isAllowed(restrictionType))) 
					return false;
			}
		}
		for (RestrictionEntity restrictionEntity: user.getRestrictionEntityList())
		{
			if (restrictionEntity.getEntity().getEntityId().equals(entityId) && (!restrictionEntity.isAllowed(restrictionType))) return false;
		}
		return true;
	}

}
