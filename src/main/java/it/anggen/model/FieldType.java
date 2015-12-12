
package it.anggen.model;


public enum FieldType {

    STRING(0),
    INTEGER(1),
    DATE(2),
    DOUBLE(3),
    TIME(4),
    BOOLEAN(5),
    LONG(6),
    FILE(7),
    PHOTO(8);
    private final int value;

    private FieldType(int value) {
        this.value=value; 

    }

    public int getValue() {
        return this.value; 

    }

}
