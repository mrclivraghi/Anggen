
package it.generated.anggen.model.field;

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
@Table(schema = "mustle", name = "enum_value")
public class EnumValue {

    public final static java.lang.Long staticEntityId = 5285L;
    @javax.persistence.Column(name = "enum_value_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DescriptionField
    private java.lang.Long enumValueId;
    @javax.persistence.Column(name = "value")
    private Integer value;
    @javax.persistence.Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enum_field_id_enum_field")
    private it.generated.anggen.model.field.EnumField enumField;

    public java.lang.Long getEnumValueId() {
        return this.enumValueId;
    }

    public void setEnumValueId(java.lang.Long enumValueId) {
        this.enumValueId=enumValueId;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value=value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public it.generated.anggen.model.field.EnumField getEnumField() {
        return this.enumField;
    }

    public void setEnumField(it.generated.anggen.model.field.EnumField enumField) {
        this.enumField=enumField;
    }

}
