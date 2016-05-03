
package it.anggen.model.field;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import it.anggen.model.field.Annotation;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.annotation.MaxDescendantLevel;
import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema = "meta", name = "enum_field")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class EnumField
    extends EntityAttribute
{

    public final static java.lang.Long staticEntityId = 16L;
    @javax.persistence.Column(name = "priority")
    private Integer priority;
    @javax.persistence.Column(name = "enum_field_id")
    @Id
    @GeneratedValue
    @it.anggen.utils.annotation.DescriptionField
    private java.lang.Long enumFieldId;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    private String name;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    private it.anggen.model.entity.Entity entity;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "enum_entity_id_enum_entity")
    private it.anggen.model.entity.EnumEntity enumEntity;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.LAZY)
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    private it.anggen.model.entity.Tab tab;
    @OneToMany(fetch = javax.persistence.FetchType.LAZY)
    @Type(type = "it.anggen.model.field.Annotation")
    @javax.persistence.JoinColumn(name = "enum_field_id_enum_field")
    private List<Annotation> annotationList;

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority=priority;
    }

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

    public it.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

    public it.anggen.model.entity.EnumEntity getEnumEntity() {
        return this.enumEntity;
    }

    public void setEnumEntity(it.anggen.model.entity.EnumEntity enumEntity) {
        this.enumEntity=enumEntity;
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

}
