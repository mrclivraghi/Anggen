
package it.generated.anggen.model.field;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.generated.anggen.model.FieldType;
import it.generated.anggen.model.field.Annotation;
import it.generated.anggen.model.security.RestrictionField;
import it.polimi.utils.annotation.DescriptionField;
import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema = "mustle", name = "field")
public class Field {

    public final static java.lang.Long staticEntityId = 5288L;
    @javax.persistence.Column(name = "field_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long fieldId;
    @javax.persistence.Column(name = "name")
    private String name;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    private it.generated.anggen.model.entity.Tab tab;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.security.RestrictionField")
    @javax.persistence.JoinColumn(name = "field_id_restriction_field")
    private List<RestrictionField> restrictionFieldList;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.generated.anggen.model.field.Annotation")
    @javax.persistence.JoinColumn(name = "field_id_annotation")
    private List<Annotation> annotationList;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private it.generated.anggen.model.entity.Entity entity;
    @javax.persistence.Column(name = "field_type")
    private FieldType fieldType;

    public java.lang.Long getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(java.lang.Long fieldId) {
        this.fieldId=fieldId;
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

    public List<RestrictionField> getRestrictionFieldList() {
        return this.restrictionFieldList;
    }

    public void setRestrictionFieldList(List<RestrictionField> restrictionFieldList) {
        this.restrictionFieldList=restrictionFieldList;
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

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType=fieldType;
    }

}
