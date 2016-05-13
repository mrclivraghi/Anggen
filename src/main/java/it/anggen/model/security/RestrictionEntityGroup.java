
package it.anggen.model.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import it.anggen.utils.annotation.DescriptionField;
import it.anggen.utils.annotation.GenerationType;
import it.anggen.utils.annotation.IncludeMenu;
import it.anggen.utils.annotation.MaxDescendantLevel;

@Entity
@Table(schema = "sso", name = "restriction_entity_group")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
@IncludeMenu
@GenerationType(type=it.anggen.model.GenerationType.HIDE_IGNORE)
public class RestrictionEntityGroup {

    public final static java.lang.Long staticEntityId = 2L;
    @javax.persistence.Column(name = "restriction_entity_group_id")
    @Id
    @GeneratedValue
    @DescriptionField
    private java.lang.Long restrictionEntityGroupId;
    @javax.persistence.Column(name = "can_create")
    private java.lang.Boolean canCreate;
    @javax.persistence.Column(name = "can_search")
    private java.lang.Boolean canSearch;
    @javax.persistence.Column(name = "can_delete")
    private java.lang.Boolean canDelete;
    @javax.persistence.Column(name = "can_update")
    private java.lang.Boolean canUpdate;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "role_id_role")
    private it.anggen.model.security.Role role;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    private it.anggen.model.entity.EntityGroup entityGroup;

    public java.lang.Long getRestrictionEntityGroupId() {
        return this.restrictionEntityGroupId;
    }

    public void setRestrictionEntityGroupId(java.lang.Long restrictionEntityGroupId) {
        this.restrictionEntityGroupId=restrictionEntityGroupId;
    }

    public java.lang.Boolean getCanCreate() {
        return this.canCreate;
    }

    public void setCanCreate(java.lang.Boolean canCreate) {
        this.canCreate=canCreate;
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

    public java.lang.Boolean getCanUpdate() {
        return this.canUpdate;
    }

    public void setCanUpdate(java.lang.Boolean canUpdate) {
        this.canUpdate=canUpdate;
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
