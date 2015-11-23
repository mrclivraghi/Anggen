
package it.generated.domain;


public enum ExampleType {

    TYPE2(1),
    TYPE1(0);
    private final int value;

    private ExampleType(int value) {
        this.value=value; 

    }

    public int getValue() {
        return this.value; 

    }

}
