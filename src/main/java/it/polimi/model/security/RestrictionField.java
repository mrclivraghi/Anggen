package it.polimi.model.security;

import it.polimi.model.field.Field;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(schema="sso", name="restriction_field")
public class RestrictionField {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="restriction_field_id")
	private Long restrictionFieldId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="field_id_field")
	private Field field;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id_role")
	private Role role;

	
	
	
	/**
	 * @return the validationRestrictionId
	 */
	public Long getRestrictionFieldId() {
		return restrictionFieldId;
	}

	/**
	 * @param validationRestrictionId the validationRestrictionId to set
	 */
	public void setRestrictionFieldId(Long restrictionFieldId) {
		this.restrictionFieldId = restrictionFieldId;
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
