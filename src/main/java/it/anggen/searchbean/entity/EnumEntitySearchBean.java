
package it.anggen.searchbean.entity;

import java.util.List;
import it.anggen.model.field.EnumValue;

public class EnumEntitySearchBean {

    public java.lang.Long enumEntityId;
    public java.lang.String name;
    public List<EnumValue> enumValueList;
    public it.anggen.model.entity.Project project;

    public java.lang.Long getEnumEntityId() {
        return this.enumEntityId;
    }

    public void setEnumEntityId(java.lang.Long enumEntityId) {
        this.enumEntityId=enumEntityId;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public List<EnumValue> getEnumValueList() {
        return this.enumValueList;
    }

    public void setEnumValueList(List<EnumValue> enumValueList) {
        this.enumValueList=enumValueList;
    }

    public it.anggen.model.entity.Project getProject() {
        return this.project;
    }

    public void setProject(it.anggen.model.entity.Project project) {
        this.project=project;
    }

}
