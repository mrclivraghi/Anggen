
package it.generated.anggen.controller.security;

import java.util.List;
import it.generated.anggen.searchbean.security.RestrictionEntityGroupSearchBean;
import it.generated.anggen.security.SecurityService;
import it.generated.anggen.service.security.RestrictionEntityGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(it.generated.anggen.model.security.RestrictionEntityGroup.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "restrictionEntityGroup";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RestrictionEntityGroupSearchBean restrictionEntityGroup) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.generated.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList;
        if (restrictionEntityGroup.getRestrictionEntityGroupId()!=null)
         log.info("Searching restrictionEntityGroup like {}",restrictionEntityGroup.toString());
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
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching restrictionEntityGroup with id {}",restrictionEntityGroupId);
        List<it.generated.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList=restrictionEntityGroupService.findById(Long.valueOf(restrictionEntityGroupId));
        getRightMapping(restrictionEntityGroupList);
         log.info("Search: returning {} restrictionEntityGroup.",restrictionEntityGroupList.size());
        return ResponseEntity.ok().body(restrictionEntityGroupList);
    }

    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityGroupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRestrictionEntityGroupById(
        @PathVariable
        String restrictionEntityGroupId) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting restrictionEntityGroup with id {}",restrictionEntityGroupId);
        restrictionEntityGroupService.deleteById(Long.valueOf(restrictionEntityGroupId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRestrictionEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (restrictionEntityGroup.getRestrictionEntityGroupId()!=null)
        log.info("Inserting restrictionEntityGroup like {}",restrictionEntityGroup.toString());
        it.generated.anggen.model.security.RestrictionEntityGroup insertedRestrictionEntityGroup=restrictionEntityGroupService.insert(restrictionEntityGroup);
        getRightMapping(insertedRestrictionEntityGroup);
        log.info("Inserted restrictionEntityGroup with id {}",insertedRestrictionEntityGroup.getRestrictionEntityGroupId());
        return ResponseEntity.ok().body(insertedRestrictionEntityGroup);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRestrictionEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating restrictionEntityGroup with id {}",restrictionEntityGroup.getRestrictionEntityGroupId());
        rebuildSecurityMapping(restrictionEntityGroup);
        it.generated.anggen.model.security.RestrictionEntityGroup updatedRestrictionEntityGroup=restrictionEntityGroupService.update(restrictionEntityGroup);
        getRightMapping(updatedRestrictionEntityGroup);
        getSecurityMapping(updatedRestrictionEntityGroup);
        return ResponseEntity.ok().body(updatedRestrictionEntityGroup);
    }

    private List<it.generated.anggen.model.security.RestrictionEntityGroup> getRightMapping(List<it.generated.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList) {
        for (it.generated.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
        {
        getRightMapping(restrictionEntityGroup);
        }
        return restrictionEntityGroupList;
    }

    private void getRightMapping(it.generated.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (restrictionEntityGroup.getRole()!=null)
        {
        restrictionEntityGroup.getRole().setRestrictionEntityGroupList(null);
        restrictionEntityGroup.getRole().setRestrictionFieldList(null);
        restrictionEntityGroup.getRole().setRestrictionEntityList(null);
        restrictionEntityGroup.getRole().setUserList(null);
        }
        if (restrictionEntityGroup.getEntityGroup()!=null)
        {
        restrictionEntityGroup.getEntityGroup().setProject(null);
        restrictionEntityGroup.getEntityGroup().setRestrictionEntityGroupList(null);
        restrictionEntityGroup.getEntityGroup().setEntityList(null);
        }
    }

    private void rebuildSecurityMapping(it.generated.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        restrictionEntityGroup.setRole(restrictionEntityGroupService.findById(restrictionEntityGroup.getRestrictionEntityGroupId()).get(0).getRole());
        if (!securityService.isAllowed(it.generated.anggen.model.entity.EntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        restrictionEntityGroup.setEntityGroup(restrictionEntityGroupService.findById(restrictionEntityGroup.getRestrictionEntityGroupId()).get(0).getEntityGroup());
    }

    private List<it.generated.anggen.model.security.RestrictionEntityGroup> getSecurityMapping(List<it.generated.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList) {
        for (it.generated.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup: restrictionEntityGroupList)
        {
        getSecurityMapping(restrictionEntityGroup);
        }
        return restrictionEntityGroupList;
    }

    private void getSecurityMapping(it.generated.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (restrictionEntityGroup.getRole()!=null  && !securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        restrictionEntityGroup.setRole(null);

        if (restrictionEntityGroup.getEntityGroup()!=null  && !securityService.isAllowed(it.generated.anggen.model.entity.EntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        restrictionEntityGroup.setEntityGroup(null);

    }

}
