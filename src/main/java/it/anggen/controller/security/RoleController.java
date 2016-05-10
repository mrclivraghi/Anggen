
package it.anggen.controller.security;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import it.anggen.searchbean.security.RoleSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.log.LogEntryService;
import it.anggen.service.security.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @org.springframework.beans.factory.annotation.Autowired
    private RoleService roleService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.security.Role.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @ApiOperation(value = "Return a page of role", notes = "Return a single page of role", response = it.anggen.model.security.Role.class, responseContainer = "List")
    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.security.Role> page = roleService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @ApiOperation(value = "Return a list of role", notes = "Return a list of role based on the search bean requested", response = it.anggen.model.security.Role.class, responseContainer = "List")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RoleSearchBean role) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.security.Role> roleList;
        if (role.getRoleId()!=null)
         log.info("Searching role like {}", role.getRole()+' '+ role.getRoleId());
        logEntryService.addLogEntry( "Searching entity like "+ role.getRole()+' '+ role.getRoleId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.security.Role.staticEntityId, securityService.getLoggedUser(),log);
        roleList=roleService.find(role);
        getSecurityMapping(roleList);
        getRightMapping(roleList);
         log.info("Search: returning {} role.",roleList.size());
        return ResponseEntity.ok().body(roleList);
    }

    @ApiOperation(value = "Return a the role identified by the given id", notes = "Return a the role identified by the given id", response = it.anggen.model.security.Role.class, responseContainer = "List")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public ResponseEntity getRoleById(
        @PathVariable
        String roleId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching role with id "+roleId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.security.Role.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.security.Role> roleList=roleService.findById(Integer.valueOf(roleId));
        getSecurityMapping(roleList);
        getRightMapping(roleList);
         log.info("Search: returning {} role.",roleList.size());
        return ResponseEntity.ok().body(roleList);
    }

    @ApiOperation(value = "Delete the role identified by the given id", notes = "Delete the role identified by the given id")
    @Timed
    @ResponseBody
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRoleById(
        @PathVariable
        String roleId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting role with id "+roleId);
        logEntryService.addLogEntry( "Deleting role with id {}"+roleId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.security.Role.staticEntityId, securityService.getLoggedUser(),log);
        roleService.deleteById(Integer.valueOf(roleId));
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Insert the role given", notes = "Insert the role given ", response = it.anggen.model.security.Role.class)
    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRole(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.Role role) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (role.getRoleId()!=null)
        log.info("Inserting role like "+ role.getRole()+' '+ role.getRoleId());
        it.anggen.model.security.Role insertedRole=roleService.insert(role);
        getRightMapping(insertedRole);
        logEntryService.addLogEntry( "Inserted role with id "+ insertedRole.getRoleId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.security.Role.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedRole);
    }

    @ApiOperation(value = "Update the role given", notes = "Update the role given ", response = it.anggen.model.security.Role.class)
    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRole(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.Role role) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating role with id "+role.getRoleId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.security.Role.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(role);
        it.anggen.model.security.Role updatedRole=roleService.update(role);
        getSecurityMapping(updatedRole);
        getRightMapping(updatedRole);
        return ResponseEntity.ok().body(updatedRole);
    }

    private List<it.anggen.model.security.Role> getRightMapping(List<it.anggen.model.security.Role> roleList) {
        for (it.anggen.model.security.Role role: roleList)
        {
        getRightMapping(role);
        }
        return roleList;
    }

    private void getRightMapping(it.anggen.model.security.Role role) {
        if (role.getRestrictionEntityList()!=null)
        for (it.anggen.model.security.RestrictionEntity restrictionEntity :role.getRestrictionEntityList())

        {

        restrictionEntity.setRole(null);
        restrictionEntity.setEntity(null);
        }
        if (role.getRestrictionFieldList()!=null)
        for (it.anggen.model.security.RestrictionField restrictionField :role.getRestrictionFieldList())

        {

        restrictionField.setRole(null);
        restrictionField.setField(null);
        }
        if (role.getUserList()!=null)
        for (it.anggen.model.security.User user :role.getUserList())

        {

        user.setRoleList(null);
        }
        if (role.getRestrictionEntityGroupList()!=null)
        for (it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup :role.getRestrictionEntityGroupList())

        {

        restrictionEntityGroup.setRole(null);
        restrictionEntityGroup.setEntityGroup(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.security.Role role) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        role.setRestrictionEntityList(roleService.findById(role.getRoleId()).get(0).getRestrictionEntityList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        role.setRestrictionFieldList(roleService.findById(role.getRoleId()).get(0).getRestrictionFieldList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        role.setUserList(roleService.findById(role.getRoleId()).get(0).getUserList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        role.setRestrictionEntityGroupList(roleService.findById(role.getRoleId()).get(0).getRestrictionEntityGroupList());
    }

    private List<it.anggen.model.security.Role> getSecurityMapping(List<it.anggen.model.security.Role> roleList) {
        for (it.anggen.model.security.Role role: roleList)
        {
        getSecurityMapping(role);
        }
        return roleList;
    }

    private void getSecurityMapping(it.anggen.model.security.Role role) {
        if (securityEnabled && role.getRestrictionEntityList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        role.setRestrictionEntityList(null);

        if (securityEnabled && role.getRestrictionFieldList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        role.setRestrictionFieldList(null);

        if (securityEnabled && role.getUserList()!=null && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        role.setUserList(null);

        if (securityEnabled && role.getRestrictionEntityGroupList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        role.setRestrictionEntityGroupList(null);

    }

}
