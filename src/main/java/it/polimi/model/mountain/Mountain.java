package it.polimi.model.mountain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/* 
 * Bean that represent the mountain
 */
@Entity
@Table(name="mountain", schema="ebsn")
public class Mountain {
	
	public static class Summary {};
	public static class Extended extends Summary{};
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_mountain")
	private Long mountainId;
		
	@NotBlank
	@Size(min=2, max=14)
	private String name;
	
	private String height;
	

	@OneToMany(fetch=FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@Type(type="it.polimi.model.SeedQuery")
	@JoinColumn(name="mountain_id_mountain")
	private List<SeedQuery> seedQueryList;
	
	public Mountain()
	{
		
	}
	
	public List<SeedQuery> getSeedQueryList() {
		return seedQueryList;
	}

	public void setSeedQueryList(List<SeedQuery> seedQueryList) {
		this.seedQueryList = seedQueryList;
	}


	public Long getMountainId() {
		return mountainId;
	}

	public void setMountainId(Long mountainId) {
		this.mountainId = mountainId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}



}
