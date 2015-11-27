
package it.polimi.searchbean;

import java.util.List;
import it.polimi.model.domain.Restriction;
import it.polimi.model.domain.User;

public class RoleSearchBean {

    public Integer roleId;
    public String role;
    public List<User> userList;
    public List<Restriction> restrictionList;

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

    public List<Restriction> getRestrictionList() {
        return this.restrictionList;
    }

    public void setRestrictionList(List<Restriction> restrictionList) {
        this.restrictionList=restrictionList;
    }

}
