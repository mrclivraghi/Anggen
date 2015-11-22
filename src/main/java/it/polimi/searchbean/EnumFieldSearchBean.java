
package it.polimi.searchbean;

import it.polimi.model.domain.Entity;
import it.polimi.model.domain.EnumValue;

import java.util.List;

public class EnumFieldSearchBean {

    public Long enumFieldId;
    public String name;
    public List<EnumValue> enumValueList;
    public Entity entity;

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

}
