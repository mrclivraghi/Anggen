package it.polimi.controller.security;

import it.polimi.model.security.Entity;
import it.polimi.model.security.Role;
import it.polimi.searchbean.security.EntitySearchBean;
import it.polimi.searchbean.security.UserSearchBean;
import it.polimi.service.security.EntityService;
import it.polimi.service.security.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/menu")
public class MenuController {

	 @Autowired 
	    public UserService userService;
	    
	    @Autowired
	    public EntityService entityService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView laodMenu()
	{
		User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	UserSearchBean userSearchBean = new UserSearchBean();
    	userSearchBean.setUsername(principal.getUsername());
    	
    	it.polimi.model.security.User myUser = userService.find(userSearchBean).get(0);
    	EntitySearchBean entitySearchBean = new EntitySearchBean();
    	List<Role> roleList = new ArrayList<Role>();
    	roleList.add(myUser.getRole());
    	
    	entitySearchBean.setRoleList(roleList);
    	List<Entity> entityList = entityService.find(entitySearchBean);
    	
        ModelAndView mav = new ModelAndView("menu");
        List<String> entityNameList = new ArrayList<String>();
        if (entityList!=null)
        	for (Entity entity: entityList)
        	{
        		entityNameList.add(entity.getEntityName());
        	}
        mav.addObject("entityList",entityList);
        return mav;
	}
}
