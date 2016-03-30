
package it.anggen.service.log;

import java.util.List;
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
        return logEntryRepository.findByLogEntryIdAndInfoAndHostNameAndIpAddressAndLogDateAndLogTypeAndOperationTypeAndUserAndEntity(logEntry.getLogEntryId(),logEntry.getInfo(),logEntry.getHostName(),logEntry.getIpAddress(),it.anggen.utils.Utility.formatDate(logEntry.getLogDate()), (logEntry.getLogType()==null)? null : logEntry.getLogType().getValue(), (logEntry.getOperationType()==null)? null : logEntry.getOperationType().getValue(),logEntry.getUser(),logEntry.getEntity());
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

}
