
package it.generated.anggen.searchbean.relationship;

import java.util.List;
import it.generated.anggen.model.field.Annotation;

public class RelationshipSearchBean {

    public java.lang.Long relationshipId;
    public java.lang.String name;
    public it.generated.anggen.model.entity.Entity entity;
    public it.generated.anggen.model.entity.Entity entityTarget;
    public List<Annotation> annotationList;
    public it.generated.anggen.model.entity.Tab tab;
    public it.generated.anggen.model.RelationshipType relationshipType;

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

    public it.generated.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.generated.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

    public it.generated.anggen.model.entity.Entity getEntityTarget() {
        return this.entityTarget;
    }

    public void setEntityTarget(it.generated.anggen.model.entity.Entity entityTarget) {
        this.entityTarget=entityTarget;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public it.generated.anggen.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.generated.anggen.model.entity.Tab tab) {
        this.tab=tab;
    }

    public it.generated.anggen.model.RelationshipType getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(it.generated.anggen.model.RelationshipType relationshipType) {
        this.relationshipType=relationshipType;
    }

}
