package it.polimi.service;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Restriction;
import it.polimi.model.domain.RestrictionType;
import it.polimi.model.domain.User;
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
		if (userRepository==null)
		{
			System.out.println("userrepository is null");
		}
		List<User> userList = userRepository.findByUsername(username);
		if (userList==null || userList.size()==0) return false;
		User user = userList.get(0);
		for (Restriction restriction: user.getRestrictionList())
		{
			if (restriction.getEntity().getEntityId().equals(entityId) && (!restriction.isAllowed(restrictionType))) return false;
		}
		return true;
	}

}
