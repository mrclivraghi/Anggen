
package it.polimi.searchbean.domain;

import it.polimi.model.domain.EnumField;

public class EnumValueSearchBean {

    public Long enumValueId;
    public Integer value;
    public String name;
    public EnumField enumField;

    public Long getEnumValueId() {
        return this.enumValueId;
    }

    public void setEnumValueId(Long enumValueId) {
        this.enumValueId=enumValueId;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value=value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public EnumField getEnumField() {
        return this.enumField;
    }

    public void setEnumField(EnumField enumField) {
        this.enumField=enumField;
    }

}
