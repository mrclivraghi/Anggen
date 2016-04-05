package it.anggen.controller;

import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.security.RestrictionItem;
import it.anggen.security.UserManager;
import it.anggen.model.entity.Entity;
import it.anggen.model.field.Annotation;
import it.anggen.model.field.Field;
import it.anggen.model.security.RestrictionEntity;
import it.anggen.model.security.RestrictionEntityGroup;
import it.anggen.model.security.RestrictionField;
import it.anggen.model.security.User;
import it.anggen.searchbean.security.UserSearchBean;
import it.anggen.service.security.UserService;
import it.anggen.utils.MessageResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	UserService userService;
	
	
	@ResponseBody
	@RequestMapping(value="/username",method = RequestMethod.POST)
    public ResponseEntity manage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true); // true == allow create
        System.out.println(attr.getSessionId());
        if (username.equals("anonymousUser"))
        	username="";
        return ResponseEntity.ok().body(new MessageResponse(username,!username.equals("")));
    }
	
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity login()
	{
		
		return ResponseEntity.ok().body(null);
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
			UserManager userManager = new UserManager(loggedUserList.get(0));
	       return ResponseEntity.status(HttpStatus.ACCEPTED).body(buildRestrictionMap(userManager.getRestrictionEntityList(),userManager.getRestrictionEntityGroupList(),userManager.getRestrictionFieldList()));
		}else
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(buildRestrictionMap(null,null,null));
	}
	
	private Map<String,RestrictionItem> buildRestrictionMap(List<RestrictionEntity> restrictionEntityList, List<RestrictionEntityGroup> restrictionEntityGroupList, List<RestrictionField> restrictionFieldList)
	{
		Map<String,RestrictionItem> restrictionMap= new HashMap<String, RestrictionItem>();
		if (restrictionEntityGroupList!=null)
		for (RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
		{
			for (Entity entity:restrictionEntityGroup.getEntityGroup().getEntityList())
			{
				RestrictionItem fakeRestrictionItem= new RestrictionItem();
				fakeRestrictionItem.setCanCreate(restrictionEntityGroup.getCanCreate());
				fakeRestrictionItem.setCanDelete(restrictionEntityGroup.getCanDelete());
				fakeRestrictionItem.setCanSearch(restrictionEntityGroup.getCanSearch());
				fakeRestrictionItem.setCanUpdate(restrictionEntityGroup.getCanUpdate());
				//fakeRestrictionItem.setEntity(entity);
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
					fakeRestrictionItem.setCanCreate(restrictionEntity.getCanCreate() && restrictionEntityGroup.getCanCreate());
					fakeRestrictionItem.setCanDelete(restrictionEntity.getCanDelete() && restrictionEntityGroup.getCanDelete());
					fakeRestrictionItem.setCanUpdate(restrictionEntity.getCanUpdate() && restrictionEntityGroup.getCanUpdate());
					fakeRestrictionItem.setCanSearch(restrictionEntity.getCanSearch() && restrictionEntityGroup.getCanSearch());
					//fakeRestrictionItem.setEntity(restrictionEntity.getEntity());
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
			if (restrictionItem.getEntity()!=null)
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
