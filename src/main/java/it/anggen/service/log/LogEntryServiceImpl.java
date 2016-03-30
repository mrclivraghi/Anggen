
package it.anggen.service.log;

import java.util.Date;
import java.util.List;

import it.anggen.model.LogType;
import it.anggen.model.OperationType;
import it.anggen.model.entity.Entity;
import it.anggen.model.log.LogEntry;
import it.anggen.model.security.User;
import it.anggen.repository.entity.EntityRepository;
import it.anggen.repository.log.LogEntryRepository;
import it.anggen.searchbean.log.LogEntrySearchBean;
import it.anggen.service.log.LogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogEntryServiceImpl
    implements LogEntryService
{

    @Autowired
    public LogEntryRepository logEntryRepository;
    
    @Autowired
    public EntityRepository entityRepository;
    
    private static Integer PAGE_SIZE = (5);

    @Override
    public List<it.anggen.model.log.LogEntry> findById(Long logEntryId) {
        return logEntryRepository.findByLogEntryId(logEntryId);
    }

    @Override
    public Page<it.anggen.model.log.LogEntry> findByPage(Integer pageNumber) {
        org.springframework.data.domain.PageRequest pageRequest = new org.springframework.data.domain.PageRequest(pageNumber - 1, PAGE_SIZE, org.springframework.data.domain.Sort.Direction.DESC, "logEntryId");
        return logEntryRepository.findAll(pageRequest);
    }

    @Override
    public List<it.anggen.model.log.LogEntry> find(LogEntrySearchBean logEntry) {
        return logEntryRepository.findByLogEntryIdAndInfoAndHostNameAndIpAddressAndLogDateAndLogTypeAndOperationType(logEntry.getLogEntryId(),logEntry.getInfo(),logEntry.getHostName(),logEntry.getIpAddress(),it.anggen.utils.Utility.formatDate(logEntry.getLogDate()), (logEntry.getLogType()==null)? null : logEntry.getLogType().getValue(), (logEntry.getOperationType()==null)? null : logEntry.getOperationType().getValue());
    }

    @Override
    public void deleteById(Long logEntryId) {
        logEntryRepository.delete(logEntryId);
        return;
    }

    @Override
    public it.anggen.model.log.LogEntry insert(it.anggen.model.log.LogEntry logEntry) {
        return logEntryRepository.save(logEntry);
    }

    @Override
    @Transactional
    public it.anggen.model.log.LogEntry update(it.anggen.model.log.LogEntry logEntry) {
        it.anggen.model.log.LogEntry returnedLogEntry=logEntryRepository.save(logEntry);
         return returnedLogEntry;
    }


	@Override
	public void addLogEntry(String hostName, String info, String ipAddress, LogType logType,
			OperationType operationType, Long entityId, User user) {
		LogEntry logEntry = new LogEntry();
		logEntry.setHostName(hostName);
		logEntry.setInfo(info);
		logEntry.setIpAddress(ipAddress);
		if (logType!=null)
			logEntry.setLogType(logType);
		else
			logEntry.setLogType(LogType.WARNING);
		logEntry.setOperationType(operationType);
		logEntry.setEntityId(entityId);
		if (user!=null && user.getUserId()!=null)
			logEntry.setUserId(user.getUserId());
		else
			logEntry.setUserId(-1L);
		logEntry.setLogDate(new Date());
		logEntryRepository.save(logEntry);
	}

}
