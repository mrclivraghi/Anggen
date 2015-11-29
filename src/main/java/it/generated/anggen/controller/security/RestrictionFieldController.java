
package it.generated.anggen.controller.security;

import java.util.List;
import it.generated.anggen.searchbean.security.RestrictionFieldSearchBean;
import it.generated.anggen.security.SecurityService;
import it.generated.anggen.service.security.RestrictionFieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/restrictionField")
public class RestrictionFieldController {

    @org.springframework.beans.factory.annotation.Autowired
    private RestrictionFieldService restrictionFieldService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.generated.anggen.model.security.RestrictionField.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "restrictionField";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionFieldSearchBean restrictionField) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.generated.anggen.model.security.RestrictionField> restrictionFieldList;
        if (restrictionField.getRestrictionFieldId()!=null)
         log.info("Searching restrictionField like {}",restrictionField.toString());
        restrictionFieldList=restrictionFieldService.find(restrictionField);
        getRightMapping(restrictionFieldList);
        getSecurityMapping(restrictionFieldList);
         log.info("Search: returning {} restrictionField.",restrictionFieldList.size());
        return ResponseEntity.ok().body(restrictionFieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionFieldId}", method = RequestMethod.GET)
    public ResponseEntity getRestrictionFieldById(
        @PathVariable
        String restrictionFieldId) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching restrictionField with id {}",restrictionFieldId);
        List<it.generated.anggen.model.security.RestrictionField> restrictionFieldList=restrictionFieldService.findById(Long.valueOf(restrictionFieldId));
        getRightMapping(restrictionFieldList);
         log.info("Search: returning {} restrictionField.",restrictionFieldList.size());
        return ResponseEntity.ok().body(restrictionFieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionFieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRestrictionFieldById(
        @PathVariable
        String restrictionFieldId) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionField.staticEntityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting restrictionField with id {}",restrictionFieldId);
        restrictionFieldService.deleteById(Long.valueOf(restrictionFieldId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRestrictionField(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.security.RestrictionField restrictionField) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionField.staticEntityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (restrictionField.getRestrictionFieldId()!=null)
        log.info("Inserting restrictionField like {}",restrictionField.toString());
        it.generated.anggen.model.security.RestrictionField insertedRestrictionField=restrictionFieldService.insert(restrictionField);
        getRightMapping(insertedRestrictionField);
        log.info("Inserted restrictionField with id {}",insertedRestrictionField.getRestrictionFieldId());
        return ResponseEntity.ok().body(insertedRestrictionField);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRestrictionField(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.security.RestrictionField restrictionField) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionField.staticEntityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating restrictionField with id {}",restrictionField.getRestrictionFieldId());
        rebuildSecurityMapping(restrictionField);
        it.generated.anggen.model.security.RestrictionField updatedRestrictionField=restrictionFieldService.update(restrictionField);
        getRightMapping(updatedRestrictionField);
        getSecurityMapping(updatedRestrictionField);
        return ResponseEntity.ok().body(updatedRestrictionField);
    }

    private List<it.generated.anggen.model.security.RestrictionField> getRightMapping(List<it.generated.anggen.model.security.RestrictionField> restrictionFieldList) {
        for (it.generated.anggen.model.security.RestrictionField restrictionField: restrictionFieldList)
        {
        getRightMapping(restrictionField);
        }
        return restrictionFieldList;
    }

    private void getRightMapping(it.generated.anggen.model.security.RestrictionField restrictionField) {
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

    private void rebuildSecurityMapping(it.generated.anggen.model.security.RestrictionField restrictionField) {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        restrictionField.setField(restrictionFieldService.findById(restrictionField.getRestrictionFieldId()).get(0).getField());
        if (!securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        restrictionField.setRole(restrictionFieldService.findById(restrictionField.getRestrictionFieldId()).get(0).getRole());
    }

    private List<it.generated.anggen.model.security.RestrictionField> getSecurityMapping(List<it.generated.anggen.model.security.RestrictionField> restrictionFieldList) {
        for (it.generated.anggen.model.security.RestrictionField restrictionField: restrictionFieldList)
        {
        getSecurityMapping(restrictionField);
        }
        return restrictionFieldList;
    }

    private void getSecurityMapping(it.generated.anggen.model.security.RestrictionField restrictionField) {
        if (restrictionField.getField()!=null  && !securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        restrictionField.setField(null);

        if (restrictionField.getRole()!=null  && !securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        restrictionField.setRole(null);

    }

}
