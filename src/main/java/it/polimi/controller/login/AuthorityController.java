
package it.polimi.controller.login;

import java.util.List;
import it.polimi.model.login.Authority;
import it.polimi.searchbean.login.AuthoritySearchBean;
import it.polimi.service.login.AuthorityService;
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
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    public AuthorityService authorityService;
    private final static Logger log = LoggerFactory.getLogger(Authority.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "authority";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        AuthoritySearchBean authority) {
        List<Authority> authorityList;
        if (authority.getAuthorityId()!=null)
         log.info("Searching authority like {}",authority.toString());
        authorityList=authorityService.find(authority);
        getRightMapping(authorityList);
         log.info("Search: returning {} authority.",authorityList.size());
        return ResponseEntity.ok().body(authorityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{authorityId}", method = RequestMethod.GET)
    public ResponseEntity getauthorityById(
        @PathVariable
        String authorityId) {
        log.info("Searching authority with id {}",authorityId);
        List<Authority> authorityList=authorityService.findById(java.lang.Long.valueOf(authorityId));
        getRightMapping(authorityList);
         log.info("Search: returning {} authority.",authorityList.size());
        return ResponseEntity.ok().body(authorityList);
    }

    @ResponseBody
    @RequestMapping(value = "/{authorityId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteauthorityById(
        @PathVariable
        String authorityId) {
        log.info("Deleting authority with id {}",authorityId);
        authorityService.deleteById(java.lang.Long.valueOf(authorityId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertauthority(
        @org.springframework.web.bind.annotation.RequestBody
        Authority authority) {
        log.info("Inserting authority like {}",authority.toString());
        Authority insertedauthority=authorityService.insert(authority);
        getRightMapping(insertedauthority);
        log.info("Inserted authority with id {}",insertedauthority.getAuthorityId());
        return ResponseEntity.ok().body(insertedauthority);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateauthority(
        @org.springframework.web.bind.annotation.RequestBody
        Authority authority) {
        log.info("Updating authority with id {}",authority.getAuthorityId());
        Authority updatedauthority=authorityService.update(authority);
        getRightMapping(updatedauthority);
        return ResponseEntity.ok().body(updatedauthority);
    }

    private List<Authority> getRightMapping(List<Authority> authorityList) {
        for (Authority authority: authorityList)
        {
        getRightMapping(authority);
        }
        return authorityList;
    }

    private void getRightMapping(Authority authority) {
        if (authority.getUserList()!=null)
        for (it.polimi.model.login.User user :authority.getUserList())

        {

        user.setAuthorityList(null);
        }
    }

}
