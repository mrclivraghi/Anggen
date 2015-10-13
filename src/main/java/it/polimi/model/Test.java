package it.polimi.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Test {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long testId;
	
	private Timestamp myDate;

	
	public Test()
	{
		
	}
	
	public Timestamp getMyDate() {
		return myDate;
	}

	public void setMyDate(Timestamp myDate) {
		this.myDate = myDate;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}
	

}
