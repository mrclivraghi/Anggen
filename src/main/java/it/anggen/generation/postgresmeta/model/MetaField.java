package it.anggen.generation.postgresmeta.model;

public class MetaField {
	private String tableCatalog;
	private String tableSchema;
	private String tableName;
	private String columnName;
	private Integer ordinalPosition;
	private String columnDefault;
	private String isNullable;
	private String dataType;
	private Integer characterMaximumLength;
	private Integer characterOctetLength;
	private Integer numericPrecision;
	private Integer numericPrecisionRadix;
	private Integer numericScale;
	private Integer datetimePrecision;
	private String udtCatalog;
	private String udtName;
	private Integer dtdIdentifier;
	private String isSelfReferencing;
	private String isIdentity;
	private String isGenerated;
	private String isUpdatable;
	
	public MetaField(){
		
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
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer getOrdinalPosition() {
		return ordinalPosition;
	}
	public void setOrdinalPosition(Integer ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}
	public String getColumnDefault() {
		return columnDefault;
	}
	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}
	public String getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getCharacterMaximumLength() {
		return characterMaximumLength;
	}
	public void setCharacterMaximumLength(Integer characterMaximumLength) {
		this.characterMaximumLength = characterMaximumLength;
	}
	public Integer getCharacterOctetLength() {
		return characterOctetLength;
	}
	public void setCharacterOctetLength(Integer characterOctetLength) {
		this.characterOctetLength = characterOctetLength;
	}
	public Integer getNumericPrecision() {
		return numericPrecision;
	}
	public void setNumericPrecision(Integer numericPrecision) {
		this.numericPrecision = numericPrecision;
	}
	public Integer getNumericPrecisionRadix() {
		return numericPrecisionRadix;
	}
	public void setNumericPrecisionRadix(Integer numericPrecisionRadix) {
		this.numericPrecisionRadix = numericPrecisionRadix;
	}
	public Integer getNumericScale() {
		return numericScale;
	}
	public void setNumericScale(Integer numericScale) {
		this.numericScale = numericScale;
	}
	public Integer getDatetimePrecision() {
		return datetimePrecision;
	}
	public void setDatetimePrecision(Integer datetimePrecision) {
		this.datetimePrecision = datetimePrecision;
	}
	public String getUdtCatalog() {
		return udtCatalog;
	}
	public void setUdtCatalog(String udtCatalog) {
		this.udtCatalog = udtCatalog;
	}
	public String getUdtName() {
		return udtName;
	}
	public void setUdtName(String udtName) {
		this.udtName = udtName;
	}
	public Integer getDtdIdentifier() {
		return dtdIdentifier;
	}
	public void setDtdIdentifier(Integer dtdIdentifier) {
		this.dtdIdentifier = dtdIdentifier;
	}
	public String getIsSelfReferencing() {
		return isSelfReferencing;
	}
	public void setIsSelfReferencing(String isSelfReferencing) {
		this.isSelfReferencing = isSelfReferencing;
	}
	public String getIsIdentity() {
		return isIdentity;
	}
	public void setIsIdentity(String isIdentity) {
		this.isIdentity = isIdentity;
	}
	public String getIsGenerated() {
		return isGenerated;
	}
	public void setIsGenerated(String isGenerated) {
		this.isGenerated = isGenerated;
	}
	public String getIsUpdatable() {
		return isUpdatable;
	}
	public void setIsUpdatable(String isUpdatable) {
		this.isUpdatable = isUpdatable;
	}
	
}
