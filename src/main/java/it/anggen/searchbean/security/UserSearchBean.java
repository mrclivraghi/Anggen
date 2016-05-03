
package it.anggen.searchbean.security;

import java.util.List;
import it.anggen.model.security.Role;

public class UserSearchBean {

    public java.lang.Long userId;
    public java.lang.String password;
    public java.lang.String username;
    public java.lang.Boolean enabled;
    public List<Role> roleList;

    public java.lang.Long getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.Long userId) {
        this.userId=userId;
    }

    public java.lang.String getPassword() {
        return this.password;
    }

    public void setPassword(java.lang.String password) {
        this.password=password;
    }

    public java.lang.String getUsername() {
        return this.username;
    }

    public void setUsername(java.lang.String username) {
        this.username=username;
    }

    public java.lang.Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(java.lang.Boolean enabled) {
        this.enabled=enabled;
    }

    public List<Role> getRoleList() {
        return this.roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList=roleList;
    }

}
