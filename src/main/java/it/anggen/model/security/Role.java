
package it.anggen.model.security;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.anggen.model.security.RestrictionEntity;
import it.anggen.model.security.RestrictionEntityGroup;
import it.anggen.model.security.RestrictionField;
import it.anggen.model.security.User;
import it.anggen.utils.annotation.MaxDescendantLevel;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "sso", name = "role")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class Role {

    public final static Long staticEntityId = 15L;
    @javax.persistence.Column(name = "role_id")
    @it.anggen.utils.annotation.DescriptionField
    @Id
    @GeneratedValue
    private Integer roleId;
    @javax.persistence.Column(name = "role")
    @it.anggen.utils.annotation.DescriptionField
    private String role;
    @OneToMany(fetch = FetchType.LAZY)
    @Type(type = "it.anggen.model.security.RestrictionEntity")
    @JoinColumn(name = "role_id_role")
    private List<RestrictionEntity> restrictionEntityList;
    @OneToMany(fetch = FetchType.LAZY)
    @Type(type = "it.anggen.model.security.RestrictionField")
    @JoinColumn(name = "role_id_role")
    private List<RestrictionField> restrictionFieldList;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleList")
    private List<User> userList;
    @OneToMany(fetch = FetchType.LAZY)
    @Type(type = "it.anggen.model.security.RestrictionEntityGroup")
    @JoinColumn(name = "role_id_role")
    private List<RestrictionEntityGroup> restrictionEntityGroupList;

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

    public List<User> getUserList() {
        return this.userList;
    }

    public void setUserList(List<User> userList) {
        this.userList=userList;
    }

    public List<RestrictionEntityGroup> getRestrictionEntityGroupList() {
        return this.restrictionEntityGroupList;
    }

    public void setRestrictionEntityGroupList(List<RestrictionEntityGroup> restrictionEntityGroupList) {
        this.restrictionEntityGroupList=restrictionEntityGroupList;
    }

}
