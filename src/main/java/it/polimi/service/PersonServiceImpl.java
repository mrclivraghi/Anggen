
package it.polimi.service;

import java.util.List;
import it.polimi.model.Person;
import it.polimi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl
    implements PersonService
{

    @Autowired
    public PersonRepository personRepository;

    @Override
    public List<Person> findById(Long personId) {
        return personRepository.findByPersonId(personId);
    }

    @Override
    public List<Person> find(Person person) {
        return personRepository.findByPersonIdAndFirstNameAndLastNameAndBirthDate(person.getPersonId(),person.getFirstName(),person.getLastName(),it.polimi.utils.Utility.formatDate(person.getBirthDate()));
    }

    @Override
    public void deleteById(Long personId) {
        personRepository.delete(personId);
        return;
    }

    @Override
    public Person insert(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public Person update(Person person) {
        Person returnedPerson=personRepository.save(person);
         return returnedPerson;
    }

}
