
package it.polimi.controller;

import java.util.List;
import it.polimi.model.Example;
import it.polimi.service.ExampleService;
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
@RequestMapping("/example")
public class ExampleController {

    @Autowired
    public ExampleService exampleService;
    private final static Logger log = LoggerFactory.getLogger(Example.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "example";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Example example) {
        List<Example> exampleList;
        if (example.getExampleId()!=null)
         log.info("Searching example like {}",example.toString());
        exampleList=exampleService.find(example);
        getRightMapping(exampleList);
         log.info("Search: returning {} example.",exampleList.size());
        return ResponseEntity.ok().body(exampleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{exampleId}", method = RequestMethod.GET)
    public ResponseEntity getexampleById(
        @PathVariable
        String exampleId) {
        log.info("Searching example with id {}",exampleId);
        List<Example> exampleList=exampleService.findById(java.lang.Integer.valueOf(exampleId));
        getRightMapping(exampleList);
         log.info("Search: returning {} example.",exampleList.size());
        return ResponseEntity.ok().body(exampleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{exampleId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteexampleById(
        @PathVariable
        String exampleId) {
        log.info("Deleting example with id {}",exampleId);
        exampleService.deleteById(java.lang.Integer.valueOf(exampleId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertexample(
        @RequestBody
        Example example) {
        log.info("Inserting example like {}",example.toString());
        Example insertedexample=exampleService.insert(example);
        getRightMapping(insertedexample);
        log.info("Inserted example with id {}",insertedexample.getExampleId());
        return ResponseEntity.ok().body(insertedexample);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateexample(
        @RequestBody
        Example example) {
        log.info("Updating example with id {}",example.getExampleId());
        Example updatedexample=exampleService.update(example);
        getRightMapping(updatedexample);
        return ResponseEntity.ok().body(updatedexample);
    }

    private List<Example> getRightMapping(List<Example> exampleList) {
        for (Example example: exampleList)
        {
        getRightMapping(example);
        }
        return exampleList;
    }

    private void getRightMapping(Example example) {
    }

}
