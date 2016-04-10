
package it.anggen.searchbean.security;


public class RestrictionEntitySearchBean {

    public java.lang.Long restrictionEntityId;
    public java.lang.Boolean canCreate;
    public java.lang.Boolean canUpdate;
    public java.lang.Boolean canDelete;
    public java.lang.Boolean canSearch;
    public it.anggen.model.security.Role role;
    public it.anggen.model.entity.Entity entity;

    public java.lang.Long getRestrictionEntityId() {
        return this.restrictionEntityId;
    }

    public void setRestrictionEntityId(java.lang.Long restrictionEntityId) {
        this.restrictionEntityId=restrictionEntityId;
    }

    public java.lang.Boolean getCanCreate() {
        return this.canCreate;
    }

    public void setCanCreate(java.lang.Boolean canCreate) {
        this.canCreate=canCreate;
    }

    public java.lang.Boolean getCanUpdate() {
        return this.canUpdate;
    }

    public void setCanUpdate(java.lang.Boolean canUpdate) {
        this.canUpdate=canUpdate;
    }

    public java.lang.Boolean getCanDelete() {
        return this.canDelete;
    }

    public void setCanDelete(java.lang.Boolean canDelete) {
        this.canDelete=canDelete;
    }

    public java.lang.Boolean getCanSearch() {
        return this.canSearch;
    }

    public void setCanSearch(java.lang.Boolean canSearch) {
        this.canSearch=canSearch;
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
