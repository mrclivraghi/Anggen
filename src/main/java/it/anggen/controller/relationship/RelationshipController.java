
package it.anggen.controller.relationship;

import java.util.List;
import it.anggen.searchbean.relationship.RelationshipSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.relationship.RelationshipService;
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
@RequestMapping("/relationship")
public class RelationshipController {

    @org.springframework.beans.factory.annotation.Autowired
    private RelationshipService relationshipService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.relationship.Relationship.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "relationship";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RelationshipSearchBean relationship) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.relationship.Relationship> relationshipList;
        if (relationship.getRelationshipId()!=null)
         log.info("Searching relationship like {}", relationship.getRelationshipId()+' '+ relationship.getName());
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
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching relationship with id {}",relationshipId);
        List<it.anggen.model.relationship.Relationship> relationshipList=relationshipService.findById(Long.valueOf(relationshipId));
        getRightMapping(relationshipList);
         log.info("Search: returning {} relationship.",relationshipList.size());
        return ResponseEntity.ok().body(relationshipList);
    }

    @ResponseBody
    @RequestMapping(value = "/{relationshipId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRelationshipById(
        @PathVariable
        String relationshipId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting relationship with id {}",relationshipId);
        relationshipService.deleteById(Long.valueOf(relationshipId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRelationship(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.relationship.Relationship relationship) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (relationship.getRelationshipId()!=null)
        log.info("Inserting relationship like {}", relationship.getRelationshipId()+' '+ relationship.getName());
        it.anggen.model.relationship.Relationship insertedRelationship=relationshipService.insert(relationship);
        getRightMapping(insertedRelationship);
        log.info("Inserted relationship with id {}",insertedRelationship.getRelationshipId());
        return ResponseEntity.ok().body(insertedRelationship);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRelationship(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.relationship.Relationship relationship) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating relationship with id {}",relationship.getRelationshipId());
        rebuildSecurityMapping(relationship);
        it.anggen.model.relationship.Relationship updatedRelationship=relationshipService.update(relationship);
        getRightMapping(updatedRelationship);
        getSecurityMapping(updatedRelationship);
        return ResponseEntity.ok().body(updatedRelationship);
    }

    private List<it.anggen.model.relationship.Relationship> getRightMapping(List<it.anggen.model.relationship.Relationship> relationshipList) {
        for (it.anggen.model.relationship.Relationship relationship: relationshipList)
        {
        getRightMapping(relationship);
        }
        return relationshipList;
    }

    private void getRightMapping(it.anggen.model.relationship.Relationship relationship) {
        if (relationship.getEntity()!=null)
        {
        relationship.getEntity().setFieldList(null);
        relationship.getEntity().setEntityGroup(null);
        relationship.getEntity().setRestrictionEntityList(null);
        relationship.getEntity().setTabList(null);
        relationship.getEntity().setEnumFieldList(null);
        relationship.getEntity().setRelationshipList(null);
        }
        if (relationship.getEntity()!=null)
        {
        relationship.getEntity().setFieldList(null);
        relationship.getEntity().setEntityGroup(null);
        relationship.getEntity().setRestrictionEntityList(null);
        relationship.getEntity().setTabList(null);
        relationship.getEntity().setEnumFieldList(null);
        relationship.getEntity().setRelationshipList(null);
        }
        if (relationship.getAnnotationList()!=null)
        for (it.anggen.model.field.Annotation annotation :relationship.getAnnotationList())

        {

        annotation.setEnumField(null);
        annotation.setRelationship(null);
        annotation.setField(null);
        annotation.setAnnotationAttributeList(null);
        }
        if (relationship.getTab()!=null)
        {
        relationship.getTab().setEnumFieldList(null);
        relationship.getTab().setRelationshipList(null);
        relationship.getTab().setFieldList(null);
        relationship.getTab().setEntity(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.relationship.Relationship relationship) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        relationship.setEntity(relationshipService.findById(relationship.getRelationshipId()).get(0).getEntity());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        relationship.setEntity(relationshipService.findById(relationship.getRelationshipId()).get(0).getEntity());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        relationship.setAnnotationList(relationshipService.findById(relationship.getRelationshipId()).get(0).getAnnotationList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        relationship.setTab(relationshipService.findById(relationship.getRelationshipId()).get(0).getTab());
    }

    private List<it.anggen.model.relationship.Relationship> getSecurityMapping(List<it.anggen.model.relationship.Relationship> relationshipList) {
        for (it.anggen.model.relationship.Relationship relationship: relationshipList)
        {
        getSecurityMapping(relationship);
        }
        return relationshipList;
    }

    private void getSecurityMapping(it.anggen.model.relationship.Relationship relationship) {
        if (securityEnabled && relationship.getEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        relationship.setEntity(null);

        if (securityEnabled && relationship.getEntityTarget()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        relationship.setEntityTarget(null);

        if (securityEnabled && relationship.getAnnotationList()!=null && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        relationship.setAnnotationList(null);

        if (securityEnabled && relationship.getTab()!=null  && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        relationship.setTab(null);

    }

}
