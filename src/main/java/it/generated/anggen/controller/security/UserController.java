
package it.generated.anggen.controller.security;

import java.util.List;
import it.generated.anggen.searchbean.security.UserSearchBean;
import it.generated.anggen.security.SecurityService;
import it.generated.anggen.service.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @org.springframework.beans.factory.annotation.Autowired
    private UserService userService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.generated.anggen.model.security.User.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.generated.anggen.model.security.User.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "user";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        UserSearchBean user) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.User.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.generated.anggen.model.security.User> userList;
        if (user.getUserId()!=null)
         log.info("Searching user like {}",user.toString());
        userList=userService.find(user);
        getRightMapping(userList);
        getSecurityMapping(userList);
         log.info("Search: returning {} user.",userList.size());
        return ResponseEntity.ok().body(userList);
    }

    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity getUserById(
        @PathVariable
        String userId) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.User.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching user with id {}",userId);
        List<it.generated.anggen.model.security.User> userList=userService.findById(Long.valueOf(userId));
        getRightMapping(userList);
         log.info("Search: returning {} user.",userList.size());
        return ResponseEntity.ok().body(userList);
    }

    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserById(
        @PathVariable
        String userId) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.User.staticEntityId, it.polimi.model.security.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting user with id {}",userId);
        userService.deleteById(Long.valueOf(userId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertUser(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.security.User user) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.User.staticEntityId, it.polimi.model.security.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (user.getUserId()!=null)
        log.info("Inserting user like {}",user.toString());
        it.generated.anggen.model.security.User insertedUser=userService.insert(user);
        getRightMapping(insertedUser);
        log.info("Inserted user with id {}",insertedUser.getUserId());
        return ResponseEntity.ok().body(insertedUser);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateUser(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.anggen.model.security.User user) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.User.staticEntityId, it.polimi.model.security.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating user with id {}",user.getUserId());
        rebuildSecurityMapping(user);
        it.generated.anggen.model.security.User updatedUser=userService.update(user);
        getRightMapping(updatedUser);
        getSecurityMapping(updatedUser);
        return ResponseEntity.ok().body(updatedUser);
    }

    private List<it.generated.anggen.model.security.User> getRightMapping(List<it.generated.anggen.model.security.User> userList) {
        for (it.generated.anggen.model.security.User user: userList)
        {
        getRightMapping(user);
        }
        return userList;
    }

    private void getRightMapping(it.generated.anggen.model.security.User user) {
        if (user.getRoleList()!=null)
        for (it.generated.anggen.model.security.Role role :user.getRoleList())

        {

        role.setUserList(null);
        role.setRestrictionEntityList(null);
        role.setRestrictionFieldList(null);
        role.setRestrictionEntityGroupList(null);
        }
    }

    private void rebuildSecurityMapping(it.generated.anggen.model.security.User user) {
        if (!securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH))
        user.setRoleList(userService.findById(user.getUserId()).get(0).getRoleList());
    }

    private List<it.generated.anggen.model.security.User> getSecurityMapping(List<it.generated.anggen.model.security.User> userList) {
        for (it.generated.anggen.model.security.User user: userList)
        {
        getSecurityMapping(user);
        }
        return userList;
    }

    private void getSecurityMapping(it.generated.anggen.model.security.User user) {
        if (user.getRoleList()!=null && !securityService.isAllowed(it.generated.anggen.model.security.Role.staticEntityId, it.polimi.model.security.RestrictionType.SEARCH) )
        user.setRoleList(null);

    }

}
