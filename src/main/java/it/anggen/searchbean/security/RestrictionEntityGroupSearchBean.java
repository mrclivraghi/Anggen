
package it.anggen.searchbean.security;


public class RestrictionEntityGroupSearchBean {

    public java.lang.Long restrictionEntityGroupId;
    public java.lang.Boolean canCreate;
    public java.lang.Boolean canSearch;
    public java.lang.Boolean canDelete;
    public java.lang.Boolean canUpdate;
    public it.anggen.model.security.Role role;
    public it.anggen.model.entity.EntityGroup entityGroup;

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
