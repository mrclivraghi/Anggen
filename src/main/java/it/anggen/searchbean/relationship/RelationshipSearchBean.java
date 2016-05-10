
package it.anggen.searchbean.relationship;

import java.util.List;
import it.anggen.model.field.Annotation;

public class RelationshipSearchBean {

    public java.lang.Long relationshipId;
    public java.lang.Integer priority;
    public java.lang.String name;
    public java.util.Date addDate;
    public java.util.Date modDate;
    public it.anggen.model.RelationshipType relationshipType;
    public List<Annotation> annotationList;
    public it.anggen.model.entity.Entity entityTarget;
    public it.anggen.model.entity.Entity entity;
    public it.anggen.model.entity.Tab tab;

    public java.lang.Long getRelationshipId() {
        return this.relationshipId;
    }

    public void setRelationshipId(java.lang.Long relationshipId) {
        this.relationshipId=relationshipId;
    }

    public java.lang.Integer getPriority() {
        return this.priority;
    }

    public void setPriority(java.lang.Integer priority) {
        this.priority=priority;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public java.util.Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(java.util.Date addDate) {
        this.addDate=addDate;
    }

    public java.util.Date getModDate() {
        return this.modDate;
    }

    public void setModDate(java.util.Date modDate) {
        this.modDate=modDate;
    }

    public it.anggen.model.RelationshipType getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(it.anggen.model.RelationshipType relationshipType) {
        this.relationshipType=relationshipType;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public it.anggen.model.entity.Entity getEntityTarget() {
        return this.entityTarget;
    }

    public void setEntityTarget(it.anggen.model.entity.Entity entityTarget) {
        this.entityTarget=entityTarget;
    }

    public it.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

    public it.anggen.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.anggen.model.entity.Tab tab) {
        this.tab=tab;
    }

}
