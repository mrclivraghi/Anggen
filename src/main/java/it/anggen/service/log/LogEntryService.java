
package it.anggen.service.log;

import java.util.List;
import it.anggen.searchbean.log.LogEntrySearchBean;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface LogEntryService {


    public List<it.anggen.model.log.LogEntry> findById(Long LogEntryId);

    public List<it.anggen.model.log.LogEntry> find(LogEntrySearchBean LogEntry);

    public void deleteById(Long LogEntryId);

    public it.anggen.model.log.LogEntry insert(it.anggen.model.log.LogEntry LogEntry);

    public it.anggen.model.log.LogEntry update(it.anggen.model.log.LogEntry LogEntry);

    public Page<it.anggen.model.log.LogEntry> findByPage(
        @PathVariable
        Integer pageNumber);

}
