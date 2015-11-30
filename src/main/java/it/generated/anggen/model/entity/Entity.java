
package it.generated.anggen.model.entity;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.generated.anggen.model.entity.Tab;
import it.generated.anggen.model.field.EnumField;
import it.generated.anggen.model.field.Field;
import it.generated.anggen.model.relationship.Relationship;
import it.generated.anggen.model.security.RestrictionEntity;
import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema = "mustle", name = "entity")
public class Entity {

    public final static java.lang.Long staticEntityId = 5289L;
    @javax.persistence.Column(name = "entity_id")
    @it.polimi.utils.annotation.DescriptionField
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private java.lang.Long entityId;
    @javax.persistence.Column(name = "name")
    @it.polimi.utils.annotation.DescriptionField
    private String name;
    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_group_id_entity_group")
    private it.generated.anggen.model.entity.EntityGroup entityGroup;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.security.RestrictionEntity")
    @javax.persistence.JoinColumn(name = "entity_id_restriction_entity")
    private List<RestrictionEntity> restrictionEntityList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.entity.Tab")
    @javax.persistence.JoinColumn(name = "entity_id_tab")
    private List<Tab> tabList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.EnumField")
    @javax.persistence.JoinColumn(name = "entity_id_enum_field")
    private List<EnumField> enumFieldList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.relationship.Relationship")
    @javax.persistence.JoinColumn(name = "entity_id_relationship")
    private List<Relationship> relationshipList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.Field")
    @javax.persistence.JoinColumn(name = "entity_id_field")
    private List<Field> fieldList;

    public java.lang.Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(java.lang.Long entityId) {
        this.entityId=entityId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public it.generated.anggen.model.entity.EntityGroup getEntityGroup() {
        return this.entityGroup;
    }

    public void setEntityGroup(it.generated.anggen.model.entity.EntityGroup entityGroup) {
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

    public List<Field> getFieldList() {
        return this.fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList=fieldList;
    }

}
