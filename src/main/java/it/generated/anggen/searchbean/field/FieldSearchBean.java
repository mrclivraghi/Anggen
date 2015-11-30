
package it.generated.anggen.searchbean.field;

import java.util.List;
import it.generated.anggen.model.field.Annotation;
import it.generated.anggen.model.security.RestrictionField;

public class FieldSearchBean {

    public java.lang.String name;
    public java.lang.Long fieldId;
    public it.generated.anggen.model.entity.Tab tab;
    public List<RestrictionField> restrictionFieldList;
    public List<Annotation> annotationList;
    public it.generated.anggen.model.entity.Entity entity;
    public it.generated.anggen.model.FieldType fieldType;

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public java.lang.Long getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(java.lang.Long fieldId) {
        this.fieldId=fieldId;
    }

    public it.generated.anggen.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.generated.anggen.model.entity.Tab tab) {
        this.tab=tab;
    }

    public List<RestrictionField> getRestrictionFieldList() {
        return this.restrictionFieldList;
    }

    public void setRestrictionFieldList(List<RestrictionField> restrictionFieldList) {
        this.restrictionFieldList=restrictionFieldList;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public it.generated.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.generated.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

    public it.generated.anggen.model.FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(it.generated.anggen.model.FieldType fieldType) {
        this.fieldType=fieldType;
    }

}
