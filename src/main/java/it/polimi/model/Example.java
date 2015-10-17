package it.polimi.model;

import it.polimi.utils.annotation.IgnoreSearch;
import it.polimi.utils.annotation.IgnoreTableList;
import it.polimi.utils.annotation.IgnoreUpdate;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.repository.Temporal;

@Entity
public class Example {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_example", unique=true, nullable=false)
	@IgnoreUpdate
	private Integer exampleId;
	
	@NotBlank
	@Size(min=3, max=20)
	@IgnoreSearch
	private String name;
	
	@IgnoreTableList
	private Long eta;
	
	@IgnoreTableList
	private Boolean male;
	
	@javax.persistence.Temporal(value=TemporalType.DATE)
	private Date birthDate;
	
	//@javax.persistence.Temporal(TemporalType.TIME)
	@IgnoreSearch
	private Time birthTime;
	
	private Sex sex;
	
	public Example()
	{
		
	}

	/**
	 * @return the exampleId
	 */
	public Integer getExampleId() {
		return exampleId;
	}

	/**
	 * @param exampleId the exampleId to set
	 */
	public void setExampleId(Integer exampleId) {
		this.exampleId = exampleId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the eta
	 */
	public Long getEta() {
		return eta;
	}

	/**
	 * @param eta the eta to set
	 */
	public void setEta(Long eta) {
		this.eta = eta;
	}

	/**
	 * @return the male
	 */
	public Boolean getMale() {
		return male;
	}

	/**
	 * @param male the male to set
	 */
	public void setMale(Boolean male) {
		this.male = male;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the birthTime
	 */
	public Time getBirthTime() {
		return birthTime;
	}

	/**
	 * @param birthTime the birthTime to set
	 */
	public void setBirthTime(Time birthTime) {
		this.birthTime = birthTime;
	}

	/**
	 * @return the sex
	 */
	public Sex getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}

}
