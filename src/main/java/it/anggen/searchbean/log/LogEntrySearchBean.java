
package it.anggen.searchbean.log;


public class LogEntrySearchBean {

    public java.lang.Long logEntryId;
    public java.lang.String info;
    public java.lang.String hostName;
    public java.lang.String ipAddress;
    public java.util.Date logDate;
    public it.anggen.model.LogType logType;
    public it.anggen.model.OperationType operationType;
    public it.anggen.model.security.User user;
    public it.anggen.model.entity.Entity entity;

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

    public java.util.Date getLogDate() {
        return this.logDate;
    }

    public void setLogDate(java.util.Date logDate) {
        this.logDate=logDate;
    }

    public it.anggen.model.LogType getLogType() {
        return this.logType;
    }

    public void setLogType(it.anggen.model.LogType logType) {
        this.logType=logType;
    }

    public it.anggen.model.OperationType getOperationType() {
        return this.operationType;
    }

    public void setOperationType(it.anggen.model.OperationType operationType) {
        this.operationType=operationType;
    }

    public it.anggen.model.security.User getUser() {
        return this.user;
    }

    public void setUser(it.anggen.model.security.User user) {
        this.user=user;
    }

    public it.anggen.model.entity.Entity getEntity() {
        return this.entity;
    }

    public void setEntity(it.anggen.model.entity.Entity entity) {
        this.entity=entity;
    }

}
