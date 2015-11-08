
package it.polimi.controller.mountain;

import java.util.List;

import it.polimi.model.mountain.Mountain;
import it.polimi.searchbean.mountain.MountainSearchBean;
import it.polimi.searchbean.security.UserSearchBean;
import it.polimi.service.mountain.MountainService;
import it.polimi.service.security.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mountain")
public class MountainController {

    @Autowired
    public MountainService mountainService;
    
    @Autowired UserService userService;
    
    
    private final static Logger log = LoggerFactory.getLogger(Mountain.class);

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView manage() {
    	User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	UserSearchBean userSearchBean = new UserSearchBean();
    	userSearchBean.setUsername(principal.getUsername());
    	
    	it.polimi.model.security.User myUser = userService.find(userSearchBean).get(0);
    	
        ModelAndView mav = new ModelAndView("mountain");
        mav.addObject(myUser.getRole().getEntityList());
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        MountainSearchBean mountain) {
        List<Mountain> mountainList;
        if (mountain.getMountainId()!=null)
         log.info("Searching mountain like {}", mountain.getMountainId()+' '+ mountain.getName());
        mountainList=mountainService.find(mountain);
        getRightMapping(mountainList);
         log.info("Search: returning {} mountain.",mountainList.size());
        return ResponseEntity.ok().body(mountainList);
    }

    @ResponseBody
    @RequestMapping(value = "/{mountainId}", method = RequestMethod.GET)
    public ResponseEntity getmountainById(
        @PathVariable
        String mountainId) {
        log.info("Searching mountain with id {}",mountainId);
        List<Mountain> mountainList=mountainService.findById(java.lang.Long.valueOf(mountainId));
        getRightMapping(mountainList);
         log.info("Search: returning {} mountain.",mountainList.size());
        return ResponseEntity.ok().body(mountainList);
    }

    @ResponseBody
    @RequestMapping(value = "/{mountainId}", method = RequestMethod.DELETE)
    public ResponseEntity deletemountainById(
        @PathVariable
        String mountainId) {
        log.info("Deleting mountain with id {}",mountainId);
        mountainService.deleteById(java.lang.Long.valueOf(mountainId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertmountain(
        @org.springframework.web.bind.annotation.RequestBody
        Mountain mountain) {
        if (mountain.getMountainId()!=null)
        log.info("Inserting mountain like {}", mountain.getMountainId()+' '+ mountain.getName());
        Mountain insertedmountain=mountainService.insert(mountain);
        getRightMapping(insertedmountain);
        log.info("Inserted mountain with id {}",insertedmountain.getMountainId());
        return ResponseEntity.ok().body(insertedmountain);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatemountain(
        @org.springframework.web.bind.annotation.RequestBody
        Mountain mountain) {
        log.info("Updating mountain with id {}",mountain.getMountainId());
        Mountain updatedmountain=mountainService.update(mountain);
        getRightMapping(updatedmountain);
        return ResponseEntity.ok().body(updatedmountain);
    }

    private List<Mountain> getRightMapping(List<Mountain> mountainList) {
        for (Mountain mountain: mountainList)
        {
        getRightMapping(mountain);
        }
        return mountainList;
    }

    private void getRightMapping(Mountain mountain) {
        if (mountain.getSeedQueryList()!=null)
        for (it.polimi.model.mountain.SeedQuery seedQuery :mountain.getSeedQueryList())

        {

        seedQuery.setMountain(null);
        seedQuery.setPhotoList(null);
        }
    }

}
