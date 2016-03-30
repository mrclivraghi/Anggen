
package it.anggen.controller.entity;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.entity.EntitySearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.entity.EntityService;
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
@RequestMapping("/entity")
public class EntityController {

    @org.springframework.beans.factory.annotation.Autowired
    private EntityService entityService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.entity.Entity.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "entity";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.entity.Entity> page = entityService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EntitySearchBean entity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.entity.Entity> entityList;
        if (entity.getEntityId()!=null)
         log.info("Searching entity like {}", entity.getEntityId()+' '+ entity.getName());
        logEntryService.addLogEntry( "Searching entity like "+ entity.getEntityId()+' '+ entity.getName(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.entity.Entity.staticEntityId, securityService.getLoggedUser(),log);
        entityList=entityService.find(entity);
        getSecurityMapping(entityList);
        getRightMapping(entityList);
         log.info("Search: returning {} entity.",entityList.size());
        return ResponseEntity.ok().body(entityList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{entityId}", method = RequestMethod.GET)
    public ResponseEntity getEntityById(
        @PathVariable
        String entityId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching entity with id "+entityId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.entity.Entity.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.entity.Entity> entityList=entityService.findById(Long.valueOf(entityId));
        getSecurityMapping(entityList);
        getRightMapping(entityList);
         log.info("Search: returning {} entity.",entityList.size());
        return ResponseEntity.ok().body(entityList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{entityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityById(
        @PathVariable
        String entityId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting entity with id "+entityId);
        logEntryService.addLogEntry( "Deleting entity with id {}"+entityId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.entity.Entity.staticEntityId, securityService.getLoggedUser(),log);
        entityService.deleteById(Long.valueOf(entityId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.Entity entity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (entity.getEntityId()!=null)
        log.info("Inserting entity like "+ entity.getEntityId()+' '+ entity.getName());
        it.anggen.model.entity.Entity insertedEntity=entityService.insert(entity);
        getRightMapping(insertedEntity);
        logEntryService.addLogEntry( "Inserted entity with id "+ insertedEntity.getEntityId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.entity.Entity.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedEntity);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.Entity entity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating entity with id "+entity.getEntityId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.entity.Entity.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(entity);
        it.anggen.model.entity.Entity updatedEntity=entityService.update(entity);
        getSecurityMapping(updatedEntity);
        getRightMapping(updatedEntity);
        return ResponseEntity.ok().body(updatedEntity);
    }

    private List<it.anggen.model.entity.Entity> getRightMapping(List<it.anggen.model.entity.Entity> entityList) {
        for (it.anggen.model.entity.Entity entity: entityList)
        {
        getRightMapping(entity);
        }
        return entityList;
    }

    private void getRightMapping(it.anggen.model.entity.Entity entity) {
        if (entity.getEnumFieldList()!=null)
        for (it.anggen.model.field.EnumField enumField :entity.getEnumFieldList())

        {

        enumField.setAnnotationList(null);
        enumField.setTab(null);
        enumField.setEnumEntity(null);
        enumField.setEntity(null);
        }
        if (entity.getFieldList()!=null)
        for (it.anggen.model.field.Field field :entity.getFieldList())

        {

        field.setAnnotationList(null);
        field.setRestrictionFieldList(null);
        field.setTab(null);
        field.setEntity(null);
        }
        if (entity.getEntityGroup()!=null)
        {
        entity.getEntityGroup().setRestrictionEntityGroupList(null);
        entity.getEntityGroup().setProject(null);
        entity.getEntityGroup().setEntityList(null);
        }
        if (entity.getTabList()!=null)
        for (it.anggen.model.entity.Tab tab :entity.getTabList())

        {

        tab.setFieldList(null);
        tab.setEnumFieldList(null);
        tab.setRelationshipList(null);
        tab.setEntity(null);
        }
        if (entity.getRestrictionEntityList()!=null)
        for (it.anggen.model.security.RestrictionEntity restrictionEntity :entity.getRestrictionEntityList())

        {

        restrictionEntity.setEntity(null);
        restrictionEntity.setRole(null);
        }
        if (entity.getRelationshipList()!=null)
        for (it.anggen.model.relationship.Relationship relationship :entity.getRelationshipList())

        {

        relationship.setEntity(null);
        relationship.setEntityTarget(null);
        relationship.setTab(null);
        relationship.setAnnotationList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.entity.Entity entity) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entity.setEnumFieldList(entityService.findById(entity.getEntityId()).get(0).getEnumFieldList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entity.setFieldList(entityService.findById(entity.getEntityId()).get(0).getFieldList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entity.setEntityGroup(entityService.findById(entity.getEntityId()).get(0).getEntityGroup());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entity.setTabList(entityService.findById(entity.getEntityId()).get(0).getTabList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entity.setRestrictionEntityList(entityService.findById(entity.getEntityId()).get(0).getRestrictionEntityList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entity.setRelationshipList(entityService.findById(entity.getEntityId()).get(0).getRelationshipList());
    }

    private List<it.anggen.model.entity.Entity> getSecurityMapping(List<it.anggen.model.entity.Entity> entityList) {
        for (it.anggen.model.entity.Entity entity: entityList)
        {
        getSecurityMapping(entity);
        }
        return entityList;
    }

    private void getSecurityMapping(it.anggen.model.entity.Entity entity) {
        if (securityEnabled && entity.getEnumFieldList()!=null && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entity.setEnumFieldList(null);

        if (securityEnabled && entity.getFieldList()!=null && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entity.setFieldList(null);

        if (securityEnabled && entity.getEntityGroup()!=null  && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entity.setEntityGroup(null);

        if (securityEnabled && entity.getTabList()!=null && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entity.setTabList(null);

        if (securityEnabled && entity.getRestrictionEntityList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entity.setRestrictionEntityList(null);

        if (securityEnabled && entity.getRelationshipList()!=null && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entity.setRelationshipList(null);

    }

}
