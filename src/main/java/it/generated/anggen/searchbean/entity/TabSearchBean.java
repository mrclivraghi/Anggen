
package it.generated.anggen.searchbean.entity;

import java.util.List;
import it.generated.anggen.model.field.EnumField;
import it.generated.anggen.model.field.Field;
import it.generated.anggen.model.relationship.Relationship;

public class TabSearchBean {

    public java.lang.String name;
    public java.lang.Long tabId;
    public List<EnumField> enumFieldList;
    public List<Relationship> relationshipList;
    public List<Field> fieldList;
    public it.generated.anggen.model.entity.Entity entity;

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public java.lang.Long getTabId() {
        return this.tabId;
    }

    public void setTabId(java.lang.Long tabId) {
        this.tabId=tabId;
    }

    public List<EnumField> getEnumFieldList() {
        return this.enumFieldList;
    }

    public void setEnumFieldList(List<EnumField> enumFieldList) {
        this.enumFieldList=enumFieldList;
    }

    public List<Relationship> getRelationshipList() {
        return this.relationshipList;
    }

    public void setRelationshipList(List<Relationship> relationshipList) {
        this.relationshipList=relationshipList;
    }

    public List<Field> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList=fieldList;
    }

    public it.generated.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.generated.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

}
