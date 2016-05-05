
package it.anggen.model.security;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import it.anggen.utils.annotation.MaxDescendantLevel;

@javax.persistence.Entity
@Table(schema = "sso", name = "restriction_entity")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class RestrictionEntity {

    public final static java.lang.Long staticEntityId = 10L;
    @javax.persistence.Column(name = "restriction_entity_id")
    @Id
    @GeneratedValue
    @it.anggen.utils.annotation.DescriptionField
    private java.lang.Long restrictionEntityId;
    @javax.persistence.Column(name = "can_update")
    private java.lang.Boolean canUpdate;
    @javax.persistence.Column(name = "can_search")
    private java.lang.Boolean canSearch;
    @javax.persistence.Column(name = "can_create")
    private java.lang.Boolean canCreate;
    @javax.persistence.Column(name = "can_delete")
    private java.lang.Boolean canDelete;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "role_id_role")
    private it.anggen.model.security.Role role;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    @it.anggen.utils.annotation.DescriptionField
    private it.anggen.model.entity.Entity entity;

    public java.lang.Long getRestrictionEntityId() {
        return this.restrictionEntityId;
    }

    public void setRestrictionEntityId(java.lang.Long restrictionEntityId) {
        this.restrictionEntityId=restrictionEntityId;
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

    public java.lang.Boolean getCanDelete() {
        return this.canDelete;
    }

    public void setCanDelete(java.lang.Boolean canDelete) {
        this.canDelete=canDelete;
    }

    public it.anggen.model.security.Role getRole() {
        return this.role;
    }

    public void setRole(it.anggen.model.security.Role role) {
        this.role=role;
    }

    public it.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

}
