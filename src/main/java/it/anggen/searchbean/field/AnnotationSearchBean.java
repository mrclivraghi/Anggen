
package it.anggen.searchbean.field;

import java.util.List;
import it.anggen.model.field.AnnotationAttribute;

public class AnnotationSearchBean {

    public java.lang.Long annotationId;
    public it.anggen.model.AnnotationType annotationType;
    public List<AnnotationAttribute> annotationAttributeList;
    public it.anggen.model.field.Field field;
    public it.anggen.model.relationship.Relationship relationship;
    public it.anggen.model.field.EnumField enumField;

    public java.lang.Long getAnnotationId() {
        return this.annotationId;
    }

    public void setAnnotationId(java.lang.Long annotationId) {
        this.annotationId=annotationId;
    }

    public it.anggen.model.AnnotationType getAnnotationType() {
        return this.annotationType;
    }

    public void setAnnotationType(it.anggen.model.AnnotationType annotationType) {
        this.annotationType=annotationType;
    }

    public List<AnnotationAttribute> getAnnotationAttributeList() {
        return this.annotationAttributeList;
    }

    public void setAnnotationAttributeList(List<AnnotationAttribute> annotationAttributeList) {
        this.annotationAttributeList=annotationAttributeList;
    }

    public it.anggen.model.field.Field getField() {
        return this.field;
    }

    public void setField(it.anggen.model.field.Field field) {
        this.field=field;
    }

    public it.anggen.model.relationship.Relationship getRelationship() {
        return this.relationship;
    }

    public void setRelationship(it.anggen.model.relationship.Relationship relationship) {
        this.relationship=relationship;
    }

    public it.anggen.model.field.EnumField getEnumField() {
        return this.enumField;
    }

    public void setEnumField(it.anggen.model.field.EnumField enumField) {
        this.enumField=enumField;
    }

}
