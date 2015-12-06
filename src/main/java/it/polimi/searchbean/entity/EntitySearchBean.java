
package it.polimi.searchbean.entity;

import java.util.List;

import it.polimi.model.entity.Tab;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;
import it.polimi.model.security.RestrictionEntity;

public class EntitySearchBean {

    public java.lang.Long entityId;
    public java.lang.String name;
    public List<Relationship> relationshipList;
    public List<EnumField> enumFieldList;
    public List<Tab> tabList;
    public List<RestrictionEntity> restrictionEntityList;
    public it.polimi.model.entity.EntityGroup entityGroup;
    public List<Field> fieldList;
    public it.polimi.model.SecurityType securityType;

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

    public it.polimi.model.entity.EntityGroup getEntityGroup() {
        return this.entityGroup;
    }

    public void setEntityGroup(it.polimi.model.entity.EntityGroup entityGroup) {
        this.entityGroup=entityGroup;
    }

    public List<Field> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList=fieldList;
    }

    public it.polimi.model.SecurityType getSecurityType() {
        return this.securityType;
    }

    public void setSecurityType(it.polimi.model.SecurityType securityType) {
        this.securityType=securityType;
    }

}
