
package it.polimi.searchbean.field;

import java.util.List;

import it.polimi.model.field.AnnotationAttribute;

public class AnnotationSearchBean {

    public java.lang.Long annotationId;
    public List<AnnotationAttribute> annotationAttributeList;
    public it.polimi.model.field.Field field;
    public it.polimi.model.relationship.Relationship relationship;
    public it.polimi.model.field.EnumField enumField;
    public it.polimi.model.AnnotationType annotationType;

    public java.lang.Long getAnnotationId() {
        return this.annotationId;
    }

    public void setAnnotationId(java.lang.Long annotationId) {
        this.annotationId=annotationId;
    }

    public List<AnnotationAttribute> getAnnotationAttributeList() {
        return this.annotationAttributeList;
    }

    public void setAnnotationAttributeList(List<AnnotationAttribute> annotationAttributeList) {
        this.annotationAttributeList=annotationAttributeList;
    }

    public it.polimi.model.field.Field getField() {
        return this.field;
    }

    public void setField(it.polimi.model.field.Field field) {
        this.field=field;
    }

    public it.polimi.model.relationship.Relationship getRelationship() {
        return this.relationship;
    }

    public void setRelationship(it.polimi.model.relationship.Relationship relationship) {
        this.relationship=relationship;
    }

    public it.polimi.model.field.EnumField getEnumField() {
        return this.enumField;
    }

    public void setEnumField(it.polimi.model.field.EnumField enumField) {
        this.enumField=enumField;
    }

    public it.polimi.model.AnnotationType getAnnotationType() {
        return this.annotationType;
    }

    public void setAnnotationType(it.polimi.model.AnnotationType annotationType) {
        this.annotationType=annotationType;
    }

}
