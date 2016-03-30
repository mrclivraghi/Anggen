package it.anggen.model;

public enum LogType {
	 INFO(0),
	 DEBUG(1),
	 WARNING(2),
	 ERROR(3);
	
	
	    private final int value;

	    private LogType(int value) {
	        this.value=value; 

	    }

	    public int getValue() {
	        return this.value; 

	    }
}
