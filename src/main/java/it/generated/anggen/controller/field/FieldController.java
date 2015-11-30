
package it.generated.anggen.controller.field;

import java.util.List;
import it.generated.anggen.searchbean.field.FieldSearchBean;
import it.generated.anggen.security.SecurityService;
import it.generated.anggen.service.field.FieldService;
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
    private final static Logger log = LoggerFactory.getLogger(it.generated.anggen.model.field.Field.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "field";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        FieldSearchBean field) {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.generated.anggen.model.field.Field> fieldList;
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
        if (!securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching field with id {}",fieldId);
        List<it.generated.anggen.model.field.Field> fieldList=fieldService.findById(Long.valueOf(fieldId));
        getRightMapping(fieldList);
         log.info("Search: returning {} field.",fieldList.size());
        return ResponseEntity.ok().body(fieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{fieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteFieldById(
        @PathVariable
        String fieldId) {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting field with id {}",fieldId);
        fieldService.deleteById(Long.valueOf(fieldId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertField(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.field.Field field) {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (field.getFieldId()!=null)
        log.info("Inserting field like {}",field.toString());
        it.generated.anggen.model.field.Field insertedField=fieldService.insert(field);
        getRightMapping(insertedField);
        log.info("Inserted field with id {}",insertedField.getFieldId());
        return ResponseEntity.ok().body(insertedField);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateField(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.field.Field field) {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating field with id {}",field.getFieldId());
        rebuildSecurityMapping(field);
        it.generated.anggen.model.field.Field updatedField=fieldService.update(field);
        getRightMapping(updatedField);
        getSecurityMapping(updatedField);
        return ResponseEntity.ok().body(updatedField);
    }

    private List<it.generated.anggen.model.field.Field> getRightMapping(List<it.generated.anggen.model.field.Field> fieldList) {
        for (it.generated.anggen.model.field.Field field: fieldList)
        {
        getRightMapping(field);
        }
        return fieldList;
    }

    private void getRightMapping(it.generated.anggen.model.field.Field field) {
        if (field.getTab()!=null)
        {
        field.getTab().setEnumFieldList(null);
        field.getTab().setRelationshipList(null);
        field.getTab().setFieldList(null);
        field.getTab().setEntity(null);
        }
        if (field.getRestrictionFieldList()!=null)
        for (it.generated.anggen.model.security.RestrictionField restrictionField :field.getRestrictionFieldList())

        {

        restrictionField.setRole(null);
        restrictionField.setField(null);
        }
        if (field.getAnnotationList()!=null)
        for (it.generated.anggen.model.field.Annotation annotation :field.getAnnotationList())

        {

        annotation.setEnumField(null);
        annotation.setRelationship(null);
        annotation.setField(null);
        annotation.setAnnotationAttributeList(null);
        }
        if (field.getEntity()!=null)
        {
        field.getEntity().setEntityGroup(null);
        field.getEntity().setRestrictionEntityList(null);
        field.getEntity().setTabList(null);
        field.getEntity().setEnumFieldList(null);
        field.getEntity().setRelationshipList(null);
        field.getEntity().setFieldList(null);
        }
    }

    private void rebuildSecurityMapping(it.generated.anggen.model.field.Field field) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Tab.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        field.setTab(fieldService.findById(field.getFieldId()).get(0).getTab());
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        field.setRestrictionFieldList(fieldService.findById(field.getFieldId()).get(0).getRestrictionFieldList());
        if (!securityService.isAllowed(it.generated.anggen.model.field.Annotation.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        field.setAnnotationList(fieldService.findById(field.getFieldId()).get(0).getAnnotationList());
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        field.setEntity(fieldService.findById(field.getFieldId()).get(0).getEntity());
    }

    private List<it.generated.anggen.model.field.Field> getSecurityMapping(List<it.generated.anggen.model.field.Field> fieldList) {
        for (it.generated.anggen.model.field.Field field: fieldList)
        {
        getSecurityMapping(field);
        }
        return fieldList;
    }

    private void getSecurityMapping(it.generated.anggen.model.field.Field field) {
        if (field.getTab()!=null  && !securityService.isAllowed(it.generated.anggen.model.entity.Tab.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        field.setTab(null);

        if (field.getRestrictionFieldList()!=null && !securityService.isAllowed(it.generated.anggen.model.security.RestrictionField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        field.setRestrictionFieldList(null);

        if (field.getAnnotationList()!=null && !securityService.isAllowed(it.generated.anggen.model.field.Annotation.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        field.setAnnotationList(null);

        if (field.getEntity()!=null  && !securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        field.setEntity(null);

    }

}
