
package it.polimi.controller;

import java.util.List;
import it.polimi.searchbean.ExampleSearchBean;
import it.polimi.service.ExampleService;
import it.polimi.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/example")
public class ExampleController {

    @org.springframework.beans.factory.annotation.Autowired
    private ExampleService exampleService;
    @org.springframework.beans.factory.annotation.Autowired
    private SecurityService securityService;
    private final static Logger log = LoggerFactory.getLogger(it.polimi.domain.Example.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        if (!securityService.isAllowed(it.polimi.domain.Example.entityId, it.polimi.model.domain.RestrictionType.SEARCH)) 
return "forbidden"; 

        return "example";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        ExampleSearchBean example) {
        if (!securityService.isAllowed(it.polimi.domain.Example.entityId, it.polimi.model.domain.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        List<it.polimi.domain.Example> exampleList;
        if (example.getExampleId()!=null)
         log.info("Searching example like {}",example.toString());
        exampleList=exampleService.find(example);
        getRightMapping(exampleList);
        getSecurityMapping(exampleList);
         log.info("Search: returning {} example.",exampleList.size());
        return ResponseEntity.ok().body(exampleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{exampleId}", method = RequestMethod.GET)
    public ResponseEntity getExampleById(
        @PathVariable
        String exampleId) {
        if (!securityService.isAllowed(it.polimi.domain.Example.entityId, it.polimi.model.domain.RestrictionType.SEARCH)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Searching example with id {}",exampleId);
        List<it.polimi.domain.Example> exampleList=exampleService.findById(Integer.valueOf(exampleId));
        getRightMapping(exampleList);
         log.info("Search: returning {} example.",exampleList.size());
        return ResponseEntity.ok().body(exampleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{exampleId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteExampleById(
        @PathVariable
        String exampleId) {
        if (!securityService.isAllowed(it.polimi.domain.Example.entityId, it.polimi.model.domain.RestrictionType.DELETE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Deleting example with id {}",exampleId);
        exampleService.deleteById(Integer.valueOf(exampleId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertExample(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.domain.Example example) {
        if (!securityService.isAllowed(it.polimi.domain.Example.entityId, it.polimi.model.domain.RestrictionType.INSERT)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        if (example.getExampleId()!=null)
        log.info("Inserting example like {}",example.toString());
        it.polimi.domain.Example insertedExample=exampleService.insert(example);
        getRightMapping(insertedExample);
        log.info("Inserted example with id {}",insertedExample.getExampleId());
        return ResponseEntity.ok().body(insertedExample);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateExample(
        @org.springframework.web.bind.annotation.RequestBody
        it.polimi.domain.Example example) {
        if (!securityService.isAllowed(it.polimi.domain.Example.entityId, it.polimi.model.domain.RestrictionType.UPDATE)) 
return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build(); 

        log.info("Updating example with id {}",example.getExampleId());
        rebuildSecurityMapping(example);
        it.polimi.domain.Example updatedExample=exampleService.update(example);
        getRightMapping(updatedExample);
        getSecurityMapping(updatedExample);
        return ResponseEntity.ok().body(updatedExample);
    }

    private List<it.polimi.domain.Example> getRightMapping(List<it.polimi.domain.Example> exampleList) {
        for (it.polimi.domain.Example example: exampleList)
        {
        getRightMapping(example);
        }
        return exampleList;
    }

    private void getRightMapping(it.polimi.domain.Example example) {
        if (example.getPlaceList()!=null)
        for (it.polimi.domain.Place place :example.getPlaceList())

        {

        place.setExample(null);
        }
    }

    private void rebuildSecurityMapping(it.polimi.domain.Example example) {
        if (!securityService.isAllowed(it.polimi.domain.Place.entityId, it.polimi.model.domain.RestrictionType.SEARCH))
        example.setPlaceList(exampleService.findById(example.getExampleId()).get(0).getPlaceList());
    }

    private List<it.polimi.domain.Example> getSecurityMapping(List<it.polimi.domain.Example> exampleList) {
        for (it.polimi.domain.Example example: exampleList)
        {
        getSecurityMapping(example);
        }
        return exampleList;
    }

    private void getSecurityMapping(it.polimi.domain.Example example) {
        if (example.getPlaceList()!=null && !securityService.isAllowed(it.polimi.domain.Place.entityId, it.polimi.model.domain.RestrictionType.SEARCH) )
        example.setPlaceList(null);

    }

}
