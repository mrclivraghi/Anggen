
package it.polimi.model.security;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.polimi.utils.annotation.DescriptionField;

@javax.persistence.Entity
@Table(schema = "sso", name = "restriction_entity")
public class RestrictionEntity {

    public final static java.lang.Long staticEntityId = 5L;
    @javax.persistence.Column(name = "can_create")
    private java.lang.Boolean canCreate;
    @javax.persistence.Column(name = "can_delete")
    private java.lang.Boolean canDelete;
    @javax.persistence.Column(name = "restriction_entity_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long restrictionEntityId;
    @javax.persistence.Column(name = "can_update")
    private java.lang.Boolean canUpdate;
    @javax.persistence.Column(name = "can_search")
    private java.lang.Boolean canSearch;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "role_id_role")
    private it.polimi.model.security.Role role;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private it.polimi.model.entity.Entity entity;

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

    public it.polimi.model.security.Role getRole() {
        return this.role;
    }

    public void setRole(it.polimi.model.security.Role role) {
        this.role=role;
    }

    public it.polimi.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.polimi.model.entity.Entity entity) {
        this.entity=entity;
    }

}
