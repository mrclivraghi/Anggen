
package it.anggen.model;


public enum OperationType {

    CREATE_ENTITY(0),
    UPDATE_ENTITY(1),
    DELETE_ENTITY(2),
    SEARCH_ENTITY(3),
    LOGIN_SUCCESS(4),
    LOGIN_FAILED(5),
    VIEW_METRICS(6),
    SECURITY_VIOLATION_ATTEMPT(7);
    private final int value;

    private OperationType(int value) {
        this.value=value; 

    }

    public int getValue() {
        return this.value; 

    }

}
