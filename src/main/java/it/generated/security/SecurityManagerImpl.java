package it.generated.security;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import it.polimi.model.Restriction;
import it.polimi.model.entity.Entity;
import it.polimi.model.security.RestrictionType;
import it.polimi.model.security.User;
import it.polimi.repository.UserRepository;

@Service
public class SecurityManagerImpl implements SecurityManager{

	@Autowired
	UserRepository userRepository;
	
	private User user;
	
	
	public SecurityManagerImpl() {

	}

	
	public SecurityManagerImpl(User user){
		this.user=user;
	}
	
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

	@Override
	public Boolean canUpdate(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean canInsert(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean canDelete(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean canSearch(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Boolean isAllowed(Long entityId, RestrictionType restrictionType) {
		org.springframework.security.core.userdetails.User principal =  (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(principal.getUsername()==null? "null":principal.getUsername());
		List<User> userList = userRepository.findByUsername(principal.getUsername());
		if (userList==null || userList.size()==0) return false;
		User user = userList.get(0);
		for (Restriction restriction: user.getRestrictionEntityList())
		{
			if (restriction.getEntity().getEntityId().equals(entityId) && (!restriction.isAllowed(restrictionType))) return false;
		}
		return true;
	}

}
