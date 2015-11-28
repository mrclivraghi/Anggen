package it.polimi.model.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema="mustle", name="entity_group")
public class EntityGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_entity_group")
	private Long entityGroupId;
	
	private String name;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.Entity")
	@JoinColumn(name="entity_group_id_entity_group")
	private List<Entity> entityList;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.RestrictionEntityGroup")
	@JoinColumn(name="entity_group_id_entity_group")
	private List<RestrictionEntityGroup> restrictionEntityGroupList;
	
	
	public EntityGroup() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the entityGroupId
	 */
	public Long getEntityGroupId() {
		return entityGroupId;
	}


	/**
	 * @param entityGroupId the entityGroupId to set
	 */
	public void setEntityGroupId(Long entityGroupId) {
		this.entityGroupId = entityGroupId;
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
	 * @return the entityList
	 */
	public List<Entity> getEntityList() {
		return entityList;
	}


	/**
	 * @param entityList the entityList to set
	 */
	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
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
