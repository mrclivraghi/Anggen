
package it.anggen.repository.log;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepository
    extends JpaRepository<it.anggen.model.log.LogEntry, java.lang.Long>
{


    @Query("select l from LogEntry l")
    public List<it.anggen.model.log.LogEntry> findAll();

    public List<it.anggen.model.log.LogEntry> findByLogEntryId(java.lang.Long logEntryId);

    public List<it.anggen.model.log.LogEntry> findByInfo(java.lang.String info);

    public List<it.anggen.model.log.LogEntry> findByHostName(java.lang.String hostName);

    public List<it.anggen.model.log.LogEntry> findByIpAddress(java.lang.String ipAddress);

    public List<it.anggen.model.log.LogEntry> findByLogDate(java.lang.String logDate);

    public List<it.anggen.model.log.LogEntry> findByLogType(java.lang.Integer logType);

    public List<it.anggen.model.log.LogEntry> findByOperationType(java.lang.Integer operationType);

    public List<it.anggen.model.log.LogEntry> findByUserId(Long userId);

    public List<it.anggen.model.log.LogEntry> findByEntityId(Long entityId);

    @Query("select l from LogEntry l where  (:logEntryId is null or cast(:logEntryId as string)=cast(l.logEntryId as string))"
    		+ " and (:info is null or :info='' or cast(:info as string)=l.info) "
    		+ "and (:hostName is null or :hostName='' or cast(:hostName as string)=l.hostName)"
    		+ " and (:ipAddress is null or :ipAddress='' or cast(:ipAddress as string)=l.ipAddress) "
    		+ "and (:logDate is null or cast(:logDate as string)=cast(date(l.logDate) as string)) and "
    		+ "(:logType is null or cast(:logType as string)=cast(l.logType as string)) and "
    		+ "(:operationType is null or cast(:operationType as string)=cast(l.operationType as string))"
    		+ " ")
    public List<it.anggen.model.log.LogEntry> findByLogEntryIdAndInfoAndHostNameAndIpAddressAndLogDateAndLogTypeAndOperationType(
        @org.springframework.data.repository.query.Param("logEntryId")
        java.lang.Long logEntryId,
        @org.springframework.data.repository.query.Param("info")
        java.lang.String info,
        @org.springframework.data.repository.query.Param("hostName")
        java.lang.String hostName,
        @org.springframework.data.repository.query.Param("ipAddress")
        java.lang.String ipAddress,
        @org.springframework.data.repository.query.Param("logDate")
        java.lang.String logDate,
        @org.springframework.data.repository.query.Param("logType")
        java.lang.Integer logType,
        @org.springframework.data.repository.query.Param("operationType")
        java.lang.Integer operationType);

}
