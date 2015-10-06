
package it.polimi.controller;

import java.util.List;
import it.polimi.model.SeedQuery;
import it.polimi.service.SeedQueryService;
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

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "seedQuery";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        SeedQuery seedQuery) {
        List<SeedQuery> seedQueryList;
        seedQueryList=seedQueryService.find(seedQuery);
        return ResponseEntity.ok().body(seedQueryList);
    }

    @ResponseBody
    @RequestMapping(value = "/{seedQueryId}", method = RequestMethod.GET)
    public ResponseEntity getseedQueryById(
        @PathVariable
        String seedQueryId) {
        return ResponseEntity.ok().body(seedQueryService.findById(Long.valueOf(seedQueryId)));
    }

    @ResponseBody
    @RequestMapping(value = "/{seedQueryId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteseedQueryById(
        @PathVariable
        String seedQueryId) {
        seedQueryService.deleteById(Long.valueOf(seedQueryId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertseedQuery(
        @RequestBody
        SeedQuery seedQuery) {
        SeedQuery insertedseedQuery=seedQueryService.insert(seedQuery);
        return ResponseEntity.ok().body(insertedseedQuery);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateseedQuery(
        @RequestBody
        SeedQuery seedQuery) {
        SeedQuery updatedseedQuery=seedQueryService.update(seedQuery);
        return ResponseEntity.ok().body(updatedseedQuery);
    }

}
