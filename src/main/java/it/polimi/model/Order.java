package it.polimi.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="order", schema="ebsn")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="order_id")
	private Long orderId;
	
	@NotBlank
	private String name;
	
	// Social network the photo comes from
	@Column(name="timeslot_date")
	private Date timeslotDate;

	public Order()
	{
		
	}
	//getters and setters
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTimeslotDate() {
		return timeslotDate;
	}

	public void setTimeslotDate(Date timeslotDate) {
		this.timeslotDate = timeslotDate;
	}
}
