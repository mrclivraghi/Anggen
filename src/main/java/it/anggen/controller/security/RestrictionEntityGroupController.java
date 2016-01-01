
package it.anggen.controller.security;

import java.util.List;
import it.anggen.searchbean.security.RestrictionEntityGroupSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.security.RestrictionEntityGroupService;
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
@RequestMapping("/restrictionEntityGroup")
public class RestrictionEntityGroupController {

    @org.springframework.beans.factory.annotation.Autowired
    private RestrictionEntityGroupService restrictionEntityGroupService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.security.RestrictionEntityGroup.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "restrictionEntityGroup";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntityGroupSearchBean restrictionEntityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList;
        if (restrictionEntityGroup.getRestrictionEntityGroupId()!=null)
         log.info("Searching restrictionEntityGroup like {}", restrictionEntityGroup.getRestrictionEntityGroupId());
        restrictionEntityGroupList=restrictionEntityGroupService.find(restrictionEntityGroup);
        getRightMapping(restrictionEntityGroupList);
        getSecurityMapping(restrictionEntityGroupList);
         log.info("Search: returning {} restrictionEntityGroup.",restrictionEntityGroupList.size());
        return ResponseEntity.ok().body(restrictionEntityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityGroupId}", method = RequestMethod.GET)
    public ResponseEntity getRestrictionEntityGroupById(
        @PathVariable
        String restrictionEntityGroupId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching restrictionEntityGroup with id {}",restrictionEntityGroupId);
        List<it.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList=restrictionEntityGroupService.findById(Long.valueOf(restrictionEntityGroupId));
        getRightMapping(restrictionEntityGroupList);
        getSecurityMapping(restrictionEntityGroupList);
         log.info("Search: returning {} restrictionEntityGroup.",restrictionEntityGroupList.size());
        return ResponseEntity.ok().body(restrictionEntityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityGroupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRestrictionEntityGroupById(
        @PathVariable
        String restrictionEntityGroupId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting restrictionEntityGroup with id {}",restrictionEntityGroupId);
        restrictionEntityGroupService.deleteById(Long.valueOf(restrictionEntityGroupId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRestrictionEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (restrictionEntityGroup.getRestrictionEntityGroupId()!=null)
        log.info("Inserting restrictionEntityGroup like {}", restrictionEntityGroup.getRestrictionEntityGroupId());
        it.anggen.model.security.RestrictionEntityGroup insertedRestrictionEntityGroup=restrictionEntityGroupService.insert(restrictionEntityGroup);
        getRightMapping(insertedRestrictionEntityGroup);
        log.info("Inserted restrictionEntityGroup with id {}",insertedRestrictionEntityGroup.getRestrictionEntityGroupId());
        return ResponseEntity.ok().body(insertedRestrictionEntityGroup);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRestrictionEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating restrictionEntityGroup with id {}",restrictionEntityGroup.getRestrictionEntityGroupId());
        rebuildSecurityMapping(restrictionEntityGroup);
        it.anggen.model.security.RestrictionEntityGroup updatedRestrictionEntityGroup=restrictionEntityGroupService.update(restrictionEntityGroup);
        getRightMapping(updatedRestrictionEntityGroup);
        getSecurityMapping(updatedRestrictionEntityGroup);
        return ResponseEntity.ok().body(updatedRestrictionEntityGroup);
    }

    private List<it.anggen.model.security.RestrictionEntityGroup> getRightMapping(List<it.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList) {
        for (it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
        {
        getRightMapping(restrictionEntityGroup);
        }
        return restrictionEntityGroupList;
    }

    private void getRightMapping(it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (restrictionEntityGroup.getEntityGroup()!=null)
        {
        restrictionEntityGroup.getEntityGroup().setProject(null);
        restrictionEntityGroup.getEntityGroup().setEntityList(null);
        restrictionEntityGroup.getEntityGroup().setRestrictionEntityGroupList(null);
        }
        if (restrictionEntityGroup.getRole()!=null)
        {
        restrictionEntityGroup.getRole().setRestrictionEntityGroupList(null);
        restrictionEntityGroup.getRole().setRestrictionEntityList(null);
        restrictionEntityGroup.getRole().setRestrictionFieldList(null);
        restrictionEntityGroup.getRole().setUserList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        restrictionEntityGroup.setEntityGroup(restrictionEntityGroupService.findById(restrictionEntityGroup.getRestrictionEntityGroupId()).get(0).getEntityGroup());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        restrictionEntityGroup.setRole(restrictionEntityGroupService.findById(restrictionEntityGroup.getRestrictionEntityGroupId()).get(0).getRole());
    }

    private List<it.anggen.model.security.RestrictionEntityGroup> getSecurityMapping(List<it.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList) {
        for (it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
        {
        getSecurityMapping(restrictionEntityGroup);
        }
        return restrictionEntityGroupList;
    }

    private void getSecurityMapping(it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (securityEnabled && restrictionEntityGroup.getEntityGroup()!=null  && !securityService.hasPermission(it.anggen.model.entity.EntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        restrictionEntityGroup.setEntityGroup(null);

        if (securityEnabled && restrictionEntityGroup.getRole()!=null  && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        restrictionEntityGroup.setRole(null);

    }

}
