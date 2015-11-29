
package it.polimi.searchbean;

import java.util.List;
import it.polimi.domain.EntityGroupTest;

public class ProjectSearchBean {

    public java.lang.String name;
    public java.lang.Integer projectId;
    public List<EntityGroupTest> EntityGroupTestList;

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

    public List<EntityGroupTest> getEntityGroupTestList() {
        return this.EntityGroupTestList;
    }

    public void setEntityGroupTestList(List<EntityGroupTest> EntityGroupTestList) {
        this.EntityGroupTestList=EntityGroupTestList;
    }

}
