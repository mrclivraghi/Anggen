
package it.anggen.controller.field;

import java.util.List;
import it.anggen.searchbean.field.AnnotationSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.field.AnnotationService;
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
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.field.Annotation.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "annotation";
    }

    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.field.Annotation> page = annotationService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

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
        annotationList=annotationService.find(annotation);
        getSecurityMapping(annotationList);
        getRightMapping(annotationList);
         log.info("Search: returning {} annotation.",annotationList.size());
        return ResponseEntity.ok().body(annotationList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationId}", method = RequestMethod.GET)
    public ResponseEntity getAnnotationById(
        @PathVariable
        String annotationId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching annotation with id {}",annotationId);
        List<it.anggen.model.field.Annotation> annotationList=annotationService.findById(Long.valueOf(annotationId));
        getSecurityMapping(annotationList);
        getRightMapping(annotationList);
         log.info("Search: returning {} annotation.",annotationList.size());
        return ResponseEntity.ok().body(annotationList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAnnotationById(
        @PathVariable
        String annotationId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting annotation with id {}",annotationId);
        annotationService.deleteById(Long.valueOf(annotationId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertAnnotation(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.Annotation annotation) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (annotation.getAnnotationId()!=null)
        log.info("Inserting annotation like {}", annotation.getAnnotationId());
        it.anggen.model.field.Annotation insertedAnnotation=annotationService.insert(annotation);
        getRightMapping(insertedAnnotation);
        log.info("Inserted annotation with id {}",insertedAnnotation.getAnnotationId());
        return ResponseEntity.ok().body(insertedAnnotation);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateAnnotation(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.Annotation annotation) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating annotation with id {}",annotation.getAnnotationId());
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
        if (annotation.getRelationship()!=null)
        {
        annotation.getRelationship().setAnnotationList(null);
        annotation.getRelationship().setEntity(null);
        annotation.getRelationship().setEntityTarget(null);
        annotation.getRelationship().setTab(null);
        }
        if (annotation.getField()!=null)
        {
        annotation.getField().setTab(null);
        annotation.getField().setEntity(null);
        annotation.getField().setAnnotationList(null);
        annotation.getField().setRestrictionFieldList(null);
        }
        if (annotation.getEnumField()!=null)
        {
        annotation.getEnumField().setTab(null);
        annotation.getEnumField().setEnumEntity(null);
        annotation.getEnumField().setEntity(null);
        annotation.getEnumField().setAnnotationList(null);
        }
        if (annotation.getAnnotationAttributeList()!=null)
        for (it.anggen.model.field.AnnotationAttribute annotationAttribute :annotation.getAnnotationAttributeList())

        {

        annotationAttribute.setAnnotation(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.field.Annotation annotation) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        annotation.setRelationship(annotationService.findById(annotation.getAnnotationId()).get(0).getRelationship());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        annotation.setField(annotationService.findById(annotation.getAnnotationId()).get(0).getField());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        annotation.setEnumField(annotationService.findById(annotation.getAnnotationId()).get(0).getEnumField());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.AnnotationAttribute.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        annotation.setAnnotationAttributeList(annotationService.findById(annotation.getAnnotationId()).get(0).getAnnotationAttributeList());
    }

    private List<it.anggen.model.field.Annotation> getSecurityMapping(List<it.anggen.model.field.Annotation> annotationList) {
        for (it.anggen.model.field.Annotation annotation: annotationList)
        {
        getSecurityMapping(annotation);
        }
        return annotationList;
    }

    private void getSecurityMapping(it.anggen.model.field.Annotation annotation) {
        if (securityEnabled && annotation.getRelationship()!=null  && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        annotation.setRelationship(null);

        if (securityEnabled && annotation.getField()!=null  && !securityService.hasPermission(it.anggen.model.field.Field.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        annotation.setField(null);

        if (securityEnabled && annotation.getEnumField()!=null  && !securityService.hasPermission(it.anggen.model.field.EnumField.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        annotation.setEnumField(null);

        if (securityEnabled && annotation.getAnnotationAttributeList()!=null && !securityService.hasPermission(it.anggen.model.field.AnnotationAttribute.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        annotation.setAnnotationAttributeList(null);

    }

}
