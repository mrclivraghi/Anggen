
package it.polimi.searchbean;

import java.util.List;
import it.polimi.model.domain.RestrictionEntity;
import it.polimi.model.domain.RestrictionEntityGroup;
import it.polimi.model.domain.RestrictionField;
import it.polimi.model.domain.User;

public class RoleSearchBean {

    public Integer roleId;
    public String role;
    public List<User> userList;
    public List<RestrictionEntity> restrictionEntityList;
    public List<RestrictionField> restrictionFieldList;
    public List<RestrictionEntityGroup> restrictionEntityGroupList;

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId=roleId;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role=role;
    }

    public List<User> getUserList() {
        return this.userList;
    }

    public void setUserList(List<User> userList) {
        this.userList=userList;
    }

    public List<RestrictionEntity> getRestrictionEntityList() {
        return this.restrictionEntityList;
    }

    public void setRestrictionEntityList(List<RestrictionEntity> restrictionEntityList) {
        this.restrictionEntityList=restrictionEntityList;
    }

    public List<RestrictionField> getRestrictionFieldList() {
        return this.restrictionFieldList;
    }

    public void setRestrictionFieldList(List<RestrictionField> restrictionFieldList) {
        this.restrictionFieldList=restrictionFieldList;
    }

    public List<RestrictionEntityGroup> getRestrictionEntityGroupList() {
        return this.restrictionEntityGroupList;
    }

    public void setRestrictionEntityGroupList(List<RestrictionEntityGroup> restrictionEntityGroupList) {
        this.restrictionEntityGroupList=restrictionEntityGroupList;
    }

}
