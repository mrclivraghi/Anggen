package it.anggen.controller;

import it.anggen.reflection.EntityManager;
import it.anggen.reflection.EntityManagerImpl;
import it.anggen.repository.entity.EntityRepository;
import it.anggen.security.UserManager;
import it.anggen.security.view.RestrictionGroup;
import it.anggen.security.view.RestrictionItem;
import it.anggen.model.SecurityType;
import it.anggen.model.entity.Entity;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.field.Annotation;
import it.anggen.model.field.Field;
import it.anggen.model.security.RestrictionEntity;
import it.anggen.model.security.RestrictionEntityGroup;
import it.anggen.model.security.RestrictionField;
import it.anggen.model.security.User;
import it.anggen.searchbean.entity.EntityGroupSearchBean;
import it.anggen.searchbean.security.UserSearchBean;
import it.anggen.service.entity.EntityGroupService;
import it.anggen.service.entity.EntityService;
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
	
	@Autowired
	private EntityService entityService;
	
	@Autowired
	private EntityGroupService entityGroupService;
	
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
	
	private Map<String,RestrictionGroup> buildRestrictionMap(List<RestrictionEntity> restrictionEntityList, List<RestrictionEntityGroup> restrictionEntityGroupList, List<RestrictionField> restrictionFieldList)
	{
		Map<Long,RestrictionEntityGroup> restrictionEntityGroupMap = new HashMap<Long,RestrictionEntityGroup>();
		Map<Long,RestrictionEntity> restrictionEntityMap = new HashMap<Long,RestrictionEntity>();
		for (RestrictionEntity restrictionEntity : restrictionEntityList)
			restrictionEntityMap.put(restrictionEntity.getEntity().getEntityId(), restrictionEntity);
		for (RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
			restrictionEntityGroupMap.put(restrictionEntityGroup.getEntityGroup().getEntityGroupId(), restrictionEntityGroup);
		
		Map<String,RestrictionGroup> restrictionMap= new HashMap<String, RestrictionGroup>();
		
		List<EntityGroup> entityGroupList= entityGroupService.find(new EntityGroupSearchBean());
		for (EntityGroup entityGroup: entityGroupList)
		{
			RestrictionGroup tempRestrictionGroup = new RestrictionGroup();
			
			if (restrictionEntityGroupMap.get(entityGroup.getEntityGroupId())!=null)
			{
				tempRestrictionGroup.setCanCreate(restrictionEntityGroupMap.get(entityGroup.getEntityGroupId()).getCanCreate());
				tempRestrictionGroup.setCanDelete(restrictionEntityGroupMap.get(entityGroup.getEntityGroupId()).getCanDelete());
				tempRestrictionGroup.setCanSearch(restrictionEntityGroupMap.get(entityGroup.getEntityGroupId()).getCanSearch());
				tempRestrictionGroup.setCanUpdate(restrictionEntityGroupMap.get(entityGroup.getEntityGroupId()).getCanUpdate());
			} else
			{ //no row, use default
			
				if (entityGroup.getSecurityType()==SecurityType.ACCESS_WITH_PERMISSION)
				{
					tempRestrictionGroup.setCanCreate(false);
					tempRestrictionGroup.setCanDelete(false);
					tempRestrictionGroup.setCanSearch(false);
					tempRestrictionGroup.setCanUpdate(false);
				} else
				{
					tempRestrictionGroup.setCanCreate(true);
					tempRestrictionGroup.setCanDelete(true);
					tempRestrictionGroup.setCanSearch(true);
					tempRestrictionGroup.setCanUpdate(true);
				}
			
			}
			
			Map<String,RestrictionItem> tempRestrictionItemList= new HashMap<String,RestrictionItem>();
			
			for (Entity entity: entityGroup.getEntityList())
			{
				RestrictionItem fakeRestrictionItem= new RestrictionItem();
				fakeRestrictionItem.setEntity(entity);
				if (restrictionEntityMap.get(entity.getEntityId())!=null)
				{
					fakeRestrictionItem.setCanCreate(tempRestrictionGroup.getCanCreate() && restrictionEntityMap.get(entity.getEntityId()).getCanCreate());
					fakeRestrictionItem.setCanDelete(tempRestrictionGroup.getCanDelete() && restrictionEntityMap.get(entity.getEntityId()).getCanDelete());
					fakeRestrictionItem.setCanSearch(tempRestrictionGroup.getCanSearch() && restrictionEntityMap.get(entity.getEntityId()).getCanSearch());
					fakeRestrictionItem.setCanUpdate(tempRestrictionGroup.getCanUpdate() && restrictionEntityMap.get(entity.getEntityId()).getCanUpdate());
					
				} else
				{
					if (entity.getSecurityType()==SecurityType.ACCESS_WITH_PERMISSION)
					{
						fakeRestrictionItem.setCanCreate(false);
						fakeRestrictionItem.setCanDelete(false);
						fakeRestrictionItem.setCanSearch(false);
						fakeRestrictionItem.setCanUpdate(false);
					} else
					{
						fakeRestrictionItem.setCanCreate(tempRestrictionGroup.getCanCreate());
						fakeRestrictionItem.setCanDelete(tempRestrictionGroup.getCanDelete());
						fakeRestrictionItem.setCanSearch(tempRestrictionGroup.getCanSearch());
						fakeRestrictionItem.setCanUpdate(tempRestrictionGroup.getCanUpdate());
					}
				}
				
				Map<String,Boolean> restrictionFieldMap= new HashMap<String, Boolean>();
				for (Field field: entity.getFieldList())
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
				fakeRestrictionItem.setEntity(null);
				fakeRestrictionItem.setRestrictionFieldList(restrictionFieldMap);
				
				
				tempRestrictionItemList.put(entity.getName(), fakeRestrictionItem);
				
			}
			
			restrictionMap.put(entityGroup.getName(), tempRestrictionGroup);
			
		}
		
		
		return restrictionMap;
		
		
		/*if (restrictionEntityGroupList!=null)
		for (RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
		{
			
			RestrictionGroup tempRestrictionGroup = new RestrictionGroup();
			//tempRestrictionGroup.setEntityGroup(restrictionEntityGroup.getEntityGroup());
			tempRestrictionGroup.setCanCreate(restrictionEntityGroup.getCanCreate());
			tempRestrictionGroup.setCanDelete(restrictionEntityGroup.getCanDelete());
			tempRestrictionGroup.setCanSearch(restrictionEntityGroup.getCanSearch());
			tempRestrictionGroup.setCanUpdate(restrictionEntityGroup.getCanUpdate());
			Map<String,RestrictionItem> tempRestrictionItemList= new HashMap<String,RestrictionItem>();
			for (Entity entity:restrictionEntityGroup.getEntityGroup().getEntityList())
			{
				RestrictionItem fakeRestrictionItem= new RestrictionItem();
				fakeRestrictionItem.setCanCreate(restrictionEntityGroup.getCanCreate());
				fakeRestrictionItem.setCanDelete(restrictionEntityGroup.getCanDelete());
				fakeRestrictionItem.setCanSearch(restrictionEntityGroup.getCanSearch());
				fakeRestrictionItem.setCanUpdate(restrictionEntityGroup.getCanUpdate());
				fakeRestrictionItem.setEntity(entity);
				tempRestrictionItemList.put(entity.getName(), fakeRestrictionItem);
			}
			tempRestrictionGroup.setRestrictionItemMap(tempRestrictionItemList);
			
			restrictionMap.put(restrictionEntityGroup.getEntityGroup().getName(), tempRestrictionGroup);
			
		}
		
		if (restrictionEntityList!=null)
		for (RestrictionEntity restrictionEntity: restrictionEntityList)
		{
			RestrictionItem fakeRestrictionItem= restrictionMap.get(restrictionEntity.getEntity().getEntityGroup().getName()).getRestrictionItemMap().get(restrictionEntity.getEntity().getName());
			fakeRestrictionItem.setCanCreate(restrictionEntity.getCanCreate() && fakeRestrictionItem.getCanCreate());
			fakeRestrictionItem.setCanDelete(restrictionEntity.getCanDelete() && fakeRestrictionItem.getCanDelete());
			fakeRestrictionItem.setCanUpdate(restrictionEntity.getCanUpdate() && fakeRestrictionItem.getCanUpdate());
			fakeRestrictionItem.setCanSearch(restrictionEntity.getCanSearch() && fakeRestrictionItem.getCanSearch());
		}
		
		addRestrictionField(restrictionMap,restrictionFieldList);
		return restrictionMap;*/
	}
	
	private void addRestrictionField(Map<String,RestrictionGroup> restrictionMap, List<RestrictionField> restrictionFieldList)
	{
		
		for (RestrictionGroup restrictionGroup: restrictionMap.values())
		for (RestrictionItem restrictionItem: restrictionGroup.getRestrictionItemMap().values())
		{
			if (restrictionItem==null) continue;
			Map<String,Boolean> restrictionFieldMap= new HashMap<String, Boolean>();
			if (restrictionItem!=null && restrictionItem.getEntity()!=null)
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
