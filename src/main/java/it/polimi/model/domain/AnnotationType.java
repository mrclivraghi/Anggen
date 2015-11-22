package it.polimi.model.domain;

public enum AnnotationType {
	PRIMARY_KEY(0),
	NOT_NULL(1),
	BOT_BLANK(2),
	DESCRIPTION_FIELD(3),
	BETWEEN_FILTER(4),
	EXCEL_EXPORT(5),
	FILTER_FIELD(6),
	IGNORE_SEARCH(7),
	IGNORE_UPDATE(8),
	IGNORE_TABLE_LIST(9);
	
	private final int value;
	
	private AnnotationType(int value)
	{
		this.value=value;
	}
	
	public int getValue()
	{
		return this.value;
	}
}
