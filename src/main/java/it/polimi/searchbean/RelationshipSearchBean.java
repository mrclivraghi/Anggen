
package it.polimi.searchbean;

import java.util.List;
import it.polimi.model.domain.Annotation;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.RelationshipType;
import it.polimi.model.domain.Tab;

public class RelationshipSearchBean {

    public Long relationshipId;
    public String name;
    public Entity entity;
    public Entity entityTarget;
    public RelationshipType relationshipType;
    public List<Annotation> annotationList;
    public Tab tab;

    public Long getRelationshipId() {
        return this.relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId=relationshipId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void setEntity(Entity entity) {
        this.entity=entity;
    }

    public Entity getEntityTarget() {
        return this.entityTarget;
    }

    public void setEntityTarget(Entity entityTarget) {
        this.entityTarget=entityTarget;
    }

    public RelationshipType getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType=relationshipType;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public Tab getTab() {
        return this.tab;
    }

    public void setTab(Tab tab) {
        this.tab=tab;
    }

}
