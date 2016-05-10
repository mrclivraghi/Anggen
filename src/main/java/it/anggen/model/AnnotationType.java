
package it.anggen.model;


public enum AnnotationType {

    PRIMARY_KEY(0),
    NOT_NULL(1),
    NOT_BLANK(2),
    DESCRIPTION_FIELD(3),
    BETWEEN_FILTER(4),
    EXCEL_EXPORT(5),
    FILTER_FIELD(6),
    IGNORE_SEARCH(7),
    IGNORE_UPDATE(8),
    IGNORE_TABLE_LIST(9),
    SIZE(10),
    PASSWORD(11),
    PRIORITY(12),
    EMBEDDED(13),
    CACHE(14),
    INCLUDE_SEARCH(15),
    INCLUDE_TABLE_LIST(16),
    INCLUDE_UPDATE(17);
    private final int value;

    private AnnotationType(int value) {
        this.value=value; 

    }

    public int getValue() {
        return this.value; 

    }

}
