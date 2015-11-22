
package it.polimi.controller;

import java.util.List;

import it.polimi.model.domain.AnnotationAttribute;
import it.polimi.searchbean.AnnotationAttributeSearchBean;
import it.polimi.service.AnnotationAttributeService;

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
@RequestMapping("/annotationAttribute")
public class AnnotationAttributeController {

    @Autowired
    public AnnotationAttributeService annotationAttributeService;
    private final static Logger log = LoggerFactory.getLogger(AnnotationAttribute.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "annotationAttribute";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        AnnotationAttributeSearchBean annotationAttribute) {
        List<AnnotationAttribute> annotationAttributeList;
        if (annotationAttribute.getAnnotationAttributeId()!=null)
         log.info("Searching annotationAttribute like {}",annotationAttribute.toString());
        annotationAttributeList=annotationAttributeService.find(annotationAttribute);
        getRightMapping(annotationAttributeList);
         log.info("Search: returning {} annotationAttribute.",annotationAttributeList.size());
        return ResponseEntity.ok().body(annotationAttributeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationAttributeId}", method = RequestMethod.GET)
    public ResponseEntity getannotationAttributeById(
        @PathVariable
        String annotationAttributeId) {
        log.info("Searching annotationAttribute with id {}",annotationAttributeId);
        List<AnnotationAttribute> annotationAttributeList=annotationAttributeService.findById(java.lang.Long.valueOf(annotationAttributeId));
        getRightMapping(annotationAttributeList);
         log.info("Search: returning {} annotationAttribute.",annotationAttributeList.size());
        return ResponseEntity.ok().body(annotationAttributeList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationAttributeId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteannotationAttributeById(
        @PathVariable
        String annotationAttributeId) {
        log.info("Deleting annotationAttribute with id {}",annotationAttributeId);
        annotationAttributeService.deleteById(java.lang.Long.valueOf(annotationAttributeId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertannotationAttribute(
        @org.springframework.web.bind.annotation.RequestBody
        AnnotationAttribute annotationAttribute) {
        if (annotationAttribute.getAnnotationAttributeId()!=null)
        log.info("Inserting annotationAttribute like {}",annotationAttribute.toString());
        AnnotationAttribute insertedannotationAttribute=annotationAttributeService.insert(annotationAttribute);
        getRightMapping(insertedannotationAttribute);
        log.info("Inserted annotationAttribute with id {}",insertedannotationAttribute.getAnnotationAttributeId());
        return ResponseEntity.ok().body(insertedannotationAttribute);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateannotationAttribute(
        @org.springframework.web.bind.annotation.RequestBody
        AnnotationAttribute annotationAttribute) {
        log.info("Updating annotationAttribute with id {}",annotationAttribute.getAnnotationAttributeId());
        AnnotationAttribute updatedannotationAttribute=annotationAttributeService.update(annotationAttribute);
        getRightMapping(updatedannotationAttribute);
        return ResponseEntity.ok().body(updatedannotationAttribute);
    }

    private List<AnnotationAttribute> getRightMapping(List<AnnotationAttribute> annotationAttributeList) {
        for (AnnotationAttribute annotationAttribute: annotationAttributeList)
        {
        getRightMapping(annotationAttribute);
        }
        return annotationAttributeList;
    }

    private void getRightMapping(AnnotationAttribute annotationAttribute) {
        if (annotationAttribute.getAnnotation()!=null)
        {
        annotationAttribute.getAnnotation().setAnnotationAttributeList(null);
        annotationAttribute.getAnnotation().setField(null);
        annotationAttribute.getAnnotation().setRelationship(null);
        }
    }

}
