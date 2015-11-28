
package it.polimi.controller;

import java.util.List;

import it.polimi.model.domain.EntityGroup;
import it.polimi.searchbean.EntityGroupSearchBean;
import it.polimi.service.EntityGroupService;

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
@RequestMapping("/entityGroup")
public class EntityGroupController {

    @Autowired
    public EntityGroupService entityGroupService;
    private final static Logger log = LoggerFactory.getLogger(EntityGroup.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "entityGroup";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EntityGroupSearchBean entityGroup) {
        List<EntityGroup> entityGroupList;
        if (entityGroup.getEntityGroupId()!=null)
         log.info("Searching entityGroup like {}",entityGroup.toString());
        entityGroupList=entityGroupService.find(entityGroup);
        getRightMapping(entityGroupList);
         log.info("Search: returning {} entityGroup.",entityGroupList.size());
        return ResponseEntity.ok().body(entityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityGroupId}", method = RequestMethod.GET)
    public ResponseEntity getentityGroupById(
        @PathVariable
        String entityGroupId) {
        log.info("Searching entityGroup with id {}",entityGroupId);
        List<EntityGroup> entityGroupList=entityGroupService.findById(java.lang.Long.valueOf(entityGroupId));
        getRightMapping(entityGroupList);
         log.info("Search: returning {} entityGroup.",entityGroupList.size());
        return ResponseEntity.ok().body(entityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityGroupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteentityGroupById(
        @PathVariable
        String entityGroupId) {
        log.info("Deleting entityGroup with id {}",entityGroupId);
        entityGroupService.deleteById(java.lang.Long.valueOf(entityGroupId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertentityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        EntityGroup entityGroup) {
        if (entityGroup.getEntityGroupId()!=null)
        log.info("Inserting entityGroup like {}",entityGroup.toString());
        EntityGroup insertedentityGroup=entityGroupService.insert(entityGroup);
        getRightMapping(insertedentityGroup);
        log.info("Inserted entityGroup with id {}",insertedentityGroup.getEntityGroupId());
        return ResponseEntity.ok().body(insertedentityGroup);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateentityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        EntityGroup entityGroup) {
        log.info("Updating entityGroup with id {}",entityGroup.getEntityGroupId());
        EntityGroup updatedentityGroup=entityGroupService.update(entityGroup);
        getRightMapping(updatedentityGroup);
        return ResponseEntity.ok().body(updatedentityGroup);
    }

    private List<EntityGroup> getRightMapping(List<EntityGroup> entityGroupList) {
        for (EntityGroup entityGroup: entityGroupList)
        {
        getRightMapping(entityGroup);
        }
        return entityGroupList;
    }

    private void getRightMapping(EntityGroup entityGroup) {
        if (entityGroup.getEntityList()!=null)
        for (it.polimi.model.domain.Entity entity :entityGroup.getEntityList())

        {

        entity.setFieldList(null);
        entity.setRelationshipList(null);
        entity.setEnumFieldList(null);
        entity.setTabList(null);
        entity.setRestrictionEntityList(null);
        entity.setEntityGroup(null);
        }
        if (entityGroup.getRestrictionEntityGroupList()!=null)
        for (it.polimi.model.domain.RestrictionEntityGroup restrictionEntityGroup :entityGroup.getRestrictionEntityGroupList())

        {

        restrictionEntityGroup.setEntityGroup(null);
        restrictionEntityGroup.setRole(null);
        }
    }

}
