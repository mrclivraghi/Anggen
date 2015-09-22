package it.polimi.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="order", schema="ebsn")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="order_id")
	private Long orderId;
	
	private String name;
	
	@Column(name="timeslot_date")
	@Type(type="date")
	private Date timeslotDate;

	@OneToOne
	@Cascade({CascadeType.ALL})
	@Type(type="it.polimi.model.Person")
	private Person person;
	
	
	@OneToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@Type(type="it.polimi.model.Place")
	@JoinColumn(name="order_order_id")
	private List<Place> placeList;
	
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
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<Place> getPlaceList() {
		return placeList;
	}
	public void setPlaceList(List<Place> placeList) {
		this.placeList = placeList;
	}
}
