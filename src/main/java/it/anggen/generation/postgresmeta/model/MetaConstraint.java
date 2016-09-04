package it.anggen.generation.postgresmeta.model;

public class MetaConstraint {
	private String constraintName;
	private String constraintType;
	private String tableName;
	private String columnName;
	private String isDeferrable;
	private String initiallyDeferred;
	private String matchType;
	private String onUpdate;
	private String onDelete;
	private String referencesTable;
	private String referencesField;
	
	public MetaConstraint(){
		
	}
	
	public String getConstraintName() {
		return constraintName;
	}
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	public String getConstraintType() {
		return constraintType;
	}
	public void setConstraintType(String constraintType) {
		this.constraintType = constraintType;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getIsDeferrable() {
		return isDeferrable;
	}
	public void setIsDeferrable(String isDeferrable) {
		this.isDeferrable = isDeferrable;
	}
	public String getInitiallyDeferred() {
		return initiallyDeferred;
	}
	public void setInitiallyDeferred(String initiallyDeferred) {
		this.initiallyDeferred = initiallyDeferred;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getOnUpdate() {
		return onUpdate;
	}
	public void setOnUpdate(String onUpdate) {
		this.onUpdate = onUpdate;
	}
	public String getOnDelete() {
		return onDelete;
	}
	public void setOnDelete(String onDelete) {
		this.onDelete = onDelete;
	}
	public String getReferencesField() {
		return referencesField;
	}
	public void setReferencesField(String referencesField) {
		this.referencesField = referencesField;
	}
	public String getReferencesTable() {
		return referencesTable;
	}
	public void setReferencesTable(String referencesTable) {
		this.referencesTable = referencesTable;
	}
}
