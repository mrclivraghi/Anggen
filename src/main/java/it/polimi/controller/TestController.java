
package it.polimi.controller;

import java.util.Date;
import java.util.List;

import it.polimi.model.Photo;
import it.polimi.model.Test;
import it.polimi.repository.TestRepository;
import it.polimi.service.PhotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    public TestRepository testRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "photo";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity search() {
    	List<Test> testList= testRepository.findByMyDate(new Date());
        return ResponseEntity.ok().body(testList);
    }
    
    @ResponseBody
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity searchId() {
    	List<Test> testList= testRepository.findByTestIdOrTestIdIsNull(null);
        return ResponseEntity.ok().body(testList);
    }

   
}
