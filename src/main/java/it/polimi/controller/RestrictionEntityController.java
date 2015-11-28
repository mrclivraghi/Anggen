
package it.polimi.controller;

import java.util.List;

import it.polimi.model.domain.RestrictionEntity;
import it.polimi.searchbean.RestrictionEntitySearchBean;
import it.polimi.service.RestrictionEntityService;

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
@RequestMapping("/restrictionEntity")
public class RestrictionEntityController {

    @Autowired
    public RestrictionEntityService restrictionEntityService;
    private final static Logger log = LoggerFactory.getLogger(RestrictionEntity.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "restrictionEntity";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntitySearchBean restrictionEntity) {
        List<RestrictionEntity> restrictionEntityList;
        if (restrictionEntity.getRestrictionEntityId()!=null)
         log.info("Searching restrictionEntity like {}",restrictionEntity.toString());
        restrictionEntityList=restrictionEntityService.find(restrictionEntity);
        getRightMapping(restrictionEntityList);
         log.info("Search: returning {} restrictionEntity.",restrictionEntityList.size());
        return ResponseEntity.ok().body(restrictionEntityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityId}", method = RequestMethod.GET)
    public ResponseEntity getrestrictionEntityById(
        @PathVariable
        String restrictionEntityId) {
        log.info("Searching restrictionEntity with id {}",restrictionEntityId);
        List<RestrictionEntity> restrictionEntityList=restrictionEntityService.findById(java.lang.Long.valueOf(restrictionEntityId));
        getRightMapping(restrictionEntityList);
         log.info("Search: returning {} restrictionEntity.",restrictionEntityList.size());
        return ResponseEntity.ok().body(restrictionEntityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleterestrictionEntityById(
        @PathVariable
        String restrictionEntityId) {
        log.info("Deleting restrictionEntity with id {}",restrictionEntityId);
        restrictionEntityService.deleteById(java.lang.Long.valueOf(restrictionEntityId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertrestrictionEntity(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntity restrictionEntity) {
        if (restrictionEntity.getRestrictionEntityId()!=null)
        log.info("Inserting restrictionEntity like {}",restrictionEntity.toString());
        RestrictionEntity insertedrestrictionEntity=restrictionEntityService.insert(restrictionEntity);
        getRightMapping(insertedrestrictionEntity);
        log.info("Inserted restrictionEntity with id {}",insertedrestrictionEntity.getRestrictionEntityId());
        return ResponseEntity.ok().body(insertedrestrictionEntity);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updaterestrictionEntity(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntity restrictionEntity) {
        log.info("Updating restrictionEntity with id {}",restrictionEntity.getRestrictionEntityId());
        RestrictionEntity updatedrestrictionEntity=restrictionEntityService.update(restrictionEntity);
        getRightMapping(updatedrestrictionEntity);
        return ResponseEntity.ok().body(updatedrestrictionEntity);
    }

    private List<RestrictionEntity> getRightMapping(List<RestrictionEntity> restrictionEntityList) {
        for (RestrictionEntity restrictionEntity: restrictionEntityList)
        {
        getRightMapping(restrictionEntity);
        }
        return restrictionEntityList;
    }

    private void getRightMapping(RestrictionEntity restrictionEntity) {
        if (restrictionEntity.getRole()!=null)
        {
        restrictionEntity.getRole().setUserList(null);
        restrictionEntity.getRole().setRestrictionEntityList(null);
        restrictionEntity.getRole().setRestrictionFieldList(null);
        restrictionEntity.getRole().setRestrictionEntityGroupList(null);
        }
        if (restrictionEntity.getEntity()!=null)
        {
        restrictionEntity.getEntity().setFieldList(null);
        restrictionEntity.getEntity().setRelationshipList(null);
        restrictionEntity.getEntity().setEnumFieldList(null);
        restrictionEntity.getEntity().setTabList(null);
        restrictionEntity.getEntity().setRestrictionEntityList(null);
        restrictionEntity.getEntity().setEntityGroup(null);
        }
    }

}
