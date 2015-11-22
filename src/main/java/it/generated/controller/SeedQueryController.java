
package it.generated.controller;

import java.util.List;
import it.generated.searchbean.SeedQuerySearchBean;
import it.generated.service.SeedQueryService;
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
@RequestMapping("/seedQuery")
public class SeedQueryController {

    @Autowired
    public SeedQueryService seedQueryService;
    private final static Logger log = LoggerFactory.getLogger(it.generated.domain.SeedQuery.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "seedQuery";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        SeedQuerySearchBean seedQuery) {
        List<it.generated.domain.SeedQuery> seedQueryList;
        if (seedQuery.getSeedQueryId()!=null)
         log.info("Searching seedQuery like {}", seedQuery.getSeedQueryId()+' '+ seedQuery.getName());
        seedQueryList=seedQueryService.find(seedQuery);
        getRightMapping(seedQueryList);
         log.info("Search: returning {} seedQuery.",seedQueryList.size());
        return ResponseEntity.ok().body(seedQueryList);
    }

    @ResponseBody
    @RequestMapping(value = "/{seedQueryId}", method = RequestMethod.GET)
    public ResponseEntity getSeedQueryById(
        @PathVariable
        String seedQueryId) {
        log.info("Searching seedQuery with id {}",seedQueryId);
        List<it.generated.domain.SeedQuery> seedQueryList=seedQueryService.findById(Integer.valueOf(seedQueryId));
        getRightMapping(seedQueryList);
         log.info("Search: returning {} seedQuery.",seedQueryList.size());
        return ResponseEntity.ok().body(seedQueryList);
    }

    @ResponseBody
    @RequestMapping(value = "/{seedQueryId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteSeedQueryById(
        @PathVariable
        String seedQueryId) {
        log.info("Deleting seedQuery with id {}",seedQueryId);
        seedQueryService.deleteById(Integer.valueOf(seedQueryId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertSeedQuery(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.domain.SeedQuery seedQuery) {
        if (seedQuery.getSeedQueryId()!=null)
        log.info("Inserting seedQuery like {}", seedQuery.getSeedQueryId()+' '+ seedQuery.getName());
        it.generated.domain.SeedQuery insertedSeedQuery=seedQueryService.insert(seedQuery);
        getRightMapping(insertedSeedQuery);
        log.info("Inserted seedQuery with id {}",insertedSeedQuery.getSeedQueryId());
        return ResponseEntity.ok().body(insertedSeedQuery);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateSeedQuery(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.domain.SeedQuery seedQuery) {
        log.info("Updating seedQuery with id {}",seedQuery.getSeedQueryId());
        it.generated.domain.SeedQuery updatedSeedQuery=seedQueryService.update(seedQuery);
        getRightMapping(updatedSeedQuery);
        return ResponseEntity.ok().body(updatedSeedQuery);
    }

    private List<it.generated.domain.SeedQuery> getRightMapping(List<it.generated.domain.SeedQuery> seedQueryList) {
        for (it.generated.domain.SeedQuery seedQuery: seedQueryList)
        {
        getRightMapping(seedQuery);
        }
        return seedQueryList;
    }

    private void getRightMapping(it.generated.domain.SeedQuery seedQuery) {
        if (seedQuery.getMountain()!=null)
        {
        seedQuery.getMountain().setSeedQueryList(null);
        }
    }

}
