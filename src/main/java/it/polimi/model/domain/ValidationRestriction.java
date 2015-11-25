package it.polimi.model.domain;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(schema="sso", name="validation_restriction")
public class ValidationRestriction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_validation_restriction")
	private Long validationRestrictionId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="field_id_field")
	private Field field;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id_role")
	private Role role;

	
	
	
	/**
	 * @return the validationRestrictionId
	 */
	public Long getValidationRestrictionId() {
		return validationRestrictionId;
	}

	/**
	 * @param validationRestrictionId the validationRestrictionId to set
	 */
	public void setValidationRestrictionId(Long validationRestrictionId) {
		this.validationRestrictionId = validationRestrictionId;
	}

	/**
	 * @return the field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(Field field) {
		this.field = field;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

}
