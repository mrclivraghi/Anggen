
package it.generated.anggen.searchbean.entity;

import java.util.List;
import it.generated.anggen.model.entity.EntityGroup;

public class ProjectSearchBean {

    public java.lang.Long entityId;
    public java.lang.Integer projectId;
    public java.lang.String name;
    public List<EntityGroup> entityGroupList;

    public java.lang.Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(java.lang.Long entityId) {
        this.entityId=entityId;
    }

    public java.lang.Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(java.lang.Integer projectId) {
        this.projectId=projectId;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public List<EntityGroup> getEntityGroupList() {
        return this.entityGroupList;
    }

    public void setEntityGroupList(List<EntityGroup> entityGroupList) {
        this.entityGroupList=entityGroupList;
    }

}
