
package it.polimi.searchbean;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Role;

public class RestrictionSearchBean {

    public Long restrictionId;
    public Boolean canCreate;
    public Boolean canUpdate;
    public Boolean canSearch;
    public Boolean canDelete;
    public Role role;
    public Entity entity;

    public Long getRestrictionId() {
        return this.restrictionId;
    }

    public void setRestrictionId(Long restrictionId) {
        this.restrictionId=restrictionId;
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

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role=role;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void setEntity(Entity entity) {
        this.entity=entity;
    }

}
