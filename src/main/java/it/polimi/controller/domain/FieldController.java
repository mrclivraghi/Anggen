
package it.polimi.controller.domain;

import java.util.List;
import it.polimi.model.domain.Field;
import it.polimi.searchbean.domain.FieldSearchBean;
import it.polimi.service.domain.FieldService;
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
@RequestMapping("/field")
public class FieldController {

    @Autowired
    public FieldService fieldService;
    private final static Logger log = LoggerFactory.getLogger(Field.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "field";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        FieldSearchBean field) {
        List<Field> fieldList;
        if (field.getFieldId()!=null)
         log.info("Searching field like {}",field.toString());
        fieldList=fieldService.find(field);
        getRightMapping(fieldList);
         log.info("Search: returning {} field.",fieldList.size());
        return ResponseEntity.ok().body(fieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{fieldId}", method = RequestMethod.GET)
    public ResponseEntity getfieldById(
        @PathVariable
        String fieldId) {
        log.info("Searching field with id {}",fieldId);
        List<Field> fieldList=fieldService.findById(java.lang.Long.valueOf(fieldId));
        getRightMapping(fieldList);
         log.info("Search: returning {} field.",fieldList.size());
        return ResponseEntity.ok().body(fieldList);
    }

    @ResponseBody
    @RequestMapping(value = "/{fieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deletefieldById(
        @PathVariable
        String fieldId) {
        log.info("Deleting field with id {}",fieldId);
        fieldService.deleteById(java.lang.Long.valueOf(fieldId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertfield(
        @org.springframework.web.bind.annotation.RequestBody
        Field field) {
        if (field.getFieldId()!=null)
        log.info("Inserting field like {}",field.toString());
        Field insertedfield=fieldService.insert(field);
        getRightMapping(insertedfield);
        log.info("Inserted field with id {}",insertedfield.getFieldId());
        return ResponseEntity.ok().body(insertedfield);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatefield(
        @org.springframework.web.bind.annotation.RequestBody
        Field field) {
        log.info("Updating field with id {}",field.getFieldId());
        Field updatedfield=fieldService.update(field);
        getRightMapping(updatedfield);
        return ResponseEntity.ok().body(updatedfield);
    }

    private List<Field> getRightMapping(List<Field> fieldList) {
        for (Field field: fieldList)
        {
        getRightMapping(field);
        }
        return fieldList;
    }

    private void getRightMapping(Field field) {
        if (field.getEntity()!=null)
        {
        field.getEntity().setFieldList(null);
        field.getEntity().setRelationshipList(null);
        field.getEntity().setEnumFieldList(null);
        field.getEntity().setTabList(null);
        field.getEntity().setRestrictionList(null);
        }
        if (field.getAnnotationList()!=null)
        for (it.polimi.model.domain.Annotation annotation :field.getAnnotationList())

        {

        annotation.setAnnotationAttributeList(null);
        annotation.setField(null);
        annotation.setRelationship(null);
        annotation.setEnumField(null);
        }
        if (field.getTab()!=null)
        {
        field.getTab().setEntity(null);
        field.getTab().setFieldList(null);
        field.getTab().setRelationshipList(null);
        field.getTab().setEnumFieldList(null);
        }
    }

}
