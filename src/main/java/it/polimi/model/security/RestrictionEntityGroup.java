
package it.polimi.model.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.polimi.utils.annotation.DescriptionField;

@Entity
@Table(schema = "sso", name = "restriction_entity_group")
public class RestrictionEntityGroup {

    public final static java.lang.Long staticEntityId = 12L;
    @javax.persistence.Column(name = "can_create")
    private java.lang.Boolean canCreate;
    @javax.persistence.Column(name = "restriction_entity_group_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long restrictionEntityGroupId;
    @javax.persistence.Column(name = "can_update")
    private java.lang.Boolean canUpdate;
    @javax.persistence.Column(name = "can_search")
    private java.lang.Boolean canSearch;
    @javax.persistence.Column(name = "can_delete")
    private java.lang.Boolean canDelete;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    private it.polimi.model.entity.EntityGroup entityGroup;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "role_id_role")
    private it.polimi.model.security.Role role;

    public java.lang.Boolean getCanCreate() {
        return this.canCreate;
    }

    public void setCanCreate(java.lang.Boolean canCreate) {
        this.canCreate=canCreate;
    }

    public java.lang.Long getRestrictionEntityGroupId() {
        return this.restrictionEntityGroupId;
    }

    public void setRestrictionEntityGroupId(java.lang.Long restrictionEntityGroupId) {
        this.restrictionEntityGroupId=restrictionEntityGroupId;
    }

    public java.lang.Boolean getCanUpdate() {
        return this.canUpdate;
    }

    public void setCanUpdate(java.lang.Boolean canUpdate) {
        this.canUpdate=canUpdate;
    }

    public java.lang.Boolean getCanSearch() {
        return this.canSearch;
    }

    public void setCanSearch(java.lang.Boolean canSearch) {
        this.canSearch=canSearch;
    }

    public java.lang.Boolean getCanDelete() {
        return this.canDelete;
    }

    public void setCanDelete(java.lang.Boolean canDelete) {
        this.canDelete=canDelete;
    }

    public it.polimi.model.entity.EntityGroup getEntityGroup() {
        return this.entityGroup;
    }

    public void setEntityGroup(it.polimi.model.entity.EntityGroup entityGroup) {
        this.entityGroup=entityGroup;
    }

    public it.polimi.model.security.Role getRole() {
        return this.role;
    }

    public void setRole(it.polimi.model.security.Role role) {
        this.role=role;
    }

}
