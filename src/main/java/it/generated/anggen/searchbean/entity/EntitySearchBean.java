
package it.generated.anggen.searchbean.entity;

import java.util.List;
import it.generated.anggen.model.entity.Tab;
import it.generated.anggen.model.field.EnumField;
import it.generated.anggen.model.field.Field;
import it.generated.anggen.model.relationship.Relationship;
import it.generated.anggen.model.security.RestrictionEntity;

public class EntitySearchBean {

    public java.lang.Long entityId;
    public java.lang.String name;
    public List<Field> fieldList;
    public List<Relationship> relationshipList;
    public List<EnumField> enumFieldList;
    public List<Tab> tabList;
    public List<RestrictionEntity> restrictionEntityList;
    public it.generated.anggen.model.entity.EntityGroup entityGroup;

    public java.lang.Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(java.lang.Long entityId) {
        this.entityId=entityId;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name=name;
    }

    public List<Field> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList=fieldList;
    }

    public List<Relationship> getRelationshipList() {
        return this.relationshipList;
    }

    public void setRelationshipList(List<Relationship> relationshipList) {
        this.relationshipList=relationshipList;
    }

    public List<EnumField> getEnumFieldList() {
        return this.enumFieldList;
    }

    public void setEnumFieldList(List<EnumField> enumFieldList) {
        this.enumFieldList=enumFieldList;
    }

    public List<Tab> getTabList() {
        return this.tabList;
    }

    public void setTabList(List<Tab> tabList) {
        this.tabList=tabList;
    }

    public List<RestrictionEntity> getRestrictionEntityList() {
        return this.restrictionEntityList;
    }

    public void setRestrictionEntityList(List<RestrictionEntity> restrictionEntityList) {
        this.restrictionEntityList=restrictionEntityList;
    }

    public it.generated.anggen.model.entity.EntityGroup getEntityGroup() {
        return this.entityGroup;
    }

    public void setEntityGroup(it.generated.anggen.model.entity.EntityGroup entityGroup) {
        this.entityGroup=entityGroup;
    }

}
