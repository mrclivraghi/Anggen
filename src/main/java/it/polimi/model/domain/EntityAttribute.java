package it.polimi.model.domain;

import javax.persistence.Column;

public class EntityAttribute {

	private String name;
	
	private Boolean descriptionField;
	
	private Boolean excelExport;
	
	private Boolean filter;
	
	private Boolean ignoreSearch;
	
	private Boolean ignoreTableList;
	
	private Boolean ignoreUpdate;
	
	private Boolean betweenFilter;
	
	
	/**
	 * @return the descriptionField
	 */
	public Boolean getDescriptionField() {
		if (descriptionField==null) return false;
		return descriptionField;
	}
	/**
	 * @param descriptionField the descriptionField to set
	 */
	public void setDescriptionField(Boolean descriptionField) {
		this.descriptionField = descriptionField;
	}
	/**
	 * @return the excelExport
	 */
	public Boolean getExcelExport() {
		if (excelExport==null) return false;
		return excelExport;
	}
	/**
	 * @param excelExport the excelExport to set
	 */
	public void setExcelExport(Boolean excelExport) {
		this.excelExport = excelExport;
	}
	/**
	 * @return the filter
	 */
	public Boolean getFilter() {
		if (filter==null) return false;
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(Boolean filter) {
		this.filter = filter;
	}
	/**
	 * @return the ignoreSearch
	 */
	public Boolean getIgnoreSearch() {
		if (ignoreSearch==null) return false;
		return ignoreSearch;
	}
	/**
	 * @param ignoreSearch the ignoreSearch to set
	 */
	public void setIgnoreSearch(Boolean ignoreSearch) {
		this.ignoreSearch = ignoreSearch;
	}
	/**
	 * @return the ignoreTableList
	 */
	public Boolean getIgnoreTableList() {
		if (ignoreTableList==null) return false;
		return ignoreTableList;
	}
	/**
	 * @param ignoreTableList the ignoreTableList to set
	 */
	public void setIgnoreTableList(Boolean ignoreTableList) {
		this.ignoreTableList = ignoreTableList;
	}
	/**
	 * @return the ignoreUpdate
	 */
	public Boolean getIgnoreUpdate() {
		if (ignoreUpdate==null) return false;
		return ignoreUpdate;
	}
	/**
	 * @param ignoreUpdate the ignoreUpdate to set
	 */
	public void setIgnoreUpdate(Boolean ignoreUpdate) {
		this.ignoreUpdate = ignoreUpdate;
	}
	/**
	 * @return the betweenFilter
	 */
	public Boolean getBetweenFilter() {
		if (betweenFilter==null) return false;
		return betweenFilter;
	}
	/**
	 * @param betweenFilter the betweenFilter to set
	 */
	public void setBetweenFilter(Boolean betweenFilter) {
		this.betweenFilter = betweenFilter;
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
	
	
	public Boolean isField()
	{
		return this instanceof Field;
	}
	
	public Boolean isRelationship()
	{
		return this instanceof Relationship;
	}
	
	public Relationship asRelationship()
	{
		if (isRelationship())
			return (Relationship) this;
		else
			return null;
	}
	public Field asField()
	{
		if (isField())
			return (Field) this;
		else 
			return null;
	}

}
