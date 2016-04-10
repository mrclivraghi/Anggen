
package it.anggen.model.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import it.anggen.model.LogType;
import it.anggen.model.OperationType;
import it.anggen.utils.annotation.DescriptionField;
import it.anggen.utils.annotation.MaxDescendantLevel;

@javax.persistence.Entity
@Table(schema = "log", name = "log_entry")
@it.anggen.utils.annotation.SecurityType(type = it.anggen.model.SecurityType.ACCESS_WITH_PERMISSION)
@MaxDescendantLevel(1)
public class LogEntry {

    public final static java.lang.Long staticEntityId = 16L;
    @javax.persistence.Column(name = "log_date")
    private Date logDate;
    @javax.persistence.Column(name = "host_name")
    private java.lang.String hostName;
    @javax.persistence.Column(name = "ip_address")
    private java.lang.String ipAddress;
    @javax.persistence.Column(name = "log_entry_id")
    @Id
    @GeneratedValue
    @DescriptionField
    private java.lang.Long logEntryId;
    @javax.persistence.Column(name = "info")
    private java.lang.String info;
   
    @Column(name = "user_id")
    private Long userId;
    
    
    @Column(name = "entity_id")
    private Long entityId;
    
    
    @javax.persistence.Column(name = "log_type")
    private LogType logType;
    @javax.persistence.Column(name = "operation_type")
    private OperationType operationType;

    public Date getLogDate() {
        return this.logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate=logDate;
    }

    public java.lang.String getHostName() {
        return this.hostName;
    }

    public void setHostName(java.lang.String hostName) {
        this.hostName=hostName;
    }

    public java.lang.String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(java.lang.String ipAddress) {
        this.ipAddress=ipAddress;
    }

    public java.lang.Long getLogEntryId() {
        return this.logEntryId;
    }

    public void setLogEntryId(java.lang.Long logEntryId) {
        this.logEntryId=logEntryId;
    }

    public java.lang.String getInfo() {
        return this.info;
    }

    public void setInfo(java.lang.String info) {
        this.info=info;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId=userId;
    }

    public Long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId=entityId;
    }

    public LogType getLogType() {
        return this.logType;
    }

    public void setLogType(LogType logType) {
        this.logType=logType;
    }

    public OperationType getOperationType() {
        return this.operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType=operationType;
    }

}
