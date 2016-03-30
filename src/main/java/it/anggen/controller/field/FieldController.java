
package it.anggen.controller.field;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.field.FieldSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.field.FieldService;
import it.anggen.service.log.LogEntryService;
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
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.field.Field.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "field";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.field.Field> page = fieldService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        FieldSearchBean field) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.field.Field> fieldList;
        if (field.getFieldId()!=null)
         log.info("Searching field like {}", field.getFieldId()+' '+ field.getName());
        logEntryService.addLogEntry( "Searching entity like "+ field.getFieldId()+' '+ field.getName(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.field.Field.staticEntityId, securityService.getLoggedUser(),log);
        fieldList=fieldService.find(field);
        getSecurityMapping(fieldList);
        getRightMapping(fieldList);
         log.info("Search: returning {} field.",fieldList.size());
        return ResponseEntity.ok().body(fieldList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{fieldId}", method = RequestMethod.GET)
    public ResponseEntity getFieldById(
        @PathVariable
        String fieldId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching field with id "+fieldId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.field.Field.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.field.Field> fieldList=fieldService.findById(Long.valueOf(fieldId));
        getSecurityMapping(fieldList);
        getRightMapping(fieldList);
         log.info("Search: returning {} field.",fieldList.size());
        return ResponseEntity.ok().body(fieldList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{fieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteFieldById(
        @PathVariable
        String fieldId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting field with id "+fieldId);
        logEntryService.addLogEntry( "Deleting field with id {}"+fieldId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.field.Field.staticEntityId, securityService.getLoggedUser(),log);
        fieldService.deleteById(Long.valueOf(fieldId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertField(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.Field field) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (field.getFieldId()!=null)
        log.info("Inserting field like "+ field.getFieldId()+' '+ field.getName());
        it.anggen.model.field.Field insertedField=fieldService.insert(field);
        getRightMapping(insertedField);
        logEntryService.addLogEntry( "Inserted field with id "+ insertedField.getFieldId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.field.Field.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedField);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateField(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.Field field) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating field with id "+field.getFieldId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.field.Field.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(field);
        it.anggen.model.field.Field updatedField=fieldService.update(field);
        getSecurityMapping(updatedField);
        getRightMapping(updatedField);
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
        if (field.getRestrictionFieldList()!=null)
        for (it.anggen.model.security.RestrictionField restrictionField :field.getRestrictionFieldList())

        {

        restrictionField.setRole(null);
        restrictionField.setField(null);
        }
        if (field.getTab()!=null)
        {
        field.getTab().setRelationshipList(null);
        field.getTab().setEntity(null);
        field.getTab().setFieldList(null);
        field.getTab().setEnumFieldList(null);
        }
        if (field.getEntity()!=null)
        {
        field.getEntity().setFieldList(null);
        field.getEntity().setEnumFieldList(null);
        field.getEntity().setTabList(null);
        field.getEntity().setEntityGroup(null);
        field.getEntity().setRestrictionEntityList(null);
        field.getEntity().setRelationshipList(null);
        }
        if (field.getAnnotationList()!=null)
        for (it.anggen.model.field.Annotation annotation :field.getAnnotationList())

        {

        annotation.setRelationship(null);
        annotation.setAnnotationAttributeList(null);
        annotation.setField(null);
        annotation.setEnumField(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.field.Field field) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        field.setRestrictionFieldList(fieldService.findById(field.getFieldId()).get(0).getRestrictionFieldList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        field.setTab(fieldService.findById(field.getFieldId()).get(0).getTab());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        field.setEntity(fieldService.findById(field.getFieldId()).get(0).getEntity());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        field.setAnnotationList(fieldService.findById(field.getFieldId()).get(0).getAnnotationList());
    }

    private List<it.anggen.model.field.Field> getSecurityMapping(List<it.anggen.model.field.Field> fieldList) {
        for (it.anggen.model.field.Field field: fieldList)
        {
        getSecurityMapping(field);
        }
        return fieldList;
    }

    private void getSecurityMapping(it.anggen.model.field.Field field) {
        if (securityEnabled && field.getRestrictionFieldList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        field.setRestrictionFieldList(null);

        if (securityEnabled && field.getTab()!=null  && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        field.setTab(null);

        if (securityEnabled && field.getEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        field.setEntity(null);

        if (securityEnabled && field.getAnnotationList()!=null && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        field.setAnnotationList(null);

    }

}
