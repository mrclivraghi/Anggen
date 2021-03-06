
package it.anggen.controller.field;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.field.EnumFieldSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.field.EnumFieldService;
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
@RequestMapping("/enumField")
public class EnumFieldController {

    @org.springframework.beans.factory.annotation.Autowired
    private EnumFieldService enumFieldService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.field.EnumField.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "enumField";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.field.EnumField> page = enumFieldService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EnumFieldSearchBean enumField) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.field.EnumField> enumFieldList;
        if (enumField.getEnumFieldId()!=null)
         log.info("Searching enumField like {}", enumField.getEnumFieldId()+' '+ enumField.getName());
        logEntryService.addLogEntry( "Searching entity like "+ enumField.getEnumFieldId()+' '+ enumField.getName(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.field.EnumField.staticEntityId, securityService.getLoggedUser(),log);
        enumFieldList=enumFieldService.find(enumField);
        getSecurityMapping(enumFieldList);
        getRightMapping(enumFieldList);
         log.info("Search: returning {} enumField.",enumFieldList.size());
        return ResponseEntity.ok().body(enumFieldList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{enumFieldId}", method = RequestMethod.GET)
    public ResponseEntity getEnumFieldById(
        @PathVariable
        String enumFieldId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching enumField with id "+enumFieldId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.field.EnumField.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.field.EnumField> enumFieldList=enumFieldService.findById(Long.valueOf(enumFieldId));
        getSecurityMapping(enumFieldList);
        getRightMapping(enumFieldList);
         log.info("Search: returning {} enumField.",enumFieldList.size());
        return ResponseEntity.ok().body(enumFieldList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{enumFieldId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEnumFieldById(
        @PathVariable
        String enumFieldId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting enumField with id "+enumFieldId);
        logEntryService.addLogEntry( "Deleting enumField with id {}"+enumFieldId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.field.EnumField.staticEntityId, securityService.getLoggedUser(),log);
        enumFieldService.deleteById(Long.valueOf(enumFieldId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEnumField(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.EnumField enumField) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (enumField.getEnumFieldId()!=null)
        log.info("Inserting enumField like "+ enumField.getEnumFieldId()+' '+ enumField.getName());
        it.anggen.model.field.EnumField insertedEnumField=enumFieldService.insert(enumField);
        getRightMapping(insertedEnumField);
        logEntryService.addLogEntry( "Inserted enumField with id "+ insertedEnumField.getEnumFieldId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.field.EnumField.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedEnumField);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEnumField(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.EnumField enumField) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating enumField with id "+enumField.getEnumFieldId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.field.EnumField.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(enumField);
        it.anggen.model.field.EnumField updatedEnumField=enumFieldService.update(enumField);
        getSecurityMapping(updatedEnumField);
        getRightMapping(updatedEnumField);
        return ResponseEntity.ok().body(updatedEnumField);
    }

    private List<it.anggen.model.field.EnumField> getRightMapping(List<it.anggen.model.field.EnumField> enumFieldList) {
        for (it.anggen.model.field.EnumField enumField: enumFieldList)
        {
        getRightMapping(enumField);
        }
        return enumFieldList;
    }

    private void getRightMapping(it.anggen.model.field.EnumField enumField) {
        if (enumField.getEntity()!=null)
        {
        enumField.getEntity().setRestrictionEntityList(null);
        enumField.getEntity().setFieldList(null);
        enumField.getEntity().setEnumFieldList(null);
        enumField.getEntity().setEntityGroup(null);
        enumField.getEntity().setTabList(null);
        enumField.getEntity().setRelationshipList(null);
        }
        if (enumField.getEnumEntity()!=null)
        {
        enumField.getEnumEntity().setProject(null);
        enumField.getEnumEntity().setEnumValueList(null);
        enumField.getEnumEntity().setEnumFieldList(null);
        }
        if (enumField.getTab()!=null)
        {
        enumField.getTab().setEntity(null);
        enumField.getTab().setFieldList(null);
        enumField.getTab().setEnumFieldList(null);
        enumField.getTab().setRelationshipList(null);
        }
        if (enumField.getAnnotationList()!=null)
        for (it.anggen.model.field.Annotation annotation :enumField.getAnnotationList())

        {

        annotation.setAnnotationAttributeList(null);
        annotation.setField(null);
        annotation.setEnumField(null);
        annotation.setRelationship(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.field.EnumField enumField) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        enumField.setEntity(enumFieldService.findById(enumField.getEnumFieldId()).get(0).getEntity());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        enumField.setEnumEntity(enumFieldService.findById(enumField.getEnumFieldId()).get(0).getEnumEntity());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        enumField.setTab(enumFieldService.findById(enumField.getEnumFieldId()).get(0).getTab());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        enumField.setAnnotationList(enumFieldService.findById(enumField.getEnumFieldId()).get(0).getAnnotationList());
    }

    private List<it.anggen.model.field.EnumField> getSecurityMapping(List<it.anggen.model.field.EnumField> enumFieldList) {
        for (it.anggen.model.field.EnumField enumField: enumFieldList)
        {
        getSecurityMapping(enumField);
        }
        return enumFieldList;
    }

    private void getSecurityMapping(it.anggen.model.field.EnumField enumField) {
        if (securityEnabled && enumField.getEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        enumField.setEntity(null);

        if (securityEnabled && enumField.getEnumEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.EnumEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        enumField.setEnumEntity(null);

        if (securityEnabled && enumField.getTab()!=null  && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        enumField.setTab(null);

        if (securityEnabled && enumField.getAnnotationList()!=null && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        enumField.setAnnotationList(null);

    }

}
