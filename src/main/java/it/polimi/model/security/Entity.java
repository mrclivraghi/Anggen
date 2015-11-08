package it.polimi.model.security;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "entity", schema = "sso")
public class Entity {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "entity_id", 
		unique = true, nullable = false)
	
	private Long entityId;
	@Column(name="entity_name")
	private String entityName;
	
	@ManyToMany(mappedBy = "entityList",fetch=FetchType.EAGER)
	private List<Role> roleList;
	
	/**
	 * @return the entityId
	 */
	public Long getEntityId() {
		return entityId;
	}
	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}
	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	/**
	 * @return the roleList
	 */
	public List<Role> getRoleList() {
		return roleList;
	}
	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
