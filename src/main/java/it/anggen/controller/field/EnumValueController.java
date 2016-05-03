
package it.anggen.controller.field;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import it.anggen.searchbean.field.EnumValueSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.field.EnumValueService;
import it.anggen.service.log.LogEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enumValue")
public class EnumValueController {

    @org.springframework.beans.factory.annotation.Autowired
    private EnumValueService enumValueService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.field.EnumValue.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @ApiOperation(value = "Return a page of enumValue", notes = "Return a single page of enumValue", response = it.anggen.model.field.EnumValue.class, responseContainer = "List")
    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.field.EnumValue> page = enumValueService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @ApiOperation(value = "Return a list of enumValue", notes = "Return a list of enumValue based on the search bean requested", response = it.anggen.model.field.EnumValue.class, responseContainer = "List")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EnumValueSearchBean enumValue) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.field.EnumValue> enumValueList;
        if (enumValue.getEnumValueId()!=null)
         log.info("Searching enumValue like {}", enumValue.getName()+' '+ enumValue.getEnumValueId());
        logEntryService.addLogEntry( "Searching entity like "+ enumValue.getName()+' '+ enumValue.getEnumValueId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.field.EnumValue.staticEntityId, securityService.getLoggedUser(),log);
        enumValueList=enumValueService.find(enumValue);
        getSecurityMapping(enumValueList);
        getRightMapping(enumValueList);
         log.info("Search: returning {} enumValue.",enumValueList.size());
        return ResponseEntity.ok().body(enumValueList);
    }

    @ApiOperation(value = "Return a the enumValue identified by the given id", notes = "Return a the enumValue identified by the given id", response = it.anggen.model.field.EnumValue.class, responseContainer = "List")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/{enumValueId}", method = RequestMethod.GET)
    public ResponseEntity getEnumValueById(
        @PathVariable
        String enumValueId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching enumValue with id "+enumValueId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.field.EnumValue.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.field.EnumValue> enumValueList=enumValueService.findById(Long.valueOf(enumValueId));
        getSecurityMapping(enumValueList);
        getRightMapping(enumValueList);
         log.info("Search: returning {} enumValue.",enumValueList.size());
        return ResponseEntity.ok().body(enumValueList);
    }

    @ApiOperation(value = "Delete the enumValue identified by the given id", notes = "Delete the enumValue identified by the given id")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/{enumValueId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEnumValueById(
        @PathVariable
        String enumValueId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting enumValue with id "+enumValueId);
        logEntryService.addLogEntry( "Deleting enumValue with id {}"+enumValueId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.field.EnumValue.staticEntityId, securityService.getLoggedUser(),log);
        enumValueService.deleteById(Long.valueOf(enumValueId));
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Insert the enumValue given", notes = "Insert the enumValue given ", response = it.anggen.model.field.EnumValue.class)
    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEnumValue(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.EnumValue enumValue) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (enumValue.getEnumValueId()!=null)
        log.info("Inserting enumValue like "+ enumValue.getName()+' '+ enumValue.getEnumValueId());
        it.anggen.model.field.EnumValue insertedEnumValue=enumValueService.insert(enumValue);
        getRightMapping(insertedEnumValue);
        logEntryService.addLogEntry( "Inserted enumValue with id "+ insertedEnumValue.getEnumValueId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.field.EnumValue.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedEnumValue);
    }

    @ApiOperation(value = "Update the enumValue given", notes = "Update the enumValue given ", response = it.anggen.model.field.EnumValue.class)
    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEnumValue(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.EnumValue enumValue) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating enumValue with id "+enumValue.getEnumValueId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.field.EnumValue.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(enumValue);
        it.anggen.model.field.EnumValue updatedEnumValue=enumValueService.update(enumValue);
        getSecurityMapping(updatedEnumValue);
        getRightMapping(updatedEnumValue);
        return ResponseEntity.ok().body(updatedEnumValue);
    }

    private List<it.anggen.model.field.EnumValue> getRightMapping(List<it.anggen.model.field.EnumValue> enumValueList) {
        for (it.anggen.model.field.EnumValue enumValue: enumValueList)
        {
        getRightMapping(enumValue);
        }
        return enumValueList;
    }

    private void getRightMapping(it.anggen.model.field.EnumValue enumValue) {
        if (enumValue.getEnumEntity()!=null)
        {
        enumValue.getEnumEntity().setProject(null);
        enumValue.getEnumEntity().setEnumValueList(null);
        enumValue.getEnumEntity().setEnumFieldList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.field.EnumValue enumValue) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        enumValue.setEnumEntity(enumValueService.findById(enumValue.getEnumValueId()).get(0).getEnumEntity());
    }

    private List<it.anggen.model.field.EnumValue> getSecurityMapping(List<it.anggen.model.field.EnumValue> enumValueList) {
        for (it.anggen.model.field.EnumValue enumValue: enumValueList)
        {
        getSecurityMapping(enumValue);
        }
        return enumValueList;
    }

    private void getSecurityMapping(it.anggen.model.field.EnumValue enumValue) {
        if (securityEnabled && enumValue.getEnumEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        enumValue.setEnumEntity(null);

    }

}
