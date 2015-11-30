
package it.generated.anggen.model.field;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.generated.anggen.model.field.Annotation;
import it.generated.anggen.model.field.EnumValue;
import it.polimi.utils.annotation.DescriptionField;
import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema = "mustle", name = "enum_field")
public class EnumField {

    public final static java.lang.Long staticEntityId = 5280L;
    @javax.persistence.Column(name = "enum_field_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long enumFieldId;
    @javax.persistence.Column(name = "name")
    private String name;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    private it.generated.anggen.model.entity.Tab tab;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.Annotation")
    @javax.persistence.JoinColumn(name = "enum_field_id_annotation")
    private List<Annotation> annotationList;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private it.generated.anggen.model.entity.Entity entity;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.EnumValue")
    @javax.persistence.JoinColumn(name = "enum_field_id_enum_value")
    private List<EnumValue> enumValueList;

    public java.lang.Long getEnumFieldId() {
        return this.enumFieldId;
    }

    public void setEnumFieldId(java.lang.Long enumFieldId) {
        this.enumFieldId=enumFieldId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public it.generated.anggen.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.generated.anggen.model.entity.Tab tab) {
        this.tab=tab;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public it.generated.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.generated.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

    public List<EnumValue> getEnumValueList() {
        return this.enumValueList;
    }

    public void setEnumValueList(List<EnumValue> enumValueList) {
        this.enumValueList=enumValueList;
    }

}
