
package it.anggen.model;


public enum GenerationType {

    SHOW_INCLUDE(0),
    HIDE_IGNORE(1);
    private final int value;

    private GenerationType(int value) {
        this.value=value; 

    }

    public int getValue() {
        return this.value; 

    }

}
