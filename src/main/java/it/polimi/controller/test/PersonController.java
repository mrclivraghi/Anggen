
package it.polimi.controller.test;

import java.util.List;
import it.polimi.model.test.Person;
import it.polimi.searchbean.test.PersonSearchBean;
import it.polimi.service.test.PersonService;
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
@RequestMapping("/person")
public class PersonController {

    @Autowired
    public PersonService personService;
    private final static Logger log = LoggerFactory.getLogger(Person.class);

    @RequestMapping(method = RequestMethod.GET)
    public String manage() {
        return "person";
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity search(
        @org.springframework.web.bind.annotation.RequestBody
        PersonSearchBean person) {
        List<Person> personList;
        if (person.getPersonId()!=null)
         log.info("Searching person like {}",person.toString());
        personList=personService.find(person);
        getRightMapping(personList);
         log.info("Search: returning {} person.",personList.size());
        return ResponseEntity.ok().body(personList);
    }

    @ResponseBody
    @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
    public ResponseEntity getpersonById(
        @PathVariable
        String personId) {
        log.info("Searching person with id {}",personId);
        List<Person> personList=personService.findById(java.lang.Long.valueOf(personId));
        getRightMapping(personList);
         log.info("Search: returning {} person.",personList.size());
        return ResponseEntity.ok().body(personList);
    }

    @ResponseBody
    @RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
    public ResponseEntity deletepersonById(
        @PathVariable
        String personId) {
        log.info("Deleting person with id {}",personId);
        personService.deleteById(java.lang.Long.valueOf(personId));
        return ResponseEntity.ok().build();
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity insertperson(
        @org.springframework.web.bind.annotation.RequestBody
        Person person) {
        if (person.getPersonId()!=null)
        log.info("Inserting person like {}",person.toString());
        Person insertedperson=personService.insert(person);
        getRightMapping(insertedperson);
        log.info("Inserted person with id {}",insertedperson.getPersonId());
        return ResponseEntity.ok().body(insertedperson);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity updateperson(
        @org.springframework.web.bind.annotation.RequestBody
        Person person) {
        log.info("Updating person with id {}",person.getPersonId());
        Person updatedperson=personService.update(person);
        getRightMapping(updatedperson);
        return ResponseEntity.ok().body(updatedperson);
    }

    private List<Person> getRightMapping(List<Person> personList) {
        for (Person person: personList)
        {
        getRightMapping(person);
        }
        return personList;
    }

    private void getRightMapping(Person person) {
    }

}
