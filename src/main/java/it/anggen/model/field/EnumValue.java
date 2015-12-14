
package it.anggen.model.field;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.anggen.model.entity.EnumEntity;
import it.anggen.utils.annotation.MaxDescendantLevel;

@Entity
@Table(schema = "meta", name = "enum_value")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(100)
public class EnumValue {

   
	public final static java.lang.Long staticEntityId = 313L;
    @javax.persistence.Column(name = "name")
    @it.anggen.utils.annotation.DescriptionField
    @it.anggen.utils.annotation.Priority(2)
    private String name;
    @javax.persistence.Column(name = "enum_value_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @it.anggen.utils.annotation.DescriptionField
    @it.anggen.utils.annotation.Priority(1)
    private java.lang.Long enumValueId;
    @javax.persistence.Column(name = "value")
    @it.anggen.utils.annotation.Priority(2)
    private Integer value;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enum_entity_id_enum_entity")
    @it.anggen.utils.annotation.Priority(4)
    private EnumEntity enumEntity;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name=name;
    }

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
	
	 @Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return getEnumValueId().equals(((EnumValue)obj).getEnumValueId());
		}


}
