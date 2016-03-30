package it.anggen.model.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.anggen.model.LogType;
import it.anggen.model.OperationType;
import it.anggen.model.security.User;

@Entity
@Table(schema="log",name="log_entry")
public class LogEntry {

	@Id
	@GeneratedValue
	@Column(name="log_entry_id")
	private Long logEntryId;
	
	@Column(name="log_date")
	private Date logDate;
	
	@Column(name="ip_address")
	private String ipAddress;
	
	@Column(name="host_name")
	private String hostName;
	
	@Column(name="info")
	private String info;
	
	@Column(name="log_type")
	private LogType logType;
	
	@Column(name="operation_type")
	private OperationType operationType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@javax.persistence.JoinColumn(name = "entity_id_entity")
	private it.anggen.model.entity.Entity entity;

	@ManyToOne(fetch=FetchType.EAGER)
	@javax.persistence.JoinColumn(name = "user_id_user")
	private User user;

	public Long getLogEntryId() {
		return logEntryId;
	}

	public void setLogEntryId(Long logEntryId) {
		this.logEntryId = logEntryId;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public it.anggen.model.entity.Entity getEntity() {
		return entity;
	}

	public void setEntity(it.anggen.model.entity.Entity entity) {
		this.entity = entity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
