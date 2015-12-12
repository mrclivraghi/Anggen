
package it.polimi.model.security;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.polimi.model.security.RestrictionEntity;
import it.polimi.model.security.RestrictionEntityGroup;
import it.polimi.model.security.RestrictionField;
import it.polimi.model.security.User;
import it.polimi.utils.annotation.DescriptionField;

import org.hibernate.annotations.Type;

@Entity
@Table(schema = "sso", name = "role")
public class Role {

    public final static Long staticEntityId = 13L;
    @javax.persistence.Column(name = "role")
    @DescriptionField
    private String role;
    @javax.persistence.Column(name = "role_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private Integer roleId;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roleList")
    private List<User> userList;
    
    @OneToMany(fetch = FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.security.RestrictionEntity")
    @JoinColumn(name = "role_id_role")
    private List<RestrictionEntity> restrictionEntityList;
    
    @OneToMany(fetch = FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.security.RestrictionField")
    @JoinColumn(name = "role_id_role")
    private List<RestrictionField> restrictionFieldList;
    
    @OneToMany(fetch = FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.security.RestrictionEntityGroup")
    @JoinColumn(name = "role_id_role")
    private List<RestrictionEntityGroup> restrictionEntityGroupList;

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role=role;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId=roleId;
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
