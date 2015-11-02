package it.polimi.model.login;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="user", schema="security")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name ="id_user")
	private Long userId;
	private String username;
	private String password;
	private String role;
	private Boolean enabled;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.model.login.Authority")
	@JoinTable(name="user_authority", joinColumns = {
			@JoinColumn(name="user_id") },
			inverseJoinColumns= {
			@JoinColumn(name="authority_id")
			
	})
	private List<Authority> authorityList;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the authority
	 */
	public List<Authority> getAuthorityList() {
		return authorityList;
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
