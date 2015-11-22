
package it.generated.controller;

import java.util.List;
import it.generated.searchbean.MountainSearchBean;
import it.generated.service.MountainService;
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
@RequestMapping("/mountain")
public class MountainController {

    @Autowired
    public MountainService mountainService;
    private final static Logger log = LoggerFactory.getLogger(it.generated.domain.Mountain.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "mountain";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        MountainSearchBean mountain) {
        List<it.generated.domain.Mountain> mountainList;
        if (mountain.getMountainId()!=null)
         log.info("Searching mountain like {}", mountain.getMountainId()+' '+ mountain.getName());
        mountainList=mountainService.find(mountain);
        getRightMapping(mountainList);
         log.info("Search: returning {} mountain.",mountainList.size());
        return ResponseEntity.ok().body(mountainList);
    }

    @ResponseBody
    @RequestMapping(value = "/{mountainId}", method = RequestMethod.GET)
    public ResponseEntity getMountainById(
        @PathVariable
        String mountainId) {
        log.info("Searching mountain with id {}",mountainId);
        List<it.generated.domain.Mountain> mountainList=mountainService.findById(Integer.valueOf(mountainId));
        getRightMapping(mountainList);
         log.info("Search: returning {} mountain.",mountainList.size());
        return ResponseEntity.ok().body(mountainList);
    }

    @ResponseBody
    @RequestMapping(value = "/{mountainId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteMountainById(
        @PathVariable
        String mountainId) {
        log.info("Deleting mountain with id {}",mountainId);
        mountainService.deleteById(Integer.valueOf(mountainId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertMountain(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.domain.Mountain mountain) {
        if (mountain.getMountainId()!=null)
        log.info("Inserting mountain like {}", mountain.getMountainId()+' '+ mountain.getName());
        it.generated.domain.Mountain insertedMountain=mountainService.insert(mountain);
        getRightMapping(insertedMountain);
        log.info("Inserted mountain with id {}",insertedMountain.getMountainId());
        return ResponseEntity.ok().body(insertedMountain);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateMountain(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.domain.Mountain mountain) {
        log.info("Updating mountain with id {}",mountain.getMountainId());
        it.generated.domain.Mountain updatedMountain=mountainService.update(mountain);
        getRightMapping(updatedMountain);
        return ResponseEntity.ok().body(updatedMountain);
    }

    private List<it.generated.domain.Mountain> getRightMapping(List<it.generated.domain.Mountain> mountainList) {
        for (it.generated.domain.Mountain mountain: mountainList)
        {
        getRightMapping(mountain);
        }
        return mountainList;
    }

    private void getRightMapping(it.generated.domain.Mountain mountain) {
        if (mountain.getSeedQueryList()!=null)
        for (it.generated.domain.SeedQuery seedQuery :mountain.getSeedQueryList())

        {

        seedQuery.setMountain(null);
        }
    }

}
