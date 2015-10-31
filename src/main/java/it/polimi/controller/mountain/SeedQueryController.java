
package it.polimi.controller.mountain;

import java.util.List;
import it.polimi.model.mountain.SeedQuery;
import it.polimi.searchbean.mountain.SeedQuerySearchBean;
import it.polimi.service.mountain.SeedQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/seedQuery")
public class SeedQueryController {

    @Autowired
    public SeedQueryService seedQueryService;
    private final static Logger log = LoggerFactory.getLogger(SeedQuery.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "seedQuery";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        SeedQuerySearchBean seedQuery) {
        List<SeedQuery> seedQueryList;
        if (seedQuery.getSeedQueryId()!=null)
         log.info("Searching seedQuery like {}", seedQuery.getSeedQueryId()+' '+ seedQuery.getSeedKeyword());
        seedQueryList=seedQueryService.find(seedQuery);
        getRightMapping(seedQueryList);
         log.info("Search: returning {} seedQuery.",seedQueryList.size());
        return ResponseEntity.ok().body(seedQueryList);
    }

    @ResponseBody
    @RequestMapping(value = "/{seedQueryId}", method = RequestMethod.GET)
    public ResponseEntity getseedQueryById(
        @PathVariable
        String seedQueryId) {
        log.info("Searching seedQuery with id {}",seedQueryId);
        List<SeedQuery> seedQueryList=seedQueryService.findById(java.lang.Long.valueOf(seedQueryId));
        getRightMapping(seedQueryList);
         log.info("Search: returning {} seedQuery.",seedQueryList.size());
        return ResponseEntity.ok().body(seedQueryList);
    }

    @ResponseBody
    @RequestMapping(value = "/{seedQueryId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteseedQueryById(
        @PathVariable
        String seedQueryId) {
        log.info("Deleting seedQuery with id {}",seedQueryId);
        seedQueryService.deleteById(java.lang.Long.valueOf(seedQueryId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertseedQuery(
        @RequestBody
        SeedQuery seedQuery) {
        log.info("Inserting seedQuery like {}", seedQuery.getSeedQueryId()+' '+ seedQuery.getSeedKeyword());
        SeedQuery insertedseedQuery=seedQueryService.insert(seedQuery);
        getRightMapping(insertedseedQuery);
        log.info("Inserted seedQuery with id {}",insertedseedQuery.getSeedQueryId());
        return ResponseEntity.ok().body(insertedseedQuery);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateseedQuery(
        @RequestBody
        SeedQuery seedQuery) {
        log.info("Updating seedQuery with id {}",seedQuery.getSeedQueryId());
        SeedQuery updatedseedQuery=seedQueryService.update(seedQuery);
        getRightMapping(updatedseedQuery);
        return ResponseEntity.ok().body(updatedseedQuery);
    }

    private List<SeedQuery> getRightMapping(List<SeedQuery> seedQueryList) {
        for (SeedQuery seedQuery: seedQueryList)
        {
        getRightMapping(seedQuery);
        }
        return seedQueryList;
    }

    private void getRightMapping(SeedQuery seedQuery) {
        if (seedQuery.getMountain()!=null)
        {
        seedQuery.getMountain().setSeedQueryList(null);
        }
        if (seedQuery.getPhotoList()!=null)
        for (it.polimi.model.mountain.Photo photo :seedQuery.getPhotoList())

        {

        photo.setSeedQuery(null);
        }
    }

}
