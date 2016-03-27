
package it.anggen.controller.security;

import java.util.List;
import it.anggen.searchbean.security.RoleSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.security.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    @org.springframework.beans.factory.annotation.Autowired
    private RoleService roleService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.security.Role.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "role";
    }

    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.security.Role> page = roleService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

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
        roleList=roleService.find(role);
        getSecurityMapping(roleList);
        getRightMapping(roleList);
         log.info("Search: returning {} role.",roleList.size());
        return ResponseEntity.ok().body(roleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public ResponseEntity getRoleById(
        @PathVariable
        String roleId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching role with id {}",roleId);
        List<it.anggen.model.security.Role> roleList=roleService.findById(Integer.valueOf(roleId));
        getSecurityMapping(roleList);
        getRightMapping(roleList);
         log.info("Search: returning {} role.",roleList.size());
        return ResponseEntity.ok().body(roleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRoleById(
        @PathVariable
        String roleId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting role with id {}",roleId);
        roleService.deleteById(Integer.valueOf(roleId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRole(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.Role role) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (role.getRoleId()!=null)
        log.info("Inserting role like {}", role.getRole()+' '+ role.getRoleId());
        it.anggen.model.security.Role insertedRole=roleService.insert(role);
        getRightMapping(insertedRole);
        log.info("Inserted role with id {}",insertedRole.getRoleId());
        return ResponseEntity.ok().body(insertedRole);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRole(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.Role role) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating role with id {}",role.getRoleId());
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
        if (role.getRestrictionEntityGroupList()!=null)
        for (it.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup :role.getRestrictionEntityGroupList())

        {

        restrictionEntityGroup.setEntityGroup(null);
        restrictionEntityGroup.setRole(null);
        }
        if (role.getRestrictionEntityList()!=null)
        for (it.anggen.model.security.RestrictionEntity restrictionEntity :role.getRestrictionEntityList())

        {

        restrictionEntity.setEntity(null);
        restrictionEntity.setRole(null);
        }
        if (role.getRestrictionFieldList()!=null)
        for (it.anggen.model.security.RestrictionField restrictionField :role.getRestrictionFieldList())

        {

        restrictionField.setField(null);
        restrictionField.setRole(null);
        }
        if (role.getUserList()!=null)
        for (it.anggen.model.security.User user :role.getUserList())

        {

        user.setRoleList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.security.Role role) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        role.setRestrictionEntityGroupList(roleService.findById(role.getRoleId()).get(0).getRestrictionEntityGroupList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        role.setRestrictionEntityList(roleService.findById(role.getRoleId()).get(0).getRestrictionEntityList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        role.setRestrictionFieldList(roleService.findById(role.getRoleId()).get(0).getRestrictionFieldList());
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        role.setUserList(roleService.findById(role.getRoleId()).get(0).getUserList());
    }

    private List<it.anggen.model.security.Role> getSecurityMapping(List<it.anggen.model.security.Role> roleList) {
        for (it.anggen.model.security.Role role: roleList)
        {
        getSecurityMapping(role);
        }
        return roleList;
    }

    private void getSecurityMapping(it.anggen.model.security.Role role) {
        if (securityEnabled && role.getRestrictionEntityGroupList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        role.setRestrictionEntityGroupList(null);

        if (securityEnabled && role.getRestrictionEntityList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionEntity.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        role.setRestrictionEntityList(null);

        if (securityEnabled && role.getRestrictionFieldList()!=null && !securityService.hasPermission(it.anggen.model.security.RestrictionField.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        role.setRestrictionFieldList(null);

        if (securityEnabled && role.getUserList()!=null && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        role.setUserList(null);

    }

}
