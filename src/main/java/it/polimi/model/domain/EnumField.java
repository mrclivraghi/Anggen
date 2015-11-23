package it.polimi.model.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(schema="mustle",name="enum_field")
public class EnumField extends EntityAttribute {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_enum_field")
	private Long enumFieldId;
	
	private String name;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.EnumValue")
	@JoinColumn(name="enum_field_id_enum_field")
	private List<EnumValue> enumValueList;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entity_id_entity")
	private Entity entity;
	
	@OneToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.domain.Annotation")
	@JoinColumn(name="enum_field_id_enum_field")
	private List<Annotation> annotationList;
	
	@ManyToOne
	@JoinColumn(name="tab_id_tab")
	private Tab tab;
	
	public EnumField() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the enumFieldId
	 */
	public Long getEnumFieldId() {
		return enumFieldId;
	}


	/**
	 * @param enumFieldId the enumFieldId to set
	 */
	public void setEnumFieldId(Long enumFieldId) {
		this.enumFieldId = enumFieldId;
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
	 * @return the enumValueList
	 */
	public List<EnumValue> getEnumValueList() {
		return enumValueList;
	}


	/**
	 * @param enumValueList the enumValueList to set
	 */
	public void setEnumValueList(List<EnumValue> enumValueList) {
		this.enumValueList = enumValueList;
	}


	/**
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}


	/**
	 * @param entity the entity to set
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}


	/**
	 * @return the annotationList
	 */
	public List<Annotation> getAnnotationList() {
		return annotationList;
	}


	/**
	 * @param annotationList the annotationList to set
	 */
	public void setAnnotationList(List<Annotation> annotationList) {
		this.annotationList = annotationList;
	}


	/**
	 * @return the tab
	 */
	public Tab getTab() {
		return tab;
	}


	/**
	 * @param tab the tab to set
	 */
	public void setTab(Tab tab) {
		this.tab = tab;
	}

}
