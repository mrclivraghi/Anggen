
package it.anggen.controller.entity;

import java.util.List;
import it.anggen.searchbean.entity.EntityGroupSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.entity.EntityGroupService;
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
@RequestMapping("/entityGroup")
public class EntityGroupController {

    @org.springframework.beans.factory.annotation.Autowired
    private EntityGroupService entityGroupService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.entity.EntityGroup.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "entityGroup";
    }

    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.entity.EntityGroup> page = entityGroupService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        EntityGroupSearchBean entityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.entity.EntityGroup> entityGroupList;
        if (entityGroup.getEntityGroupId()!=null)
         log.info("Searching entityGroup like {}", entityGroup.getName()+' '+ entityGroup.getEntityGroupId());
        entityGroupList=entityGroupService.find(entityGroup);
        getSecurityMapping(entityGroupList);
        getRightMapping(entityGroupList);
         log.info("Search: returning {} entityGroup.",entityGroupList.size());
        return ResponseEntity.ok().body(entityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityGroupId}", method = RequestMethod.GET)
    public ResponseEntity getEntityGroupById(
        @PathVariable
        String entityGroupId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching entityGroup with id {}",entityGroupId);
        List<it.anggen.model.entity.EntityGroup> entityGroupList=entityGroupService.findById(Long.valueOf(entityGroupId));
        getSecurityMapping(entityGroupList);
        getRightMapping(entityGroupList);
         log.info("Search: returning {} entityGroup.",entityGroupList.size());
        return ResponseEntity.ok().body(entityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{entityGroupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityGroupById(
        @PathVariable
        String entityGroupId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting entityGroup with id {}",entityGroupId);
        entityGroupService.deleteById(Long.valueOf(entityGroupId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.EntityGroup entityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (entityGroup.getEntityGroupId()!=null)
        log.info("Inserting entityGroup like {}", entityGroup.getName()+' '+ entityGroup.getEntityGroupId());
        it.anggen.model.entity.EntityGroup insertedEntityGroup=entityGroupService.insert(entityGroup);
        getRightMapping(insertedEntityGroup);
        log.info("Inserted entityGroup with id {}",insertedEntityGroup.getEntityGroupId());
        return ResponseEntity.ok().body(insertedEntityGroup);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.entity.EntityGroup entityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating entityGroup with id {}",entityGroup.getEntityGroupId());
        rebuildSecurityMapping(entityGroup);
        it.anggen.model.entity.EntityGroup updatedEntityGroup=entityGroupService.update(entityGroup);
        getSecurityMapping(updatedEntityGroup);
        getRightMapping(updatedEntityGroup);
        return ResponseEntity.ok().body(updatedEntityGroup);
    }

    private List<it.anggen.model.entity.EntityGroup> getRightMapping(List<it.anggen.model.entity.EntityGroup> entityGroupList) {
        for (it.anggen.model.entity.EntityGroup entityGroup: entityGroupList)
        {
        getRightMapping(entityGroup);
        }
        return entityGroupList;
    }

    private void getRightMapping(it.anggen.model.entity.EntityGroup entityGroup) {
        if (entityGroup.getProject()!=null)
        {
        entityGroup.getProject().setEnumEntityList(null);
        entityGroup.getProject().setEntityGroupList(null);
        }
        if (entityGroup.getEntityList()!=null)
        for (it.anggen.model.entity.Entity entity :entityGroup.getEntityList())

        {

        entity.setRestrictionEntityList(null);
        entity.setEnumFieldList(null);
        entity.setFieldList(null);
        entity.setTabList(null);
        entity.setEntityGroup(null);
        entity.setRelationshipList(null);
        }
        if (entityGroup.getRestrictionEntityGroupList()!=null)
        for (it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup :entityGroup.getRestrictionEntityGroupList())

        {

        restrictionEntityGroup.setEntityGroup(null);
        restrictionEntityGroup.setRole(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.entity.EntityGroup entityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entityGroup.setProject(entityGroupService.findById(entityGroup.getEntityGroupId()).get(0).getProject());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entityGroup.setEntityList(entityGroupService.findById(entityGroup.getEntityGroupId()).get(0).getEntityList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        entityGroup.setRestrictionEntityGroupList(entityGroupService.findById(entityGroup.getEntityGroupId()).get(0).getRestrictionEntityGroupList());
    }

    private List<it.anggen.model.entity.EntityGroup> getSecurityMapping(List<it.anggen.model.entity.EntityGroup> entityGroupList) {
        for (it.anggen.model.entity.EntityGroup entityGroup: entityGroupList)
        {
        getSecurityMapping(entityGroup);
        }
        return entityGroupList;
    }

    private void getSecurityMapping(it.anggen.model.entity.EntityGroup entityGroup) {
        if (securityEnabled && entityGroup.getProject()!=null  && !securityService.hasPermission(it.anggen.model.entity.Project.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entityGroup.setProject(null);

        if (securityEnabled && entityGroup.getEntityList()!=null && !securityService.hasPermission(it.anggen.model.entity.Entity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entityGroup.setEntityList(null);

        if (securityEnabled && entityGroup.getRestrictionEntityGroupList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        entityGroup.setRestrictionEntityGroupList(null);

    }

}
