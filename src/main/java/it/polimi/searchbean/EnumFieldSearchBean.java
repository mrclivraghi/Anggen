
package it.polimi.searchbean;

import java.util.List;

import it.polimi.model.entity.Entity;
import it.polimi.model.entity.Tab;
import it.polimi.model.field.Annotation;
import it.polimi.model.field.EnumValue;

public class EnumFieldSearchBean {

    public Long enumFieldId;
    public String name;
    public List<EnumValue> enumValueList;
    public Entity entity;
    public List<Annotation> annotationList;
    public Tab tab;

    public Long getEnumFieldId() {
        return this.enumFieldId;
    }

    public void setEnumFieldId(Long enumFieldId) {
        this.enumFieldId=enumFieldId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public List<EnumValue> getEnumValueList() {
        return this.enumValueList;
    }

    public void setEnumValueList(List<EnumValue> enumValueList) {
        this.enumValueList=enumValueList;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void setEntity(Entity entity) {
        this.entity=entity;
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
