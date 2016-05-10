
package it.anggen.model.security;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import it.anggen.model.security.Role;
import it.anggen.utils.annotation.MaxDescendantLevel;
import it.anggen.utils.annotation.Password;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "sso", name = "user")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class User {

    public final static java.lang.Long staticEntityId = 3L;
    @javax.persistence.Column(name = "user_id")
    @it.anggen.utils.annotation.DescriptionField
    @Id
    @GeneratedValue
    private java.lang.Long userId;
    @javax.persistence.Column(name = "username")
    @it.anggen.utils.annotation.DescriptionField
    private java.lang.String username;
    @javax.persistence.Column(name = "enabled")
    private Boolean enabled;
    @javax.persistence.Column(name = "password")
    @Password
    private java.lang.String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @Type(type = "it.anggen.model.security.Role")
    @JoinTable(name = "user_role", schema = "sso", joinColumns = {
        @JoinColumn(name = "user_id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "role_id")
    })
    private List<Role> roleList;

    public java.lang.Long getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.Long userId) {
        this.userId=userId;
    }

    public java.lang.String getUsername() {
        return this.username;
    }

    public void setUsername(java.lang.String username) {
        this.username=username;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled=enabled;
    }

    public java.lang.String getPassword() {
        return this.password;
    }

    public void setPassword(java.lang.String password) {
        this.password=password;
    }

    public List<Role> getRoleList() {
        return this.roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList=roleList;
    }

}
