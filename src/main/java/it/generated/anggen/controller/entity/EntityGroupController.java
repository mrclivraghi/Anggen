
package it.generated.anggen.controller.entity;

import java.util.List;
import it.generated.anggen.searchbean.entity.EntityGroupSearchBean;
import it.generated.anggen.security.SecurityService;
import it.generated.anggen.service.entity.EntityGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/entityGroup")
public class EntityGroupController {

    @org.springframework.beans.factory.annotation.Autowired
    private EntityGroupService entityGroupService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.generated.anggen.model.entity.EntityGroup.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.EntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "entityGroup";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EntityGroupSearchBean entityGroup) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.EntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.generated.anggen.model.entity.EntityGroup> entityGroupList;
        if (entityGroup.getEntityGroupId()!=null)
         log.info("Searching entityGroup like {}",entityGroup.toString());
        entityGroupList=entityGroupService.find(entityGroup);
        getRightMapping(entityGroupList);
        getSecurityMapping(entityGroupList);
         log.info("Search: returning {} entityGroup.",entityGroupList.size());
        return ResponseEntity.ok().body(entityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityGroupId}", method = RequestMethod.GET)
    public ResponseEntity getEntityGroupById(
        @PathVariable
        String entityGroupId) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.EntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching entityGroup with id {}",entityGroupId);
        List<it.generated.anggen.model.entity.EntityGroup> entityGroupList=entityGroupService.findById(Long.valueOf(entityGroupId));
        getRightMapping(entityGroupList);
         log.info("Search: returning {} entityGroup.",entityGroupList.size());
        return ResponseEntity.ok().body(entityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityGroupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityGroupById(
        @PathVariable
        String entityGroupId) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.EntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting entityGroup with id {}",entityGroupId);
        entityGroupService.deleteById(Long.valueOf(entityGroupId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.entity.EntityGroup entityGroup) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.EntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (entityGroup.getEntityGroupId()!=null)
        log.info("Inserting entityGroup like {}",entityGroup.toString());
        it.generated.anggen.model.entity.EntityGroup insertedEntityGroup=entityGroupService.insert(entityGroup);
        getRightMapping(insertedEntityGroup);
        log.info("Inserted entityGroup with id {}",insertedEntityGroup.getEntityGroupId());
        return ResponseEntity.ok().body(insertedEntityGroup);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.entity.EntityGroup entityGroup) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.EntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating entityGroup with id {}",entityGroup.getEntityGroupId());
        rebuildSecurityMapping(entityGroup);
        it.generated.anggen.model.entity.EntityGroup updatedEntityGroup=entityGroupService.update(entityGroup);
        getRightMapping(updatedEntityGroup);
        getSecurityMapping(updatedEntityGroup);
        return ResponseEntity.ok().body(updatedEntityGroup);
    }

    private List<it.generated.anggen.model.entity.EntityGroup> getRightMapping(List<it.generated.anggen.model.entity.EntityGroup> entityGroupList) {
        for (it.generated.anggen.model.entity.EntityGroup entityGroup: entityGroupList)
        {
        getRightMapping(entityGroup);
        }
        return entityGroupList;
    }

    private void getRightMapping(it.generated.anggen.model.entity.EntityGroup entityGroup) {
        if (entityGroup.getProject()!=null)
        {
        entityGroup.getProject().setEntityGroupList(null);
        }
        if (entityGroup.getRestrictionEntityGroupList()!=null)
        for (it.generated.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup :entityGroup.getRestrictionEntityGroupList())

        {

        restrictionEntityGroup.setRole(null);
        restrictionEntityGroup.setEntityGroup(null);
        }
        if (entityGroup.getEntityList()!=null)
        for (it.generated.anggen.model.entity.Entity entity :entityGroup.getEntityList())

        {

        entity.setEntityGroup(null);
        entity.setRestrictionEntityList(null);
        entity.setTabList(null);
        entity.setEnumFieldList(null);
        entity.setRelationshipList(null);
        entity.setFieldList(null);
        }
    }

    private void rebuildSecurityMapping(it.generated.anggen.model.entity.EntityGroup entityGroup) {
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Project.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        entityGroup.setProject(entityGroupService.findById(entityGroup.getEntityGroupId()).get(0).getProject());
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        entityGroup.setRestrictionEntityGroupList(entityGroupService.findById(entityGroup.getEntityGroupId()).get(0).getRestrictionEntityGroupList());
        if (!securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        entityGroup.setEntityList(entityGroupService.findById(entityGroup.getEntityGroupId()).get(0).getEntityList());
    }

    private List<it.generated.anggen.model.entity.EntityGroup> getSecurityMapping(List<it.generated.anggen.model.entity.EntityGroup> entityGroupList) {
        for (it.generated.anggen.model.entity.EntityGroup entityGroup: entityGroupList)
        {
        getSecurityMapping(entityGroup);
        }
        return entityGroupList;
    }

    private void getSecurityMapping(it.generated.anggen.model.entity.EntityGroup entityGroup) {
        if (entityGroup.getProject()!=null  && !securityService.isAllowed(it.generated.anggen.model.entity.Project.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        entityGroup.setProject(null);

        if (entityGroup.getRestrictionEntityGroupList()!=null && !securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        entityGroup.setRestrictionEntityGroupList(null);

        if (entityGroup.getEntityList()!=null && !securityService.isAllowed(it.generated.anggen.model.entity.Entity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        entityGroup.setEntityList(null);

    }

}
