package it.polimi.controller;

import it.polimi.model.domain.Annotation;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Field;
import it.polimi.model.domain.RestrictionEntity;
import it.polimi.model.domain.RestrictionEntityGroup;
import it.polimi.model.domain.RestrictionField;
import it.polimi.model.domain.User;
import it.polimi.reflection.EntityManager;
import it.polimi.reflection.EntityManagerImpl;
import it.polimi.searchbean.AnnotationSearchBean;
import it.polimi.searchbean.UserSearchBean;
import it.polimi.security.RestrictionItem;
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
	       return ResponseEntity.status(HttpStatus.ACCEPTED).body(buildRestrictionMap(loggedUserList.get(0).getRestrictionEntityList(),loggedUserList.get(0).getRestrictionEntityGroupList(),loggedUserList.get(0).getRestrictionFieldList()));
		}else
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(buildRestrictionMap(null,null,null));
	}
	
	private Map<String,RestrictionItem> buildRestrictionMap(List<RestrictionEntity> restrictionEntityList, List<RestrictionEntityGroup> restrictionEntityGroupList, List<RestrictionField> restrictionFieldList)
	{
		Map<String,RestrictionItem> restrictionMap= new HashMap<String, RestrictionItem>();
		
		for (RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
		{
			for (Entity entity:restrictionEntityGroup.getEntityGroup().getEntityList())
			{
				RestrictionItem fakeRestrictionItem= new RestrictionItem();
				fakeRestrictionItem.setCanCreate(restrictionEntityGroup.getCanCreate());
				fakeRestrictionItem.setCanDelete(restrictionEntityGroup.getCanDelete());
				fakeRestrictionItem.setCanSearch(restrictionEntityGroup.getCanSearch());
				fakeRestrictionItem.setCanUpdate(restrictionEntityGroup.getCanUpdate());
				fakeRestrictionItem.setEntity(entity);
				restrictionMap.put(entity.getName(), fakeRestrictionItem);
				
			}
		}
		
		if (restrictionEntityList!=null)
		for (RestrictionEntity restrictionEntity: restrictionEntityList)
		{
			RestrictionItem fakeRestrictionItem= null;
			for (RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
			{
				if (restrictionEntity.getEntity().getEntityGroup().getEntityGroupId().equals(restrictionEntityGroup.getEntityGroup().getEntityGroupId()))
				{
					fakeRestrictionItem= new RestrictionItem();
					fakeRestrictionItem.setCanCreate(fakeRestrictionItem.getCanCreate() && restrictionEntityGroup.getCanCreate());
					fakeRestrictionItem.setCanDelete(fakeRestrictionItem.getCanDelete() && restrictionEntityGroup.getCanDelete());
					fakeRestrictionItem.setCanUpdate(fakeRestrictionItem.getCanUpdate() && restrictionEntityGroup.getCanUpdate());
					fakeRestrictionItem.setCanSearch(fakeRestrictionItem.getCanSearch() && restrictionEntityGroup.getCanSearch());
					fakeRestrictionItem.setEntity(restrictionEntity.getEntity());
					break;
				}
			}
			
			String entityName=restrictionEntity.getEntity().getName();
			restrictionMap.put(entityName, fakeRestrictionItem);
		}
		
		addRestrictionField(restrictionMap,restrictionFieldList);
		return restrictionMap;
	}
	
	private void addRestrictionField(Map<String,RestrictionItem> restrictionMap, List<RestrictionField> restrictionFieldList)
	{
		for (RestrictionItem restrictionItem: restrictionMap.values())
		{
			Map<String,Boolean> restrictionFieldMap= new HashMap<String, Boolean>();
			for (Field field: restrictionItem.getEntity().getFieldList())
			{
				for (RestrictionField restrictionField: field.getRestrictionFieldList())
				{
					if (restrictionFieldList.contains(restrictionField))
					{
						restrictionFieldMap.put(field.getName(), true);
						break;
					}
				}
			}
			restrictionItem.setEntity(null);
			restrictionItem.setRestrictionFieldList(restrictionFieldMap);
		}
	}

}
