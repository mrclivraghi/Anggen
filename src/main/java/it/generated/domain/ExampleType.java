
package it.generated.domain;


public enum ExampleType {

    TYPE1(0),
    TYPE2(1);
    private final int value;

    private ExampleType(int value) {
        this.value=value; 

    }

    public int getValue() {
        return this.value; 

    }

}
