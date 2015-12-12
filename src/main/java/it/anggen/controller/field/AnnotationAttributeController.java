
package it.anggen.controller.field;

import java.util.List;

import it.anggen.searchbean.field.AnnotationAttributeSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.field.AnnotationAttributeService;

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
@RequestMapping("/annotationAttribute")
public class AnnotationAttributeController {

    @org.springframework.beans.factory.annotation.Autowired
    private AnnotationAttributeService annotationAttributeService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.field.AnnotationAttribute.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.AnnotationAttribute.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "annotationAttribute";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        AnnotationAttributeSearchBean annotationAttribute) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.AnnotationAttribute.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.field.AnnotationAttribute> annotationAttributeList;
        if (annotationAttribute.getAnnotationAttributeId()!=null)
         log.info("Searching annotationAttribute like {}", annotationAttribute.getAnnotationAttributeId()+' '+ annotationAttribute.getProperty());
        annotationAttributeList=annotationAttributeService.find(annotationAttribute);
        getRightMapping(annotationAttributeList);
        getSecurityMapping(annotationAttributeList);
         log.info("Search: returning {} annotationAttribute.",annotationAttributeList.size());
        return ResponseEntity.ok().body(annotationAttributeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationAttributeId}", method = RequestMethod.GET)
    public ResponseEntity getAnnotationAttributeById(
        @PathVariable
        String annotationAttributeId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.AnnotationAttribute.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching annotationAttribute with id {}",annotationAttributeId);
        List<it.anggen.model.field.AnnotationAttribute> annotationAttributeList=annotationAttributeService.findById(Long.valueOf(annotationAttributeId));
        getRightMapping(annotationAttributeList);
         log.info("Search: returning {} annotationAttribute.",annotationAttributeList.size());
        return ResponseEntity.ok().body(annotationAttributeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationAttributeId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAnnotationAttributeById(
        @PathVariable
        String annotationAttributeId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.AnnotationAttribute.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting annotationAttribute with id {}",annotationAttributeId);
        annotationAttributeService.deleteById(Long.valueOf(annotationAttributeId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertAnnotationAttribute(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.AnnotationAttribute annotationAttribute) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.AnnotationAttribute.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (annotationAttribute.getAnnotationAttributeId()!=null)
        log.info("Inserting annotationAttribute like {}", annotationAttribute.getAnnotationAttributeId()+' '+ annotationAttribute.getProperty());
        it.anggen.model.field.AnnotationAttribute insertedAnnotationAttribute=annotationAttributeService.insert(annotationAttribute);
        getRightMapping(insertedAnnotationAttribute);
        log.info("Inserted annotationAttribute with id {}",insertedAnnotationAttribute.getAnnotationAttributeId());
        return ResponseEntity.ok().body(insertedAnnotationAttribute);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateAnnotationAttribute(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.field.AnnotationAttribute annotationAttribute) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.AnnotationAttribute.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating annotationAttribute with id {}",annotationAttribute.getAnnotationAttributeId());
        rebuildSecurityMapping(annotationAttribute);
        it.anggen.model.field.AnnotationAttribute updatedAnnotationAttribute=annotationAttributeService.update(annotationAttribute);
        getRightMapping(updatedAnnotationAttribute);
        getSecurityMapping(updatedAnnotationAttribute);
        return ResponseEntity.ok().body(updatedAnnotationAttribute);
    }

    private List<it.anggen.model.field.AnnotationAttribute> getRightMapping(List<it.anggen.model.field.AnnotationAttribute> annotationAttributeList) {
        for (it.anggen.model.field.AnnotationAttribute annotationAttribute: annotationAttributeList)
        {
        getRightMapping(annotationAttribute);
        }
        return annotationAttributeList;
    }

    private void getRightMapping(it.anggen.model.field.AnnotationAttribute annotationAttribute) {
        if (annotationAttribute.getAnnotation()!=null)
        {
        annotationAttribute.getAnnotation().setAnnotationAttributeList(null);
        annotationAttribute.getAnnotation().setField(null);
        annotationAttribute.getAnnotation().setRelationship(null);
        annotationAttribute.getAnnotation().setEnumField(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.field.AnnotationAttribute annotationAttribute) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        annotationAttribute.setAnnotation(annotationAttributeService.findById(annotationAttribute.getAnnotationAttributeId()).get(0).getAnnotation());
    }

    private List<it.anggen.model.field.AnnotationAttribute> getSecurityMapping(List<it.anggen.model.field.AnnotationAttribute> annotationAttributeList) {
        for (it.anggen.model.field.AnnotationAttribute annotationAttribute: annotationAttributeList)
        {
        getSecurityMapping(annotationAttribute);
        }
        return annotationAttributeList;
    }

    private void getSecurityMapping(it.anggen.model.field.AnnotationAttribute annotationAttribute) {
        if (securityEnabled && annotationAttribute.getAnnotation()!=null  && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        annotationAttribute.setAnnotation(null);

    }

}
