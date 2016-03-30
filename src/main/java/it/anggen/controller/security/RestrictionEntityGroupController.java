
package it.anggen.controller.security;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.security.RestrictionEntityGroupSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.log.LogEntryService;
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
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.security.RestrictionEntityGroup.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "restrictionEntityGroup";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.security.RestrictionEntityGroup> page = restrictionEntityGroupService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
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
        logEntryService.addLogEntry( "Searching entity like "+ restrictionEntityGroup.getRestrictionEntityGroupId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.security.RestrictionEntityGroup.staticEntityId, securityService.getLoggedUser(),log);
        restrictionEntityGroupList=restrictionEntityGroupService.find(restrictionEntityGroup);
        getSecurityMapping(restrictionEntityGroupList);
        getRightMapping(restrictionEntityGroupList);
         log.info("Search: returning {} restrictionEntityGroup.",restrictionEntityGroupList.size());
        return ResponseEntity.ok().body(restrictionEntityGroupList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityGroupId}", method = RequestMethod.GET)
    public ResponseEntity getRestrictionEntityGroupById(
        @PathVariable
        String restrictionEntityGroupId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching restrictionEntityGroup with id "+restrictionEntityGroupId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.security.RestrictionEntityGroup.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.security.RestrictionEntityGroup> restrictionEntityGroupList=restrictionEntityGroupService.findById(Long.valueOf(restrictionEntityGroupId));
        getSecurityMapping(restrictionEntityGroupList);
        getRightMapping(restrictionEntityGroupList);
         log.info("Search: returning {} restrictionEntityGroup.",restrictionEntityGroupList.size());
        return ResponseEntity.ok().body(restrictionEntityGroupList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{restrictionEntityGroupId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRestrictionEntityGroupById(
        @PathVariable
        String restrictionEntityGroupId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting restrictionEntityGroup with id "+restrictionEntityGroupId);
        logEntryService.addLogEntry( "Deleting restrictionEntityGroup with id {}"+restrictionEntityGroupId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.security.RestrictionEntityGroup.staticEntityId, securityService.getLoggedUser(),log);
        restrictionEntityGroupService.deleteById(Long.valueOf(restrictionEntityGroupId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRestrictionEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (restrictionEntityGroup.getRestrictionEntityGroupId()!=null)
        log.info("Inserting restrictionEntityGroup like "+ restrictionEntityGroup.getRestrictionEntityGroupId());
        it.anggen.model.security.RestrictionEntityGroup insertedRestrictionEntityGroup=restrictionEntityGroupService.insert(restrictionEntityGroup);
        getRightMapping(insertedRestrictionEntityGroup);
        logEntryService.addLogEntry( "Inserted restrictionEntityGroup with id "+ insertedRestrictionEntityGroup.getRestrictionEntityGroupId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.security.RestrictionEntityGroup.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedRestrictionEntityGroup);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRestrictionEntityGroup(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating restrictionEntityGroup with id "+restrictionEntityGroup.getRestrictionEntityGroupId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.security.RestrictionEntityGroup.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(restrictionEntityGroup);
        it.anggen.model.security.RestrictionEntityGroup updatedRestrictionEntityGroup=restrictionEntityGroupService.update(restrictionEntityGroup);
        getSecurityMapping(updatedRestrictionEntityGroup);
        getRightMapping(updatedRestrictionEntityGroup);
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
        restrictionEntityGroup.getEntityGroup().setRestrictionEntityGroupList(null);
        restrictionEntityGroup.getEntityGroup().setProject(null);
        restrictionEntityGroup.getEntityGroup().setEntityList(null);
        }
        if (restrictionEntityGroup.getRole()!=null)
        {
        restrictionEntityGroup.getRole().setRestrictionEntityList(null);
        restrictionEntityGroup.getRole().setRestrictionFieldList(null);
        restrictionEntityGroup.getRole().setUserList(null);
        restrictionEntityGroup.getRole().setRestrictionEntityGroupList(null);
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
