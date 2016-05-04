
package it.anggen.model.entity;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.anggen.model.entity.Tab;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;
import it.anggen.model.security.RestrictionEntity;
import it.anggen.utils.annotation.MaxDescendantLevel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@javax.persistence.Entity
@Table(schema = "meta", name = "entity")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class Entity {

    public final static java.lang.Long staticEntityId = 9L;
    @javax.persistence.Column(name = "generate_front_end")
    private java.lang.Boolean generateFrontEnd;
    @javax.persistence.Column(name = "disable_view_generation")
    private java.lang.Boolean disableViewGeneration;
    @javax.persistence.Column(name = "descendant_max_level")
    private Integer descendantMaxLevel;
    @javax.persistence.Column(name = "enable_restriction_data")
    private java.lang.Boolean enableRestrictionData;
    @javax.persistence.Column(name = "ignore_menu")
    private java.lang.Boolean ignoreMenu;
    @javax.persistence.Column(name = "entity_id")
    @it.anggen.utils.annotation.DescriptionField
    @Id
    @GeneratedValue
    private java.lang.Long entityId;
    @javax.persistence.Column(name = "add_date")
    @CreationTimestamp
    private java.util.Date addDate;
    @javax.persistence.Column(name = "cache")
    private java.lang.Boolean cache;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    private String name;
    @javax.persistence.Column(name = "mod_date")
    @UpdateTimestamp
    private java.util.Date modDate;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.security.RestrictionEntity")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private List<RestrictionEntity> restrictionEntityList;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.field.Field")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private List<Field> fieldList;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.field.EnumField")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private List<EnumField> enumFieldList;
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    private it.anggen.model.entity.EntityGroup entityGroup;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.entity.Tab")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private List<Tab> tabList;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.relationship.Relationship")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private List<Relationship> relationshipList;
    @javax.persistence.Column(name = "security_type")
    private it.anggen.model.SecurityType securityType;

    public java.lang.Boolean getGenerateFrontEnd() {
        return this.generateFrontEnd;
    }

    public void setGenerateFrontEnd(java.lang.Boolean generateFrontEnd) {
        this.generateFrontEnd=generateFrontEnd;
    }

    public java.lang.Boolean getDisableViewGeneration() {
        return this.disableViewGeneration;
    }

    public void setDisableViewGeneration(java.lang.Boolean disableViewGeneration) {
        this.disableViewGeneration=disableViewGeneration;
    }

    public Integer getDescendantMaxLevel() {
        return this.descendantMaxLevel;
    }

    public void setDescendantMaxLevel(Integer descendantMaxLevel) {
        this.descendantMaxLevel=descendantMaxLevel;
    }

    public java.lang.Boolean getEnableRestrictionData() {
        return this.enableRestrictionData;
    }

    public void setEnableRestrictionData(java.lang.Boolean enableRestrictionData) {
        this.enableRestrictionData=enableRestrictionData;
    }

    public java.lang.Boolean getIgnoreMenu() {
        return this.ignoreMenu;
    }

    public void setIgnoreMenu(java.lang.Boolean ignoreMenu) {
        this.ignoreMenu=ignoreMenu;
    }

    public java.lang.Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(java.lang.Long entityId) {
        this.entityId=entityId;
    }

    public java.util.Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(java.util.Date addDate) {
        this.addDate=addDate;
    }

    public java.lang.Boolean getCache() {
        return this.cache;
    }

    public void setCache(java.lang.Boolean cache) {
        this.cache=cache;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public java.util.Date getModDate() {
        return this.modDate;
    }

    public void setModDate(java.util.Date modDate) {
        this.modDate=modDate;
    }

    public List<RestrictionEntity> getRestrictionEntityList() {
        return this.restrictionEntityList;
    }

    public void setRestrictionEntityList(List<RestrictionEntity> restrictionEntityList) {
        this.restrictionEntityList=restrictionEntityList;
    }

    public List<Field> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList=fieldList;
    }

    public List<EnumField> getEnumFieldList() {
        return this.enumFieldList;
    }

    public void setEnumFieldList(List<EnumField> enumFieldList) {
        this.enumFieldList=enumFieldList;
    }

    public it.anggen.model.entity.EntityGroup getEntityGroup() {
        return this.entityGroup;
    }

    public void setEntityGroup(it.anggen.model.entity.EntityGroup entityGroup) {
        this.entityGroup=entityGroup;
    }

    public List<Tab> getTabList() {
        return this.tabList;
    }

    public void setTabList(List<Tab> tabList) {
        this.tabList=tabList;
    }

    public List<Relationship> getRelationshipList() {
        return this.relationshipList;
    }

    public void setRelationshipList(List<Relationship> relationshipList) {
        this.relationshipList=relationshipList;
    }

    public it.anggen.model.SecurityType getSecurityType() {
        return this.securityType;
    }

    public void setSecurityType(it.anggen.model.SecurityType securityType) {
        this.securityType=securityType;
    }

}
