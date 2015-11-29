
package it.polimi.controller;

import java.util.List;

import it.polimi.model.security.RestrictionField;
import it.polimi.searchbean.RestrictionFieldSearchBean;
import it.polimi.service.RestrictionFieldService;

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
@RequestMapping("/restrictionField")
public class RestrictionFieldController {

    @Autowired
    public RestrictionFieldService restrictionFieldService;
    private final static Logger log = LoggerFactory.getLogger(RestrictionField.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "restrictionField";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionFieldSearchBean restrictionField) {
        List<RestrictionField> restrictionFieldList;
        if (restrictionField.getRestrictionFieldId()!=null)
         log.info("Searching restrictionField like {}",restrictionField.toString());
        restrictionFieldList=restrictionFieldService.find(restrictionField);
        getRightMapping(restrictionFieldList);
         log.info("Search: returning {} restrictionField.",restrictionFieldList.size());
        return ResponseEntity.ok().body(restrictionFieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionFieldId}", method = RequestMethod.GET)
    public ResponseEntity getrestrictionFieldById(
        @PathVariable
        String restrictionFieldId) {
        log.info("Searching restrictionField with id {}",restrictionFieldId);
        List<RestrictionField> restrictionFieldList=restrictionFieldService.findById(java.lang.Long.valueOf(restrictionFieldId));
        getRightMapping(restrictionFieldList);
         log.info("Search: returning {} restrictionField.",restrictionFieldList.size());
        return ResponseEntity.ok().body(restrictionFieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionFieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleterestrictionFieldById(
        @PathVariable
        String restrictionFieldId) {
        log.info("Deleting restrictionField with id {}",restrictionFieldId);
        restrictionFieldService.deleteById(java.lang.Long.valueOf(restrictionFieldId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertrestrictionField(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionField restrictionField) {
        if (restrictionField.getRestrictionFieldId()!=null)
        log.info("Inserting restrictionField like {}",restrictionField.toString());
        RestrictionField insertedrestrictionField=restrictionFieldService.insert(restrictionField);
        getRightMapping(insertedrestrictionField);
        log.info("Inserted restrictionField with id {}",insertedrestrictionField.getRestrictionFieldId());
        return ResponseEntity.ok().body(insertedrestrictionField);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updaterestrictionField(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionField restrictionField) {
        log.info("Updating restrictionField with id {}",restrictionField.getRestrictionFieldId());
        RestrictionField updatedrestrictionField=restrictionFieldService.update(restrictionField);
        getRightMapping(updatedrestrictionField);
        return ResponseEntity.ok().body(updatedrestrictionField);
    }

    private List<RestrictionField> getRightMapping(List<RestrictionField> restrictionFieldList) {
        for (RestrictionField restrictionField: restrictionFieldList)
        {
        getRightMapping(restrictionField);
        }
        return restrictionFieldList;
    }

    private void getRightMapping(RestrictionField restrictionField) {
        if (restrictionField.getField()!=null)
        {
        restrictionField.getField().setEntity(null);
        restrictionField.getField().setAnnotationList(null);
        restrictionField.getField().setRestrictionFieldList(null);
        restrictionField.getField().setTab(null);
        }
        if (restrictionField.getRole()!=null)
        {
        restrictionField.getRole().setUserList(null);
        restrictionField.getRole().setRestrictionEntityList(null);
        restrictionField.getRole().setRestrictionFieldList(null);
        restrictionField.getRole().setRestrictionEntityGroupList(null);
        }
    }

}
