
package it.polimi.searchbean.security;

import it.polimi.model.security.Role;

public class UserSearchBean {

    public Long userId;
    public String username;
    public String password;
    public Boolean enabled;
    public Role role;

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

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role=role;
    }

}
