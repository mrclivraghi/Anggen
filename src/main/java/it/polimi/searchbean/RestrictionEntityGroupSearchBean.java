
package it.polimi.searchbean;

import it.polimi.model.entity.EntityGroup;
import it.polimi.model.security.Role;

public class RestrictionEntityGroupSearchBean {

    public Long restrictionEntityGroupId;
    public Boolean canCreate;
    public Boolean canUpdate;
    public Boolean canSearch;
    public Boolean canDelete;
    public EntityGroup entityGroup;
    public Role role;

    public Long getRestrictionEntityGroupId() {
        return this.restrictionEntityGroupId;
    }

    public void setRestrictionEntityGroupId(Long restrictionEntityGroupId) {
        this.restrictionEntityGroupId=restrictionEntityGroupId;
    }

    public Boolean getCanCreate() {
        return this.canCreate;
    }

    public void setCanCreate(Boolean canCreate) {
        this.canCreate=canCreate;
    }

    public Boolean getCanUpdate() {
        return this.canUpdate;
    }

    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate=canUpdate;
    }

    public Boolean getCanSearch() {
        return this.canSearch;
    }

    public void setCanSearch(Boolean canSearch) {
        this.canSearch=canSearch;
    }

    public Boolean getCanDelete() {
        return this.canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete=canDelete;
    }

    public EntityGroup getEntityGroup() {
        return this.entityGroup;
    }

    public void setEntityGroup(EntityGroup entityGroup) {
        this.entityGroup=entityGroup;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role=role;
    }

}
