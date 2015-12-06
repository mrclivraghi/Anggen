
package it.polimi.controller.field;

import java.util.List;

import it.polimi.model.RestrictionType;
import it.polimi.searchbean.field.EnumValueSearchBean;
import it.polimi.security.SecurityService;
import it.polimi.service.field.EnumValueService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/enumValue")
public class EnumValueController {

    @org.springframework.beans.factory.annotation.Autowired
    private EnumValueService enumValueService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.polimi.model.field.EnumValue.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.hasPermission(it.polimi.model.field.EnumValue.staticEntityId, RestrictionType.SEARCH)) 
return "forbidden"; 

        return "enumValue";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EnumValueSearchBean enumValue) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumValue.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.polimi.model.field.EnumValue> enumValueList;
        if (enumValue.getEnumValueId()!=null)
         log.info("Searching enumValue like {}",enumValue.toString());
        enumValueList=enumValueService.find(enumValue);
        getRightMapping(enumValueList);
        getSecurityMapping(enumValueList);
         log.info("Search: returning {} enumValue.",enumValueList.size());
        return ResponseEntity.ok().body(enumValueList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumValueId}", method = RequestMethod.GET)
    public ResponseEntity getEnumValueById(
        @PathVariable
        String enumValueId) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumValue.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching enumValue with id {}",enumValueId);
        List<it.polimi.model.field.EnumValue> enumValueList=enumValueService.findById(Long.valueOf(enumValueId));
        getRightMapping(enumValueList);
         log.info("Search: returning {} enumValue.",enumValueList.size());
        return ResponseEntity.ok().body(enumValueList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumValueId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEnumValueById(
        @PathVariable
        String enumValueId) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumValue.staticEntityId, RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting enumValue with id {}",enumValueId);
        enumValueService.deleteById(Long.valueOf(enumValueId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEnumValue(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.field.EnumValue enumValue) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumValue.staticEntityId, RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (enumValue.getEnumValueId()!=null)
        log.info("Inserting enumValue like {}",enumValue.toString());
        it.polimi.model.field.EnumValue insertedEnumValue=enumValueService.insert(enumValue);
        getRightMapping(insertedEnumValue);
        log.info("Inserted enumValue with id {}",insertedEnumValue.getEnumValueId());
        return ResponseEntity.ok().body(insertedEnumValue);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEnumValue(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.field.EnumValue enumValue) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumValue.staticEntityId, RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating enumValue with id {}",enumValue.getEnumValueId());
        rebuildSecurityMapping(enumValue);
        it.polimi.model.field.EnumValue updatedEnumValue=enumValueService.update(enumValue);
        getRightMapping(updatedEnumValue);
        getSecurityMapping(updatedEnumValue);
        return ResponseEntity.ok().body(updatedEnumValue);
    }

    private List<it.polimi.model.field.EnumValue> getRightMapping(List<it.polimi.model.field.EnumValue> enumValueList) {
        for (it.polimi.model.field.EnumValue enumValue: enumValueList)
        {
        getRightMapping(enumValue);
        }
        return enumValueList;
    }

    private void getRightMapping(it.polimi.model.field.EnumValue enumValue) {
        if (enumValue.getEnumField()!=null)
        {
        enumValue.getEnumField().setEnumValueList(null);
        enumValue.getEnumField().setEntity(null);
        enumValue.getEnumField().setAnnotationList(null);
        enumValue.getEnumField().setTab(null);
        }
    }

    private void rebuildSecurityMapping(it.polimi.model.field.EnumValue enumValue) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH))
        enumValue.setEnumField(enumValueService.findById(enumValue.getEnumValueId()).get(0).getEnumField());
    }

    private List<it.polimi.model.field.EnumValue> getSecurityMapping(List<it.polimi.model.field.EnumValue> enumValueList) {
        for (it.polimi.model.field.EnumValue enumValue: enumValueList)
        {
        getSecurityMapping(enumValue);
        }
        return enumValueList;
    }

    private void getSecurityMapping(it.polimi.model.field.EnumValue enumValue) {
        if (enumValue.getEnumField()!=null  && !securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH) )
        enumValue.setEnumField(null);

    }

}
