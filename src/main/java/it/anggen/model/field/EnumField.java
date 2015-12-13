
package it.anggen.model.field;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.anggen.model.entity.EnumEntity;
import it.anggen.model.field.Annotation;
import it.anggen.model.field.EnumValue;
import it.anggen.utils.EntityAttribute;
import it.anggen.utils.annotation.MaxDescendantLevel;

import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema = "meta", name = "enum_field")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class EnumField  extends EntityAttribute{

    public final static java.lang.Long staticEntityId = 309L;
    @javax.persistence.Column(name = "enum_field_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @it.anggen.utils.annotation.DescriptionField
    @it.anggen.utils.annotation.Priority(1)
    private java.lang.Long enumFieldId;
    @javax.persistence.Column(name = "priority")
    @it.anggen.utils.annotation.Priority(2)
    private Integer priority;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    @it.anggen.utils.annotation.Priority(2)
    private String name;
    
    
    @ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "enum_entity_id_enum_entity")
    @it.anggen.utils.annotation.Priority(4)
    private EnumEntity enumEntity;
    
    
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "entity_id_entity")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.entity.Entity entity;
    @OneToMany(fetch = javax.persistence.FetchType.EAGER)
    @Type(type = "it.anggen.model.field.Annotation")
    @javax.persistence.JoinColumn(name = "enum_field_id_enum_field")
    @it.anggen.utils.annotation.Priority(4)
    private List<Annotation> annotationList;
    @javax.persistence.ManyToOne(fetch = javax.persistence.FetchType.EAGER)
    @javax.persistence.JoinColumn(name = "tab_id_tab")
    @it.anggen.utils.annotation.Priority(4)
    private it.anggen.model.entity.Tab tab;

    public java.lang.Long getEnumFieldId() {
        return this.enumFieldId;
    }

    public void setEnumFieldId(java.lang.Long enumFieldId) {
        this.enumFieldId=enumFieldId;
    }

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

    public it.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

    public List<Annotation> getAnnotationList() {
        return this.annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList=annotationList;
    }

    public it.anggen.model.entity.Tab getTab() {
        return this.tab;
    }

    public void setTab(it.anggen.model.entity.Tab tab) {
        this.tab=tab;
    }

	/**
	 * @return the enumEntity
	 */
	public EnumEntity getEnumEntity() {
		return enumEntity;
	}

	/**
	 * @param enumEntity the enumEntity to set
	 */
	public void setEnumEntity(EnumEntity enumEntity) {
		this.enumEntity = enumEntity;
	}

}
