
package it.anggen.controller.security;

import java.util.List;
import com.codahale.metrics.annotation.Timed;
import it.anggen.searchbean.security.UserSearchBean;
import it.anggen.security.SecurityService;
import it.anggen.service.log.LogEntryService;
import it.anggen.service.security.UserService;
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
@RequestMapping("/user")
public class UserController {

    @org.springframework.beans.factory.annotation.Autowired
    private UserService userService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    @org.springframework.beans.factory.annotation.Autowired
    private LogEntryService logEntryService;
    private final static Logger log = LoggerFactory.getLogger(it.anggen.model.security.User.class);
    @Value("${application.security}")
    private Boolean securityEnabled;

    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "user";
    }

    @Timed
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity findPage(
        @PathVariable
        Integer pageNumber) {
        org.springframework.data.domain.Page<it.anggen.model.security.User> page = userService.findByPage(pageNumber);
        getRightMapping(page.getContent());
        return ResponseEntity.ok().body(page);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        UserSearchBean user) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.anggen.model.security.User> userList;
        if (user.getUserId()!=null)
         log.info("Searching user like {}", user.getUsername()+' '+ user.getUserId());
        logEntryService.addLogEntry( "Searching entity like "+ user.getUsername()+' '+ user.getUserId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.security.User.staticEntityId, securityService.getLoggedUser(),log);
        userList=userService.find(user);
        getSecurityMapping(userList);
        getRightMapping(userList);
         log.info("Search: returning {} user.",userList.size());
        return ResponseEntity.ok().body(userList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity getUserById(
        @PathVariable
        String userId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Searching user with id "+userId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.SEARCH_ENTITY, it.anggen.model.security.User.staticEntityId, securityService.getLoggedUser(),log);
        List<it.anggen.model.security.User> userList=userService.findById(Long.valueOf(userId));
        getSecurityMapping(userList);
        getRightMapping(userList);
         log.info("Search: returning {} user.",userList.size());
        return ResponseEntity.ok().body(userList);
    }

    @Timed
    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUserById(
        @PathVariable
        String userId) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting user with id "+userId);
        logEntryService.addLogEntry( "Deleting user with id {}"+userId,
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.DELETE_ENTITY, it.anggen.model.security.User.staticEntityId, securityService.getLoggedUser(),log);
        userService.deleteById(Long.valueOf(userId));
        return ResponseEntity.ok().build();
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertUser(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.User user) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (user.getUserId()!=null)
        log.info("Inserting user like "+ user.getUsername()+' '+ user.getUserId());
        it.anggen.model.security.User insertedUser=userService.insert(user);
        getRightMapping(insertedUser);
        logEntryService.addLogEntry( "Inserted user with id "+ insertedUser.getUserId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.CREATE_ENTITY, it.anggen.model.security.User.staticEntityId, securityService.getLoggedUser(),log);
        return ResponseEntity.ok().body(insertedUser);
    }

    @Timed
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateUser(
        @org.springframework.web.bind.annotation.RequestBody
        it.anggen.model.security.User user) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.User.staticEntityId, it.anggen.model.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        logEntryService.addLogEntry( "Updating user with id "+user.getUserId(),
        it.anggen.model.LogType.INFO, it.anggen.model.OperationType.UPDATE_ENTITY, it.anggen.model.security.User.staticEntityId, securityService.getLoggedUser(),log);
        rebuildSecurityMapping(user);
        it.anggen.model.security.User updatedUser=userService.update(user);
        getSecurityMapping(updatedUser);
        getRightMapping(updatedUser);
        return ResponseEntity.ok().body(updatedUser);
    }

    private List<it.anggen.model.security.User> getRightMapping(List<it.anggen.model.security.User> userList) {
        for (it.anggen.model.security.User user: userList)
        {
        getRightMapping(user);
        }
        return userList;
    }

    private void getRightMapping(it.anggen.model.security.User user) {
        if (user.getRoleList()!=null)
        for (it.anggen.model.security.Role role :user.getRoleList())

        {

        role.setRestrictionEntityList(null);
        role.setRestrictionFieldList(null);
        role.setRestrictionEntityGroupList(null);
        role.setUserList(null);
        }
    }

    private void rebuildSecurityMapping(it.anggen.model.security.User user) {
        if (securityEnabled && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH))
        user.setRoleList(userService.findById(user.getUserId()).get(0).getRoleList());
    }

    private List<it.anggen.model.security.User> getSecurityMapping(List<it.anggen.model.security.User> userList) {
        for (it.anggen.model.security.User user: userList)
        {
        getSecurityMapping(user);
        }
        return userList;
    }

    private void getSecurityMapping(it.anggen.model.security.User user) {
        if (securityEnabled && user.getRoleList()!=null && !securityService.hasPermission(it.anggen.model.security.Role.staticEntityId, it.anggen.model.RestrictionType.SEARCH) )
        user.setRoleList(null);

    }

}
