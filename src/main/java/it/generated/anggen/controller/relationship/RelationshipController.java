
package it.generated.anggen.controller.relationship;

import java.util.List;
import it.generated.anggen.searchbean.relationship.RelationshipSearchBean;
import it.generated.anggen.security.SecurityService;
import it.generated.anggen.service.relationship.RelationshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/relationship")
public class RelationshipController {

    @org.springframework.beans.factory.annotation.Autowired
    private RelationshipService relationshipService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.generated.anggen.model.relationship.Relationship.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.generated.anggen.model.relationship.Relationship.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "relationship";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RelationshipSearchBean relationship) {
        if (!securityService.isAllowed(it.generated.anggen.model.relationship.Relationship.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.generated.anggen.model.relationship.Relationship> relationshipList;
        if (relationship.getRelationshipId()!=null)
         log.info("Searching relationship like {}",relationship.toString());
        relationshipList=relationshipService.find(relationship);
        getRightMapping(relationshipList);
        getSecurityMapping(relationshipList);
         log.info("Search: returning {} relationship.",relationshipList.size());
        return ResponseEntity.ok().body(relationshipList);
    }

    @ResponseBody
    @RequestMapping(value = "/{relationshipId}", method = RequestMethod.GET)
    public ResponseEntity getRelationshipById(
        @PathVariable
        String relationshipId) {
        if (!securityService.isAllowed(it.generated.anggen.model.relationship.Relationship.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching relationship with id {}",relationshipId);
        List<it.generated.anggen.model.relationship.Relationship> relationshipList=relationshipService.findById(Long.valueOf(relationshipId));
        getRightMapping(relationshipList);
         log.info("Search: returning {} relationship.",relationshipList.size());
        return ResponseEntity.ok().body(relationshipList);
    }

    @ResponseBody
    @RequestMapping(value = "/{relationshipId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRelationshipById(
        @PathVariable
        String relationshipId) {
        if (!securityService.isAllowed(it.generated.anggen.model.relationship.Relationship.staticEntityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting relationship with id {}",relationshipId);
        relationshipService.deleteById(Long.valueOf(relationshipId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRelationship(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.relationship.Relationship relationship) {
        if (!securityService.isAllowed(it.generated.anggen.model.relationship.Relationship.staticEntityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (relationship.getRelationshipId()!=null)
        log.info("Inserting relationship like {}",relationship.toString());
        it.generated.anggen.model.relationship.Relationship insertedRelationship=relationshipService.insert(relationship);
        getRightMapping(insertedRelationship);
        log.info("Inserted relationship with id {}",insertedRelationship.getRelationshipId());
        return ResponseEntity.ok().body(insertedRelationship);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRelationship(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.relationship.Relationship relationship) {
        if (!securityService.isAllowed(it.generated.anggen.model.relationship.Relationship.staticEntityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating relationship with id {}",relationship.getRelationshipId());
        rebuildSecurityMapping(relationship);
        it.generated.anggen.model.relationship.Relationship updatedRelationship=relationshipService.update(relationship);
        getRightMapping(updatedRelationship);
        getSecurityMapping(updatedRelationship);
        return ResponseEntity.ok().body(updatedRelationship);
    }

    private List<it.generated.anggen.model.relationship.Relationship> getRightMapping(List<it.generated.anggen.model.relationship.Relationship> relationshipList) {
        for (it.generated.anggen.model.relationship.Relationship relationship: relationshipList)
        {
        getRightMapping(relationship);
        }
        return relationshipList;
    }

    private void getRightMapping(it.generated.anggen.model.relationship.Relationship relationship) {
        if (relationship.getTab()!=null)
        {
        relationship.getTab().setEnumFieldList(null);
        relationship.getTab().setRelationshipList(null);
        relationship.getTab().setFieldList(null);
        relationship.getTab().setEntity(null);
        }
        if (relationship.getAnnotationList()!=null)
        for (it.generated.anggen.model.field.Annotation annotation :relationship.getAnnotationList())

        {

        annotation.setEnumField(null);
        annotation.setRelationship(null);
        annotation.setField(null);
        annotation.setAnnotationAttributeList(null);
        }
        if (relationship.getEntity()!=null)
        {
        relationship.getEntity().setEntityGroup(null);
        relationship.getEntity().setRestrictionEntityList(null);
        relationship.getEntity().setTabList(null);
        relationship.getEntity().setEnumFieldList(null);
        relationship.getEntity().setRelationshipList(null);
        relationship.getEntity().setFieldList(null);
        }
        if (relationship.getEntity()!=null)
        {
        relationship.getEntity().setEntityGroup(null);
        relationship.getEntity().setRestrictionEntityList(null);
        relationship.getEntity().setTabList(null);
        relationship.getEntity().setEnumFieldList(null);
        relationship.getEntity().setRelationshipList(null);
        relationship.getEntity().setFieldList(null);
        }
    }

    private void rebuildSecurityMapping(it.generated.anggen.model.relationship.Relationship relationship) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Tab.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        relationship.setTab(relationshipService.findById(relationship.getRelationshipId()).get(0).getTab());
        if (!securityService.isAllowed(it.generated.anggen.model.field.Annotation.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        relationship.setAnnotationList(relationshipService.findById(relationship.getRelationshipId()).get(0).getAnnotationList());
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        relationship.setEntity(relationshipService.findById(relationship.getRelationshipId()).get(0).getEntity());
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        relationship.setEntity(relationshipService.findById(relationship.getRelationshipId()).get(0).getEntity());
    }

    private List<it.generated.anggen.model.relationship.Relationship> getSecurityMapping(List<it.generated.anggen.model.relationship.Relationship> relationshipList) {
        for (it.generated.anggen.model.relationship.Relationship relationship: relationshipList)
        {
        getSecurityMapping(relationship);
        }
        return relationshipList;
    }

    private void getSecurityMapping(it.generated.anggen.model.relationship.Relationship relationship) {
        if (relationship.getTab()!=null  && !securityService.isAllowed(it.generated.anggen.model.entity.Tab.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        relationship.setTab(null);

        if (relationship.getAnnotationList()!=null && !securityService.isAllowed(it.generated.anggen.model.field.Annotation.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        relationship.setAnnotationList(null);

        if (relationship.getEntity()!=null  && !securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        relationship.setEntity(null);

        if (relationship.getEntity()!=null  && !securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        relationship.setEntity(null);

    }

}
