
package it.polimi.searchbean.relationship;

import java.util.List;

import it.polimi.model.field.Annotation;

public class RelationshipSearchBean {

    public java.lang.Long relationshipId;
    public java.lang.String name;
    public it.polimi.model.entity.Entity entity;
    public it.polimi.model.entity.Entity entityTarget;
    public List<Annotation> annotationList;
    public it.polimi.model.entity.Tab tab;
    public it.polimi.model.RelationshipType relationshipType;

    public java.lang.Long getRelationshipId() {
        return this.relationshipId;
    }

    public void setRelationshipId(java.lang.Long relationshipId) {
        this.relationshipId=relationshipId;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public it.polimi.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.polimi.model.entity.Entity entity) {
        this.entity=entity;
    }

    public it.polimi.model.entity.Entity getEntityTarget() {
        return this.entityTarget;
    }

    public void setEntityTarget(it.polimi.model.entity.Entity entityTarget) {
        this.entityTarget=entityTarget;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public it.polimi.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.polimi.model.entity.Tab tab) {
        this.tab=tab;
    }

    public it.polimi.model.RelationshipType getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(it.polimi.model.RelationshipType relationshipType) {
        this.relationshipType=relationshipType;
    }

}
