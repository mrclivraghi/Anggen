
package it.anggen.controller.field;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.field.AnnotationSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.field.AnnotationService;
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
@RequestMapping("/annotation")
public class AnnotationController {

    @org.springframework.beans.factory.annotation.Autowired
    private AnnotationService annotationService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.field.Annotation.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "annotation";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.field.Annotation> page = annotationService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        AnnotationSearchBean annotation) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.field.Annotation> annotationList;
        if (annotation.getAnnotationId()!=null)
         log.info("Searching annotation like {}", annotation.getAnnotationId());
        logEntryService.addLogEntry( "Searching entity like "+ annotation.getAnnotationId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.field.Annotation.staticEntityId, securityService.getLoggedUser(),log);
        annotationList=annotationService.find(annotation);
        getSecurityMapping(annotationList);
        getRightMapping(annotationList);
         log.info("Search: returning {} annotation.",annotationList.size());
        return ResponseEntity.ok().body(annotationList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{annotationId}", method = RequestMethod.GET)
    public ResponseEntity getAnnotationById(
        @PathVariable
        String annotationId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching annotation with id "+annotationId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.field.Annotation.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.field.Annotation> annotationList=annotationService.findById(Long.valueOf(annotationId));
        getSecurityMapping(annotationList);
        getRightMapping(annotationList);
         log.info("Search: returning {} annotation.",annotationList.size());
        return ResponseEntity.ok().body(annotationList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{annotationId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAnnotationById(
        @PathVariable
        String annotationId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting annotation with id "+annotationId);
        logEntryService.addLogEntry( "Deleting annotation with id {}"+annotationId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.field.Annotation.staticEntityId, securityService.getLoggedUser(),log);
        annotationService.deleteById(Long.valueOf(annotationId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertAnnotation(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.Annotation annotation) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (annotation.getAnnotationId()!=null)
        log.info("Inserting annotation like "+ annotation.getAnnotationId());
        it.anggen.model.field.Annotation insertedAnnotation=annotationService.insert(annotation);
        getRightMapping(insertedAnnotation);
        logEntryService.addLogEntry( "Inserted annotation with id "+ insertedAnnotation.getAnnotationId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.field.Annotation.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedAnnotation);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateAnnotation(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.Annotation annotation) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating annotation with id "+annotation.getAnnotationId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.field.Annotation.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(annotation);
        it.anggen.model.field.Annotation updatedAnnotation=annotationService.update(annotation);
        getSecurityMapping(updatedAnnotation);
        getRightMapping(updatedAnnotation);
        return ResponseEntity.ok().body(updatedAnnotation);
    }

    private List<it.anggen.model.field.Annotation> getRightMapping(List<it.anggen.model.field.Annotation> annotationList) {
        for (it.anggen.model.field.Annotation annotation: annotationList)
        {
        getRightMapping(annotation);
        }
        return annotationList;
    }

    private void getRightMapping(it.anggen.model.field.Annotation annotation) {
        if (annotation.getAnnotationAttributeList()!=null)
        for (it.anggen.model.field.AnnotationAttribute annotationAttribute :annotation.getAnnotationAttributeList())

        {

        annotationAttribute.setAnnotation(null);
        }
        if (annotation.getField()!=null)
        {
        annotation.getField().setAnnotationList(null);
        annotation.getField().setRestrictionFieldList(null);
        annotation.getField().setTab(null);
        annotation.getField().setEntity(null);
        }
        if (annotation.getEnumField()!=null)
        {
        annotation.getEnumField().setAnnotationList(null);
        annotation.getEnumField().setTab(null);
        annotation.getEnumField().setEnumEntity(null);
        annotation.getEnumField().setEntity(null);
        }
        if (annotation.getRelationship()!=null)
        {
        annotation.getRelationship().setEntity(null);
        annotation.getRelationship().setEntityTarget(null);
        annotation.getRelationship().setTab(null);
        annotation.getRelationship().setAnnotationList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.field.Annotation annotation) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.AnnotationAttribute.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        annotation.setAnnotationAttributeList(annotationService.findById(annotation.getAnnotationId()).get(0).getAnnotationAttributeList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        annotation.setField(annotationService.findById(annotation.getAnnotationId()).get(0).getField());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        annotation.setEnumField(annotationService.findById(annotation.getAnnotationId()).get(0).getEnumField());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        annotation.setRelationship(annotationService.findById(annotation.getAnnotationId()).get(0).getRelationship());
    }

    private List<it.anggen.model.field.Annotation> getSecurityMapping(List<it.anggen.model.field.Annotation> annotationList) {
        for (it.anggen.model.field.Annotation annotation: annotationList)
        {
        getSecurityMapping(annotation);
        }
        return annotationList;
    }

    private void getSecurityMapping(it.anggen.model.field.Annotation annotation) {
        if (securityEnabled && annotation.getAnnotationAttributeList()!=null && !securityService.hasPermission(it.anggen.model.field.AnnotationAttribute.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        annotation.setAnnotationAttributeList(null);

        if (securityEnabled && annotation.getField()!=null  && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        annotation.setField(null);

        if (securityEnabled && annotation.getEnumField()!=null  && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        annotation.setEnumField(null);

        if (securityEnabled && annotation.getRelationship()!=null  && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        annotation.setRelationship(null);

    }

}
