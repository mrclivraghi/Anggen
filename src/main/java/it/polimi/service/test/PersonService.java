
package it.polimi.service.test;

import java.util.List;
import it.polimi.model.test.Person;
import it.polimi.searchbean.test.PersonSearchBean;

public interface PersonService {


    public List<Person> findById(Long personId);

    public List<Person> find(PersonSearchBean person);

    public void deleteById(Long personId);

    public Person insert(Person person);

    public Person update(Person person);

}
