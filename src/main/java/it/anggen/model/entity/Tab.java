
package it.anggen.model.entity;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.Field;
import it.anggen.model.relationship.Relationship;
import it.anggen.utils.annotation.MaxDescendantLevel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

@javax.persistence.Entity
@Table(schema = "meta", name = "tab")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class Tab {

    public final static java.lang.Long staticEntityId = 8L;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    private String name;
    @javax.persistence.Column(name = "mod_date")
    @UpdateTimestamp
    private java.util.Date modDate;
    @javax.persistence.Column(name = "tab_id")
    @Id
    @GeneratedValue
    @it.anggen.utils.annotation.DescriptionField
    private java.lang.Long tabId;
    @javax.persistence.Column(name = "add_date")
    @CreationTimestamp
    private java.util.Date addDate;
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private it.anggen.model.entity.Entity entity;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.field.Field")
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    private List<Field> fieldList;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.field.EnumField")
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    private List<EnumField> enumFieldList;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.relationship.Relationship")
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    private List<Relationship> relationshipList;

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

    public java.lang.Long getTabId() {
        return this.tabId;
    }

    public void setTabId(java.lang.Long tabId) {
        this.tabId=tabId;
    }

    public java.util.Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(java.util.Date addDate) {
        this.addDate=addDate;
    }

    public it.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.anggen.model.entity.Entity entity) {
        this.entity=entity;
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

    public List<Relationship> getRelationshipList() {
        return this.relationshipList;
    }

    public void setRelationshipList(List<Relationship> relationshipList) {
        this.relationshipList=relationshipList;
    }

}
