
package it.polimi.controller.domain;

import java.util.List;
import it.polimi.model.domain.Restriction;
import it.polimi.searchbean.domain.RestrictionSearchBean;
import it.polimi.service.domain.RestrictionService;
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
@RequestMapping("/restriction")
public class RestrictionController {

    @Autowired
    public RestrictionService restrictionService;
    private final static Logger log = LoggerFactory.getLogger(Restriction.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "restriction";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionSearchBean restriction) {
        List<Restriction> restrictionList;
        if (restriction.getRestrictionId()!=null)
         log.info("Searching restriction like {}",restriction.toString());
        restrictionList=restrictionService.find(restriction);
        getRightMapping(restrictionList);
         log.info("Search: returning {} restriction.",restrictionList.size());
        return ResponseEntity.ok().body(restrictionList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionId}", method = RequestMethod.GET)
    public ResponseEntity getrestrictionById(
        @PathVariable
        String restrictionId) {
        log.info("Searching restriction with id {}",restrictionId);
        List<Restriction> restrictionList=restrictionService.findById(java.lang.Long.valueOf(restrictionId));
        getRightMapping(restrictionList);
         log.info("Search: returning {} restriction.",restrictionList.size());
        return ResponseEntity.ok().body(restrictionList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionId}", method = RequestMethod.DELETE)
    public ResponseEntity deleterestrictionById(
        @PathVariable
        String restrictionId) {
        log.info("Deleting restriction with id {}",restrictionId);
        restrictionService.deleteById(java.lang.Long.valueOf(restrictionId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertrestriction(
        @org.springframework.web.bind.annotation.RequestBody
        Restriction restriction) {
        if (restriction.getRestrictionId()!=null)
        log.info("Inserting restriction like {}",restriction.toString());
        Restriction insertedrestriction=restrictionService.insert(restriction);
        getRightMapping(insertedrestriction);
        log.info("Inserted restriction with id {}",insertedrestriction.getRestrictionId());
        return ResponseEntity.ok().body(insertedrestriction);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updaterestriction(
        @org.springframework.web.bind.annotation.RequestBody
        Restriction restriction) {
        log.info("Updating restriction with id {}",restriction.getRestrictionId());
        Restriction updatedrestriction=restrictionService.update(restriction);
        getRightMapping(updatedrestriction);
        return ResponseEntity.ok().body(updatedrestriction);
    }

    private List<Restriction> getRightMapping(List<Restriction> restrictionList) {
        for (Restriction restriction: restrictionList)
        {
        getRightMapping(restriction);
        }
        return restrictionList;
    }

    private void getRightMapping(Restriction restriction) {
        if (restriction.getRole()!=null)
        {
        restriction.getRole().setUserList(null);
        restriction.getRole().setRestrictionList(null);
        }
        if (restriction.getEntity()!=null)
        {
        restriction.getEntity().setFieldList(null);
        restriction.getEntity().setRelationshipList(null);
        restriction.getEntity().setEnumFieldList(null);
        restriction.getEntity().setTabList(null);
        restriction.getEntity().setRestrictionList(null);
        }
    }

}
