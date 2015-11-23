
package it.polimi.controller;

import java.util.List;

import it.polimi.model.domain.Entity;
import it.polimi.searchbean.EntitySearchBean;
import it.polimi.service.EntityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/entity")
public class EntityController {

    @Autowired
    public EntityService entityService;
    private final static Logger log = LoggerFactory.getLogger(Entity.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "entity";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EntitySearchBean entity) {
        List<Entity> entityList;
        if (entity.getEntityId()!=null)
         log.info("Searching entity like {}", entity.getEntityId()+' '+ entity.getName());
        entityList=entityService.find(entity);
        getRightMapping(entityList);
         log.info("Search: returning {} entity.",entityList.size());
        return ResponseEntity.ok().body(entityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityId}", method = RequestMethod.GET)
    public ResponseEntity getentityById(
        @PathVariable
        String entityId) {
        log.info("Searching entity with id {}",entityId);
        List<Entity> entityList=entityService.findById(java.lang.Long.valueOf(entityId));
        getRightMapping(entityList);
         log.info("Search: returning {} entity.",entityList.size());
        return ResponseEntity.ok().body(entityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteentityById(
        @PathVariable
        String entityId) {
        log.info("Deleting entity with id {}",entityId);
        entityService.deleteById(java.lang.Long.valueOf(entityId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertentity(
        @org.springframework.web.bind.annotation.RequestBody
        Entity entity) {
        if (entity.getEntityId()!=null)
        log.info("Inserting entity like {}", entity.getEntityId()+' '+ entity.getName());
        Entity insertedentity=entityService.insert(entity);
        getRightMapping(insertedentity);
        log.info("Inserted entity with id {}",insertedentity.getEntityId());
        return ResponseEntity.ok().body(insertedentity);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateentity(
        @org.springframework.web.bind.annotation.RequestBody
        Entity entity) {
        log.info("Updating entity with id {}",entity.getEntityId());
        Entity updatedentity=entityService.update(entity);
        getRightMapping(updatedentity);
        return ResponseEntity.ok().body(updatedentity);
    }

    private List<Entity> getRightMapping(List<Entity> entityList) {
        for (Entity entity: entityList)
        {
        getRightMapping(entity);
        }
        return entityList;
    }

    private void getRightMapping(Entity entity) {
        if (entity.getFieldList()!=null)
        for (it.polimi.model.domain.Field field :entity.getFieldList())

        {

        field.setEntity(null);
        field.setAnnotationList(null);
        field.setTab(null);
        }
        if (entity.getRelationshipList()!=null)
        for (it.polimi.model.domain.Relationship relationship :entity.getRelationshipList())

        {

        relationship.setEntity(null);
        relationship.setEntityTarget(null);
        relationship.setAnnotationList(null);
        relationship.setTab(null);
        }
        if (entity.getEnumFieldList()!=null)
        for (it.polimi.model.domain.EnumField enumField :entity.getEnumFieldList())

        {

        enumField.setEnumValueList(null);
        enumField.setEntity(null);
        enumField.setAnnotationList(null);
        enumField.setTab(null);
        }
        if (entity.getTabList()!=null)
        for (it.polimi.model.domain.Tab tab :entity.getTabList())

        {

        tab.setEntity(null);
        tab.setFieldList(null);
        tab.setRelationshipList(null);
        tab.setEnumFieldList(null);
        }
        if (entity.getRestrictionList()!=null)
        for (it.polimi.model.domain.Restriction restriction :entity.getRestrictionList())

        {

        restriction.setRole(null);
        restriction.setEntity(null);
        }
    }

}
