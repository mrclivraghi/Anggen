
package it.generated.anggen.controller.field;

import java.util.List;
import it.generated.anggen.searchbean.field.AnnotationSearchBean;
import it.generated.anggen.security.SecurityService;
import it.generated.anggen.service.field.AnnotationService;
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
    private final static Logger log = LoggerFactory.getLogger(it.generated.anggen.model.field.Annotation.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Annotation.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "annotation";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        AnnotationSearchBean annotation) {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Annotation.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.generated.anggen.model.field.Annotation> annotationList;
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
        if (!securityService.isAllowed(it.generated.anggen.model.field.Annotation.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching annotation with id {}",annotationId);
        List<it.generated.anggen.model.field.Annotation> annotationList=annotationService.findById(Long.valueOf(annotationId));
        getRightMapping(annotationList);
         log.info("Search: returning {} annotation.",annotationList.size());
        return ResponseEntity.ok().body(annotationList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAnnotationById(
        @PathVariable
        String annotationId) {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Annotation.staticEntityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting annotation with id {}",annotationId);
        annotationService.deleteById(Long.valueOf(annotationId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertAnnotation(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.field.Annotation annotation) {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Annotation.staticEntityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (annotation.getAnnotationId()!=null)
        log.info("Inserting annotation like {}",annotation.toString());
        it.generated.anggen.model.field.Annotation insertedAnnotation=annotationService.insert(annotation);
        getRightMapping(insertedAnnotation);
        log.info("Inserted annotation with id {}",insertedAnnotation.getAnnotationId());
        return ResponseEntity.ok().body(insertedAnnotation);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateAnnotation(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.field.Annotation annotation) {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Annotation.staticEntityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating annotation with id {}",annotation.getAnnotationId());
        rebuildSecurityMapping(annotation);
        it.generated.anggen.model.field.Annotation updatedAnnotation=annotationService.update(annotation);
        getRightMapping(updatedAnnotation);
        getSecurityMapping(updatedAnnotation);
        return ResponseEntity.ok().body(updatedAnnotation);
    }

    private List<it.generated.anggen.model.field.Annotation> getRightMapping(List<it.generated.anggen.model.field.Annotation> annotationList) {
        for (it.generated.anggen.model.field.Annotation annotation: annotationList)
        {
        getRightMapping(annotation);
        }
        return annotationList;
    }

    private void getRightMapping(it.generated.anggen.model.field.Annotation annotation) {
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
        if (annotation.getAnnotationAttributeList()!=null)
        for (it.generated.anggen.model.field.AnnotationAttribute annotationAttribute :annotation.getAnnotationAttributeList())

        {

        annotationAttribute.setAnnotation(null);
        }
    }

    private void rebuildSecurityMapping(it.generated.anggen.model.field.Annotation annotation) {
        if (!securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        annotation.setField(annotationService.findById(annotation.getAnnotationId()).get(0).getField());
        if (!securityService.isAllowed(it.generated.anggen.model.relationship.Relationship.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        annotation.setRelationship(annotationService.findById(annotation.getAnnotationId()).get(0).getRelationship());
        if (!securityService.isAllowed(it.generated.anggen.model.field.EnumField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        annotation.setEnumField(annotationService.findById(annotation.getAnnotationId()).get(0).getEnumField());
        if (!securityService.isAllowed(it.generated.anggen.model.field.AnnotationAttribute.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        annotation.setAnnotationAttributeList(annotationService.findById(annotation.getAnnotationId()).get(0).getAnnotationAttributeList());
    }

    private List<it.generated.anggen.model.field.Annotation> getSecurityMapping(List<it.generated.anggen.model.field.Annotation> annotationList) {
        for (it.generated.anggen.model.field.Annotation annotation: annotationList)
        {
        getSecurityMapping(annotation);
        }
        return annotationList;
    }

    private void getSecurityMapping(it.generated.anggen.model.field.Annotation annotation) {
        if (annotation.getField()!=null  && !securityService.isAllowed(it.generated.anggen.model.field.Field.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        annotation.setField(null);

        if (annotation.getRelationship()!=null  && !securityService.isAllowed(it.generated.anggen.model.relationship.Relationship.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        annotation.setRelationship(null);

        if (annotation.getEnumField()!=null  && !securityService.isAllowed(it.generated.anggen.model.field.EnumField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        annotation.setEnumField(null);

        if (annotation.getAnnotationAttributeList()!=null && !securityService.isAllowed(it.generated.anggen.model.field.AnnotationAttribute.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        annotation.setAnnotationAttributeList(null);

    }

}
