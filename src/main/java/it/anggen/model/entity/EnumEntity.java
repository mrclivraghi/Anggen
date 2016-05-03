
package it.anggen.model.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.anggen.model.field.EnumField;
import it.anggen.model.field.EnumValue;
import it.anggen.utils.annotation.MaxDescendantLevel;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "meta", name = "enum_entity")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(1)
public class EnumEntity {

    public final static java.lang.Long staticEntityId = 6L;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    private String name;
    @javax.persistence.Column(name = "enum_entity_id")
    @Id
    @GeneratedValue
    @it.anggen.utils.annotation.DescriptionField
    private java.lang.Long enumEntityId;
    @ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "project_id_project")
    private it.anggen.model.entity.Project project;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.field.EnumValue")
    @javax.persistence.JoinColumn(name = "enum_entity_id_enum_entity")
    private List<EnumValue> enumValueList;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.field.EnumField")
    @javax.persistence.JoinColumn(name = "enum_entity_id_enum_entity")
    private List<EnumField> enumFieldList;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public java.lang.Long getEnumEntityId() {
        return this.enumEntityId;
    }

    public void setEnumEntityId(java.lang.Long enumEntityId) {
        this.enumEntityId=enumEntityId;
    }

    public it.anggen.model.entity.Project getProject() {
        return this.project;
    }

    public void setProject(it.anggen.model.entity.Project project) {
        this.project=project;
    }

    public List<EnumValue> getEnumValueList() {
        return this.enumValueList;
    }

    public void setEnumValueList(List<EnumValue> enumValueList) {
        this.enumValueList=enumValueList;
    }

    public List<EnumField> getEnumFieldList() {
        return this.enumFieldList;
    }

    public void setEnumFieldList(List<EnumField> enumFieldList) {
        this.enumFieldList=enumFieldList;
    }

}
