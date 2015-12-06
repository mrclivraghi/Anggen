
package it.polimi.controller.field;

import java.util.List;

import it.polimi.model.RestrictionType;
import it.polimi.searchbean.field.EnumFieldSearchBean;
import it.polimi.security.SecurityService;
import it.polimi.service.field.EnumFieldService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/enumField")
public class EnumFieldController {

    @org.springframework.beans.factory.annotation.Autowired
    private EnumFieldService enumFieldService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.polimi.model.field.EnumField.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH)) 
return "forbidden"; 

        return "enumField";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EnumFieldSearchBean enumField) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.polimi.model.field.EnumField> enumFieldList;
        if (enumField.getEnumFieldId()!=null)
         log.info("Searching enumField like {}",enumField.toString());
        enumFieldList=enumFieldService.find(enumField);
        getRightMapping(enumFieldList);
        getSecurityMapping(enumFieldList);
         log.info("Search: returning {} enumField.",enumFieldList.size());
        return ResponseEntity.ok().body(enumFieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumFieldId}", method = RequestMethod.GET)
    public ResponseEntity getEnumFieldById(
        @PathVariable
        String enumFieldId) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching enumField with id {}",enumFieldId);
        List<it.polimi.model.field.EnumField> enumFieldList=enumFieldService.findById(Long.valueOf(enumFieldId));
        getRightMapping(enumFieldList);
         log.info("Search: returning {} enumField.",enumFieldList.size());
        return ResponseEntity.ok().body(enumFieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{enumFieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEnumFieldById(
        @PathVariable
        String enumFieldId) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting enumField with id {}",enumFieldId);
        enumFieldService.deleteById(Long.valueOf(enumFieldId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEnumField(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.field.EnumField enumField) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (enumField.getEnumFieldId()!=null)
        log.info("Inserting enumField like {}",enumField.toString());
        it.polimi.model.field.EnumField insertedEnumField=enumFieldService.insert(enumField);
        getRightMapping(insertedEnumField);
        log.info("Inserted enumField with id {}",insertedEnumField.getEnumFieldId());
        return ResponseEntity.ok().body(insertedEnumField);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEnumField(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.field.EnumField enumField) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating enumField with id {}",enumField.getEnumFieldId());
        rebuildSecurityMapping(enumField);
        it.polimi.model.field.EnumField updatedEnumField=enumFieldService.update(enumField);
        getRightMapping(updatedEnumField);
        getSecurityMapping(updatedEnumField);
        return ResponseEntity.ok().body(updatedEnumField);
    }

    private List<it.polimi.model.field.EnumField> getRightMapping(List<it.polimi.model.field.EnumField> enumFieldList) {
        for (it.polimi.model.field.EnumField enumField: enumFieldList)
        {
        getRightMapping(enumField);
        }
        return enumFieldList;
    }

    private void getRightMapping(it.polimi.model.field.EnumField enumField) {
        if (enumField.getEnumValueList()!=null)
        for (it.polimi.model.field.EnumValue enumValue :enumField.getEnumValueList())

        {

        enumValue.setEnumField(null);
        }
        if (enumField.getEntity()!=null)
        {
        enumField.getEntity().setRelationshipList(null);
        enumField.getEntity().setEnumFieldList(null);
        enumField.getEntity().setTabList(null);
        enumField.getEntity().setRestrictionEntityList(null);
        enumField.getEntity().setEntityGroup(null);
        enumField.getEntity().setFieldList(null);
        }
        if (enumField.getAnnotationList()!=null)
        for (it.polimi.model.field.Annotation annotation :enumField.getAnnotationList())

        {

        annotation.setAnnotationAttributeList(null);
        annotation.setField(null);
        annotation.setRelationship(null);
        annotation.setEnumField(null);
        }
        if (enumField.getTab()!=null)
        {
        enumField.getTab().setEntity(null);
        enumField.getTab().setFieldList(null);
        enumField.getTab().setRelationshipList(null);
        enumField.getTab().setEnumFieldList(null);
        }
    }

    private void rebuildSecurityMapping(it.polimi.model.field.EnumField enumField) {
        if (!securityService.hasPermission(it.polimi.model.field.EnumValue.staticEntityId, RestrictionType.SEARCH))
        enumField.setEnumValueList(enumFieldService.findById(enumField.getEnumFieldId()).get(0).getEnumValueList());
        if (!securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.SEARCH))
        enumField.setEntity(enumFieldService.findById(enumField.getEnumFieldId()).get(0).getEntity());
        if (!securityService.hasPermission(it.polimi.model.field.Annotation.staticEntityId, RestrictionType.SEARCH))
        enumField.setAnnotationList(enumFieldService.findById(enumField.getEnumFieldId()).get(0).getAnnotationList());
        if (!securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.SEARCH))
        enumField.setTab(enumFieldService.findById(enumField.getEnumFieldId()).get(0).getTab());
    }

    private List<it.polimi.model.field.EnumField> getSecurityMapping(List<it.polimi.model.field.EnumField> enumFieldList) {
        for (it.polimi.model.field.EnumField enumField: enumFieldList)
        {
        getSecurityMapping(enumField);
        }
        return enumFieldList;
    }

    private void getSecurityMapping(it.polimi.model.field.EnumField enumField) {
        if (enumField.getEnumValueList()!=null && !securityService.hasPermission(it.polimi.model.field.EnumValue.staticEntityId, RestrictionType.SEARCH) )
        enumField.setEnumValueList(null);

        if (enumField.getEntity()!=null  && !securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.SEARCH) )
        enumField.setEntity(null);

        if (enumField.getAnnotationList()!=null && !securityService.hasPermission(it.polimi.model.field.Annotation.staticEntityId, RestrictionType.SEARCH) )
        enumField.setAnnotationList(null);

        if (enumField.getTab()!=null  && !securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.SEARCH) )
        enumField.setTab(null);

    }

}
