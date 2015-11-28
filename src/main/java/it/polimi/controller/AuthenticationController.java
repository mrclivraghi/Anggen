package it.polimi.controller;

import it.polimi.model.domain.Annotation;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.RestrictionEntity;
import it.polimi.model.domain.RestrictionEntityGroup;
import it.polimi.model.domain.User;
import it.polimi.searchbean.AnnotationSearchBean;
import it.polimi.searchbean.UserSearchBean;
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
	       return ResponseEntity.status(HttpStatus.ACCEPTED).body(buildRestrictionMap(loggedUserList.get(0).getRestrictionEntityList(),loggedUserList.get(0).getRestrictionEntityGroupList()));
		}else
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(buildRestrictionMap(null,null));
	}
	
	private Map<String,RestrictionEntity> buildRestrictionMap(List<RestrictionEntity> restrictionEntityList, List<RestrictionEntityGroup> restrictionEntityGroupList)
	{
		Map<String,RestrictionEntity> restrictionMap= new HashMap<String, RestrictionEntity>();
		
		for (RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
		{
			for (Entity entity:restrictionEntityGroup.getEntityGroup().getEntityList())
			{
				RestrictionEntity fakeRestrictionEntity= new RestrictionEntity();
				fakeRestrictionEntity.setCanCreate(restrictionEntityGroup.getCanCreate());
				fakeRestrictionEntity.setCanDelete(restrictionEntityGroup.getCanDelete());
				fakeRestrictionEntity.setCanSearch(restrictionEntityGroup.getCanSearch());
				fakeRestrictionEntity.setCanUpdate(restrictionEntityGroup.getCanUpdate());
				restrictionMap.put(entity.getName(), fakeRestrictionEntity);
				
			}
		}
		
		if (restrictionEntityList!=null)
		for (RestrictionEntity restrictionEntity: restrictionEntityList)
		{
			for (RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
			{
				if (restrictionEntity.getEntity().getEntityGroup().getEntityGroupId().equals(restrictionEntityGroup.getEntityGroup().getEntityGroupId()))
				{
					restrictionEntity.setCanCreate(restrictionEntity.getCanCreate() && restrictionEntityGroup.getCanCreate());
					restrictionEntity.setCanDelete(restrictionEntity.getCanDelete() && restrictionEntityGroup.getCanDelete());
					restrictionEntity.setCanUpdate(restrictionEntity.getCanUpdate() && restrictionEntityGroup.getCanUpdate());
					restrictionEntity.setCanSearch(restrictionEntity.getCanSearch() && restrictionEntityGroup.getCanSearch());
					break;
				}
			}
			
			String entityName=restrictionEntity.getEntity().getName();
			restrictionEntity.setEntity(null);
			restrictionEntity.setRole(null);
			restrictionMap.put(entityName, restrictionEntity);
		}
		return restrictionMap;
	}

}
