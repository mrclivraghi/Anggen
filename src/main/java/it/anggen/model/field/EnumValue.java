
package it.anggen.model.field;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import it.anggen.utils.annotation.MaxDescendantLevel;

@Entity
@Table(schema = "meta", name = "enum_value")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class EnumValue {

    public final static java.lang.Long staticEntityId = 19L;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    private String name;
    @javax.persistence.Column(name = "value")
    private Integer value;
    @javax.persistence.Column(name = "enum_value_id")
    @Id
    @GeneratedValue
    @it.anggen.utils.annotation.DescriptionField
    private java.lang.Long enumValueId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enum_entity_id_enum_entity")
    private it.anggen.model.entity.EnumEntity enumEntity;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value=value;
    }

    public java.lang.Long getEnumValueId() {
        return this.enumValueId;
    }

    public void setEnumValueId(java.lang.Long enumValueId) {
        this.enumValueId=enumValueId;
    }

    public it.anggen.model.entity.EnumEntity getEnumEntity() {
        return this.enumEntity;
    }

    public void setEnumEntity(it.anggen.model.entity.EnumEntity enumEntity) {
        this.enumEntity=enumEntity;
    }

}
