
package it.anggen.controller.security;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.security.RestrictionFieldSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.log.LogEntryService;
import it.anggen.service.security.RestrictionFieldService;
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
@RequestMapping("/restrictionField")
public class RestrictionFieldController {

    @org.springframework.beans.factory.annotation.Autowired
    private RestrictionFieldService restrictionFieldService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.security.RestrictionField.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "restrictionField";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.security.RestrictionField> page = restrictionFieldService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionFieldSearchBean restrictionField) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.security.RestrictionField> restrictionFieldList;
        if (restrictionField.getRestrictionFieldId()!=null)
         log.info("Searching restrictionField like {}", restrictionField.getRestrictionFieldId());
        logEntryService.addLogEntry( "Searching entity like "+ restrictionField.getRestrictionFieldId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.security.RestrictionField.staticEntityId, securityService.getLoggedUser(),log);
        restrictionFieldList=restrictionFieldService.find(restrictionField);
        getSecurityMapping(restrictionFieldList);
        getRightMapping(restrictionFieldList);
         log.info("Search: returning {} restrictionField.",restrictionFieldList.size());
        return ResponseEntity.ok().body(restrictionFieldList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{restrictionFieldId}", method = RequestMethod.GET)
    public ResponseEntity getRestrictionFieldById(
        @PathVariable
        String restrictionFieldId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching restrictionField with id "+restrictionFieldId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.security.RestrictionField.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.security.RestrictionField> restrictionFieldList=restrictionFieldService.findById(Long.valueOf(restrictionFieldId));
        getSecurityMapping(restrictionFieldList);
        getRightMapping(restrictionFieldList);
         log.info("Search: returning {} restrictionField.",restrictionFieldList.size());
        return ResponseEntity.ok().body(restrictionFieldList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{restrictionFieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRestrictionFieldById(
        @PathVariable
        String restrictionFieldId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting restrictionField with id "+restrictionFieldId);
        logEntryService.addLogEntry( "Deleting restrictionField with id {}"+restrictionFieldId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.security.RestrictionField.staticEntityId, securityService.getLoggedUser(),log);
        restrictionFieldService.deleteById(Long.valueOf(restrictionFieldId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRestrictionField(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.RestrictionField restrictionField) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (restrictionField.getRestrictionFieldId()!=null)
        log.info("Inserting restrictionField like "+ restrictionField.getRestrictionFieldId());
        it.anggen.model.security.RestrictionField insertedRestrictionField=restrictionFieldService.insert(restrictionField);
        getRightMapping(insertedRestrictionField);
        logEntryService.addLogEntry( "Inserted restrictionField with id "+ insertedRestrictionField.getRestrictionFieldId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.security.RestrictionField.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedRestrictionField);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRestrictionField(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.RestrictionField restrictionField) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating restrictionField with id "+restrictionField.getRestrictionFieldId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.security.RestrictionField.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(restrictionField);
        it.anggen.model.security.RestrictionField updatedRestrictionField=restrictionFieldService.update(restrictionField);
        getSecurityMapping(updatedRestrictionField);
        getRightMapping(updatedRestrictionField);
        return ResponseEntity.ok().body(updatedRestrictionField);
    }

    private List<it.anggen.model.security.RestrictionField> getRightMapping(List<it.anggen.model.security.RestrictionField> restrictionFieldList) {
        for (it.anggen.model.security.RestrictionField restrictionField: restrictionFieldList)
        {
        getRightMapping(restrictionField);
        }
        return restrictionFieldList;
    }

    private void getRightMapping(it.anggen.model.security.RestrictionField restrictionField) {
        if (restrictionField.getRole()!=null)
        {
        restrictionField.getRole().setRestrictionEntityList(null);
        restrictionField.getRole().setRestrictionFieldList(null);
        restrictionField.getRole().setUserList(null);
        restrictionField.getRole().setRestrictionEntityGroupList(null);
        }
        if (restrictionField.getField()!=null)
        {
        restrictionField.getField().setRestrictionFieldList(null);
        restrictionField.getField().setEntity(null);
        restrictionField.getField().setTab(null);
        restrictionField.getField().setAnnotationList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.security.RestrictionField restrictionField) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        restrictionField.setRole(restrictionFieldService.findById(restrictionField.getRestrictionFieldId()).get(0).getRole());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        restrictionField.setField(restrictionFieldService.findById(restrictionField.getRestrictionFieldId()).get(0).getField());
    }

    private List<it.anggen.model.security.RestrictionField> getSecurityMapping(List<it.anggen.model.security.RestrictionField> restrictionFieldList) {
        for (it.anggen.model.security.RestrictionField restrictionField: restrictionFieldList)
        {
        getSecurityMapping(restrictionField);
        }
        return restrictionFieldList;
    }

    private void getSecurityMapping(it.anggen.model.security.RestrictionField restrictionField) {
        if (securityEnabled && restrictionField.getRole()!=null  && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        restrictionField.setRole(null);

        if (securityEnabled && restrictionField.getField()!=null  && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        restrictionField.setField(null);

    }

}
