
package it.anggen.searchbean.entity;

import java.util.List;
import it.anggen.model.entity.Tab;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;
import it.anggen.model.security.RestrictionEntity;

public class EntitySearchBean {

    public java.lang.Long entityId;
    public java.lang.String name;
    public java.lang.Integer descendantMaxLevel;
    public it.anggen.model.SecurityType securityType;
    public List<Field> fieldList;
    public it.anggen.model.entity.EntityGroup entityGroup;
    public List<RestrictionEntity> restrictionEntityList;
    public List<Tab> tabList;
    public List<EnumField> enumFieldList;
    public List<Relationship> relationshipList;

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

    public java.lang.Integer getDescendantMaxLevel() {
        return this.descendantMaxLevel;
    }

    public void setDescendantMaxLevel(java.lang.Integer descendantMaxLevel) {
        this.descendantMaxLevel=descendantMaxLevel;
    }

    public it.anggen.model.SecurityType getSecurityType() {
        return this.securityType;
    }

    public void setSecurityType(it.anggen.model.SecurityType securityType) {
        this.securityType=securityType;
    }

    public List<Field> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList=fieldList;
    }

    public it.anggen.model.entity.EntityGroup getEntityGroup() {
        return this.entityGroup;
    }

    public void setEntityGroup(it.anggen.model.entity.EntityGroup entityGroup) {
        this.entityGroup=entityGroup;
    }

    public List<RestrictionEntity> getRestrictionEntityList() {
        return this.restrictionEntityList;
    }

    public void setRestrictionEntityList(List<RestrictionEntity> restrictionEntityList) {
        this.restrictionEntityList=restrictionEntityList;
    }

    public List<Tab> getTabList() {
        return this.tabList;
    }

    public void setTabList(List<Tab> tabList) {
        this.tabList=tabList;
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

}
