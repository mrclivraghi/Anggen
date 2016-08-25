package it.anggen.generation.postgresmeta.model;

public class MetaTable {
	private String tableCatalog;
	private String tableSchema;
	private String tableName;
	private String tableType;
	private String selfReferencingColumnName;
	private String referenceGeneration;
	private String userDefinedTypeCatalog;
	private String userDefinedTypeSchema;
	private String userDefinedTypeName;
	private String isInsertableInto;
	private String isTyped;
	private String commitAction;
	
	public MetaTable(){
		
	}
	
	public String getTableCatalog() {
		return tableCatalog;
	}
	public void setTableCatalog(String tableCatalog) {
		this.tableCatalog = tableCatalog;
	}
	public String getTableSchema() {
		return tableSchema;
	}
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getSelfReferencingColumnName() {
		return selfReferencingColumnName;
	}
	public void setSelfReferencingColumnName(String selfReferencingColumnName) {
		this.selfReferencingColumnName = selfReferencingColumnName;
	}
	public String getReferenceGeneration() {
		return referenceGeneration;
	}
	public void setReferenceGeneration(String referenceGeneration) {
		this.referenceGeneration = referenceGeneration;
	}
	public String getUserDefinedTypeCatalog() {
		return userDefinedTypeCatalog;
	}
	public void setUserDefinedTypeCatalog(String userDefinedTypeCatalog) {
		this.userDefinedTypeCatalog = userDefinedTypeCatalog;
	}
	public String getUserDefinedTypeSchema() {
		return userDefinedTypeSchema;
	}
	public void setUserDefinedTypeSchema(String userDefinedTypeSchema) {
		this.userDefinedTypeSchema = userDefinedTypeSchema;
	}
	public String getUserDefinedTypeName() {
		return userDefinedTypeName;
	}
	public void setUserDefinedTypeName(String userDefinedTypeName) {
		this.userDefinedTypeName = userDefinedTypeName;
	}
	public String getIsInsertableInto() {
		return isInsertableInto;
	}
	public void setIsInsertableInto(String isInsertableInto) {
		this.isInsertableInto = isInsertableInto;
	}
	public String getIsTyped() {
		return isTyped;
	}
	public void setIsTyped(String isTyped) {
		this.isTyped = isTyped;
	}
	public String getCommitAction() {
		return commitAction;
	}
	public void setCommitAction(String commitAction) {
		this.commitAction = commitAction;
	}
	
}
