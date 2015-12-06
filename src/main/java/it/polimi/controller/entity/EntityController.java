
package it.polimi.controller.entity;

import java.util.List;

import it.polimi.model.RestrictionType;
import it.polimi.searchbean.entity.EntitySearchBean;
import it.polimi.security.SecurityService;
import it.polimi.service.entity.EntityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(it.polimi.model.entity.Entity.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.SEARCH)) 
return "forbidden"; 

        return "entity";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EntitySearchBean entity) {
        if (!securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.polimi.model.entity.Entity> entityList;
        if (entity.getEntityId()!=null)
         log.info("Searching entity like {}", entity.getEntityId()+' '+ entity.getName());
        entityList=entityService.find(entity);
        getRightMapping(entityList);
        getSecurityMapping(entityList);
         log.info("Search: returning {} entity.",entityList.size());
        return ResponseEntity.ok().body(entityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityId}", method = RequestMethod.GET)
    public ResponseEntity getEntityById(
        @PathVariable
        String entityId) {
        if (!securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching entity with id {}",entityId);
        List<it.polimi.model.entity.Entity> entityList=entityService.findById(Long.valueOf(entityId));
        getRightMapping(entityList);
         log.info("Search: returning {} entity.",entityList.size());
        return ResponseEntity.ok().body(entityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityById(
        @PathVariable
        String entityId) {
        if (!securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting entity with id {}",entityId);
        entityService.deleteById(Long.valueOf(entityId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.entity.Entity entity) {
        if (!securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (entity.getEntityId()!=null)
        log.info("Inserting entity like {}", entity.getEntityId()+' '+ entity.getName());
        it.polimi.model.entity.Entity insertedEntity=entityService.insert(entity);
        getRightMapping(insertedEntity);
        log.info("Inserted entity with id {}",insertedEntity.getEntityId());
        return ResponseEntity.ok().body(insertedEntity);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.entity.Entity entity) {
        if (!securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating entity with id {}",entity.getEntityId());
        rebuildSecurityMapping(entity);
        it.polimi.model.entity.Entity updatedEntity=entityService.update(entity);
        getRightMapping(updatedEntity);
        getSecurityMapping(updatedEntity);
        return ResponseEntity.ok().body(updatedEntity);
    }

    private List<it.polimi.model.entity.Entity> getRightMapping(List<it.polimi.model.entity.Entity> entityList) {
        for (it.polimi.model.entity.Entity entity: entityList)
        {
        getRightMapping(entity);
        }
        return entityList;
    }

    private void getRightMapping(it.polimi.model.entity.Entity entity) {
        if (entity.getRelationshipList()!=null)
        for (it.polimi.model.relationship.Relationship relationship :entity.getRelationshipList())

        {

        relationship.setEntity(null);
        relationship.setEntityTarget(null);
        relationship.setAnnotationList(null);
        relationship.setTab(null);
        }
        if (entity.getEnumFieldList()!=null)
        for (it.polimi.model.field.EnumField enumField :entity.getEnumFieldList())

        {

        enumField.setEnumValueList(null);
        enumField.setEntity(null);
        enumField.setAnnotationList(null);
        enumField.setTab(null);
        }
        if (entity.getTabList()!=null)
        for (it.polimi.model.entity.Tab tab :entity.getTabList())

        {

        tab.setEntity(null);
        tab.setFieldList(null);
        tab.setRelationshipList(null);
        tab.setEnumFieldList(null);
        }
        if (entity.getRestrictionEntityList()!=null)
        for (it.polimi.model.security.RestrictionEntity restrictionEntity :entity.getRestrictionEntityList())

        {

        restrictionEntity.setRole(null);
        restrictionEntity.setEntity(null);
        }
        if (entity.getEntityGroup()!=null)
        {
        entity.getEntityGroup().setEntityList(null);
        entity.getEntityGroup().setRestrictionEntityGroupList(null);
        entity.getEntityGroup().setProject(null);
        }
        if (entity.getFieldList()!=null)
        for (it.polimi.model.field.Field field :entity.getFieldList())

        {

        field.setEntity(null);
        field.setAnnotationList(null);
        field.setRestrictionFieldList(null);
        field.setTab(null);
        }
    }

    private void rebuildSecurityMapping(it.polimi.model.entity.Entity entity) {
        if (!securityService.hasPermission(it.polimi.model.relationship.Relationship.staticEntityId, RestrictionType.SEARCH))
        entity.setRelationshipList(entityService.findById(entity.getEntityId()).get(0).getRelationshipList());
        if (!securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH))
        entity.setEnumFieldList(entityService.findById(entity.getEntityId()).get(0).getEnumFieldList());
        if (!securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.SEARCH))
        entity.setTabList(entityService.findById(entity.getEntityId()).get(0).getTabList());
        if (!securityService.hasPermission(it.polimi.model.security.RestrictionEntity.staticEntityId, RestrictionType.SEARCH))
        entity.setRestrictionEntityList(entityService.findById(entity.getEntityId()).get(0).getRestrictionEntityList());
        if (!securityService.hasPermission(it.polimi.model.entity.EntityGroup.staticEntityId, RestrictionType.SEARCH))
        entity.setEntityGroup(entityService.findById(entity.getEntityId()).get(0).getEntityGroup());
        if (!securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.SEARCH))
        entity.setFieldList(entityService.findById(entity.getEntityId()).get(0).getFieldList());
    }

    private List<it.polimi.model.entity.Entity> getSecurityMapping(List<it.polimi.model.entity.Entity> entityList) {
        for (it.polimi.model.entity.Entity entity: entityList)
        {
        getSecurityMapping(entity);
        }
        return entityList;
    }

    private void getSecurityMapping(it.polimi.model.entity.Entity entity) {
        if (entity.getRelationshipList()!=null && !securityService.hasPermission(it.polimi.model.relationship.Relationship.staticEntityId, RestrictionType.SEARCH) )
        entity.setRelationshipList(null);

        if (entity.getEnumFieldList()!=null && !securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH) )
        entity.setEnumFieldList(null);

        if (entity.getTabList()!=null && !securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.SEARCH) )
        entity.setTabList(null);

        if (entity.getRestrictionEntityList()!=null && !securityService.hasPermission(it.polimi.model.security.RestrictionEntity.staticEntityId, RestrictionType.SEARCH) )
        entity.setRestrictionEntityList(null);

        if (entity.getEntityGroup()!=null  && !securityService.hasPermission(it.polimi.model.entity.EntityGroup.staticEntityId, RestrictionType.SEARCH) )
        entity.setEntityGroup(null);

        if (entity.getFieldList()!=null && !securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.SEARCH) )
        entity.setFieldList(null);

    }

}
