
package it.anggen.model;


public enum SecurityType {

    BLOCK_WITH_RESTRICTION(0),
    ACCESS_WITH_PERMISSION(1);
    private final int value;

    private SecurityType(int value) {
        this.value=value; 

    }

    public int getValue() {
        return this.value; 

    }

}
