
package it.anggen.controller.relationship;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.relationship.RelationshipSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.log.LogEntryService;
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
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.relationship.Relationship.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "relationship";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.relationship.Relationship> page = relationshipService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RelationshipSearchBean relationship) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.relationship.Relationship> relationshipList;
        if (relationship.getRelationshipId()!=null)
         log.info("Searching relationship like {}", relationship.getName()+' '+ relationship.getRelationshipId());
        logEntryService.addLogEntry( "Searching entity like "+ relationship.getName()+' '+ relationship.getRelationshipId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.relationship.Relationship.staticEntityId, securityService.getLoggedUser(),log);
        relationshipList=relationshipService.find(relationship);
        getSecurityMapping(relationshipList);
        getRightMapping(relationshipList);
         log.info("Search: returning {} relationship.",relationshipList.size());
        return ResponseEntity.ok().body(relationshipList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{relationshipId}", method = RequestMethod.GET)
    public ResponseEntity getRelationshipById(
        @PathVariable
        String relationshipId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching relationship with id "+relationshipId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.relationship.Relationship.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.relationship.Relationship> relationshipList=relationshipService.findById(Long.valueOf(relationshipId));
        getSecurityMapping(relationshipList);
        getRightMapping(relationshipList);
         log.info("Search: returning {} relationship.",relationshipList.size());
        return ResponseEntity.ok().body(relationshipList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{relationshipId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRelationshipById(
        @PathVariable
        String relationshipId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting relationship with id "+relationshipId);
        logEntryService.addLogEntry( "Deleting relationship with id {}"+relationshipId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.relationship.Relationship.staticEntityId, securityService.getLoggedUser(),log);
        relationshipService.deleteById(Long.valueOf(relationshipId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRelationship(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.relationship.Relationship relationship) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (relationship.getRelationshipId()!=null)
        log.info("Inserting relationship like "+ relationship.getName()+' '+ relationship.getRelationshipId());
        it.anggen.model.relationship.Relationship insertedRelationship=relationshipService.insert(relationship);
        getRightMapping(insertedRelationship);
        logEntryService.addLogEntry( "Inserted relationship with id "+ insertedRelationship.getRelationshipId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.relationship.Relationship.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedRelationship);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRelationship(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.relationship.Relationship relationship) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.relationship.Relationship.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating relationship with id "+relationship.getRelationshipId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.relationship.Relationship.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(relationship);
        it.anggen.model.relationship.Relationship updatedRelationship=relationshipService.update(relationship);
        getSecurityMapping(updatedRelationship);
        getRightMapping(updatedRelationship);
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
        if (relationship.getAnnotationList()!=null)
        for (it.anggen.model.field.Annotation annotation :relationship.getAnnotationList())

        {

        annotation.setAnnotationAttributeList(null);
        annotation.setField(null);
        annotation.setEnumField(null);
        annotation.setRelationship(null);
        }
        if (relationship.getEntityTarget()!=null)
        {
        relationship.getEntityTarget().setRestrictionEntityList(null);
        relationship.getEntityTarget().setFieldList(null);
        relationship.getEntityTarget().setEnumFieldList(null);
        relationship.getEntityTarget().setEntityGroup(null);
        relationship.getEntityTarget().setTabList(null);
        relationship.getEntityTarget().setRelationshipList(null);
        }
        if (relationship.getEntity()!=null)
        {
        relationship.getEntity().setRestrictionEntityList(null);
        relationship.getEntity().setFieldList(null);
        relationship.getEntity().setEnumFieldList(null);
        relationship.getEntity().setEntityGroup(null);
        relationship.getEntity().setTabList(null);
        relationship.getEntity().setRelationshipList(null);
        }
        if (relationship.getTab()!=null)
        {
        relationship.getTab().setEntity(null);
        relationship.getTab().setFieldList(null);
        relationship.getTab().setEnumFieldList(null);
        relationship.getTab().setRelationshipList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.relationship.Relationship relationship) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        relationship.setAnnotationList(relationshipService.findById(relationship.getRelationshipId()).get(0).getAnnotationList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        relationship.setEntityTarget(relationshipService.findById(relationship.getRelationshipId()).get(0).getEntityTarget());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        relationship.setEntity(relationshipService.findById(relationship.getRelationshipId()).get(0).getEntity());
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
        if (securityEnabled && relationship.getAnnotationList()!=null && !securityService.hasPermission(it.anggen.model.field.Annotation.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        relationship.setAnnotationList(null);

        if (securityEnabled && relationship.getEntityTarget()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        relationship.setEntityTarget(null);

        if (securityEnabled && relationship.getEntity()!=null  && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        relationship.setEntity(null);

        if (securityEnabled && relationship.getTab()!=null  && !securityService.hasPermission(it.anggen.model.entity.Tab.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        relationship.setTab(null);

    }

}
