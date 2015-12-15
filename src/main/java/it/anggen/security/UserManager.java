package it.anggen.security;

import java.util.ArrayList;
import java.util.List;


import it.anggen.model.security.RestrictionEntity;
import it.anggen.model.security.RestrictionEntityGroup;
import it.anggen.model.security.RestrictionField;
import it.anggen.model.security.Role;
import it.anggen.model.security.User;

public class UserManager {

	private User user;
	
	public UserManager(User user) {
		this.user=user;
	}
	
	public List<RestrictionEntity> getRestrictionEntityList(){
		List<RestrictionEntity> restrictionList = new ArrayList<RestrictionEntity>();
		for (Role role: user.getRoleList())
		{
			restrictionList.addAll(role.getRestrictionEntityList());
		}
		return restrictionList;
	}
	
	public List<RestrictionEntityGroup> getRestrictionEntityGroupList(){
		List<RestrictionEntityGroup> restrictionList = new ArrayList<RestrictionEntityGroup>();
		if (user.getRoleList()!=null)
		for (Role role: user.getRoleList())
		{
			if (role.getRestrictionEntityGroupList()!=null)
				restrictionList.addAll(role.getRestrictionEntityGroupList());
		}
		return restrictionList;
	}

	public List<RestrictionField> getRestrictionFieldList() {
		List<RestrictionField> restrictionList = new ArrayList<RestrictionField>();
		for (Role role: user.getRoleList())
		{
			restrictionList.addAll(role.getRestrictionFieldList());
		}
		return restrictionList;
	}

}
