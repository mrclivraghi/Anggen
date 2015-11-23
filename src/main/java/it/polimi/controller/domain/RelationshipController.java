
package it.polimi.controller.domain;

import java.util.List;
import it.polimi.model.domain.Relationship;
import it.polimi.searchbean.domain.RelationshipSearchBean;
import it.polimi.service.domain.RelationshipService;
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
@RequestMapping("/relationship")
public class RelationshipController {

    @Autowired
    public RelationshipService relationshipService;
    private final static Logger log = LoggerFactory.getLogger(Relationship.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "relationship";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RelationshipSearchBean relationship) {
        List<Relationship> relationshipList;
        if (relationship.getRelationshipId()!=null)
         log.info("Searching relationship like {}",relationship.toString());
        relationshipList=relationshipService.find(relationship);
        getRightMapping(relationshipList);
         log.info("Search: returning {} relationship.",relationshipList.size());
        return ResponseEntity.ok().body(relationshipList);
    }

    @ResponseBody
    @RequestMapping(value = "/{relationshipId}", method = RequestMethod.GET)
    public ResponseEntity getrelationshipById(
        @PathVariable
        String relationshipId) {
        log.info("Searching relationship with id {}",relationshipId);
        List<Relationship> relationshipList=relationshipService.findById(java.lang.Long.valueOf(relationshipId));
        getRightMapping(relationshipList);
         log.info("Search: returning {} relationship.",relationshipList.size());
        return ResponseEntity.ok().body(relationshipList);
    }

    @ResponseBody
    @RequestMapping(value = "/{relationshipId}", method = RequestMethod.DELETE)
    public ResponseEntity deleterelationshipById(
        @PathVariable
        String relationshipId) {
        log.info("Deleting relationship with id {}",relationshipId);
        relationshipService.deleteById(java.lang.Long.valueOf(relationshipId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertrelationship(
        @org.springframework.web.bind.annotation.RequestBody
        Relationship relationship) {
        if (relationship.getRelationshipId()!=null)
        log.info("Inserting relationship like {}",relationship.toString());
        Relationship insertedrelationship=relationshipService.insert(relationship);
        getRightMapping(insertedrelationship);
        log.info("Inserted relationship with id {}",insertedrelationship.getRelationshipId());
        return ResponseEntity.ok().body(insertedrelationship);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updaterelationship(
        @org.springframework.web.bind.annotation.RequestBody
        Relationship relationship) {
        log.info("Updating relationship with id {}",relationship.getRelationshipId());
        Relationship updatedrelationship=relationshipService.update(relationship);
        getRightMapping(updatedrelationship);
        return ResponseEntity.ok().body(updatedrelationship);
    }

    private List<Relationship> getRightMapping(List<Relationship> relationshipList) {
        for (Relationship relationship: relationshipList)
        {
        getRightMapping(relationship);
        }
        return relationshipList;
    }

    private void getRightMapping(Relationship relationship) {
        if (relationship.getEntity()!=null)
        {
        relationship.getEntity().setFieldList(null);
        relationship.getEntity().setRelationshipList(null);
        relationship.getEntity().setEnumFieldList(null);
        relationship.getEntity().setTabList(null);
        relationship.getEntity().setRestrictionList(null);
        }
        if (relationship.getEntityTarget()!=null)
        {
        relationship.getEntityTarget().setFieldList(null);
        relationship.getEntityTarget().setRelationshipList(null);
        relationship.getEntityTarget().setEnumFieldList(null);
        relationship.getEntityTarget().setTabList(null);
        relationship.getEntityTarget().setRestrictionList(null);
        }
        if (relationship.getAnnotationList()!=null)
        for (it.polimi.model.domain.Annotation annotation :relationship.getAnnotationList())

        {

        annotation.setAnnotationAttributeList(null);
        annotation.setField(null);
        annotation.setRelationship(null);
        annotation.setEnumField(null);
        }
        if (relationship.getTab()!=null)
        {
        relationship.getTab().setEntity(null);
        relationship.getTab().setFieldList(null);
        relationship.getTab().setRelationshipList(null);
        relationship.getTab().setEnumFieldList(null);
        }
    }

}
