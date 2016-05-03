
package it.anggen.searchbean.entity;

import java.util.List;
import it.anggen.model.entity.Entity;
import it.anggen.model.security.RestrictionEntityGroup;

public class EntityGroupSearchBean {

    public java.lang.Long entityGroupId;
    public java.lang.String name;
    public it.anggen.model.SecurityType securityType;
    public it.anggen.model.entity.Project project;
    public List<RestrictionEntityGroup> restrictionEntityGroupList;
    public List<Entity> entityList;

    public java.lang.Long getEntityGroupId() {
        return this.entityGroupId;
    }

    public void setEntityGroupId(java.lang.Long entityGroupId) {
        this.entityGroupId=entityGroupId;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public it.anggen.model.SecurityType getSecurityType() {
        return this.securityType;
    }

    public void setSecurityType(it.anggen.model.SecurityType securityType) {
        this.securityType=securityType;
    }

    public it.anggen.model.entity.Project getProject() {
        return this.project;
    }

    public void setProject(it.anggen.model.entity.Project project) {
        this.project=project;
    }

    public List<RestrictionEntityGroup> getRestrictionEntityGroupList() {
        return this.restrictionEntityGroupList;
    }

    public void setRestrictionEntityGroupList(List<RestrictionEntityGroup> restrictionEntityGroupList) {
        this.restrictionEntityGroupList=restrictionEntityGroupList;
    }

    public List<Entity> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList=entityList;
    }

}
