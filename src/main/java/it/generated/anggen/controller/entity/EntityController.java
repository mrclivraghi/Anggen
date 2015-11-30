
package it.generated.anggen.controller.entity;

import java.util.List;
import it.generated.anggen.searchbean.entity.EntitySearchBean;
import it.generated.anggen.security.SecurityService;
import it.generated.anggen.service.entity.EntityService;
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
    private final static Logger log = LoggerFactory.getLogger(it.generated.anggen.model.entity.Entity.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "entity";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EntitySearchBean entity) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.generated.anggen.model.entity.Entity> entityList;
        if (entity.getEntityId()!=null)
         log.info("Searching entity like {}", entity.getName()+' '+ entity.getEntityId());
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
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching entity with id {}",entityId);
        List<it.generated.anggen.model.entity.Entity> entityList=entityService.findById(Long.valueOf(entityId));
        getRightMapping(entityList);
         log.info("Search: returning {} entity.",entityList.size());
        return ResponseEntity.ok().body(entityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityById(
        @PathVariable
        String entityId) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting entity with id {}",entityId);
        entityService.deleteById(Long.valueOf(entityId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.entity.Entity entity) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (entity.getEntityId()!=null)
        log.info("Inserting entity like {}", entity.getName()+' '+ entity.getEntityId());
        it.generated.anggen.model.entity.Entity insertedEntity=entityService.insert(entity);
        getRightMapping(insertedEntity);
        log.info("Inserted entity with id {}",insertedEntity.getEntityId());
        return ResponseEntity.ok().body(insertedEntity);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEntity(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.entity.Entity entity) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating entity with id {}",entity.getEntityId());
        rebuildSecurityMapping(entity);
        it.generated.anggen.model.entity.Entity updatedEntity=entityService.update(entity);
        getRightMapping(updatedEntity);
        getSecurityMapping(updatedEntity);
        return ResponseEntity.ok().body(updatedEntity);
    }

    private List<it.generated.anggen.model.entity.Entity> getRightMapping(List<it.generated.anggen.model.entity.Entity> entityList) {
        for (it.generated.anggen.model.entity.Entity entity: entityList)
        {
        getRightMapping(entity);
        }
        return entityList;
    }

    private void getRightMapping(it.generated.anggen.model.entity.Entity entity) {
        if (entity.getEntityGroup()!=null)
        {
        entity.getEntityGroup().setProject(null);
        entity.getEntityGroup().setRestrictionEntityGroupList(null);
        entity.getEntityGroup().setEntityList(null);
        }
        if (entity.getRestrictionEntityList()!=null)
        for (it.generated.anggen.model.security.RestrictionEntity restrictionEntity :entity.getRestrictionEntityList())

        {

        restrictionEntity.setEntity(null);
        restrictionEntity.setRole(null);
        }
        if (entity.getTabList()!=null)
        for (it.generated.anggen.model.entity.Tab tab :entity.getTabList())

        {

        tab.setEnumFieldList(null);
        tab.setRelationshipList(null);
        tab.setFieldList(null);
        tab.setEntity(null);
        }
        if (entity.getEnumFieldList()!=null)
        for (it.generated.anggen.model.field.EnumField enumField :entity.getEnumFieldList())

        {

        enumField.setTab(null);
        enumField.setAnnotationList(null);
        enumField.setEntity(null);
        enumField.setEnumValueList(null);
        }
        if (entity.getRelationshipList()!=null)
        for (it.generated.anggen.model.relationship.Relationship relationship :entity.getRelationshipList())

        {

        relationship.setTab(null);
        relationship.setAnnotationList(null);
        relationship.setEntity(null);
        relationship.setEntity(null);
        }
        if (entity.getFieldList()!=null)
        for (it.generated.anggen.model.field.Field field :entity.getFieldList())

        {

        field.setTab(null);
        field.setRestrictionFieldList(null);
        field.setAnnotationList(null);
        field.setEntity(null);
        }
    }

    private void rebuildSecurityMapping(it.generated.anggen.model.entity.Entity entity) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.EntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        entity.setEntityGroup(entityService.findById(entity.getEntityId()).get(0).getEntityGroup());
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        entity.setRestrictionEntityList(entityService.findById(entity.getEntityId()).get(0).getRestrictionEntityList());
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Tab.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        entity.setTabList(entityService.findById(entity.getEntityId()).get(0).getTabList());
        if (!securityService.isAllowed(it.generated.anggen.model.field.EnumField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        entity.setEnumFieldList(entityService.findById(entity.getEntityId()).get(0).getEnumFieldList());
        if (!securityService.isAllowed(it.generated.anggen.model.relationship.Relationship.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        entity.setRelationshipList(entityService.findById(entity.getEntityId()).get(0).getRelationshipList());
        if (!securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        entity.setFieldList(entityService.findById(entity.getEntityId()).get(0).getFieldList());
    }

    private List<it.generated.anggen.model.entity.Entity> getSecurityMapping(List<it.generated.anggen.model.entity.Entity> entityList) {
        for (it.generated.anggen.model.entity.Entity entity: entityList)
        {
        getSecurityMapping(entity);
        }
        return entityList;
    }

    private void getSecurityMapping(it.generated.anggen.model.entity.Entity entity) {
        if (entity.getEntityGroup()!=null  && !securityService.isAllowed(it.generated.anggen.model.entity.EntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        entity.setEntityGroup(null);

        if (entity.getRestrictionEntityList()!=null && !securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        entity.setRestrictionEntityList(null);

        if (entity.getTabList()!=null && !securityService.isAllowed(it.generated.anggen.model.entity.Tab.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        entity.setTabList(null);

        if (entity.getEnumFieldList()!=null && !securityService.isAllowed(it.generated.anggen.model.field.EnumField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        entity.setEnumFieldList(null);

        if (entity.getRelationshipList()!=null && !securityService.isAllowed(it.generated.anggen.model.relationship.Relationship.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        entity.setRelationshipList(null);

        if (entity.getFieldList()!=null && !securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        entity.setFieldList(null);

    }

}
