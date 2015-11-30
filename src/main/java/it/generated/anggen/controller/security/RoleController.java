
package it.generated.anggen.controller.security;

import java.util.List;
import it.generated.anggen.searchbean.security.RoleSearchBean;
import it.generated.anggen.security.SecurityService;
import it.generated.anggen.service.security.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger log = LoggerFactory.getLogger(it.generated.anggen.model.security.Role.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "role";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RoleSearchBean role) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.generated.anggen.model.security.Role> roleList;
        if (role.getRoleId()!=null)
         log.info("Searching role like {}",role.toString());
        roleList=roleService.find(role);
        getRightMapping(roleList);
        getSecurityMapping(roleList);
         log.info("Search: returning {} role.",roleList.size());
        return ResponseEntity.ok().body(roleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public ResponseEntity getRoleById(
        @PathVariable
        String roleId) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching role with id {}",roleId);
        List<it.generated.anggen.model.security.Role> roleList=roleService.findById(Integer.valueOf(roleId));
        getRightMapping(roleList);
         log.info("Search: returning {} role.",roleList.size());
        return ResponseEntity.ok().body(roleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRoleById(
        @PathVariable
        String roleId) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting role with id {}",roleId);
        roleService.deleteById(Integer.valueOf(roleId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertRole(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.security.Role role) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (role.getRoleId()!=null)
        log.info("Inserting role like {}",role.toString());
        it.generated.anggen.model.security.Role insertedRole=roleService.insert(role);
        getRightMapping(insertedRole);
        log.info("Inserted role with id {}",insertedRole.getRoleId());
        return ResponseEntity.ok().body(insertedRole);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateRole(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.security.Role role) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating role with id {}",role.getRoleId());
        rebuildSecurityMapping(role);
        it.generated.anggen.model.security.Role updatedRole=roleService.update(role);
        getRightMapping(updatedRole);
        getSecurityMapping(updatedRole);
        return ResponseEntity.ok().body(updatedRole);
    }

    private List<it.generated.anggen.model.security.Role> getRightMapping(List<it.generated.anggen.model.security.Role> roleList) {
        for (it.generated.anggen.model.security.Role role: roleList)
        {
        getRightMapping(role);
        }
        return roleList;
    }

    private void getRightMapping(it.generated.anggen.model.security.Role role) {
        if (role.getRestrictionEntityGroupList()!=null)
        for (it.generated.anggen.model.security.RestrictionEntityGroup restrictionEntityGroup :role.getRestrictionEntityGroupList())

        {

        restrictionEntityGroup.setRole(null);
        restrictionEntityGroup.setEntityGroup(null);
        }
        if (role.getRestrictionFieldList()!=null)
        for (it.generated.anggen.model.security.RestrictionField restrictionField :role.getRestrictionFieldList())

        {

        restrictionField.setRole(null);
        restrictionField.setField(null);
        }
        if (role.getRestrictionEntityList()!=null)
        for (it.generated.anggen.model.security.RestrictionEntity restrictionEntity :role.getRestrictionEntityList())

        {

        restrictionEntity.setEntity(null);
        restrictionEntity.setRole(null);
        }
        if (role.getUserList()!=null)
        for (it.generated.anggen.model.security.User user :role.getUserList())

        {

        user.setRoleList(null);
        }
    }

    private void rebuildSecurityMapping(it.generated.anggen.model.security.Role role) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        role.setRestrictionEntityGroupList(roleService.findById(role.getRoleId()).get(0).getRestrictionEntityGroupList());
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        role.setRestrictionFieldList(roleService.findById(role.getRoleId()).get(0).getRestrictionFieldList());
        if (!securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        role.setRestrictionEntityList(roleService.findById(role.getRoleId()).get(0).getRestrictionEntityList());
        if (!securityService.isAllowed(it.generated.anggen.model.security.User.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        role.setUserList(roleService.findById(role.getRoleId()).get(0).getUserList());
    }

    private List<it.generated.anggen.model.security.Role> getSecurityMapping(List<it.generated.anggen.model.security.Role> roleList) {
        for (it.generated.anggen.model.security.Role role: roleList)
        {
        getSecurityMapping(role);
        }
        return roleList;
    }

    private void getSecurityMapping(it.generated.anggen.model.security.Role role) {
        if (role.getRestrictionEntityGroupList()!=null && !securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntityGroup.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        role.setRestrictionEntityGroupList(null);

        if (role.getRestrictionFieldList()!=null && !securityService.isAllowed(it.generated.anggen.model.security.RestrictionField.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        role.setRestrictionFieldList(null);

        if (role.getRestrictionEntityList()!=null && !securityService.isAllowed(it.generated.anggen.model.security.RestrictionEntity.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        role.setRestrictionEntityList(null);

        if (role.getUserList()!=null && !securityService.isAllowed(it.generated.anggen.model.security.User.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        role.setUserList(null);

    }

}
