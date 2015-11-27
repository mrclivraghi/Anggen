package it.polimi.controller;

import it.polimi.model.domain.Annotation;
import it.polimi.model.domain.Restriction;
import it.polimi.model.domain.User;
import it.polimi.searchbean.AnnotationSearchBean;
import it.polimi.searchbean.UserSearchBean;
import it.polimi.service.SecurityService;
import it.polimi.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	UserService userService;
	
	
	@ResponseBody
	@RequestMapping(value="/username",method = RequestMethod.GET)
    public ResponseEntity manage() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(username);
    }
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity getRestriction(){
		String username= SecurityContextHolder.getContext().getAuthentication().getName();
		UserSearchBean usb= new UserSearchBean();
		usb.setUsername(username);
		List<User> loggedUserList=userService.find(usb);
		if (loggedUserList.size()>0)
		{
	       return ResponseEntity.status(HttpStatus.ACCEPTED).body(buildRestrictionMap(loggedUserList.get(0).getRestrictionList()));
		}else
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(buildRestrictionMap(null));
	}
	
	private Map<String,Restriction> buildRestrictionMap(List<Restriction> restrictionList)
	{
		Map<String,Restriction> restrictionMap= new HashMap<String, Restriction>();
		if (restrictionList!=null)
		for (Restriction restriction: restrictionList)
		{
			String entityName=restriction.getEntity().getName();
			restriction.setEntity(null);
			restriction.setRole(null);
			restrictionMap.put(entityName, restriction);
		}
		return restrictionMap;
	}

}
