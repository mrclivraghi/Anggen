
package it.polimi.controller;

import java.util.List;

import it.polimi.model.Example;
import it.polimi.repository.ExampleRepository;
import it.polimi.service.ExampleService;

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
    
    @Autowired
    public ExampleRepository exampleRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
    	
    	 List<Example> exampleList;
         exampleList=exampleRepository.findByBirthTime("21:57:00");
    	
    	System.out.println("SIZE  "+exampleList.size());
        return "example";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Example example) {
        List<Example> exampleList;
        exampleList=exampleService.find(example);
        getRightMapping(exampleList);
        return ResponseEntity.ok().body(exampleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{exampleId}", method = RequestMethod.GET)
    public ResponseEntity getexampleById(
        @PathVariable
        String exampleId) {
        List<Example> exampleList=exampleService.findById(java.lang.Integer.valueOf(exampleId));
        getRightMapping(exampleList);
        return ResponseEntity.ok().body(exampleList);
    }

    @ResponseBody
    @RequestMapping(value = "/{exampleId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteexampleById(
        @PathVariable
        String exampleId) {
        exampleService.deleteById(java.lang.Integer.valueOf(exampleId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertexample(
        @RequestBody
        Example example) {
        Example insertedexample=exampleService.insert(example);
        getRightMapping(insertedexample);
        return ResponseEntity.ok().body(insertedexample);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateexample(
        @RequestBody
        Example example) {
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
