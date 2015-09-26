
package it.polimi.controller;

import java.util.List;
import it.polimi.model.Person;
import it.polimi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    public PersonService personService;

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "person";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @RequestBody
        Person person) {
        List<Person> personList;
        personList=personService.find(person);
        return ResponseEntity.ok().body(personList);
    }

    @ResponseBody
    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
    public ResponseEntity getpersonById(
        @PathVariable
        String personId) {
        return ResponseEntity.ok().body(personService.findById(Long.valueOf(personId)));
    }

    @ResponseBody
    @RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
    public ResponseEntity deletepersonById(
        @PathVariable
        String personId) {
        personService.deleteById(Long.valueOf(personId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertperson(
        @RequestBody
        Person person) {
        Person insertedperson=personService.insert(person);
        return ResponseEntity.ok().body(insertedperson);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateperson(
        @RequestBody
        Person person) {
        Person updatedperson=personService.update(person);
        return ResponseEntity.ok().body(updatedperson);
    }

}
