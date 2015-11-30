
package it.generated.anggen.searchbean.security;

import java.util.List;
import it.generated.anggen.model.security.RestrictionEntity;
import it.generated.anggen.model.security.RestrictionEntityGroup;
import it.generated.anggen.model.security.RestrictionField;
import it.generated.anggen.model.security.User;

public class RoleSearchBean {

    public java.lang.String role;
    public java.lang.Integer roleId;
    public List<RestrictionEntityGroup> restrictionEntityGroupList;
    public List<RestrictionField> restrictionFieldList;
    public List<RestrictionEntity> restrictionEntityList;
    public List<User> userList;

    public java.lang.String getRole() {
        return this.role;
    }

    public void setRole(java.lang.String role) {
        this.role=role;
    }

    public java.lang.Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(java.lang.Integer roleId) {
        this.roleId=roleId;
    }

    public List<RestrictionEntityGroup> getRestrictionEntityGroupList() {
        return this.restrictionEntityGroupList;
    }

    public void setRestrictionEntityGroupList(List<RestrictionEntityGroup> restrictionEntityGroupList) {
        this.restrictionEntityGroupList=restrictionEntityGroupList;
    }

    public List<RestrictionField> getRestrictionFieldList() {
        return this.restrictionFieldList;
    }

    public void setRestrictionFieldList(List<RestrictionField> restrictionFieldList) {
        this.restrictionFieldList=restrictionFieldList;
    }

    public List<RestrictionEntity> getRestrictionEntityList() {
        return this.restrictionEntityList;
    }

    public void setRestrictionEntityList(List<RestrictionEntity> restrictionEntityList) {
        this.restrictionEntityList=restrictionEntityList;
    }

    public List<User> getUserList() {
        return this.userList;
    }

    public void setUserList(List<User> userList) {
        this.userList=userList;
    }

}
