
package it.polimi.controller.field;

import java.util.List;

import it.polimi.model.RestrictionType;
import it.polimi.searchbean.field.FieldSearchBean;
import it.polimi.security.SecurityService;
import it.polimi.service.field.FieldService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/field")
public class FieldController {

    @org.springframework.beans.factory.annotation.Autowired
    private FieldService fieldService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.polimi.model.field.Field.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.SEARCH)) 
return "forbidden"; 

        return "field";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        FieldSearchBean field) {
        if (!securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.polimi.model.field.Field> fieldList;
        if (field.getFieldId()!=null)
         log.info("Searching field like {}",field.toString());
        fieldList=fieldService.find(field);
        getRightMapping(fieldList);
        getSecurityMapping(fieldList);
         log.info("Search: returning {} field.",fieldList.size());
        return ResponseEntity.ok().body(fieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{fieldId}", method = RequestMethod.GET)
    public ResponseEntity getFieldById(
        @PathVariable
        String fieldId) {
        if (!securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching field with id {}",fieldId);
        List<it.polimi.model.field.Field> fieldList=fieldService.findById(Long.valueOf(fieldId));
        getRightMapping(fieldList);
         log.info("Search: returning {} field.",fieldList.size());
        return ResponseEntity.ok().body(fieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{fieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteFieldById(
        @PathVariable
        String fieldId) {
        if (!securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting field with id {}",fieldId);
        fieldService.deleteById(Long.valueOf(fieldId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertField(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.field.Field field) {
        if (!securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (field.getFieldId()!=null)
        log.info("Inserting field like {}",field.toString());
        it.polimi.model.field.Field insertedField=fieldService.insert(field);
        getRightMapping(insertedField);
        log.info("Inserted field with id {}",insertedField.getFieldId());
        return ResponseEntity.ok().body(insertedField);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateField(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.field.Field field) {
        if (!securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating field with id {}",field.getFieldId());
        rebuildSecurityMapping(field);
        it.polimi.model.field.Field updatedField=fieldService.update(field);
        getRightMapping(updatedField);
        getSecurityMapping(updatedField);
        return ResponseEntity.ok().body(updatedField);
    }

    private List<it.polimi.model.field.Field> getRightMapping(List<it.polimi.model.field.Field> fieldList) {
        for (it.polimi.model.field.Field field: fieldList)
        {
        getRightMapping(field);
        }
        return fieldList;
    }

    private void getRightMapping(it.polimi.model.field.Field field) {
        if (field.getEntity()!=null)
        {
        field.getEntity().setRelationshipList(null);
        field.getEntity().setEnumFieldList(null);
        field.getEntity().setTabList(null);
        field.getEntity().setRestrictionEntityList(null);
        field.getEntity().setEntityGroup(null);
        field.getEntity().setFieldList(null);
        }
        if (field.getAnnotationList()!=null)
        for (it.polimi.model.field.Annotation annotation :field.getAnnotationList())

        {

        annotation.setAnnotationAttributeList(null);
        annotation.setField(null);
        annotation.setRelationship(null);
        annotation.setEnumField(null);
        }
        if (field.getRestrictionFieldList()!=null)
        for (it.polimi.model.security.RestrictionField restrictionField :field.getRestrictionFieldList())

        {

        restrictionField.setField(null);
        restrictionField.setRole(null);
        }
        if (field.getTab()!=null)
        {
        field.getTab().setEntity(null);
        field.getTab().setFieldList(null);
        field.getTab().setRelationshipList(null);
        field.getTab().setEnumFieldList(null);
        }
    }

    private void rebuildSecurityMapping(it.polimi.model.field.Field field) {
        if (!securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.SEARCH))
        field.setEntity(fieldService.findById(field.getFieldId()).get(0).getEntity());
        if (!securityService.hasPermission(it.polimi.model.field.Annotation.staticEntityId, RestrictionType.SEARCH))
        field.setAnnotationList(fieldService.findById(field.getFieldId()).get(0).getAnnotationList());
        if (!securityService.hasPermission(it.polimi.model.security.RestrictionField.staticEntityId, RestrictionType.SEARCH))
        field.setRestrictionFieldList(fieldService.findById(field.getFieldId()).get(0).getRestrictionFieldList());
        if (!securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.SEARCH))
        field.setTab(fieldService.findById(field.getFieldId()).get(0).getTab());
    }

    private List<it.polimi.model.field.Field> getSecurityMapping(List<it.polimi.model.field.Field> fieldList) {
        for (it.polimi.model.field.Field field: fieldList)
        {
        getSecurityMapping(field);
        }
        return fieldList;
    }

    private void getSecurityMapping(it.polimi.model.field.Field field) {
        if (field.getEntity()!=null  && !securityService.hasPermission(it.polimi.model.entity.Entity.staticEntityId, RestrictionType.SEARCH) )
        field.setEntity(null);

        if (field.getAnnotationList()!=null && !securityService.hasPermission(it.polimi.model.field.Annotation.staticEntityId, RestrictionType.SEARCH) )
        field.setAnnotationList(null);

        if (field.getRestrictionFieldList()!=null && !securityService.hasPermission(it.polimi.model.security.RestrictionField.staticEntityId, RestrictionType.SEARCH) )
        field.setRestrictionFieldList(null);

        if (field.getTab()!=null  && !securityService.hasPermission(it.polimi.model.entity.Tab.staticEntityId, RestrictionType.SEARCH) )
        field.setTab(null);

    }

}
