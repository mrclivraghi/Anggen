
package it.polimi.searchbean.field;

import java.util.List;

import it.polimi.model.field.Annotation;
import it.polimi.model.security.RestrictionField;

public class FieldSearchBean {

    public java.lang.Long fieldId;
    public java.lang.String name;
    public it.polimi.model.entity.Entity entity;
    public List<Annotation> annotationList;
    public List<RestrictionField> restrictionFieldList;
    public it.polimi.model.entity.Tab tab;
    public it.polimi.model.FieldType fieldType;

    public java.lang.Long getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(java.lang.Long fieldId) {
        this.fieldId=fieldId;
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

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public List<RestrictionField> getRestrictionFieldList() {
        return this.restrictionFieldList;
    }

    public void setRestrictionFieldList(List<RestrictionField> restrictionFieldList) {
        this.restrictionFieldList=restrictionFieldList;
    }

    public it.polimi.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.polimi.model.entity.Tab tab) {
        this.tab=tab;
    }

    public it.polimi.model.FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(it.polimi.model.FieldType fieldType) {
        this.fieldType=fieldType;
    }

}
