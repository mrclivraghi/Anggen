
package it.polimi.model.field;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.polimi.utils.annotation.DescriptionField;

@Entity
@Table(schema = "meta", name = "enum_value")
public class EnumValue {

    public final static java.lang.Long staticEntityId = 4L;
    @javax.persistence.Column(name = "value")
    private Integer value;
    @javax.persistence.Column(name = "enum_value_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long enumValueId;
    @javax.persistence.Column(name = "name")
    @DescriptionField
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enum_field_id_enum_field")
    private it.polimi.model.field.EnumField enumField;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public it.polimi.model.field.EnumField getEnumField() {
        return this.enumField;
    }

    public void setEnumField(it.polimi.model.field.EnumField enumField) {
        this.enumField=enumField;
    }

}
