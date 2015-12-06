
package it.polimi.model.entity;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.polimi.model.SecurityType;
import it.polimi.model.entity.Tab;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;
import it.polimi.model.security.RestrictionEntity;

import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema = "meta", name = "entity")
public class Entity {

    public final static java.lang.Long staticEntityId = 11L;
    @javax.persistence.Column(name = "name")
    @it.polimi.utils.annotation.DescriptionField
    private String name;
    @javax.persistence.Column(name = "entity_id")
    @it.polimi.utils.annotation.DescriptionField
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private java.lang.Long entityId;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.relationship.Relationship")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private List<Relationship> relationshipList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.EnumField")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private List<EnumField> enumFieldList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.entity.Tab")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private List<Tab> tabList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.security.RestrictionEntity")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private List<RestrictionEntity> restrictionEntityList;
    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    private it.polimi.model.entity.EntityGroup entityGroup;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.Field")
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private List<Field> fieldList;
    @javax.persistence.Column(name = "security_type")
    private SecurityType securityType;

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

    public SecurityType getSecurityType() {
        return this.securityType;
    }

    public void setSecurityType(SecurityType securityType) {
        this.securityType=securityType;
    }

}
