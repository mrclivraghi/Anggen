
package it.polimi.controller.security;

import java.util.List;
import it.polimi.model.security.Role;
import it.polimi.searchbean.security.RoleSearchBean;
import it.polimi.service.security.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    public RoleService roleService;
    private final static Logger log = LoggerFactory.getLogger(Role.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "role";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        RoleSearchBean role) {
        List<Role> roleList;
        if (role.getRoleId()!=null)
         log.info("Searching role like {}",role.toString());
        roleList=roleService.find(role);
        getRightMapping(roleList);
         log.info("Search: returning {} role.",roleList.size());
        return ResponseEntity.ok().body(roleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public ResponseEntity getroleById(
        @PathVariable
        String roleId) {
        log.info("Searching role with id {}",roleId);
        List<Role> roleList=roleService.findById(java.lang.Integer.valueOf(roleId));
        getRightMapping(roleList);
         log.info("Search: returning {} role.",roleList.size());
        return ResponseEntity.ok().body(roleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{roleId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteroleById(
        @PathVariable
        String roleId) {
        log.info("Deleting role with id {}",roleId);
        roleService.deleteById(java.lang.Integer.valueOf(roleId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertrole(
        @org.springframework.web.bind.annotation.RequestBody
        Role role) {
        if (role.getRoleId()!=null)
        log.info("Inserting role like {}",role.toString());
        Role insertedrole=roleService.insert(role);
        getRightMapping(insertedrole);
        log.info("Inserted role with id {}",insertedrole.getRoleId());
        return ResponseEntity.ok().body(insertedrole);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updaterole(
        @org.springframework.web.bind.annotation.RequestBody
        Role role) {
        log.info("Updating role with id {}",role.getRoleId());
        Role updatedrole=roleService.update(role);
        getRightMapping(updatedrole);
        return ResponseEntity.ok().body(updatedrole);
    }

    private List<Role> getRightMapping(List<Role> roleList) {
        for (Role role: roleList)
        {
        getRightMapping(role);
        }
        return roleList;
    }

    private void getRightMapping(Role role) {
        if (role.getUserList()!=null)
        for (it.polimi.model.security.User user :role.getUserList())

        {

        user.setRole(null);
        }
    }

}
