
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
import org.hibernate.annotations.Type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@javax.persistence.Entity
@Table(schema = "meta", name = "entity")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
//@ApiModel
public class Entity {

    public final static java.lang.Long staticEntityId = 9L;
    @javax.persistence.Column(name = "generate_front_end")
    @it.anggen.utils.annotation.Priority(2)
    @ApiModelProperty(value = "var boolean gen frontend wow", allowableValues = "true,false")
    private java.lang.Boolean generateFrontEnd;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    @it.anggen.utils.annotation.Priority(2)
    private String name;
    @javax.persistence.Column(name = "entity_id")
    @it.anggen.utils.annotation.DescriptionField
    @Id
    //@GeneratedValue
    @it.anggen.utils.annotation.Priority(1)
    private java.lang.Long entityId;
    @javax.persistence.Column(name = "cache")
    @it.anggen.utils.annotation.Priority(2)
    private java.lang.Boolean cache;
    @javax.persistence.Column(name = "disable_view_generation")
    @it.anggen.utils.annotation.Priority(2)
    private java.lang.Boolean disableViewGeneration;
    @javax.persistence.Column(name = "descendant_max_level")
    @it.anggen.utils.annotation.Priority(2)
    private Integer descendantMaxLevel;
    @javax.persistence.Column(name = "enable_restriction_data")
    @it.anggen.utils.annotation.Priority(2)
    private java.lang.Boolean enableRestrictionData;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.anggen.model.security.RestrictionEntity")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    @it.anggen.utils.annotation.Priority(4)
    private List<RestrictionEntity> restrictionEntityList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.anggen.model.field.Field")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    @it.anggen.utils.annotation.Priority(4)
    private List<Field> fieldList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.anggen.model.field.EnumField")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    @it.anggen.utils.annotation.Priority(4)
    private List<EnumField> enumFieldList;
    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.entity.EntityGroup entityGroup;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.anggen.model.entity.Tab")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    @it.anggen.utils.annotation.Priority(4)
    private List<Tab> tabList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.anggen.model.relationship.Relationship")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    @it.anggen.utils.annotation.Priority(4)
    private List<Relationship> relationshipList;
    @javax.persistence.Column(name = "security_type")
    @it.anggen.utils.annotation.Priority(3)
    private it.anggen.model.SecurityType securityType;

    public java.lang.Boolean getGenerateFrontEnd() {
        return this.generateFrontEnd;
    }

    public void setGenerateFrontEnd(java.lang.Boolean generateFrontEnd) {
        this.generateFrontEnd=generateFrontEnd;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public java.lang.Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(java.lang.Long entityId) {
        this.entityId=entityId;
    }

    public java.lang.Boolean getCache() {
        return this.cache;
    }

    public void setCache(java.lang.Boolean cache) {
        this.cache=cache;
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
