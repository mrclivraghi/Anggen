
package it.polimi.controller.field;

import java.util.List;

import it.polimi.model.RestrictionType;
import it.polimi.searchbean.field.AnnotationSearchBean;
import it.polimi.security.SecurityService;
import it.polimi.service.field.AnnotationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(it.polimi.model.field.Annotation.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.hasPermission(it.polimi.model.field.Annotation.staticEntityId, RestrictionType.SEARCH)) 
return "forbidden"; 

        return "annotation";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        AnnotationSearchBean annotation) {
        if (!securityService.hasPermission(it.polimi.model.field.Annotation.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.polimi.model.field.Annotation> annotationList;
        if (annotation.getAnnotationId()!=null)
         log.info("Searching annotation like {}",annotation.toString());
        annotationList=annotationService.find(annotation);
        getRightMapping(annotationList);
        getSecurityMapping(annotationList);
         log.info("Search: returning {} annotation.",annotationList.size());
        return ResponseEntity.ok().body(annotationList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationId}", method = RequestMethod.GET)
    public ResponseEntity getAnnotationById(
        @PathVariable
        String annotationId) {
        if (!securityService.hasPermission(it.polimi.model.field.Annotation.staticEntityId, RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching annotation with id {}",annotationId);
        List<it.polimi.model.field.Annotation> annotationList=annotationService.findById(Long.valueOf(annotationId));
        getRightMapping(annotationList);
         log.info("Search: returning {} annotation.",annotationList.size());
        return ResponseEntity.ok().body(annotationList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAnnotationById(
        @PathVariable
        String annotationId) {
        if (!securityService.hasPermission(it.polimi.model.field.Annotation.staticEntityId, RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting annotation with id {}",annotationId);
        annotationService.deleteById(Long.valueOf(annotationId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertAnnotation(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.field.Annotation annotation) {
        if (!securityService.hasPermission(it.polimi.model.field.Annotation.staticEntityId, RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (annotation.getAnnotationId()!=null)
        log.info("Inserting annotation like {}",annotation.toString());
        it.polimi.model.field.Annotation insertedAnnotation=annotationService.insert(annotation);
        getRightMapping(insertedAnnotation);
        log.info("Inserted annotation with id {}",insertedAnnotation.getAnnotationId());
        return ResponseEntity.ok().body(insertedAnnotation);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateAnnotation(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.model.field.Annotation annotation) {
        if (!securityService.hasPermission(it.polimi.model.field.Annotation.staticEntityId, RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating annotation with id {}",annotation.getAnnotationId());
        rebuildSecurityMapping(annotation);
        it.polimi.model.field.Annotation updatedAnnotation=annotationService.update(annotation);
        getRightMapping(updatedAnnotation);
        getSecurityMapping(updatedAnnotation);
        return ResponseEntity.ok().body(updatedAnnotation);
    }

    private List<it.polimi.model.field.Annotation> getRightMapping(List<it.polimi.model.field.Annotation> annotationList) {
        for (it.polimi.model.field.Annotation annotation: annotationList)
        {
        getRightMapping(annotation);
        }
        return annotationList;
    }

    private void getRightMapping(it.polimi.model.field.Annotation annotation) {
        if (annotation.getAnnotationAttributeList()!=null)
        for (it.polimi.model.field.AnnotationAttribute annotationAttribute :annotation.getAnnotationAttributeList())

        {

        annotationAttribute.setAnnotation(null);
        }
        if (annotation.getField()!=null)
        {
        annotation.getField().setEntity(null);
        annotation.getField().setAnnotationList(null);
        annotation.getField().setRestrictionFieldList(null);
        annotation.getField().setTab(null);
        }
        if (annotation.getRelationship()!=null)
        {
        annotation.getRelationship().setEntity(null);
        annotation.getRelationship().setEntity(null);
        annotation.getRelationship().setAnnotationList(null);
        annotation.getRelationship().setTab(null);
        }
        if (annotation.getEnumField()!=null)
        {
        annotation.getEnumField().setEnumValueList(null);
        annotation.getEnumField().setEntity(null);
        annotation.getEnumField().setAnnotationList(null);
        annotation.getEnumField().setTab(null);
        }
    }

    private void rebuildSecurityMapping(it.polimi.model.field.Annotation annotation) {
        if (!securityService.hasPermission(it.polimi.model.field.AnnotationAttribute.staticEntityId, RestrictionType.SEARCH))
        annotation.setAnnotationAttributeList(annotationService.findById(annotation.getAnnotationId()).get(0).getAnnotationAttributeList());
        if (!securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.SEARCH))
        annotation.setField(annotationService.findById(annotation.getAnnotationId()).get(0).getField());
        if (!securityService.hasPermission(it.polimi.model.relationship.Relationship.staticEntityId, RestrictionType.SEARCH))
        annotation.setRelationship(annotationService.findById(annotation.getAnnotationId()).get(0).getRelationship());
        if (!securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH))
        annotation.setEnumField(annotationService.findById(annotation.getAnnotationId()).get(0).getEnumField());
    }

    private List<it.polimi.model.field.Annotation> getSecurityMapping(List<it.polimi.model.field.Annotation> annotationList) {
        for (it.polimi.model.field.Annotation annotation: annotationList)
        {
        getSecurityMapping(annotation);
        }
        return annotationList;
    }

    private void getSecurityMapping(it.polimi.model.field.Annotation annotation) {
        if (annotation.getAnnotationAttributeList()!=null && !securityService.hasPermission(it.polimi.model.field.AnnotationAttribute.staticEntityId, RestrictionType.SEARCH) )
        annotation.setAnnotationAttributeList(null);

        if (annotation.getField()!=null  && !securityService.hasPermission(it.polimi.model.field.Field.staticEntityId, RestrictionType.SEARCH) )
        annotation.setField(null);

        if (annotation.getRelationship()!=null  && !securityService.hasPermission(it.polimi.model.relationship.Relationship.staticEntityId, RestrictionType.SEARCH) )
        annotation.setRelationship(null);

        if (annotation.getEnumField()!=null  && !securityService.hasPermission(it.polimi.model.field.EnumField.staticEntityId, RestrictionType.SEARCH) )
        annotation.setEnumField(null);

    }

}
