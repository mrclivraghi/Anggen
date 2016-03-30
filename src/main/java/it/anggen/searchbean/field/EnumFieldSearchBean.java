
package it.anggen.searchbean.field;

import java.util.List;
import it.anggen.model.field.Annotation;

public class EnumFieldSearchBean {

    public java.lang.Long enumFieldId;
    public java.lang.Integer priority;
    public java.lang.String name;
    public it.anggen.model.entity.Tab tab;
    public it.anggen.model.entity.Entity entity;
    public it.anggen.model.entity.EnumEntity enumEntity;
    public List<Annotation> annotationList;

    public java.lang.Long getEnumFieldId() {
        return this.enumFieldId;
    }

    public void setEnumFieldId(java.lang.Long enumFieldId) {
        this.enumFieldId=enumFieldId;
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

    public it.anggen.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.anggen.model.entity.Tab tab) {
        this.tab=tab;
    }

    public it.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

    public it.anggen.model.entity.EnumEntity getEnumEntity() {
        return this.enumEntity;
    }

    public void setEnumEntity(it.anggen.model.entity.EnumEntity enumEntity) {
        this.enumEntity=enumEntity;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

}
