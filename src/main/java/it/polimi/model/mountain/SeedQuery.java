package it.polimi.model.mountain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/* 
 * Seed Query Bean
 */
@Entity
@Table(name="seed_query", schema="ebsn")
public class SeedQuery {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_seed_query")
	private Long seedQueryId;
	
	@Column(name ="seed_keyword")
	private String seedKeyword;
	
	@NotNull
	private Integer status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	//@Cascade({CascadeType.PERSIST})
	@JoinColumn(name="mountain_id_mountain")
	private Mountain mountain;
	
	
	@OneToMany(fetch=FetchType.EAGER)
	//@Cascade({CascadeType.ALL})
	@Type(type="it.polimi.model.Photo")
	@JoinColumn(name="seedquery_id_seed_query")
	private List<Photo> photoList;
	

	public List<Photo> getPhotoList() {
		return photoList;
	}

	public void setPhotoList(List<Photo> photoList) {
		this.photoList = photoList;
	}

		public String getSeedKeyword() {
		return seedKeyword;
	}

	public void setSeedKeyword(String seedKeyword) {
		this.seedKeyword = seedKeyword;
	}

	public Mountain getMountain() {
		return mountain;
	}

	public void setMountain(Mountain mountain) {
		this.mountain = mountain;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSeedQueryId() {
		return seedQueryId;
	}

	public void setSeedQueryId(Long seedQueryId) {
		this.seedQueryId = seedQueryId;
	}

}
