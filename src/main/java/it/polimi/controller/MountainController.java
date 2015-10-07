
package it.polimi.controller;

import java.util.List;
import it.polimi.model.Mountain;
import it.polimi.service.MountainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
        List<Mountain> mountainList=mountainService.findById(Long.valueOf(mountainId));
        getRightMapping(mountainList);
        return ResponseEntity.ok().body(mountainList);
    }

    @ResponseBody
    @RequestMapping(value = "/{mountainId}", method = RequestMethod.DELETE)
    public ResponseEntity deletemountainById(
        @PathVariable
        String mountainId) {
        mountainService.deleteById(Long.valueOf(mountainId));
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
    	{
    		for (it.polimi.model.SeedQuery seedQuery : mountain.getSeedQueryList())
    		{
    			if (seedQuery.getMountain()!=null)
    			{
    				//seedQuery.getMountain()
    				seedQuery.setMountain(null);
    			}
    			if (seedQuery.getPhotoList()!=null)
    			{
    				for (it.polimi.model.Photo photo : seedQuery.getPhotoList())
    				{
    					if (photo.getSeedQuery()!=null)
    					{
    						//photo.getSeedQuery()
    						photo.setSeedQuery(null);
    					}
    				}
    			}
    		}
    	}
    }

}
