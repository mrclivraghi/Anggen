
package it.generated.anggen.searchbean.field;

import java.util.List;
import it.generated.anggen.model.field.AnnotationAttribute;

public class AnnotationSearchBean {

    public java.lang.Long annotationId;
    public it.generated.anggen.model.field.EnumField enumField;
    public it.generated.anggen.model.relationship.Relationship relationship;
    public it.generated.anggen.model.field.Field field;
    public List<AnnotationAttribute> annotationAttributeList;
    public it.generated.anggen.model.AnnotationType annotationType;

    public java.lang.Long getAnnotationId() {
        return this.annotationId;
    }

    public void setAnnotationId(java.lang.Long annotationId) {
        this.annotationId=annotationId;
    }

    public it.generated.anggen.model.field.EnumField getEnumField() {
        return this.enumField;
    }

    public void setEnumField(it.generated.anggen.model.field.EnumField enumField) {
        this.enumField=enumField;
    }

    public it.generated.anggen.model.relationship.Relationship getRelationship() {
        return this.relationship;
    }

    public void setRelationship(it.generated.anggen.model.relationship.Relationship relationship) {
        this.relationship=relationship;
    }

    public it.generated.anggen.model.field.Field getField() {
        return this.field;
    }

    public void setField(it.generated.anggen.model.field.Field field) {
        this.field=field;
    }

    public List<AnnotationAttribute> getAnnotationAttributeList() {
        return this.annotationAttributeList;
    }

    public void setAnnotationAttributeList(List<AnnotationAttribute> annotationAttributeList) {
        this.annotationAttributeList=annotationAttributeList;
    }

    public it.generated.anggen.model.AnnotationType getAnnotationType() {
        return this.annotationType;
    }

    public void setAnnotationType(it.generated.anggen.model.AnnotationType annotationType) {
        this.annotationType=annotationType;
    }

}
