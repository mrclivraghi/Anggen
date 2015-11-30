
package it.generated.anggen.searchbean.entity;

import java.util.List;
import it.generated.anggen.model.entity.Entity;
import it.generated.anggen.model.security.RestrictionEntityGroup;

public class EntityGroupSearchBean {

    public java.lang.String name;
    public java.lang.Long entityGroupId;
    public java.lang.Long entityId;
    public it.generated.anggen.model.entity.Project project;
    public List<RestrictionEntityGroup> restrictionEntityGroupList;
    public List<Entity> entityList;

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public java.lang.Long getEntityGroupId() {
        return this.entityGroupId;
    }

    public void setEntityGroupId(java.lang.Long entityGroupId) {
        this.entityGroupId=entityGroupId;
    }

    public java.lang.Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(java.lang.Long entityId) {
        this.entityId=entityId;
    }

    public it.generated.anggen.model.entity.Project getProject() {
        return this.project;
    }

    public void setProject(it.generated.anggen.model.entity.Project project) {
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
