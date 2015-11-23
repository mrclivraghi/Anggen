
package it.polimi.searchbean.domain;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.Role;

public class RestrictionSearchBean {

    public Long restrictionId;
    public Boolean create;
    public Boolean update;
    public Boolean search;
    public Boolean delete;
    public Role role;
    public Entity entity;

    public Long getRestrictionId() {
        return this.restrictionId;
    }

    public void setRestrictionId(Long restrictionId) {
        this.restrictionId=restrictionId;
    }

    public Boolean getCreate() {
        return this.create;
    }

    public void setCreate(Boolean create) {
        this.create=create;
    }

    public Boolean getUpdate() {
        return this.update;
    }

    public void setUpdate(Boolean update) {
        this.update=update;
    }

    public Boolean getSearch() {
        return this.search;
    }

    public void setSearch(Boolean search) {
        this.search=search;
    }

    public Boolean getDelete() {
        return this.delete;
    }

    public void setDelete(Boolean delete) {
        this.delete=delete;
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
