
package it.polimi.controller;

import java.util.List;

import it.polimi.model.field.Annotation;
import it.polimi.searchbean.AnnotationSearchBean;
import it.polimi.service.AnnotationService;

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
@RequestMapping("/annotation")
public class AnnotationController {

    @Autowired
    public AnnotationService annotationService;
    private final static Logger log = LoggerFactory.getLogger(Annotation.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "annotation";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        AnnotationSearchBean annotation) {
        List<Annotation> annotationList;
        if (annotation.getAnnotationId()!=null)
         log.info("Searching annotation like {}",annotation.toString());
        annotationList=annotationService.find(annotation);
        getRightMapping(annotationList);
         log.info("Search: returning {} annotation.",annotationList.size());
        return ResponseEntity.ok().body(annotationList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationId}", method = RequestMethod.GET)
    public ResponseEntity getannotationById(
        @PathVariable
        String annotationId) {
        log.info("Searching annotation with id {}",annotationId);
        List<Annotation> annotationList=annotationService.findById(java.lang.Long.valueOf(annotationId));
        getRightMapping(annotationList);
         log.info("Search: returning {} annotation.",annotationList.size());
        return ResponseEntity.ok().body(annotationList);
    }

    @ResponseBody
    @RequestMapping(value = "/{annotationId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteannotationById(
        @PathVariable
        String annotationId) {
        log.info("Deleting annotation with id {}",annotationId);
        annotationService.deleteById(java.lang.Long.valueOf(annotationId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertannotation(
        @org.springframework.web.bind.annotation.RequestBody
        Annotation annotation) {
        if (annotation.getAnnotationId()!=null)
        log.info("Inserting annotation like {}",annotation.toString());
        Annotation insertedannotation=annotationService.insert(annotation);
        getRightMapping(insertedannotation);
        log.info("Inserted annotation with id {}",insertedannotation.getAnnotationId());
        return ResponseEntity.ok().body(insertedannotation);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateannotation(
        @org.springframework.web.bind.annotation.RequestBody
        Annotation annotation) {
        log.info("Updating annotation with id {}",annotation.getAnnotationId());
        Annotation updatedannotation=annotationService.update(annotation);
        getRightMapping(updatedannotation);
        return ResponseEntity.ok().body(updatedannotation);
    }

    private List<Annotation> getRightMapping(List<Annotation> annotationList) {
        for (Annotation annotation: annotationList)
        {
        getRightMapping(annotation);
        }
        return annotationList;
    }

    private void getRightMapping(Annotation annotation) {
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
        annotation.getRelationship().setEntityTarget(null);
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

}
