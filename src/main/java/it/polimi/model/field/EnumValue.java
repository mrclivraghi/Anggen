package it.polimi.model.field;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(schema="mustle",name="enum_value")
public class EnumValue{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="enum_value_id")
	private Long enumValueId;
	
	private Integer value;
	
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="enum_field_id_enum_field")
	private EnumField enumField;
	
	public EnumValue() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the enumValueId
	 */
	public Long getEnumValueId() {
		return enumValueId;
	}


	/**
	 * @param enumValueId the enumValueId to set
	 */
	public void setEnumValueId(Long enumValueId) {
		this.enumValueId = enumValueId;
	}


	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		this.value = value;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the enumField
	 */
	public EnumField getEnumField() {
		return enumField;
	}


	/**
	 * @param enumField the enumField to set
	 */
	public void setEnumField(EnumField enumField) {
		this.enumField = enumField;
	}

}