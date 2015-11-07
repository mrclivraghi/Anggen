package it.polimi.model.mountain;

import it.polimi.utils.CustomJsonDateDeserializer;
import it.polimi.utils.annotation.DescriptionField;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/* 
 * Photo element
 */
@Entity
@Table(name="photo", schema="ebsn")
public class Photo {


		//ATTRIBUTES
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_photo")
	@DescriptionField
	private Long photoId;
	
	@DescriptionField
	private String url;
	
	private String social;
	
	
	@Column(name="date")
	//@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	
	private Integer status;
	
	// The id of photo in the social network, this is used in order to not introduce duplicates
	@Column(name ="social_id")
	private String socialId;
	

	@Column(name="related_post")
	private String relatedPost;
	
	//RELATIONSHIPS

	//@Cascade({CascadeType.ALL})
	@ManyToOne
	private SeedQuery seedQuery;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SeedQuery getSeedQuery() {
		return seedQuery;
	}

	public void setSeedQuery(SeedQuery seedQuery) {
		this.seedQuery = seedQuery;
	}
	//@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setDate(Date date) {
		this.date = date;
	}
	
	//@JsonSerialize(using = CustomDateSerializer.class)
	public Date getDate()
	{
		return this.date;
	}
	
	public void setSocial(String social) {
		this.social = social;
	}
	
	public String getSocial() {
		return social;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getRelatedPost() {
		return relatedPost;
	}

	public void setRelatedPost(String relatedPost) {
		this.relatedPost = relatedPost;
	}
	
	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

}
