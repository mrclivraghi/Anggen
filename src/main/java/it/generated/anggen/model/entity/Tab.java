
package it.generated.anggen.model.entity;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.generated.anggen.model.field.EnumField;
import it.generated.anggen.model.field.Field;
import it.generated.anggen.model.relationship.Relationship;
import it.polimi.utils.annotation.DescriptionField;
import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema = "mustle", name = "tab")
public class Tab {

    public final static java.lang.Long staticEntityId = 4710L;
    @javax.persistence.Column(name = "tab_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long tabId;
    @javax.persistence.Column(name = "name")
    private String name;
    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private it.generated.anggen.model.entity.Entity entity;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.Field")
    @javax.persistence.JoinColumn(name = "tab_id_field")
    private List<Field> fieldList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.relationship.Relationship")
    @javax.persistence.JoinColumn(name = "tab_id_relationship")
    private List<Relationship> relationshipList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.EnumField")
    @javax.persistence.JoinColumn(name = "tab_id_enum_field")
    private List<EnumField> enumFieldList;

    public java.lang.Long getTabId() {
        return this.tabId;
    }

    public void setTabId(java.lang.Long tabId) {
        this.tabId=tabId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public it.generated.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.generated.anggen.model.entity.Entity entity) {
        this.entity=entity;
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

}
