
package it.anggen.controller.field;

import java.util.List;

import it.anggen.searchbean.field.FieldSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.field.FieldService;

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
@RequestMapping("/field")
public class FieldController {

    @org.springframework.beans.factory.annotation.Autowired
    private FieldService fieldService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.field.Field.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "field";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        FieldSearchBean field) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.field.Field> fieldList;
        if (field.getFieldId()!=null)
         log.info("Searching field like {}", field.getName()+' '+ field.getFieldId());
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
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching field with id {}",fieldId);
        List<it.anggen.model.field.Field> fieldList=fieldService.findById(Long.valueOf(fieldId));
        getRightMapping(fieldList);
         log.info("Search: returning {} field.",fieldList.size());
        return ResponseEntity.ok().body(fieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{fieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteFieldById(
        @PathVariable
        String fieldId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting field with id {}",fieldId);
        fieldService.deleteById(Long.valueOf(fieldId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertField(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.Field field) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (field.getFieldId()!=null)
        log.info("Inserting field like {}", field.getName()+' '+ field.getFieldId());
        it.anggen.model.field.Field insertedField=fieldService.insert(field);
        getRightMapping(insertedField);
        log.info("Inserted field with id {}",insertedField.getFieldId());
        return ResponseEntity.ok().body(insertedField);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateField(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.Field field) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating field with id {}",field.getFieldId());
        rebuildSecurityMapping(field);
        it.anggen.model.field.Field updatedField=fieldService.update(field);
        getRightMapping(updatedField);
        getSecurityMapping(updatedField);
        return ResponseEntity.ok().body(updatedField);
    }

    private List<it.anggen.model.field.Field> getRightMapping(List<it.anggen.model.field.Field> fieldList) {
        for (it.anggen.model.field.Field field: fieldList)
        {
        getRightMapping(field);
        }
        return fieldList;
    }

    private void getRightMapping(it.anggen.model.field.Field field) {
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
        for (it.anggen.model.field.Annotation annotation :field.getAnnotationList())

        {

        annotation.setAnnotationAttributeList(null);
        annotation.setField(null);
        annotation.setRelationship(null);
        annotation.setEnumField(null);
        }
        if (field.getRestrictionFieldList()!=null)
        for (it.anggen.model.security.RestrictionField restrictionField :field.getRestrictionFieldList())

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

    private void rebuildSecurityMapping(it.anggen.model.field.Field field) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        field.setEntity(fieldService.findById(field.getFieldId()).get(0).getEntity());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        field.setAnnotationList(fieldService.findById(field.getFieldId()).get(0).getAnnotationList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        field.setRestrictionFieldList(fieldService.findById(field.getFieldId()).get(0).getRestrictionFieldList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        field.setTab(fieldService.findById(field.getFieldId()).get(0).getTab());
    }

    private List<it.anggen.model.field.Field> getSecurityMapping(List<it.anggen.model.field.Field> fieldList) {
        for (it.anggen.model.field.Field field: fieldList)
        {
        getSecurityMapping(field);
        }
        return fieldList;
    }

    private void getSecurityMapping(it.anggen.model.field.Field field) {
        if (securityEnabled && field.getEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        field.setEntity(null);

        if (securityEnabled && field.getAnnotationList()!=null && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        field.setAnnotationList(null);

        if (securityEnabled && field.getRestrictionFieldList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        field.setRestrictionFieldList(null);

        if (securityEnabled && field.getTab()!=null  && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        field.setTab(null);

    }

}