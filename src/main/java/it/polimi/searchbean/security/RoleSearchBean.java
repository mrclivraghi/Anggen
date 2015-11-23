
package it.polimi.searchbean.security;

import java.util.List;
import it.polimi.model.security.Entity;
import it.polimi.model.security.User;

public class RoleSearchBean {

    public Integer roleId;
    public String role;
    public List<User> userList;
    public List<Entity> entityList;

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

    public List<Entity> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList=entityList;
    }

}
