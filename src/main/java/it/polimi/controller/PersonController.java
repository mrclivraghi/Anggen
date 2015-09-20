
package it.polimi.controller;

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
        return ResponseEntity.ok().body(personService.find(person));
    }

    @ResponseBody
    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
    public ResponseEntity getPersonById(
        @PathVariable
        String personId) {
        return ResponseEntity.ok().body(personService.findById(Long.valueOf(personId)));
    }

    @ResponseBody
    @RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
    public ResponseEntity deletePersonById(
        @PathVariable
        String personId) {
        personService.deleteById(Long.valueOf(personId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertPerson(
        @RequestBody
        Person person) {
        Person insertedEntity=personService.insert(person);
        return ResponseEntity.ok().body(insertedEntity);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updatePerson(
        @RequestBody
        Person person) {
        Person updatedEntity=personService.update(person);
        return ResponseEntity.ok().body(updatedEntity);
    }

}
