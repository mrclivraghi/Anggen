
package it.polimi.searchbean;

import java.util.List;

import it.polimi.model.entity.Entity;
import it.polimi.model.entity.Tab;
import it.polimi.model.field.Annotation;
import it.polimi.model.field.FieldType;
import it.polimi.model.security.RestrictionField;

public class FieldSearchBean {

    public Long fieldId;
    public String name;
    public Entity entity;
    public FieldType fieldType;
    public List<Annotation> annotationList;
    public List<RestrictionField> restrictionFieldList;
    public Tab tab;

    public Long getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId=fieldId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void setEntity(Entity entity) {
        this.entity=entity;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType=fieldType;
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

    public Tab getTab() {
        return this.tab;
    }

    public void setTab(Tab tab) {
        this.tab=tab;
    }

}
