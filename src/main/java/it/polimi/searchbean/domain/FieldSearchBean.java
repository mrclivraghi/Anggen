
package it.polimi.searchbean.domain;

import java.util.List;
import it.polimi.model.domain.Annotation;
import it.polimi.model.domain.Entity;
import it.polimi.model.domain.FieldType;
import it.polimi.model.domain.Tab;

public class FieldSearchBean {

    public Long fieldId;
    public String name;
    public Entity entity;
    public FieldType fieldType;
    public List<Annotation> annotationList;
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

    public Tab getTab() {
        return this.tab;
    }

    public void setTab(Tab tab) {
        this.tab=tab;
    }

}
