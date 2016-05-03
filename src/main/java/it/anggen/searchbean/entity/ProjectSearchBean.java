
package it.anggen.searchbean.entity;

import java.util.List;
import it.anggen.model.entity.EntityGroup;
import it.anggen.model.entity.EnumEntity;
import it.anggen.model.generation.GenerationRun;

public class ProjectSearchBean {

    public java.lang.Integer projectId;
    public java.lang.String name;
    public List<EntityGroup> entityGroupList;
    public List<EnumEntity> enumEntityList;
    public List<GenerationRun> generationRunList;

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

    public List<EnumEntity> getEnumEntityList() {
        return this.enumEntityList;
    }

    public void setEnumEntityList(List<EnumEntity> enumEntityList) {
        this.enumEntityList=enumEntityList;
    }

    public List<GenerationRun> getGenerationRunList() {
        return this.generationRunList;
    }

    public void setGenerationRunList(List<GenerationRun> generationRunList) {
        this.generationRunList=generationRunList;
    }

}
