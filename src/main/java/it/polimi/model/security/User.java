package it.polimi.model.security;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "user", schema = "sso")
public class User {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", 
		unique = true, nullable = false)
	private Long userId;
	
	private String username;
	private String password;
	private Boolean enabled;
	

	@ManyToMany(fetch=FetchType.EAGER)
	@Type(type="it.polimi.domain.security.UserRole")
	@JoinTable(name="user_role", schema="sso", joinColumns = {
			@JoinColumn(name="user_id") },
			inverseJoinColumns= {
			@JoinColumn(name="role_id")
			
	})
	private List<Role> roleList = new ArrayList<Role>();

	public User() {
	}

	public User(String username, String password, boolean enabled) {
		this.setUsername(username);
		this.setPassword(password);
		this.setEnabled(enabled);
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the enabled
	 */
	public Boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the roleList
	 */
	public List<Role> getRoleList() {
		return roleList;
	}

	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}