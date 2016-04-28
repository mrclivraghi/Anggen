
package it.anggen.searchbean.field;

import java.util.List;
import it.anggen.model.field.Annotation;
import it.anggen.model.security.RestrictionField;

public class FieldSearchBean {

    public java.lang.Long fieldId;
    public java.lang.Integer priority;
    public java.lang.String name;
    public it.anggen.model.FieldType fieldType;
    public List<RestrictionField> restrictionFieldList;
    public it.anggen.model.entity.Entity entity;
    public it.anggen.model.entity.Tab tab;
    public List<Annotation> annotationList;

    public java.lang.Long getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(java.lang.Long fieldId) {
        this.fieldId=fieldId;
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

    public it.anggen.model.FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(it.anggen.model.FieldType fieldType) {
        this.fieldType=fieldType;
    }

    public List<RestrictionField> getRestrictionFieldList() {
        return this.restrictionFieldList;
    }

    public void setRestrictionFieldList(List<RestrictionField> restrictionFieldList) {
        this.restrictionFieldList=restrictionFieldList;
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

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

}
