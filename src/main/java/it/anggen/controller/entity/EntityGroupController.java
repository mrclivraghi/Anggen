
package it.anggen.controller.entity;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.entity.EntityGroupSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.entity.EntityGroupService;
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
@RequestMapping("/entityGroup")
public class EntityGroupController {

    @org.springframework.beans.factory.annotation.Autowired
    private EntityGroupService entityGroupService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.entity.EntityGroup.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "entityGroup";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.entity.EntityGroup> page = entityGroupService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EntityGroupSearchBean entityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.entity.EntityGroup> entityGroupList;
        if (entityGroup.getEntityGroupId()!=null)
         log.info("Searching entityGroup like {}", entityGroup.getName()+' '+ entityGroup.getEntityGroupId());
        logEntryService.addLogEntry( "Searching entity like "+ entityGroup.getName()+' '+ entityGroup.getEntityGroupId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.entity.EntityGroup.staticEntityId, securityService.getLoggedUser(),log);
        entityGroupList=entityGroupService.find(entityGroup);
        getSecurityMapping(entityGroupList);
        getRightMapping(entityGroupList);
         log.info("Search: returning {} entityGroup.",entityGroupList.size());
        return ResponseEntity.ok().body(entityGroupList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{entityGroupId}", method = RequestMethod.GET)
    public ResponseEntity getEntityGroupById(
        @PathVariable
        String entityGroupId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching entityGroup with id "+entityGroupId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.entity.EntityGroup.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.entity.EntityGroup> entityGroupList=entityGroupService.findById(Long.valueOf(entityGroupId));
        getSecurityMapping(entityGroupList);
        getRightMapping(entityGroupList);
         log.info("Search: returning {} entityGroup.",entityGroupList.size());
        return ResponseEntity.ok().body(entityGroupList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{entityGroupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityGroupById(
        @PathVariable
        String entityGroupId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting entityGroup with id "+entityGroupId);
        logEntryService.addLogEntry( "Deleting entityGroup with id {}"+entityGroupId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.entity.EntityGroup.staticEntityId, securityService.getLoggedUser(),log);
        entityGroupService.deleteById(Long.valueOf(entityGroupId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.EntityGroup entityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (entityGroup.getEntityGroupId()!=null)
        log.info("Inserting entityGroup like "+ entityGroup.getName()+' '+ entityGroup.getEntityGroupId());
        it.anggen.model.entity.EntityGroup insertedEntityGroup=entityGroupService.insert(entityGroup);
        getRightMapping(insertedEntityGroup);
        logEntryService.addLogEntry( "Inserted entityGroup with id "+ insertedEntityGroup.getEntityGroupId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.entity.EntityGroup.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedEntityGroup);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.EntityGroup entityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating entityGroup with id "+entityGroup.getEntityGroupId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.entity.EntityGroup.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(entityGroup);
        it.anggen.model.entity.EntityGroup updatedEntityGroup=entityGroupService.update(entityGroup);
        getSecurityMapping(updatedEntityGroup);
        getRightMapping(updatedEntityGroup);
        return ResponseEntity.ok().body(updatedEntityGroup);
    }

    private List<it.anggen.model.entity.EntityGroup> getRightMapping(List<it.anggen.model.entity.EntityGroup> entityGroupList) {
        for (it.anggen.model.entity.EntityGroup entityGroup: entityGroupList)
        {
        getRightMapping(entityGroup);
        }
        return entityGroupList;
    }

    private void getRightMapping(it.anggen.model.entity.EntityGroup entityGroup) {
        if (entityGroup.getRestrictionEntityGroupList()!=null)
        for (it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup :entityGroup.getRestrictionEntityGroupList())

        {

        restrictionEntityGroup.setRole(null);
        restrictionEntityGroup.setEntityGroup(null);
        }
        if (entityGroup.getEntityList()!=null)
        for (it.anggen.model.entity.Entity entity :entityGroup.getEntityList())

        {

        entity.setFieldList(null);
        entity.setEnumFieldList(null);
        entity.setTabList(null);
        entity.setEntityGroup(null);
        entity.setRestrictionEntityList(null);
        entity.setRelationshipList(null);
        }
        if (entityGroup.getProject()!=null)
        {
        entityGroup.getProject().setEntityGroupList(null);
        entityGroup.getProject().setEnumEntityList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.entity.EntityGroup entityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entityGroup.setRestrictionEntityGroupList(entityGroupService.findById(entityGroup.getEntityGroupId()).get(0).getRestrictionEntityGroupList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entityGroup.setEntityList(entityGroupService.findById(entityGroup.getEntityGroupId()).get(0).getEntityList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entityGroup.setProject(entityGroupService.findById(entityGroup.getEntityGroupId()).get(0).getProject());
    }

    private List<it.anggen.model.entity.EntityGroup> getSecurityMapping(List<it.anggen.model.entity.EntityGroup> entityGroupList) {
        for (it.anggen.model.entity.EntityGroup entityGroup: entityGroupList)
        {
        getSecurityMapping(entityGroup);
        }
        return entityGroupList;
    }

    private void getSecurityMapping(it.anggen.model.entity.EntityGroup entityGroup) {
        if (securityEnabled && entityGroup.getRestrictionEntityGroupList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entityGroup.setRestrictionEntityGroupList(null);

        if (securityEnabled && entityGroup.getEntityList()!=null && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entityGroup.setEntityList(null);

        if (securityEnabled && entityGroup.getProject()!=null  && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entityGroup.setProject(null);

    }

}
