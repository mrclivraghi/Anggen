package it.polimi.model.domain;

import it.polimi.utils.annotation.Tab;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(schema="mustle", name="field")
public class Field extends EntityAttribute{


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_field")
	private Long fieldId;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="entity_id_entity")
	private Entity entity;
	
	@Column(name ="field_type")
	private FieldType fieldType;
	
	@Column(name="primary_key")
	private Boolean primaryKey;
	
	@Column(name="description_field")
	private Boolean descriptionField;
	
	@Column(name="excel_export")
	private Boolean excelExport;
	
	private Boolean filter;
	
	private Boolean list;
	
	@Column(name="ignore_search")
	private Boolean ignoreSearch;
	
	@Column(name="ignore_table_list")
	private Boolean ignoreTableList;
	
	@Column(name="ignore_update")
	private Boolean ignoreUpdate;
	
	@Column(name="between_filter")
	private Boolean betweenFilter;
	
	
	/**
	 * @return the fieldId
	 */
	public Long getFieldId() {
		return fieldId;
	}
	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
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
	 * @return the fieldType
	 */
	public FieldType getFieldType() {
		return fieldType;
	}
	/**
	 * @param fieldType the fieldType to set
	 */
	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
	/**
	 * @return the primaryKey
	 */
	public Boolean getPrimaryKey() {
		return primaryKey;
	}
	/**
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(Boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	/**
	 * @return the list
	 */
	public Boolean getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(Boolean list) {
		this.list = list;
	}
	
}
