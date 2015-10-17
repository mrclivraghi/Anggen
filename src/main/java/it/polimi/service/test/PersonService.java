
package it.polimi.service.test;

import java.util.List;
import it.polimi.model.test.Person;

public interface PersonService {


    public List<Person> findById(Long personId);

    public List<Person> find(Person person);

    public void deleteById(Long personId);

    public Person insert(Person person);

    public Person update(Person person);

}
