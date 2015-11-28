package it.polimi.model.domain;


import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "role", schema = "sso")
public class Role{

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "role_id", 
		unique = true, nullable = false)
	private Integer roleId;
	
	private String role;
	
	@ManyToMany(fetch=FetchType.EAGER,mappedBy="roleList")
	private List<User> userList;


	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.RestrictionEntity")
	@JoinColumn(name="role_id_role")
	private List<RestrictionEntity> restrictionEntityList;
	
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.RestrictionField")
	@JoinColumn(name="role_id_role")
	private List<RestrictionField> restrictionFieldList;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.RestrictionEntityGroup")
	@JoinColumn(name="role_id_role")
	private List<RestrictionEntityGroup> restrictionEntityGroupList;
	
	public Role() {
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the userList
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * @return the restrictionList
	 */
	public List<RestrictionEntity> getRestrictionEntityList() {
		return restrictionEntityList;
	}

	/**
	 * @param restrictionList the restrictionList to set
	 */
	public void setRestrictionEntityList(List<RestrictionEntity> restrictionEntityList) {
		this.restrictionEntityList = restrictionEntityList;
	}

	/**
	 * @return the validationRestrictionList
	 */
	public List<RestrictionField> getRestrictionFieldList() {
		return restrictionFieldList;
	}

	/**
	 * @param validationRestrictionList the validationRestrictionList to set
	 */
	public void setRestrictionFieldList(
			List<RestrictionField> restrictionFieldList) {
		this.restrictionFieldList = restrictionFieldList;
	}

	/**
	 * @return the restrictionEntityGroupList
	 */
	public List<RestrictionEntityGroup> getRestrictionEntityGroupList() {
		return restrictionEntityGroupList;
	}

	/**
	 * @param restrictionEntityGroupList the restrictionEntityGroupList to set
	 */
	public void setRestrictionEntityGroupList(
			List<RestrictionEntityGroup> restrictionEntityGroupList) {
		this.restrictionEntityGroupList = restrictionEntityGroupList;
	}



}