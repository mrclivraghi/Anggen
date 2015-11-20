
package it.polimi.controller.security;

import java.util.List;
import it.polimi.model.security.User;
import it.polimi.searchbean.security.UserSearchBean;
import it.polimi.service.security.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;
    private final static Logger log = LoggerFactory.getLogger(User.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "user";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        UserSearchBean user) {
        List<User> userList;
        if (user.getUserId()!=null)
         log.info("Searching user like {}",user.toString());
        userList=userService.find(user);
        getRightMapping(userList);
         log.info("Search: returning {} user.",userList.size());
        return ResponseEntity.ok().body(userList);
    }

    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity getuserById(
        @PathVariable
        String userId) {
        log.info("Searching user with id {}",userId);
        List<User> userList=userService.findById(java.lang.Long.valueOf(userId));
        getRightMapping(userList);
         log.info("Search: returning {} user.",userList.size());
        return ResponseEntity.ok().body(userList);
    }

    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteuserById(
        @PathVariable
        String userId) {
        log.info("Deleting user with id {}",userId);
        userService.deleteById(java.lang.Long.valueOf(userId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertuser(
        @org.springframework.web.bind.annotation.RequestBody
        User user) {
        if (user.getUserId()!=null)
        log.info("Inserting user like {}",user.toString());
        User inserteduser=userService.insert(user);
        getRightMapping(inserteduser);
        log.info("Inserted user with id {}",inserteduser.getUserId());
        return ResponseEntity.ok().body(inserteduser);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateuser(
        @org.springframework.web.bind.annotation.RequestBody
        User user) {
        log.info("Updating user with id {}",user.getUserId());
        User updateduser=userService.update(user);
        getRightMapping(updateduser);
        return ResponseEntity.ok().body(updateduser);
    }

    private List<User> getRightMapping(List<User> userList) {
        for (User user: userList)
        {
        getRightMapping(user);
        }
        return userList;
    }

    private void getRightMapping(User user) {
        if (user.getRole()!=null)
        {
        user.getRole().setUserList(null);
        }
    }

}
