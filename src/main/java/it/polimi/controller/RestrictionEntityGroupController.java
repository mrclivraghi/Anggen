
package it.polimi.controller;

import java.util.List;

import it.polimi.model.domain.RestrictionEntityGroup;
import it.polimi.searchbean.RestrictionEntityGroupSearchBean;
import it.polimi.service.RestrictionEntityGroupService;

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
@RequestMapping("/restrictionEntityGroup")
public class RestrictionEntityGroupController {

    @Autowired
    public RestrictionEntityGroupService restrictionEntityGroupService;
    private final static Logger log = LoggerFactory.getLogger(RestrictionEntityGroup.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "restrictionEntityGroup";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntityGroupSearchBean restrictionEntityGroup) {
        List<RestrictionEntityGroup> restrictionEntityGroupList;
        if (restrictionEntityGroup.getRestrictionEntityGroupId()!=null)
         log.info("Searching restrictionEntityGroup like {}",restrictionEntityGroup.toString());
        restrictionEntityGroupList=restrictionEntityGroupService.find(restrictionEntityGroup);
        getRightMapping(restrictionEntityGroupList);
         log.info("Search: returning {} restrictionEntityGroup.",restrictionEntityGroupList.size());
        return ResponseEntity.ok().body(restrictionEntityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityGroupId}", method = RequestMethod.GET)
    public ResponseEntity getrestrictionEntityGroupById(
        @PathVariable
        String restrictionEntityGroupId) {
        log.info("Searching restrictionEntityGroup with id {}",restrictionEntityGroupId);
        List<RestrictionEntityGroup> restrictionEntityGroupList=restrictionEntityGroupService.findById(java.lang.Long.valueOf(restrictionEntityGroupId));
        getRightMapping(restrictionEntityGroupList);
         log.info("Search: returning {} restrictionEntityGroup.",restrictionEntityGroupList.size());
        return ResponseEntity.ok().body(restrictionEntityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityGroupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleterestrictionEntityGroupById(
        @PathVariable
        String restrictionEntityGroupId) {
        log.info("Deleting restrictionEntityGroup with id {}",restrictionEntityGroupId);
        restrictionEntityGroupService.deleteById(java.lang.Long.valueOf(restrictionEntityGroupId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertrestrictionEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntityGroup restrictionEntityGroup) {
        if (restrictionEntityGroup.getRestrictionEntityGroupId()!=null)
        log.info("Inserting restrictionEntityGroup like {}",restrictionEntityGroup.toString());
        RestrictionEntityGroup insertedrestrictionEntityGroup=restrictionEntityGroupService.insert(restrictionEntityGroup);
        getRightMapping(insertedrestrictionEntityGroup);
        log.info("Inserted restrictionEntityGroup with id {}",insertedrestrictionEntityGroup.getRestrictionEntityGroupId());
        return ResponseEntity.ok().body(insertedrestrictionEntityGroup);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updaterestrictionEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntityGroup restrictionEntityGroup) {
        log.info("Updating restrictionEntityGroup with id {}",restrictionEntityGroup.getRestrictionEntityGroupId());
        RestrictionEntityGroup updatedrestrictionEntityGroup=restrictionEntityGroupService.update(restrictionEntityGroup);
        getRightMapping(updatedrestrictionEntityGroup);
        return ResponseEntity.ok().body(updatedrestrictionEntityGroup);
    }

    private List<RestrictionEntityGroup> getRightMapping(List<RestrictionEntityGroup> restrictionEntityGroupList) {
        for (RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
        {
        getRightMapping(restrictionEntityGroup);
        }
        return restrictionEntityGroupList;
    }

    private void getRightMapping(RestrictionEntityGroup restrictionEntityGroup) {
        if (restrictionEntityGroup.getEntityGroup()!=null)
        {
        restrictionEntityGroup.getEntityGroup().setEntityList(null);
        restrictionEntityGroup.getEntityGroup().setRestrictionEntityGroupList(null);
        }
        if (restrictionEntityGroup.getRole()!=null)
        {
        restrictionEntityGroup.getRole().setUserList(null);
        restrictionEntityGroup.getRole().setRestrictionEntityList(null);
        restrictionEntityGroup.getRole().setRestrictionFieldList(null);
        restrictionEntityGroup.getRole().setRestrictionEntityGroupList(null);
        }
    }

}
