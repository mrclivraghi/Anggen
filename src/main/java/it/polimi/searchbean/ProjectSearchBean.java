
package it.polimi.searchbean;

import java.util.List;

import it.polimi.model.entity.EntityGroup;

public class ProjectSearchBean {

    public java.lang.String name;
    public java.lang.Integer projectId;
    public List<EntityGroup> entityGroupList;

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public java.lang.Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(java.lang.Integer projectId) {
        this.projectId=projectId;
    }

    public List<EntityGroup> getEntityGroupList() {
        return this.entityGroupList;
    }

    public void setEntityGroupList(List<EntityGroup> entityGroupList) {
        this.entityGroupList=entityGroupList;
    }

}
