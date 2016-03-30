
package it.anggen.model.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import it.anggen.utils.annotation.DescriptionField;
import it.anggen.utils.annotation.MaxDescendantLevel;

@Entity
@Table(schema = "sso", name = "restriction_entity_group")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class RestrictionEntityGroup {

    public final static java.lang.Long staticEntityId = 5L;
    @javax.persistence.Column(name = "can_delete")
    @it.anggen.utils.annotation.Priority(2)
    private java.lang.Boolean canDelete;
    @javax.persistence.Column(name = "restriction_entity_group_id")
    @Id
    @GeneratedValue
    @DescriptionField
    @it.anggen.utils.annotation.Priority(1)
    private java.lang.Long restrictionEntityGroupId;
    @javax.persistence.Column(name = "can_update")
    @it.anggen.utils.annotation.Priority(2)
    private java.lang.Boolean canUpdate;
    @javax.persistence.Column(name = "can_search")
    @it.anggen.utils.annotation.Priority(2)
    private java.lang.Boolean canSearch;
    @javax.persistence.Column(name = "can_create")
    @it.anggen.utils.annotation.Priority(2)
    private java.lang.Boolean canCreate;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "role_id_role")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.security.Role role;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.entity.EntityGroup entityGroup;

    public java.lang.Boolean getCanDelete() {
        return this.canDelete;
    }

    public void setCanDelete(java.lang.Boolean canDelete) {
        this.canDelete=canDelete;
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

    public java.lang.Boolean getCanCreate() {
        return this.canCreate;
    }

    public void setCanCreate(java.lang.Boolean canCreate) {
        this.canCreate=canCreate;
    }

    public it.anggen.model.security.Role getRole() {
        return this.role;
    }

    public void setRole(it.anggen.model.security.Role role) {
        this.role=role;
    }

    public it.anggen.model.entity.EntityGroup getEntityGroup() {
        return this.entityGroup;
    }

    public void setEntityGroup(it.anggen.model.entity.EntityGroup entityGroup) {
        this.entityGroup=entityGroup;
    }

}
