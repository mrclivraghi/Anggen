
package it.generated.controller;

import java.util.List;
import it.generated.searchbean.ExampleSearchBean;
import it.generated.service.ExampleService;
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
@RequestMapping("/example")
public class ExampleController {

    @Autowired
    public ExampleService exampleService;
    private final static Logger log = LoggerFactory.getLogger(it.generated.domain.Example.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "example";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        ExampleSearchBean example) {
        List<it.generated.domain.Example> exampleList;
        if (example.getExampleId()!=null)
         log.info("Searching example like {}",example.toString());
        exampleList=exampleService.find(example);
        getRightMapping(exampleList);
         log.info("Search: returning {} example.",exampleList.size());
        return ResponseEntity.ok().body(exampleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{exampleId}", method = RequestMethod.GET)
    public ResponseEntity getExampleById(
        @PathVariable
        String exampleId) {
        log.info("Searching example with id {}",exampleId);
        List<it.generated.domain.Example> exampleList=exampleService.findById(Integer.valueOf(exampleId));
        getRightMapping(exampleList);
         log.info("Search: returning {} example.",exampleList.size());
        return ResponseEntity.ok().body(exampleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{exampleId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteExampleById(
        @PathVariable
        String exampleId) {
        log.info("Deleting example with id {}",exampleId);
        exampleService.deleteById(Integer.valueOf(exampleId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertExample(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.domain.Example example) {
        if (example.getExampleId()!=null)
        log.info("Inserting example like {}",example.toString());
        it.generated.domain.Example insertedExample=exampleService.insert(example);
        getRightMapping(insertedExample);
        log.info("Inserted example with id {}",insertedExample.getExampleId());
        return ResponseEntity.ok().body(insertedExample);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateExample(
        @org.springframework.web.bind.annotation.RequestBody
        it.generated.domain.Example example) {
        log.info("Updating example with id {}",example.getExampleId());
        it.generated.domain.Example updatedExample=exampleService.update(example);
        getRightMapping(updatedExample);
        return ResponseEntity.ok().body(updatedExample);
    }

    private List<it.generated.domain.Example> getRightMapping(List<it.generated.domain.Example> exampleList) {
        for (it.generated.domain.Example example: exampleList)
        {
        getRightMapping(example);
        }
        return exampleList;
    }

    private void getRightMapping(it.generated.domain.Example example) {
        if (example.getPlaceList()!=null)
        for (it.generated.domain.Place place :example.getPlaceList())

        {

        place.setExample(null);
        }
    }

}
