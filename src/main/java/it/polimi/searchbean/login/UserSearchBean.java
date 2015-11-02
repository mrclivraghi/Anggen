
package it.polimi.searchbean.login;

import java.util.List;
import it.polimi.model.login.Authority;

public class UserSearchBean {

    public Long userId;
    public String username;
    public String password;
    public String role;
    public Boolean enabled;
    public List<Authority> authorityList;

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

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role=role;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled=enabled;
    }

    public List<Authority> getAuthorityList() {
        return this.authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList=authorityList;
    }

}
