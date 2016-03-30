
package it.anggen.controller.log;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.log.LogEntrySearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.log.LogEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/logEntry")
public class LogEntryController {

    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.log.LogEntry.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.log.LogEntry.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "logEntry";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.log.LogEntry> page = logEntryService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        LogEntrySearchBean logEntry) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.log.LogEntry.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.log.LogEntry> logEntryList;
        if (logEntry.getLogEntryId()!=null)
         log.info("Searching logEntry like {}",logEntry.toString());
        logEntryList=logEntryService.find(logEntry);
        getSecurityMapping(logEntryList);
        getRightMapping(logEntryList);
         log.info("Search: returning {} logEntry.",logEntryList.size());
        return ResponseEntity.ok().body(logEntryList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{logEntryId}", method = RequestMethod.GET)
    public ResponseEntity getLogEntryById(
        @PathVariable
        String logEntryId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.log.LogEntry.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching logEntry with id {}",logEntryId);
        List<it.anggen.model.log.LogEntry> logEntryList=logEntryService.findById(Long.valueOf(logEntryId));
        getSecurityMapping(logEntryList);
        getRightMapping(logEntryList);
         log.info("Search: returning {} logEntry.",logEntryList.size());
        return ResponseEntity.ok().body(logEntryList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{logEntryId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLogEntryById(
        @PathVariable
        String logEntryId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.log.LogEntry.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting logEntry with id {}",logEntryId);
        logEntryService.deleteById(Long.valueOf(logEntryId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertLogEntry(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.log.LogEntry logEntry) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.log.LogEntry.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (logEntry.getLogEntryId()!=null)
        log.info("Inserting logEntry like {}",logEntry.toString());
        it.anggen.model.log.LogEntry insertedLogEntry=logEntryService.insert(logEntry);
        getRightMapping(insertedLogEntry);
        log.info("Inserted logEntry with id {}",insertedLogEntry.getLogEntryId());
        return ResponseEntity.ok().body(insertedLogEntry);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateLogEntry(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.log.LogEntry logEntry) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.log.LogEntry.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating logEntry with id {}",logEntry.getLogEntryId());
        rebuildSecurityMapping(logEntry);
        it.anggen.model.log.LogEntry updatedLogEntry=logEntryService.update(logEntry);
        getSecurityMapping(updatedLogEntry);
        getRightMapping(updatedLogEntry);
        return ResponseEntity.ok().body(updatedLogEntry);
    }

    private List<it.anggen.model.log.LogEntry> getRightMapping(List<it.anggen.model.log.LogEntry> logEntryList) {
        for (it.anggen.model.log.LogEntry logEntry: logEntryList)
        {
        getRightMapping(logEntry);
        }
        return logEntryList;
    }

    private void getRightMapping(it.anggen.model.log.LogEntry logEntry) {
        if (logEntry.getUser()!=null)
        {
        logEntry.getUser().setRoleList(null);
        }
        if (logEntry.getEntity()!=null)
        {
        logEntry.getEntity().setFieldList(null);
        logEntry.getEntity().setEnumFieldList(null);
        logEntry.getEntity().setTabList(null);
        logEntry.getEntity().setEntityGroup(null);
        logEntry.getEntity().setRestrictionEntityList(null);
        logEntry.getEntity().setRelationshipList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.log.LogEntry logEntry) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        logEntry.setUser(logEntryService.findById(logEntry.getLogEntryId()).get(0).getUser());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        logEntry.setEntity(logEntryService.findById(logEntry.getLogEntryId()).get(0).getEntity());
    }

    private List<it.anggen.model.log.LogEntry> getSecurityMapping(List<it.anggen.model.log.LogEntry> logEntryList) {
        for (it.anggen.model.log.LogEntry logEntry: logEntryList)
        {
        getSecurityMapping(logEntry);
        }
        return logEntryList;
    }

    private void getSecurityMapping(it.anggen.model.log.LogEntry logEntry) {
        if (securityEnabled && logEntry.getUser()!=null  && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        logEntry.setUser(null);

        if (securityEnabled && logEntry.getEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        logEntry.setEntity(null);

    }

}
