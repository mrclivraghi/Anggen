package it.polimi.security;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.polimi.model.security.RestrictionEntity;
import it.polimi.model.security.RestrictionEntityGroup;
import it.polimi.model.security.RestrictionField;
import it.polimi.model.security.Role;
import it.polimi.model.security.User;

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
		for (Role role: user.getRoleList())
		{
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
