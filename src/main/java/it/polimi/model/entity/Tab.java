package it.polimi.model.entity;

import static javax.persistence.GenerationType.IDENTITY;
import it.polimi.model.field.EnumField;
import it.polimi.model.field.Field;
import it.polimi.model.relationship.Relationship;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema="mustle",name="tab")
public class Tab {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tab_id") 
	
	private Long tabId;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name="entity_id_entity")
	private Entity entity;
	
	@OneToMany
	@Type(type="it.polimi.model.Field")
	@JoinColumn(name="tab_id_tab")
	private List<Field> fieldList;
	

	@OneToMany
	@Type(type="it.polimi.model.Relationship")
	@JoinColumn(name="tab_id_tab")
	private List<Relationship> relationshipList;
	
	

	@OneToMany
	@Type(type="it.polimi.model.Enumfield")
	@JoinColumn(name="tab_id_tab")
	private List<EnumField> enumFieldList;
	
	public Tab() {
		// TODO Auto-generated constructor stub
	}

	public Long getTabId() {
		return tabId;
	}

	public void setTabId(Long tabId) {
		this.tabId = tabId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public List<Field> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Field> fieldList) {
		this.fieldList = fieldList;
	}

	public List<Relationship> getRelationshipList() {
		return relationshipList;
	}

	public void setRelationshipList(List<Relationship> relationshipList) {
		this.relationshipList = relationshipList;
	}

	public List<EnumField> getEnumFieldList() {
		return enumFieldList;
	}

	public void setEnumFieldList(List<EnumField> enumFieldList) {
		this.enumFieldList = enumFieldList;
	}

}
