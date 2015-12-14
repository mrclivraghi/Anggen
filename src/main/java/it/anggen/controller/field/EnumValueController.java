
package it.anggen.controller.field;

import java.util.List;
import it.anggen.searchbean.field.EnumValueSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.field.EnumValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.field.EnumValue.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "enumValue";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EnumValueSearchBean enumValue) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.field.EnumValue> enumValueList;
        if (enumValue.getEnumValueId()!=null)
         log.info("Searching enumValue like {}", enumValue.getEnumValueId()+' '+ enumValue.getName());
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
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching enumValue with id {}",enumValueId);
        List<it.anggen.model.field.EnumValue> enumValueList=enumValueService.findById(Long.valueOf(enumValueId));
        getRightMapping(enumValueList);
         log.info("Search: returning {} enumValue.",enumValueList.size());
        return ResponseEntity.ok().body(enumValueList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumValueId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEnumValueById(
        @PathVariable
        String enumValueId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting enumValue with id {}",enumValueId);
        enumValueService.deleteById(Long.valueOf(enumValueId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEnumValue(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.EnumValue enumValue) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (enumValue.getEnumValueId()!=null)
        log.info("Inserting enumValue like {}", enumValue.getEnumValueId()+' '+ enumValue.getName());
        it.anggen.model.field.EnumValue insertedEnumValue=enumValueService.insert(enumValue);
        getRightMapping(insertedEnumValue);
        log.info("Inserted enumValue with id {}",insertedEnumValue.getEnumValueId());
        return ResponseEntity.ok().body(insertedEnumValue);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEnumValue(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.EnumValue enumValue) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumValue.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating enumValue with id {}",enumValue.getEnumValueId());
        rebuildSecurityMapping(enumValue);
        it.anggen.model.field.EnumValue updatedEnumValue=enumValueService.update(enumValue);
        getRightMapping(updatedEnumValue);
        getSecurityMapping(updatedEnumValue);
        return ResponseEntity.ok().body(updatedEnumValue);
    }

    private List<it.anggen.model.field.EnumValue> getRightMapping(List<it.anggen.model.field.EnumValue> enumValueList) {
        for (it.anggen.model.field.EnumValue enumValue: enumValueList)
        {
        getRightMapping(enumValue);
        }
        return enumValueList;
    }

    private void getRightMapping(it.anggen.model.field.EnumValue enumValue) {
        if (enumValue.getEnumEntity()!=null)
        {
        enumValue.getEnumEntity().setProject(null);
        enumValue.getEnumEntity().setEnumValueList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.field.EnumValue enumValue) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        enumValue.setEnumEntity(enumValueService.findById(enumValue.getEnumValueId()).get(0).getEnumEntity());
    }

    private List<it.anggen.model.field.EnumValue> getSecurityMapping(List<it.anggen.model.field.EnumValue> enumValueList) {
        for (it.anggen.model.field.EnumValue enumValue: enumValueList)
        {
        getSecurityMapping(enumValue);
        }
        return enumValueList;
    }

    private void getSecurityMapping(it.anggen.model.field.EnumValue enumValue) {
        if (securityEnabled && enumValue.getEnumEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        enumValue.setEnumEntity(null);

    }

}
