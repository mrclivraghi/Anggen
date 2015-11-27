
package it.polimi.searchbean;

import java.util.List;
import it.polimi.model.domain.Role;

public class UserSearchBean {

    public Long userId;
    public String username;
    public String password;
    public Boolean enabled;
    public List<Role> roleList;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId=userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password=password;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled=enabled;
    }

    public List<Role> getRoleList() {
        return this.roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList=roleList;
    }

}
