
package it.polimi.searchbean;

import java.util.List;

import it.polimi.model.field.AnnotationAttribute;
import it.polimi.model.field.AnnotationType;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;

public class AnnotationSearchBean {

    public Long annotationId;
    public AnnotationType annotationType;
    public List<AnnotationAttribute> annotationAttributeList;
    public Field field;
    public Relationship relationship;
    public EnumField enumField;

    public Long getAnnotationId() {
        return this.annotationId;
    }

    public void setAnnotationId(Long annotationId) {
        this.annotationId=annotationId;
    }

    public AnnotationType getAnnotationType() {
        return this.annotationType;
    }

    public void setAnnotationType(AnnotationType annotationType) {
        this.annotationType=annotationType;
    }

    public List<AnnotationAttribute> getAnnotationAttributeList() {
        return this.annotationAttributeList;
    }

    public void setAnnotationAttributeList(List<AnnotationAttribute> annotationAttributeList) {
        this.annotationAttributeList=annotationAttributeList;
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field=field;
    }

    public Relationship getRelationship() {
        return this.relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship=relationship;
    }

    public EnumField getEnumField() {
        return this.enumField;
    }

    public void setEnumField(EnumField enumField) {
        this.enumField=enumField;
    }

}
