
package it.polimi.searchbean.domain;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.FieldType;

public class FieldSearchBean {

    public Long fieldId;
    public String name;
    public Entity entity;
    public FieldType fieldType;
    public Boolean list;

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

    public Boolean getList() {
        return this.list;
    }

    public void setList(Boolean list) {
        this.list=list;
    }

}
