
package it.anggen.controller.security;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.security.RestrictionEntitySearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.log.LogEntryService;
import it.anggen.service.security.RestrictionEntityService;
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
@RequestMapping("/restrictionEntity")
public class RestrictionEntityController {

    @org.springframework.beans.factory.annotation.Autowired
    private RestrictionEntityService restrictionEntityService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.security.RestrictionEntity.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "restrictionEntity";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.security.RestrictionEntity> page = restrictionEntityService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntitySearchBean restrictionEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.security.RestrictionEntity> restrictionEntityList;
        if (restrictionEntity.getRestrictionEntityId()!=null)
         log.info("Searching restrictionEntity like {}", restrictionEntity.getRestrictionEntityId()+' '+ (restrictionEntity.getEntity()!=null ? restrictionEntity.getEntity().getEntityId().toString() : "") );
        logEntryService.addLogEntry( "Searching entity like "+ restrictionEntity.getRestrictionEntityId()+' '+ (restrictionEntity.getEntity()!=null ? restrictionEntity.getEntity().getEntityId().toString() : "") ,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.security.RestrictionEntity.staticEntityId, securityService.getLoggedUser(),log);
        restrictionEntityList=restrictionEntityService.find(restrictionEntity);
        getSecurityMapping(restrictionEntityList);
        getRightMapping(restrictionEntityList);
         log.info("Search: returning {} restrictionEntity.",restrictionEntityList.size());
        return ResponseEntity.ok().body(restrictionEntityList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityId}", method = RequestMethod.GET)
    public ResponseEntity getRestrictionEntityById(
        @PathVariable
        String restrictionEntityId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching restrictionEntity with id "+restrictionEntityId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.security.RestrictionEntity.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.security.RestrictionEntity> restrictionEntityList=restrictionEntityService.findById(Long.valueOf(restrictionEntityId));
        getSecurityMapping(restrictionEntityList);
        getRightMapping(restrictionEntityList);
         log.info("Search: returning {} restrictionEntity.",restrictionEntityList.size());
        return ResponseEntity.ok().body(restrictionEntityList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRestrictionEntityById(
        @PathVariable
        String restrictionEntityId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting restrictionEntity with id "+restrictionEntityId);
        logEntryService.addLogEntry( "Deleting restrictionEntity with id {}"+restrictionEntityId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.security.RestrictionEntity.staticEntityId, securityService.getLoggedUser(),log);
        restrictionEntityService.deleteById(Long.valueOf(restrictionEntityId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRestrictionEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (restrictionEntity.getRestrictionEntityId()!=null)
        log.info("Inserting restrictionEntity like "+ restrictionEntity.getRestrictionEntityId()+' '+ (restrictionEntity.getEntity()!=null ? restrictionEntity.getEntity().getEntityId().toString() : "") );
        it.anggen.model.security.RestrictionEntity insertedRestrictionEntity=restrictionEntityService.insert(restrictionEntity);
        getRightMapping(insertedRestrictionEntity);
        logEntryService.addLogEntry( "Inserted restrictionEntity with id "+ insertedRestrictionEntity.getRestrictionEntityId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.security.RestrictionEntity.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedRestrictionEntity);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRestrictionEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating restrictionEntity with id "+restrictionEntity.getRestrictionEntityId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.security.RestrictionEntity.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(restrictionEntity);
        it.anggen.model.security.RestrictionEntity updatedRestrictionEntity=restrictionEntityService.update(restrictionEntity);
        getSecurityMapping(updatedRestrictionEntity);
        getRightMapping(updatedRestrictionEntity);
        return ResponseEntity.ok().body(updatedRestrictionEntity);
    }

    private List<it.anggen.model.security.RestrictionEntity> getRightMapping(List<it.anggen.model.security.RestrictionEntity> restrictionEntityList) {
        for (it.anggen.model.security.RestrictionEntity restrictionEntity: restrictionEntityList)
        {
        getRightMapping(restrictionEntity);
        }
        return restrictionEntityList;
    }

    private void getRightMapping(it.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (restrictionEntity.getRole()!=null)
        {
        restrictionEntity.getRole().setRestrictionEntityList(null);
        restrictionEntity.getRole().setRestrictionFieldList(null);
        restrictionEntity.getRole().setUserList(null);
        restrictionEntity.getRole().setRestrictionEntityGroupList(null);
        }
        if (restrictionEntity.getEntity()!=null)
        {
        restrictionEntity.getEntity().setRestrictionEntityList(null);
        restrictionEntity.getEntity().setTabList(null);
        restrictionEntity.getEntity().setEntityGroup(null);
        restrictionEntity.getEntity().setEnumFieldList(null);
        restrictionEntity.getEntity().setFieldList(null);
        restrictionEntity.getEntity().setRelationshipList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        restrictionEntity.setRole(restrictionEntityService.findById(restrictionEntity.getRestrictionEntityId()).get(0).getRole());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        restrictionEntity.setEntity(restrictionEntityService.findById(restrictionEntity.getRestrictionEntityId()).get(0).getEntity());
    }

    private List<it.anggen.model.security.RestrictionEntity> getSecurityMapping(List<it.anggen.model.security.RestrictionEntity> restrictionEntityList) {
        for (it.anggen.model.security.RestrictionEntity restrictionEntity: restrictionEntityList)
        {
        getSecurityMapping(restrictionEntity);
        }
        return restrictionEntityList;
    }

    private void getSecurityMapping(it.anggen.model.security.RestrictionEntity restrictionEntity) {
        if (securityEnabled && restrictionEntity.getRole()!=null  && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        restrictionEntity.setRole(null);

        if (securityEnabled && restrictionEntity.getEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        restrictionEntity.setEntity(null);

    }

}
