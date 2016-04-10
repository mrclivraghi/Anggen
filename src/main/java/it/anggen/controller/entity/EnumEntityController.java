
package it.anggen.controller.entity;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.entity.EnumEntitySearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.entity.EnumEntityService;
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
@RequestMapping("/enumEntity")
public class EnumEntityController {

    @org.springframework.beans.factory.annotation.Autowired
    private EnumEntityService enumEntityService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.entity.EnumEntity.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "enumEntity";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.entity.EnumEntity> page = enumEntityService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EnumEntitySearchBean enumEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.entity.EnumEntity> enumEntityList;
        if (enumEntity.getEnumEntityId()!=null)
         log.info("Searching enumEntity like {}", enumEntity.getName()+' '+ enumEntity.getEnumEntityId());
        logEntryService.addLogEntry( "Searching entity like "+ enumEntity.getName()+' '+ enumEntity.getEnumEntityId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.entity.EnumEntity.staticEntityId, securityService.getLoggedUser(),log);
        enumEntityList=enumEntityService.find(enumEntity);
        getSecurityMapping(enumEntityList);
        getRightMapping(enumEntityList);
         log.info("Search: returning {} enumEntity.",enumEntityList.size());
        return ResponseEntity.ok().body(enumEntityList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{enumEntityId}", method = RequestMethod.GET)
    public ResponseEntity getEnumEntityById(
        @PathVariable
        String enumEntityId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching enumEntity with id "+enumEntityId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.entity.EnumEntity.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.entity.EnumEntity> enumEntityList=enumEntityService.findById(Long.valueOf(enumEntityId));
        getSecurityMapping(enumEntityList);
        getRightMapping(enumEntityList);
         log.info("Search: returning {} enumEntity.",enumEntityList.size());
        return ResponseEntity.ok().body(enumEntityList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{enumEntityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEnumEntityById(
        @PathVariable
        String enumEntityId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting enumEntity with id "+enumEntityId);
        logEntryService.addLogEntry( "Deleting enumEntity with id {}"+enumEntityId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.entity.EnumEntity.staticEntityId, securityService.getLoggedUser(),log);
        enumEntityService.deleteById(Long.valueOf(enumEntityId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEnumEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.EnumEntity enumEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (enumEntity.getEnumEntityId()!=null)
        log.info("Inserting enumEntity like "+ enumEntity.getName()+' '+ enumEntity.getEnumEntityId());
        it.anggen.model.entity.EnumEntity insertedEnumEntity=enumEntityService.insert(enumEntity);
        getRightMapping(insertedEnumEntity);
        logEntryService.addLogEntry( "Inserted enumEntity with id "+ insertedEnumEntity.getEnumEntityId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.entity.EnumEntity.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedEnumEntity);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEnumEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.EnumEntity enumEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating enumEntity with id "+enumEntity.getEnumEntityId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.entity.EnumEntity.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(enumEntity);
        it.anggen.model.entity.EnumEntity updatedEnumEntity=enumEntityService.update(enumEntity);
        getSecurityMapping(updatedEnumEntity);
        getRightMapping(updatedEnumEntity);
        return ResponseEntity.ok().body(updatedEnumEntity);
    }

    private List<it.anggen.model.entity.EnumEntity> getRightMapping(List<it.anggen.model.entity.EnumEntity> enumEntityList) {
        for (it.anggen.model.entity.EnumEntity enumEntity: enumEntityList)
        {
        getRightMapping(enumEntity);
        }
        return enumEntityList;
    }

    private void getRightMapping(it.anggen.model.entity.EnumEntity enumEntity) {
        if (enumEntity.getProject()!=null)
        {
        enumEntity.getProject().setEnumEntityList(null);
        enumEntity.getProject().setEntityGroupList(null);
        }
        if (enumEntity.getEnumValueList()!=null)
        for (it.anggen.model.field.EnumValue enumValue :enumEntity.getEnumValueList())

        {

        enumValue.setEnumEntity(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.entity.EnumEntity enumEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        enumEntity.setProject(enumEntityService.findById(enumEntity.getEnumEntityId()).get(0).getProject());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        enumEntity.setEnumValueList(enumEntityService.findById(enumEntity.getEnumEntityId()).get(0).getEnumValueList());
    }

    private List<it.anggen.model.entity.EnumEntity> getSecurityMapping(List<it.anggen.model.entity.EnumEntity> enumEntityList) {
        for (it.anggen.model.entity.EnumEntity enumEntity: enumEntityList)
        {
        getSecurityMapping(enumEntity);
        }
        return enumEntityList;
    }

    private void getSecurityMapping(it.anggen.model.entity.EnumEntity enumEntity) {
        if (securityEnabled && enumEntity.getProject()!=null  && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        enumEntity.setProject(null);

        if (securityEnabled && enumEntity.getEnumValueList()!=null && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        enumEntity.setEnumValueList(null);

    }

}
