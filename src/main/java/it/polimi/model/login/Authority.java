package it.polimi.model.login;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="authority", schema="security")
public class Authority {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_authority")
	private Long authorityId;
	private String name;
	
	@ManyToMany(mappedBy = "authorityList",fetch=FetchType.EAGER)
	private List<User> userList;
	
	/**
	 * @return the authorityId
	 */
	public Long getAuthorityId() {
		return authorityId;
	}
	/**
	 * @param authorityId the authorityId to set
	 */
	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
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
	 * @return the userList
	 */
	public List<User> getUserList() {
		return userList;
	}
	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
