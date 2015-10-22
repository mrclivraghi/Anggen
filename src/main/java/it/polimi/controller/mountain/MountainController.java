
package it.polimi.controller.mountain;

import java.util.List;

import it.polimi.model.mountain.Mountain;
import it.polimi.service.mountain.MountainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mountain")
public class MountainController {

    @Autowired
    public MountainService mountainService;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "mountain";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Mountain mountain) {
        List<Mountain> mountainList;
        mountainList=mountainService.find(mountain);
        getRightMapping(mountainList);
        return ResponseEntity.ok().body(mountainList);
    }

    @ResponseBody
    @RequestMapping(value = "/{mountainId}", method = RequestMethod.GET)
    public ResponseEntity getmountainById(
        @PathVariable
        String mountainId) {
        List<Mountain> mountainList=mountainService.findById(java.lang.Long.valueOf(mountainId));
        getRightMapping(mountainList);
        return ResponseEntity.ok().body(mountainList);
    }

    @ResponseBody
    @RequestMapping(value = "/{mountainId}", method = RequestMethod.DELETE)
    public ResponseEntity deletemountainById(
        @PathVariable
        String mountainId) {
        mountainService.deleteById(java.lang.Long.valueOf(mountainId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertmountain(
        @RequestBody
        Mountain mountain) {
        Mountain insertedmountain=mountainService.insert(mountain);
        getRightMapping(insertedmountain);
        return ResponseEntity.ok().body(insertedmountain);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatemountain(
        @RequestBody
        Mountain mountain) {
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
