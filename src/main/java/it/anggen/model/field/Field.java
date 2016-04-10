
package it.anggen.model.field;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.anggen.model.FieldType;
import it.anggen.model.field.Annotation;
import it.anggen.model.security.RestrictionField;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.annotation.MaxDescendantLevel;
import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema = "meta", name = "field")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class Field extends EntityAttribute{

    public final static java.lang.Long staticEntityId = 17L;
    @javax.persistence.Column(name = "priority")
    @it.anggen.utils.annotation.Priority(2)
    private Integer priority;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    @it.anggen.utils.annotation.Priority(2)
    private String name;
    @javax.persistence.Column(name = "field_id")
    @it.anggen.utils.annotation.Priority(1)
    @it.anggen.utils.annotation.DescriptionField
    @Id
    @GeneratedValue
    private java.lang.Long fieldId;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.anggen.model.security.RestrictionField")
    @javax.persistence.JoinColumn(name = "field_id_field")
    @it.anggen.utils.annotation.Priority(4)
    private List<RestrictionField> restrictionFieldList;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.entity.Entity entity;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.entity.Tab tab;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.anggen.model.field.Annotation")
    @javax.persistence.JoinColumn(name = "field_id_field")
    @it.anggen.utils.annotation.Priority(4)
    private List<Annotation> annotationList;
    @javax.persistence.Column(name = "field_type")
    @it.anggen.utils.annotation.Priority(3)
    private FieldType fieldType;

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority=priority;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public java.lang.Long getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(java.lang.Long fieldId) {
        this.fieldId=fieldId;
    }

    public List<RestrictionField> getRestrictionFieldList() {
        return this.restrictionFieldList;
    }

    public void setRestrictionFieldList(List<RestrictionField> restrictionFieldList) {
        this.restrictionFieldList=restrictionFieldList;
    }

    public it.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

    public it.anggen.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.anggen.model.entity.Tab tab) {
        this.tab=tab;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType=fieldType;
    }

}
